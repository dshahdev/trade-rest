package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;


public class PnlTickerWithDetail {
    @Id
    private String ticker;
    private Double pnl;
    private List<Pnl> pnlTrade;

    @Column(name="ticker", nullable = false)
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    @Column(name="pnl", nullable = false)
    public Double getPnl() {
        return pnl;
    }

    public void setPnl(Double pnl) {
        this.pnl = pnl;
    }

    public List<Pnl> getPnlTrade() {
        return pnlTrade;
    }

    public void setPnlTrade(List<Pnl> pnlTrade) {
        this.pnlTrade = pnlTrade;
    }

    @Override
    public String toString() {
        String s= this.getTicker() + " " + this.getPnl() + " " + this.getPnlTrade().size();
        return s;
    }


}
