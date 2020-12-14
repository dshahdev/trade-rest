package com.shahs.transactions.controller;

import com.shahs.transactions.exception.ResourceNotFoundException;
import com.shahs.transactions.model.DateSummary;
import com.shahs.transactions.repository.DateSummaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins = "http://localhost:1200")
@RequestMapping("/api/v1")
public class DateSummaryController {
    @Autowired
    private DateSummaryRepository dateSummaryRepo;

    @GetMapping("/dateSummary")
    public List<DateSummary> getAllDateSummary() {
        return dateSummaryRepo.findAll();
    }

    @GetMapping("/dateSummary/{id}")
    public ResponseEntity<DateSummary> getDateSummaryById(@PathVariable(value = "id") Long dateSummaryId)
            throws ResourceNotFoundException {
        DateSummary dateSummary = dateSummaryRepo.findById(dateSummaryId)
                .orElseThrow(() -> new ResourceNotFoundException("dateSummary not found for this id :: " + dateSummaryId));
        return ResponseEntity.ok().body(dateSummary);
    }


    @PostMapping("/dateSummary")
    public DateSummary createDateSummary(@Valid @RequestBody DateSummary dateSummary) {
        return dateSummaryRepo.save(dateSummary);
    }

    @PutMapping("/dateSummary/{id}")
    public ResponseEntity<DateSummary> updateDateSummary(@PathVariable(value = "id") Long dateSummaryId,
                                                   @Valid @RequestBody DateSummary dateSummaryDetails) throws ResourceNotFoundException {
        DateSummary dateSummary = dateSummaryRepo.findById(dateSummaryId)
                .orElseThrow(() -> new ResourceNotFoundException("DateSummary not found for this id :: " + dateSummaryId));

        dateSummary.setSummaryDate(dateSummaryDetails.getSummaryDate());
        dateSummary.setSummaryTicker(dateSummaryDetails.getSummaryTicker());
        dateSummary.setProfitLoss(dateSummaryDetails.getProfitLoss());

        final DateSummary updatedDateSummary = dateSummaryRepo.save(dateSummary);
        return ResponseEntity.ok(updatedDateSummary);
    }

    @DeleteMapping("/dateSummary/{id}")
    public Map<String, Boolean> deleteDateSummary(@PathVariable(value = "id") Long dateSummaryId)
            throws ResourceNotFoundException {
        DateSummary dateSummary = dateSummaryRepo.findById(dateSummaryId)
                .orElseThrow(() -> new ResourceNotFoundException("dateSummary not found for this id :: " + dateSummaryId));

        dateSummaryRepo.delete(dateSummary);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
