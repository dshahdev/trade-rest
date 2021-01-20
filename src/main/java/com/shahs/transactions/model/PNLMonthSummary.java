package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;


@Entity
public class PNLMonthSummary {
    @Id
    private String month;
    private Double pnl;

    @Column(name="month", nullable = false)
    public String getMonth() { return month; }
    public void setDate(String month) { this.month = month; }

    @Column(name="pnl", nullable = false)
    public double getPnl() {  return pnl; }
    public void setPnl(double pnl) {  this.pnl = pnl; }

}
