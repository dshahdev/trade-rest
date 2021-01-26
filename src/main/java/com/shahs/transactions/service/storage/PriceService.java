package com.shahs.transactions.service.storage;

import com.google.gson.Gson;
import com.shahs.transactions.model.Price;
import com.shahs.transactions.model.Strdata;
import com.shahs.transactions.model.price.PriceResponse;
import com.shahs.transactions.repository.PriceRepository;
import com.shahs.transactions.repository.StrdataRepository;
import com.shahs.transactions.util.JsonReader;
import com.shahs.transactions.util.MiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

@Service
public class PriceService {
    @Autowired
    private StrdataRepository strdataRepository;

    @Autowired
    private PriceRepository priceRepository;

    public static final String DATE_FORMAT_YMD_W_DASH = "yyyy-MM-dd";
    public static final String URL = "https://query1.finance.yahoo.com/v7/finance/chart/~TICKER~?range=1d&interval=1d&indicators=quote&includeTimestamps=true";

    public boolean savePrices() {
        // get all tickers from Trade, where price for current date is not available

        List<Strdata> tickerList = strdataRepository.getAllTickers();

        // for each ticker
        tickerList.forEach(e -> {
            String ticker = e.getStrdata();
            System.out.println(ticker);
            String json = null;
            // Call yahoo link
            try {
                String url = URL.replace("~TICKER~",ticker);
                System.out.println("url: " +url);
                json = JsonReader.readJsonFromUrlAsString(url);
            } catch (IOException ioException) {
                System.out.println("error retrieving "+ ticker);

            }
            if(!json.equals(null)) {
                // ask gson to convert json data to an object
                Gson gson = new Gson();

                PriceResponse priceResponse = gson.fromJson(json, PriceResponse.class);
                System.out.println("priceResponse: "+priceResponse.getChart().getResult().get(0).getIndicators().getQuote().get(0).getVolume().get(0));
                Price p = new Price();
                p.setTicker(e.getStrdata());
                p.setPriceDate(new java.util.Date((long)priceResponse.getChart().getResult().get(0).getTimestamp().get(0)*1000));
                p.setVolume(priceResponse.getChart().getResult().get(0).getIndicators().getQuote().get(0).getVolume().get(0));
                p.setClose(priceResponse.getChart().getResult().get(0).getIndicators().getAdjclose().get(0).getAdjclose().get(0));
                p.setOpen(priceResponse.getChart().getResult().get(0).getIndicators().getQuote().get(0).getOpen().get(0));

//                if (priceRepository.existsDateAndTickerInPrice(MiscUtils.dateToString(p.getPriceDate(), DATE_FORMAT_YMD_W_DASH), ticker)) {
//                    priceRepository.delete(p);
//                }
                priceRepository.save(p);
            }
        });

            // get JSON data

            // create Price object from JSON -> Price Response Object
            // save PriceObject
        return true;
    }

}
