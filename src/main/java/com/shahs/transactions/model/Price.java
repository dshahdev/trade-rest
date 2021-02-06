package com.shahs.transactions.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="price")
@IdClass(PriceCompositeKey.class)
public class Price {
    @Id
    private Date priceDate;
    @Id
    private String ticker;
    private double open;
    private double close;
    private long volume;

    public Price() {}

    public Price( Date date, String ticker, double open, double close, long volume) {
        this.priceDate = date;
        this.ticker = ticker;
        this.open = open;
        this.close = close;
        this.volume = volume;
    }
    @Column(name="price_date", nullable = false)
    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date date) {
        this.priceDate = date;
    }
    @Column(name="ticker", nullable = false)
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    @Column(name="open", nullable = false)
    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }
    @Column(name="close", nullable = false)
    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }
    @Column(name="volume", nullable = false)
    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {

        String p =
                " Date: " + this.getPriceDate() +
                " ticker: " + this.getTicker() +
                " open: " + this.getOpen() +
                " close: " + this.getClose() +
                " volume: " + this.getVolume();

        return p;
    }

}
