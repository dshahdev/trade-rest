package com.shahs.transactions.controller;

import com.shahs.transactions.service.storage.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins ={"http://127.0.0.1:4200",
        "http://192.168.1.103:4200",
        "http://192.168.1.103:8080",
        "http://169.254.10.157:4200",
        "http://localhost:4200"})

@RequestMapping("/api/v1")
public class PriceController {

    @Autowired
    PriceService priceService;

    @GetMapping("/savePrices")
    public boolean savePrices() {
        return priceService.savePrices();
    }
}
