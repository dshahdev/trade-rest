package com.shahs.transactions.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


@Entity
@Table(name="price")
@IdClass(PriceCompositeKey.class)
public class Price {
    private Date priceDate;
    private String ticker;

    private BigInteger volume;
    private double open;
    private double close; 


    public Price() {}

    public Price(Date priceDate, String ticker, double open, double close, BigInteger volume) {
        this.priceDate = priceDate;
        this.ticker = ticker;
        this.open = open;
        this.close = close;
        this.volume = volume;
    }

    @Id
    @Column(name="price_date", nullable = false)
    public Date getPriceDate() { return priceDate; }
    public void setPriceDate(Date priceDate) {  this.priceDate = priceDate; }

    @Column(name="ticker", nullable = false)
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) {  this.ticker = ticker; }

    @Column(name="open", nullable = false)
    public double getOpen() { return open; }
    public void setOpen(double open) {  this.open = open; }

    @Column(name="close", nullable = false)
    public double getClose() {  return close; }
    public void setClose(double close) {  this.close = close; }

    @Column(name="volume", nullable = false)
    public BigInteger getVolume() { return volume; }
    public void setVolume(BigInteger volume) {  this.volume = volume; }

    @Override
    public String toString() {

        String c = " priceDate: " + this.getPriceDate()
                + " ticker: " + this.getTicker()
                + " open: " + this.getOpen()
                + " close: " + this.getClose()
                + " volume: " + this.getVolume();

        return c;
    }

    public static Price newInstance(Price c, Date overrideDate) {
        Price pNew = new Price();
        pNew.setPriceDate(overrideDate);
        pNew.setTicker(c.getTicker());
        pNew.setOpen(c.getOpen());
        pNew.setClose(c.getClose());
        pNew.setVolume(c.getVolume());

        return pNew;
    }

}

