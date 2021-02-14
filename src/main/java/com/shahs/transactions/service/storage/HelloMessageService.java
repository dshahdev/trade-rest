package com.shahs.transactions.service.storage;

import com.shahs.transactions.model.*;
import com.shahs.transactions.repository.*;
import com.shahs.transactions.util.MiscUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class HelloMessageService {

    public static final String DATE_FORMAT_MDY= "MM/dd/yyyy";
    public static final String DATE_FORMAT_YMD= "yyyy/MM/dd";
    public static final String DATE_FORMAT_YMD_NO_SLASH = "yyyyMMdd";
    public static final String DATE_FORMAT_YMD_W_DASH = "yyyy-MM-dd";

    public enum ConsumeMethod {
        FIFO,
        LIFO,
        AVERAGE;
    }

    public enum StrategyEnum {
        DAY_TRADE("DAY_TRADE"),
        ONH("ONH"),
        SIDE_BET("SIDE_BET"),
        SWING("SWING"),
        NONE("NONE");

        private final String text;

        StrategyEnum(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }

    public static ConsumeMethod costingMethod = ConsumeMethod.LIFO;

    String line = "";
    String splitBy=",";

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    AllocRepository allocRepository;

    @Autowired SwingAlertRepository swingAlertRepository;

    SystemParameters sysParams = null;


    // lastAllocatedSellId

    public void LoadParameters()  {
        System.out.println("1) In loadparams: "+parameterRepository.findAll());
        Iterable<Parameter> param = parameterRepository.findAll();

        sysParams = new SystemParameters();

        // fill sysParams:
        param.forEach( p-> {
            if (p.getParamKey().equals("lastDate")) {
                System.out.println("loaded parmas: "+ p);
                sysParams.setLastDateStr(p.getParamValue());
                sysParams.setLastDate(MiscUtils.stringToSqlDate(p.getParamValue(),DATE_FORMAT_MDY));
            } else if (p.getParamKey().startsWith("RenameTicker")) {
                System.out.println("loaded params: " + p);
                String[] arr = p.getParamValue().split("~");
                if (arr.length >= 2) {
                    sysParams.getRenamedTickerMap().put(arr[0], arr[1]);
                }
            }
        });

    }

    public void SaveParameters() {
        Parameter ps = new Parameter();
        ps.setParamKey("lastDate");
        ps.setParamValue(sysParams.getLastDateStr());
        parameterRepository.save(ps);
    }

    public boolean checkDate(java.sql.Date date) {

        if (sysParams.getLastDateStr().equals("")) {    // this means nothing is loaded in sysParams variable
            sysParams.setLastDateStr(MiscUtils.dateToString(date,DATE_FORMAT_MDY));
            sysParams.setLastDate(date);
        } else {
            if (sysParams.getLastDate().after(date) || sysParams.getLastDate().equals(date)) {
                System.out.println("date already loaded in the system, cannot load again");
                return false;
            }
        }
        return  true;

    }

    public void printCsvFile(String inputPath, String csvFile) throws IOException{
        java.sql.Date runDate = MiscUtils.stringToSqlDate(csvFile.substring(10,18), DATE_FORMAT_YMD_NO_SLASH);
        String dashDate = csvFile.substring(10,14) +  "-" + csvFile.substring(14,16) + "-" + csvFile.substring(16,18);
        String noDashDate = csvFile.substring(10,18);

        System.out.println(csvFile.substring(10,18));
        // load parameters so we have parameter last date
        LoadParameters();

        // check date passed against parameter date
        // if date passed is less than or equal to parameter date then ERROR date already loaded, cannot re-load the same date

        checkDate(runDate);
        if(!checkDate(runDate)) { return; }

        // load trades here i.e. calculate ...
        loadTrades(inputPath + csvFile);

        generateAlloc(dashDate, runDate);

        // save the date that was just run
        sysParams.setLastDateStr(MiscUtils.dateToString(runDate,DATE_FORMAT_MDY));
        sysParams.setLastDate(runDate);
        SaveParameters();

    }

    public List<Alloc> consumeSell(String ticker, Trade t, HashMap<String, List<Position>> positionHashMap,
                                   ConsumeMethod method, HashMap<Long, Trade> openTradesMap, DateTime priorDate,
                                   HashMap<String, List<SwingAlert>> swingAlertMap,
                                   HashMap<Long, Trade> todaysBuyTradesMap) {

        List<Alloc> allocList = new ArrayList<Alloc>();

        if (!positionHashMap.containsKey(ticker)) {
            System.out.println("No positions available for ticker " + ticker + " trade: " + t.toString());
            return allocList;
        }

        List<Position> positionList = positionHashMap.get(ticker);
        Collections.sort(positionList);

        int toAlloc = t.getQuantity();

        for ( Position p: positionList) {
            if (p.getAvailableQty() <= 0) { continue; }
            if (p.getBuyId() > t.getId()) { continue; }

            int toUse = (p.getAvailableQty() < toAlloc ? p.getAvailableQty(): toAlloc);

            StrategyEnum strategy = StrategyEnum.NONE;

            boolean isTodaysBuyTrade = todaysBuyTradesMap.containsKey(p.getBuyId());
            boolean isSODPositionTrade = openTradesMap.containsKey(p.getBuyId());

            Trade buyTrade = isSODPositionTrade ? openTradesMap.get(p.getBuyId()) : (isTodaysBuyTrade ? todaysBuyTradesMap.get(p.getBuyId()) : null);

            boolean hasSwingAlert = swingAlertMap.containsKey(ticker);
            boolean boughtPriorMarketDay = isSODPositionTrade && buyTrade.getDate().equals(priorDate.toDate()) && !hasSwingAlert;

            strategy = isTodaysBuyTrade ? StrategyEnum.DAY_TRADE: (boughtPriorMarketDay ? StrategyEnum.ONH: (hasSwingAlert ? SwingAlert.TradeWithinSwingDates(swingAlertMap.get(ticker), buyTrade, t, 2): StrategyEnum.SIDE_BET));

            allocList.add(new Alloc(t.getId(), p.getBuyId(), toUse, strategy.toString()));
            p.setAllocatedQty(p.getAllocatedQty() + toUse);
            p.setAvailableQty(p.getAvailableQty() - toUse);
            toAlloc -= toUse;

            if (toAlloc == 0) { break; }
        }

        return allocList;
    }

    public void generateAlloc(String dashDate, java.sql.Date dateDate) {

        // get Yesterday's EOD positions (SOD positions for today)
        List<Position> yesterdaysPositions = positionRepository.findSODPositionsForDate(dashDate);
        List<Trade> openTradesForDate = tradeRepository.findBuyTradesForOpenPositions(dashDate);

        List<SwingAlert> alertList = swingAlertRepository.getAllSwingAlerts();
        HashMap<String, List<SwingAlert>> swingAlertMap = new HashMap<String, List<SwingAlert>>();
        for (SwingAlert swingAlert : alertList) {
            String key = swingAlert.getTicker();
            if (!swingAlertMap.containsKey(key)) {
                swingAlertMap.put(key, new ArrayList<SwingAlert>());
            }
            swingAlertMap.get(key).add(swingAlert);
        }

        // add Buy Positions to current Positions for yesterday
        List<Trade> tradeList = tradeRepository.findTradesForDateByAction(dashDate, "Buy");

        // create new positions to be saved today; combine yesterday's eod positions plus today's buy positions
        List<Position> todaysPositions = new ArrayList<Position>();

        DateTime priorDate = (new DateTime(dateDate)).minusDays(1);
        if (yesterdaysPositions.size() >= 1) {
            priorDate = new DateTime(yesterdaysPositions.get(0).getPositionDate());
        }

        yesterdaysPositions.forEach( p -> { todaysPositions.add(Position.newInstance(p, dateDate)); });

        tradeList.forEach( t -> {
            todaysPositions.add(new Position(t.getId(), dateDate, t.getTicker(), t.getQuantity(), t.getQuantity(), 0));
        });

        // alloc Sell positions to current positions that are updated with today's buys
        // save positions and alloc records
        HashMap<String, List<Position>> positionHashMap = new HashMap<String, List<Position>>();
        HashMap<Long, Trade> openTradesMap = new HashMap<Long, Trade>();
        HashMap<Long, Trade> todaysBuyTradesMap = new HashMap<Long, Trade>();

        // need to keep SOD positions and today's buys in openTradesMap list
        openTradesForDate.forEach( t -> openTradesMap.put(t.getId(), t));
        tradeList.forEach(t -> todaysBuyTradesMap.put(t.getId(), t));

        todaysPositions.forEach(p -> {
            if (!positionHashMap.containsKey(p.getTicker())) {
                positionHashMap.put(p.getTicker(), new ArrayList<Position>());
            }
            positionHashMap.get(p.getTicker()).add(p);
        });

        System.out.println("today's Positions: " + todaysPositions);

        List<Alloc> allocList = new ArrayList<Alloc>();

        // load sell trades for today's date
        List<Trade> sellTrades = tradeRepository.findTradesForDateByAction(dashDate, "Sell");
        DateTime finalPriorDate = priorDate;
        sellTrades.forEach(td -> {
            System.out.println("sell entry: "+ td);
            allocList.addAll(consumeSell(td.getTicker(), td, positionHashMap, ConsumeMethod.LIFO, openTradesMap, finalPriorDate, swingAlertMap, todaysBuyTradesMap));
        });

        for (Alloc a: allocList) {
            System.out.println("saving alloc: " + a);
            allocRepository.save(a);
        }

        for (Position p: todaysPositions) {
            System.out.println("saving position: " + p);
            if (p.getAvailableQty() > 0) {
                positionRepository.save(p);
            }
        }

    }

    //Trades file
    public void loadTrades(String csvFile) throws IOException {
        Map<String, Double> tradeMap = new ConcurrentHashMap<String, Double>();

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line = br.readLine();
        String something = br.readLine();
        System.out.println(line + " " + something);
        String line1 = "";
        Trade prior = null;
        boolean priorPending = false;
        Trade current = null;

        while((line1 = br.readLine()) != null) {

            String[] arr = line1.replace("\"","")
                            .replace("$","")
                            .split(",");

            arr[1] = arr[1].equals("Sell Short") ? "Sell" : arr[1];
            if(!(arr[1].equals("Buy") || arr[1].equals("Sell"))){
                continue;
            }
            System.out.println("Line1: "+ line1);

            current = Trade.createTradeObj(arr);
            if (sysParams.getRenamedTickerMap().containsKey(current.getTicker())) {
                // override ticker value to renamed ticker (renamed tickers are stored in the parameter table);
                current.setTicker(sysParams.getRenamedTickerMap().get(current.getTicker()));
            }
            if (prior == null) { prior = current; continue; }

            if (canMerge(current, prior)) {
                prior = mergeTrades(current, prior);
                current = null;
                priorPending = true;
                continue;
            }

            System.out.println("final trade: "+prior.toString());
            tradeRepository.save(prior);
            prior = current;
        }

        if (priorPending) {
            tradeRepository.save(prior);
        }
        if (current != null) {
            tradeRepository.save(current);
        }

       }

    public boolean canMerge(Trade current, Trade prior) {
    if (current.getDate().equals(prior.getDate()) &&
            current.getTicker().equals(prior.getTicker()) &&
            current.getAction().equals(prior.getAction()) &&
            Math.abs(current.getPrice() - prior.getPrice()) <= 0.25) {
        return true;
    }
    return false;
    }

    public Trade mergeTrades(Trade current, Trade prior) {
        prior.addSameLotTrade(current);
        return prior;
    }


}
