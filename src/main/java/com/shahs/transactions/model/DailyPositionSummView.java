package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class DailyPositionSummView {


    @Id
    private Date positionDate;
    private double inv;
    private double mv;
    private double unrPnl;
    private double retP;

    @Column(name="position_date", nullable = false)
    public Date getPositionDate() {
        return positionDate;
    }
    public void setPositionDate(Date positionDate) {
        this.positionDate = positionDate;
    }

    @Column(name="inv", nullable = false)
    public double getInv() {
        return inv;
    }
    public void setInv(double inv) {
        this.inv = inv;
    }

    @Column(name="mv", nullable = false)
    public double getMv() {
        return mv;
    }
    public void setMv(double mv) {
        this.mv = mv;
    }

    @Column(name="unr_pnl", nullable = false)
    public double getUnrPnl() {
        return unrPnl;
    }
    public void setUnrPnl(double unrPnl) {
        this.unrPnl = unrPnl;
    }

    @Column(name="retp", nullable = false)
    public double getRetP() {
        return retP;
    }

    public void setRetP(double retP) {
        this.retP = retP;
    }
}
