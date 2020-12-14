package com.shahs.transactions.controller;


import com.shahs.transactions.exception.ResourceNotFoundException;
import com.shahs.transactions.model.DateSummary;
import com.shahs.transactions.model.TickerSummary;
import com.shahs.transactions.model.Trade;
import com.shahs.transactions.repository.DateSummaryRepository;
import com.shahs.transactions.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class TickerSummaryController {
    @Autowired
    private TradeRepository tradeRepo;

    @Autowired
    private DateSummaryRepository dateSummaryRepo;
//    private Object TickerSummary;

    @GetMapping("/tickersummary")
    public List<TickerSummary> getAllTickers() {
        // we are retrieving all DateSummary records
        // and Trade records
        // and making TikerSummary object array here

        // first -- read DateSummary Records
        List<DateSummary> dateSummaryList = dateSummaryRepo.findAll();


        // second -- read all Trade records
        List<Trade> tradeList = tradeRepo.findAll();

        // loop through date Summary records

            // find all coresponding trades for DateSummary.Date and DateSummary.ticker
            // add to the TickerSummary object;
        List<TickerSummary> tsList= new ArrayList<TickerSummary>();
        for (final DateSummary ds: dateSummaryList) {

            TickerSummary ts = new TickerSummary();
            ts.setDate(ds.getSummaryDate());
            ts.setTicker(ds.getSummaryTicker());
            ts.setProfit_loss(ds.getProfitLoss());

            ArrayList<Trade> tt = new ArrayList<Trade>();

            for(final Trade td: tradeList) {
                if(ts.getDate().equals(td.getDate()) && ts.getTicker().equals(td.getTicker())){
                    tt.add(td);
                }
            }
            ts.setTrades(tt);
            tsList.add(ts);
        }
        return tsList;
    }



    @PostMapping("/tickerSummaryByDate")
    public List<TickerSummary> getTickerSummaryByDate(@Valid @RequestBody String date) {
        //getting all dateSummary data
        List<DateSummary> dateSummaryList = dateSummaryRepo.findAll();
        //getting all trades data
        List<Trade> tradeList = tradeRepo.findAll();
        List<TickerSummary> tsList = new ArrayList<TickerSummary>();

        for(final DateSummary ds: dateSummaryList) {
            System.out.println("ds.getSummaryDate():"+ds.getSummaryDate()+" date:"+date);
           if(ds.getSummaryDate().equals(date)) {
               TickerSummary ts = new TickerSummary();
               ts.setProfit_loss(ds.getProfitLoss());
               ts.setTicker(ds.getSummaryTicker());
               ts.setDate(ds.getSummaryDate());

               ArrayList<Trade> tt = new ArrayList<Trade>();
               for(final Trade td: tradeList) {
                   if(ds.getSummaryDate().equals(td.getDate()) && (ds.getSummaryTicker().equals(td.getTicker()))) {
                       tt.add(td);
                   }
               }
               ts.setTrades(tt);
               tsList.add(ts);
           } else {
               System.out.print("ummatched date");
           }

        }

        return tsList;
    }





}
