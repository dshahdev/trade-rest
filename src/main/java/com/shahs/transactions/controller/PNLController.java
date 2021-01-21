package com.shahs.transactions.controller;

import com.shahs.transactions.model.PNLDateSummary;
import com.shahs.transactions.model.PNLTickerSummary;
import com.shahs.transactions.model.Pnl;
import com.shahs.transactions.repository.PNLDateRepository;
import com.shahs.transactions.repository.PNLRepository;
import com.shahs.transactions.repository.PNLTickerRepository;
import com.shahs.transactions.util.MiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<PNLTickerSummary>findPnlByTicker() {
        System.out.println("in find all pnl by Ticker ");
        return pnlTickerRepository.findPnlByTicker();
    }
    // pnlForDateByTicker -- will give data for one date -- grouped by ticker and sum of pnl

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





}
