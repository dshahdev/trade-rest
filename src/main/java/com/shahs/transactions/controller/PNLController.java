package com.shahs.transactions.controller;

import com.shahs.transactions.model.*;
import com.shahs.transactions.repository.*;
import com.shahs.transactions.util.MiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins ={"http://127.0.0.1:4200",
                        "http://192.168.1.103:4200",
                        "http://192.168.1.103:8080",
                        "http://169.254.10.157:4200",
                        "http://localhost:4200"})

@RequestMapping("/api/v1")
public class PNLController {

    public static final String DATE_FORMAT_MDY= "MM/dd/yyyy";
    public static final String DATE_FORMAT_YMD= "yyyy/MM/dd";
    public static final String DATE_FORMAT_YMD_NO_SLASH = "yyyyMMdd";

    @Autowired private PNLRepository pnlRepository;
    @Autowired private PNLDateRepository pnlDateRepository;
    @Autowired private PNLTickerRepository pnlTickerRepository;
    @Autowired private PositionViewRepository positionViewRepository;
    @Autowired private DailyPositionSummViewRepository dailyPositionSummViewRepository;

    @GetMapping("/pnl")
    public List<Pnl> getAllPnl() {
        System.out.println("in find all pnl ");
        return pnlRepository.findAll();

    }

    @PostMapping("/pnlForDate")
    public List<Pnl> getPnlForDate(@Valid @RequestBody String date) {
        return pnlRepository.findPnlForDate(date);
    }

    @PostMapping("/pnlForTicker")
    public List<Pnl> getPnlForTicker(@Valid @RequestBody String ticker) {
        return pnlRepository.findPnlForTicker(ticker);
    }

    // to do

    // pnlByDate -- will give data for all dates -- grouped by date and sum of pnl
    @GetMapping("/pnlByDate")
    public List<PNLDateSummary> findPnlByDate() {
        System.out.println("in find all pnl by Date ");
        return pnlDateRepository.findPnlByDate();
    }
    // pnlByTicker -- will give data for all dates -- grouped by Ticker and sum of pnl
    @GetMapping("/pnlByTicker")
    public List<PNLTickerSummary> findPnlByTicker() {
        System.out.println("in find all pnl by Ticker ");
        return pnlTickerRepository.findPnlByTicker();
    }

    @GetMapping("/tradesByDatePnl")
    public List<PnlDateWithDetail> getTradesByDatePnl() {
        List<PNLDateSummary> pnlByDate = pnlDateRepository.findPnlByDate();
        List<Pnl> pnls = pnlRepository.findAll();
        //creating a map
        HashMap<Date, List<Pnl>> map = new HashMap<Date,List<Pnl>>();

        for(Pnl d: pnls){
            Date key = d.getDate();
            if(!map.containsKey(key)) {
                map.put(key, new ArrayList<Pnl>());
            }
            map.get(key).add(d);
        }
        List<PnlDateWithDetail> ret = new ArrayList<PnlDateWithDetail>();
        for (PNLDateSummary pds: pnlByDate) {
            PnlDateWithDetail pdwd = new PnlDateWithDetail();
            pdwd.setDate(pds.getDate());
            pdwd.setPnl(pds.getPnl());

            pdwd.setPnlTrade(map.get(pdwd.getDate()));
            System.out.println("pdwd....."+pdwd.toString());
            ret.add(pdwd);
        }
        return ret;

    }

    @GetMapping("/tradesByTickerPnl")
    public List<PnlTickerWithDetail> getTradesByTickerPnl() {
        List<PNLTickerSummary> pnlByTicker = pnlTickerRepository.findPnlByTicker();
        List<Pnl> pnls = pnlRepository.findAll();

        //creating Map
        HashMap<String, List<Pnl>> map = new HashMap<String, List<Pnl>>();
        //collect all pnl tickerwise
        for(Pnl p: pnls) {
            String key = p.getTicker();
            if(!map.containsKey(key)) {
                map.put(key, new ArrayList<Pnl>());
            }
            map.get(key).add(p);
        }
        List<PnlTickerWithDetail> ret = new ArrayList<PnlTickerWithDetail>();
        for(PNLTickerSummary pts: pnlByTicker){
            PnlTickerWithDetail ptwd = new PnlTickerWithDetail();
            ptwd.setTicker(pts.getTicker());
            ptwd.setPnl(pts.getPnl());

            ptwd.setPnlTrade(map.get(ptwd.getTicker()));
            System.out.println("ptwd....."+ptwd.toString());
            ret.add(ptwd);
        }
        return ret;
     }



    @PostMapping("/pnlForDateByTicker")
    public List<PNLTickerSummary>findPnlForDateByTicker(@Valid @RequestBody String date) {
        System.out.println("in find all pnl for date  by ticker ");
        return pnlTickerRepository.findPnlForDateByTicker(MiscUtils.stringToDate(date, DATE_FORMAT_YMD_NO_SLASH));
    }
    // pnlForTickerByDate -- will give data for one Ticker -- grouped by date and sum of pnl


    @PostMapping("/pnlForTickerByDate")
    public List<PNLDateSummary>findPnlForTickerByDate(@Valid @RequestBody String ticker) {
        System.out.println("in find all pnl for Ticker  by date ");
        return pnlDateRepository.findPnlForTickerByDate(ticker);
    }

    @PostMapping("/pnlForMonthByTicker")
    public List<PNLTickerSummary>findPnlForMonthByTicker(@Valid @RequestBody String monthstr) {
        System.out.println("in find all pnl for Month  by ticker " + monthstr);
        return pnlTickerRepository.findPnlForMonthByTicker(monthstr);
    }

    @PostMapping("/pnlForMonthByDate")
    public List<PNLDateSummary>findPnlForMonthByDate(@Valid @RequestBody String monthstr) {
        System.out.println("in find all pnl for Month  by date " + monthstr);
        return pnlDateRepository.findPnlForMonthByDate(monthstr);
    }

    @PostMapping("/positionsForDate")
    public List<PositionView>getPositionsForDate(@Valid @RequestBody String positionDate) {
        System.out.println("in find all pnl for Month  by date " + positionDate);
        return positionViewRepository.getPositionsForDate(positionDate);
    }
    @GetMapping("/dailyPositionSummary")
    public List<DailyPositionSummView>dailyPositionSummary() {
        System.out.println("in find all position summary ");
        return dailyPositionSummViewRepository.getDailyPositionSummary();
    }



}

