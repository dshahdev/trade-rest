package com.shahs.transactions.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.shahs.transactions.model.yahooprice.YahooPrice;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String readStringFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAll(rd);
        } finally {
            is.close();
        }
    }

    public static void showJSON(JSONObject jsonObj) {
        jsonObj.keySet().forEach(keyStr ->
        {
            Object keyvalue = jsonObj.get(keyStr);
            System.out.println("key: "+ keyStr + " value: " + keyvalue);

        });
    }

    public static void main(String[] args) throws IOException, JSONException {
//        JSONObject json = readJsonFromUrl("https://query1.finance.yahoo.com/v7/finance/chart/AAPL?range=1y&interval=1d&indicators=quote&includeTimestamps=true");
//        System.out.println(json.toString());
//        showJSON(json);

        String json = readJsonFromUrl("https://query1.finance.yahoo.com/v7/finance/chart/AAPL?range=1y&interval=1d&indicators=quote&includeTimestamps=true").toString();
        Gson gson = new Gson();
        YahooPrice price = gson.fromJson(json, YahooPrice.class);
        System.out.println(price.getChart().getResult().get(0).getIndicators().getAdjclose().get(0));
    }
}