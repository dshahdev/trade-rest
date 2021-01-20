package com.shahs.transactions.service.storage;

import com.shahs.transactions.model.*;
import com.shahs.transactions.repository.AllocRepository;
import com.shahs.transactions.repository.ConsumptionRepository;
import com.shahs.transactions.repository.ParameterRepository;
import com.shahs.transactions.repository.TradeRepository;
import com.shahs.transactions.util.MiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HelloMessageService {

    public static final String DATE_FORMAT_MDY= "MM/dd/yyyy";
    public static final String DATE_FORMAT_YMD= "yyyy/MM/dd";
    public static final String DATE_FORMAT_YMD_NO_SLASH = "yyyyMMdd";
    public static final String DATE_FORMAT_YMD_W_DASH = "yyyy-MM-dd";

    String line = "";
    String splitBy=",";

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    ConsumptionRepository consumptionRepository;

    @Autowired
    AllocRepository allocRepository;

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
                sysParams.setLastDate(MiscUtils.stringToDate(p.getParamValue(),DATE_FORMAT_MDY));
            }
        });

    }

    public void SaveParameters() {
        Parameter ps = new Parameter();
        ps.setParamKey("lastDate");
        ps.setParamValue(sysParams.getLastDateStr());
        parameterRepository.save(ps);
    }
    public boolean checkDate(Date date) {

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
    public List<Consumption> loadConsumption() {
        List<Consumption> consumption = new ArrayList<Consumption>();;
        consumptionRepository.findAllConsumptionData().forEach(c -> {
            System.out.println("consumption: "+ c.toString());
            consumption.add(c);
        });

        return consumption;
    }
    public void printCsvFile(String inputPath, String csvFile) throws IOException{
        Date runDate = MiscUtils.stringToDate(csvFile.substring(10,18), DATE_FORMAT_YMD_NO_SLASH);
        String dashDate = csvFile.substring(10,14) +  "-" + csvFile.substring(14,16) + "-" + csvFile.substring(16,18);
//        java.util.Date dashDate = MiscUtils.stringToDate(csvFile.substring(10,18), DATE_FORMAT_YMD_W_DASH);
        System.out.println(csvFile.substring(10,18));
        // load parameters so we have parameter last date
        LoadParameters();

        // check date passed against parameter date
        // if date passed is less than or equal to parameter date then ERROR date already loaded, cannot re-load the same date

        checkDate(runDate);
        if(!checkDate(runDate)) { return; }

        // load trades here i.e. calculate ...
        loadTrades(inputPath + csvFile);
//        loadTrades(csvFile);

        generateAlloc(dashDate);

        // save the date that was just run
        sysParams.setLastDateStr(MiscUtils.dateToString(runDate,DATE_FORMAT_MDY));
        sysParams.setLastDate(runDate);
        SaveParameters();

    }
    public void generateAlloc(String dashDate) {
        // generate alloc data
        List<Alloc> allocList = new ArrayList<Alloc>();

        // load consumption data
        List<Consumption> consumptionsData = loadConsumption();
        System.out.println("consumptionsData: " + consumptionsData);

        // load sell trades for today's date

        List<Trade> tradeList = tradeRepository.findSellTradesForDate(dashDate);
        tradeList.forEach(td -> {
            System.out.println("sell entry: "+ td);
            //this sell needs to be allocated
            // what we have -- quantity to allocate
            // find available quantity from consumption data
            int qtyToAllocate = td.getQuantity();
            int allocatedQty = 0;
            for(Consumption c: consumptionsData) {
                if(c.getTicker().equals(td.getTicker()) && c.getAvailableQty() > 0) {
                    if(c.getAvailableQty() >= qtyToAllocate) {

                        allocatedQty += qtyToAllocate;

                        Alloc a = new Alloc();
                        a.setAllocatedQty(qtyToAllocate);
                        a.setBuyTradeId(c.getId());
                        a.setSellTradeId(td.getId());

                        allocList.add(a);

                        c.setAllocatedQty(qtyToAllocate);
                        c.setAvailableQty(c.getOriginalQty() - qtyToAllocate);

                    } else if(c.getAvailableQty() < qtyToAllocate) {

                        allocatedQty += c.getAvailableQty();
                        qtyToAllocate = qtyToAllocate - c.getAvailableQty();

                        Alloc a = new Alloc();
                        a.setAllocatedQty(c.getAvailableQty());
                        a.setBuyTradeId(c.getId());
                        a.setSellTradeId(td.getId());

                        allocList.add(a);

                        c.setAllocatedQty(c.getAvailableQty());
                        c.setAvailableQty(c.getOriginalQty() - c.getAvailableQty());
                    }
                }
                if(qtyToAllocate == 0) {
                    break;
                }
            };
        });

        allocList.forEach(allist -> {
            System.out.println("saving alloc record: "+allist);
            allocRepository.save(allist);
        });


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
            if(!(arr[1].equals("Buy") || arr[1].equals("Sell") || arr[1].equals("Sell Short"))){
                continue;
            }
            System.out.println("Line1: "+ line1);

            current = Trade.createTradeObj(arr);
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
