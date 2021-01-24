package com.shahs.transactions.service.storage;

import com.google.gson.Gson;
import com.shahs.transactions.model.*;
import com.shahs.transactions.model.yahooprice.YahooPrice;
import com.shahs.transactions.repository.*;
import com.shahs.transactions.util.JsonReader;
import com.shahs.transactions.util.MiscUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PriceService {

    @Autowired
    StrdataRepository strdataRepository;

    public final String URL="https://query1.finance.yahoo.com/v7/finance/chart/~TICKER~?range=~RANGE~&interval=1d&indicators=quote&includeTimestamps=true";

    public boolean savePrices() {
        // get all tickers
        List<Strdata> allTickers = strdataRepository.getAllTickers();

        // request one at a time
        for (Strdata s: allTickers) {

            Gson gson = new Gson();
            String url = URL.replace("~TICKER~", s.getStrdata()).replace("~RANGE~", "1d");
            String json = null;
            try {
                json = JsonReader.readStringFromUrl(url);

            } catch (IOException e) {
                System.out.println("Error retrieving price data ");
            }

            YahooPrice price = gson.fromJson(json, YahooPrice.class);
            System.out.println(s.getStrdata() + " " + price.getChart().getResult().get(0).getIndicators().getAdjclose().get(0));
        }
        // read response
        // write prices

        return true;
    }

}
