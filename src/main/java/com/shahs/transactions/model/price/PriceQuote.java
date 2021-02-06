package com.shahs.transactions.model.price;

import java.util.List;

public class PriceQuote {
    private List<String> volume;
    private List<String> open;
    private List<String> close;
    private List<String> high;
    private List<String> low;

    public List<String> getVolume() {
        return volume;
    }

    public void setVolume(List<String> volume) {
        this.volume = volume;
    }

    public List<String> getOpen() {
        return open;
    }

    public void setOpen(List<String> open) {
        this.open = open;
    }

    public List<String> getClose() {
        return close;
    }

    public void setClose(List<String> close) {
        this.close = close;
    }

    public List<String> getHigh() {
        return high;
    }

    public void setHigh(List<String> high) {
        this.high = high;
    }

    public List<String> getLow() {
        return low;
    }

    public void setLow(List<String> low) {
        this.low = low;
    }
}
