package com.shahs.transactions.model;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@IdClass(PositionDateTickerCompositeKey.class)
public class PositionForDateByTicker {
    @Id
    private String positionDate;
    @Id
    private String ticker;

    private int originalQty;
    private int availableQty;
    private int allocatedQty;
    private double mv;
    private double cost;
    private double unrealized;
    private double priorPrice;
    private double curPrice;

    public PositionForDateByTicker(){}



    public PositionForDateByTicker(String date, String ticker, int oq, int aq, int aaq, double mv,
                                   double cost, double unrealized, double pp, double cp) {
        this.positionDate= date;
        this.ticker = ticker;
        this.originalQty = oq;
        this.availableQty = aq;
        this.allocatedQty = aaq;
        this.mv = mv;
        this.cost = cost;
        this.unrealized = unrealized;
        this.priorPrice = pp;
        this.curPrice = cp;

    }
    @Column(name="position_date", nullable = false)
    public String getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(String positionDate) {
        this.positionDate = positionDate;
    }

    @Column(name="ticker", nullable = false)
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Column(name="original_qty", nullable = false)
    public int getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(int originalQty) {
        this.originalQty = originalQty;
    }

    @Column(name="available_qty", nullable = false)
    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    @Column(name="allocated_qty", nullable = false)
    public int getAllocatedQty() {
        return allocatedQty;
    }

    public void setAllocatedQty(int allocatedQty) {
        this.allocatedQty = allocatedQty;
    }

    @Column(name="mv", nullable = false)
    public double getMv() {
        return mv;
    }

    public void setMv(double mv) {
        this.mv = mv;
    }

    @Column(name="cost", nullable = false)
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Column(name="unrealized", nullable = false)
    public double getUnrealized() {
        return unrealized;
    }

    public void setUnrealized(double unrealized) {
        this.unrealized = unrealized;
    }

    @Column(name="prior_price", nullable = false)
    public double getPriorPrice() {
        return priorPrice;
    }

    public void setPriorPrice(double priorPrice) {
        this.priorPrice = priorPrice;
    }

    @Column(name="cur_price", nullable = false)
    public double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(double curPrice) {
        this.curPrice = curPrice;
    }








}
