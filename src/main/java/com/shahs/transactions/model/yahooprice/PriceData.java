package com.shahs.transactions.model.yahooprice;

import org.json.JSONObject;

import java.util.List;

public class PriceData {

    private JSONObject meta;
    private List<Long> timestamp;
    private PriceIndicator indicators;

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

    public PriceIndicator getIndicators() {
        return indicators;
    }

    public void setIndicators(PriceIndicator indicators) {
        this.indicators = indicators;
    }
}
