package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;
import java.util.List;

public class PnlDateWithDetail {
    @Id
    private Date date;
    private double pnl;
    private List<Pnl> pnlTrade;

    @Column(name="date", nullable = false)
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Column(name="pnl", nullable = false)
    public double getPnl() {  return pnl; }
    public void setPnl(double pnl) {  this.pnl = pnl; }

    public List<Pnl> getPnlTrade() {
        return pnlTrade;
    }
    public void setPnlTrade(List<Pnl> pnlTrade) {
        this.pnlTrade = pnlTrade;
    }

    @Override
    public String toString() {
        String s= this.getDate() + " " + this.getPnl() + " " + this.getPnlTrade().size();
        return s;
    }
}
