package com.shahs.transactions.service.storage;

import com.google.gson.Gson;
import com.shahs.transactions.model.Price;
import com.shahs.transactions.model.Strdata;
import com.shahs.transactions.model.price.PriceResponse;
import com.shahs.transactions.repository.PriceRepository;
import com.shahs.transactions.repository.StrdataRepository;
import com.shahs.transactions.repository.TradeDao;
import com.shahs.transactions.util.JsonReader;
import com.shahs.transactions.util.MiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceService {
    @Autowired
    private StrdataRepository strdataRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private TradeDao tradeDao;

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

                int len = priceResponse.getChart().getResult().get(0).getTimestamp().size();
                for (int i = 0; i < len; i++) {
                    System.out.println("priceResponse: "+priceResponse.getChart().getResult().get(0).getIndicators().getQuote().get(0).getVolume().get(0));
                    System.out.println(Long.parseLong(priceResponse.getChart().getResult().get(0).getTimestamp().get(i)) + " " + priceResponse.getChart().getResult().get(0).getTimestamp().get(i));

                    Price p = new Price();
                    Long l =  Long.parseLong(priceResponse.getChart().getResult().get(0).getTimestamp().get(i)) * 1000L;
                    p.setTicker(e.getStrdata());
                    p.setPriceDate(new java.sql.Date(l));

                    String vol = priceResponse.getChart().getResult().get(0).getIndicators().getQuote().get(0).getVolume().get(i);
                    String close = priceResponse.getChart().getResult().get(0).getIndicators().getAdjclose().get(0).getAdjclose().get(i);
                    String open = priceResponse.getChart().getResult().get(0).getIndicators().getQuote().get(0).getOpen().get(i);

                    p.setVolume(vol == null || vol.equals("null") ? 0: Long.parseLong(vol));
                    p.setClose(close == null || close.equals("null") ? 0: Double.parseDouble(close));
                    p.setOpen(open == null || open.equals("null") ? 0: Double.parseDouble(open));

                    System.out.println(" Price: " + p.getPriceDate() + " " );

                    tradeDao.addUpdatePrice(p);
                }

            }
        });

            // get JSON data

            // create Price object from JSON -> Price Response Object
            // save PriceObject
        return true;
    }

}
