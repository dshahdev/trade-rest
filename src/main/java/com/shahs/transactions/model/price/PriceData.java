package com.shahs.transactions.model.price;

import org.json.JSONObject;

import java.util.List;

public class PriceData {
    private JSONObject meta;
    private List<Long> timestamp;
    private PriceIndicators indicators;

    public JSONObject getMeta() {
        return meta;
    }

    public void setMeta(JSONObject meta) {
        this.meta = meta;
    }

    public List<Long> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<Long> timestamp) {
        this.timestamp = timestamp;
    }

    public PriceIndicators getIndicators() {
        return indicators;
    }

    public void setIndicators(PriceIndicators indicators) {
        this.indicators = indicators;
    }
}
