package com.shahs.transactions.model.price;

import java.util.List;

public class PriceIndicators {
    private List<PriceQuote> quote;
    private List<PriceAdjClose> adjclose;

    public List<PriceQuote> getQuote() {
        return quote;
    }

    public void setQuote(List<PriceQuote> quote) {
        this.quote = quote;
    }

    public List<PriceAdjClose> getAdjclose() {
        return adjclose;
    }

    public void setAdjclose(List<PriceAdjClose> adjclose) {
        this.adjclose = adjclose;
    }
}
