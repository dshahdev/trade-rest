package com.shahs.transactions.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(PositionViewCompositeKey.class)
public class PositionView {
    @Id
    private Date positionDate;
    @Id
    private String ticker;
    private int qty;
    private double pnl;
    private double pnl_p;
    private double cost;
    private double price;
    private double value;

    public PositionView() {}

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

    public void setQty(int originalQty) {
        this.qty = originalQty;
    }

    @Column(name="pnl", nullable = false)
    public double getPnl() {
        return pnl;
    }

    public void setPnl(double pnl) {
        this.pnl = pnl;
    }
    @Column(name="pnl_p", nullable = false)
    public double getPnl_p() {
        return pnl_p;
    }

    public void setPnl_p(double pnl_p) {
        this.pnl_p = pnl_p;
    }

    @Column(name="cost", nullable = false)
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Column(name="price", nullable = false)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name="value", nullable = false)
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

