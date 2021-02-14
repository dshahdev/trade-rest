package com.shahs.transactions.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@IdClass(PositionViewCompositeKey.class)
public class PositionView {
    @Id
    private Date positionDate;
    @Id
    private String ticker;

    private int qty;
    private double cost;
    private double curPx;
    private double priorPx;
    private double inv;
    private double value;
    private double prior;
    private double unrPnl;
    private double dodPnl;
    private double returnP;
    private double dailyP;

    @Column(name="position_date", nullable = false)
    public Date getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(Date positionDate) {
        this.positionDate = positionDate;
    }

    @Column(name="ticker", nullable = false)
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Column(name="qty", nullable = false)
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Column(name="cost", nullable = false)
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Column(name="cur_px", nullable = false)
    public double getCurPx() {
        return curPx;
    }

    public void setCurPx(double curPx) {
        this.curPx = curPx;
    }

    @Column(name="prior_px", nullable = false)
    public double getPriorPx() {
        return priorPx;
    }

    public void setPriorPx(double priorPx) {
        this.priorPx = priorPx;
    }

    @Column(name="inv", nullable = false)
    public double getInv() {
        return inv;
    }

    public void setInv(double inv) {
        this.inv = inv;
    }

    @Column(name="value", nullable = false)
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Column(name="prior", nullable = false)
    public double getPrior() {
        return prior;
    }

    public void setPrior(double prior) {
        this.prior = prior;
    }

    @Column(name="unr_pnl", nullable = false)
    public double getUnrPnl() {
        return unrPnl;
    }

    public void setUnrPnl(double unrPnl) {
        this.unrPnl = unrPnl;
    }

    @Column(name="dod_pnl", nullable = false)
    public double getDodPnl() {
        return dodPnl;
    }

    public void setDodPnl(double dodPnl) {
        this.dodPnl = dodPnl;
    }

    @Column(name="returnp", nullable = false)
    public double getReturnP() {
        return returnP;
    }

    public void setReturnP(double returnP) {
        this.returnP = returnP;
    }

    @Column(name="dailyp", nullable = false)
    public double getDailyP() {
        return dailyP;
    }

    public void setDailyP(double dailyP) {
        this.dailyP = dailyP;
    }
}

