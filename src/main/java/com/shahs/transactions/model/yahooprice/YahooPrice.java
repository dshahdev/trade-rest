package com.shahs.transactions.model.yahooprice;

import org.json.JSONObject;

import java.util.List;

public class YahooPrice {
    private List<PriceData> result;
    private JSONObject error;

    public List<PriceData> getResult() {
        return result;
    }

    public void setResult(List<PriceData> result) {
        this.result = result;
    }

    public JSONObject getError() {
        return error;
    }

    public void setError(JSONObject error) {
        this.error = error;
    }
}
