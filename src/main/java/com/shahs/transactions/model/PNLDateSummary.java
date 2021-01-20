package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class PNLDateSummary{

    @Id
    private Date date;
    private double pnl;

    @Column(name="date", nullable = false)
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Column(name="pnl", nullable = false)
    public double getPnl() {  return pnl; }
    public void setPnl(double pnl) {  this.pnl = pnl; }

}
