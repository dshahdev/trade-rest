package com.shahs.transactions.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="pnl_view")
@IdClass(PNLDataKey.class)
public class Pnl implements Serializable {

    @Id private long buyTradeId;
    @Id private long sellTradeId;
    private Date date;
    private String ticker;
    private int allocatedQty;
    private double cost;
    private double price;
    private double pnl;

    @Column(name="date", nullable = false)
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name="ticker", nullable = false)
    public String getTicker() {
        return this.ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Column(name="allocated_qty", nullable = false)
    public int getAllocatedQty() {  return this.allocatedQty; }
    public void setAllocatedQty(int alloc) {
        this.allocatedQty = alloc;
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

    @Column(name="pnl", nullable = false)
    public double getPnl() {
        return pnl;
    }
    public void setPnl(double pnl) {
        this.pnl = pnl;
    }

    @Column(name="buy_trade_id", nullable = false)
    public long getBuyTradeId() { return buyTradeId; }
    public void setBuyTradeId(long buyTradeId) { this.buyTradeId = buyTradeId; }

    @Column(name="sell_trade_id", nullable = false)
    public long getSellTradeId() { return sellTradeId; }
    public void setSellTradeId(long sellTradeId) { this.sellTradeId = sellTradeId; }

    @Override
    public String toString() {

        String pnlRecord = " BuyId:"+ this.getBuyTradeId()+
                " SellId:"+ this.getSellTradeId() +
                " Date: " + this.getDate() +
                " ticker: " + this.getTicker() +
                " allocatedQty: " + this.getAllocatedQty()+
                " cost: " + this.getCost() +
                " price: " + this.getPrice() +
                " pnl: " + this.getPnl();

        return pnlRecord;
    }

}
