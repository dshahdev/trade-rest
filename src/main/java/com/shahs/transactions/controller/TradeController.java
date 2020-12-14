package com.shahs.transactions.controller;

import com.shahs.transactions.exception.ResourceNotFoundException;
import com.shahs.transactions.model.Trade;
import com.shahs.transactions.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins ={"http://localhost:4200","http://localhost:1200"})

@RequestMapping("/api/v1")
public class TradeController {
    @Autowired
    private TradeRepository tradeRepository;

    @GetMapping("/trades")
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    @GetMapping("/trades/{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable(value = "id") Long tradeId)
            throws ResourceNotFoundException {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not found for this id :: " + tradeId));
        return ResponseEntity.ok().body(trade);
    }

    @PostMapping("/trades")
    public Trade createTrade(@Valid @RequestBody Trade trade) {
        return tradeRepository.save(trade);
    }

    @PutMapping("/trades/{id}")
    public ResponseEntity<Trade> updateTrade(@PathVariable(value = "id") Long tradeId,
                                                   @Valid @RequestBody Trade tradeDetails) throws ResourceNotFoundException {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + tradeId));

        trade.setDate(tradeDetails.getDate());
        trade.setAction(tradeDetails.getAction());
        trade.setTicker(tradeDetails.getTicker());
        trade.setQuantity(tradeDetails.getQuantity());
        trade.setPrice(tradeDetails.getPrice());
        trade.setFees(tradeDetails.getFees());
        trade.setAmount(tradeDetails.getAmount());


        final Trade updatedTrade = tradeRepository.save(trade);
        return ResponseEntity.ok(updatedTrade);
    }

    @DeleteMapping("/trades/{id}")
    public Map<String, Boolean> deleteTrade(@PathVariable(value = "id") Long tradeId)
            throws ResourceNotFoundException {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not found for this id :: " + tradeId));

        tradeRepository.delete(trade);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
