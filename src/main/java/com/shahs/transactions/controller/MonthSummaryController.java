package com.shahs.transactions.controller;

import com.shahs.transactions.model.PNLDateSummary;
import com.shahs.transactions.model.PNLMonthSummary;
import com.shahs.transactions.model.Strdata;
import com.shahs.transactions.repository.MonthSummaryRepository;
import com.shahs.transactions.repository.PNLDateRepository;
import com.shahs.transactions.repository.StrdataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins ={"http://127.0.0.1:4200",
                        "http://192.168.1.103:4200",
                        "http://192.168.1.103:8080",
                        "http://169.254.10.157:4200",
                        "http://localhost:4200"})

@RequestMapping("/api/v1")
public class MonthSummaryController {
    @Autowired
    private MonthSummaryRepository monthSummaryRepository;

    @Autowired
    private PNLDateRepository pnlDateRepository;

    @Autowired
    private StrdataRepository strdataRepository;

    @GetMapping("/monthlySummary")
    public List<PNLMonthSummary> getAllMonthSummary() {
        return monthSummaryRepository.getAllMonthSummary();
    }

    @GetMapping("/monthList")
    public List<Strdata> getAllMonths() { return strdataRepository.getAllMonths();}

    @GetMapping("/monthlyPnlList")
    public List<PNLMonthSummary> getMonthlyPnl() { return monthSummaryRepository.getPnlMonthlySummary();}


    @GetMapping("/pnlForAllDates")
    public List<PNLDateSummary> getPnlForAllMonths() {
        try {
            Thread.sleep(30000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pnlDateRepository.getPnlForAllDates();}

}
