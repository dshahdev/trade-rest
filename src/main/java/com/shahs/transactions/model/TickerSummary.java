package com.shahs.transactions.model;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;

public class TickerSummary {
    private long id;
    @Temporal(TemporalType.DATE)
    private String date;
    private String ticker;
    private double profit_loss;
    private ArrayList<Trade> trades;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getProfit_loss() {
        return profit_loss;
    }

    public void setProfit_loss(double profit_loss) {
        this.profit_loss = profit_loss;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }


}
