package com.shahs.transactions.model;

import com.shahs.transactions.service.storage.HelloMessageService;
import org.joda.time.DateTime;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="swing_alert_view")
public class SwingAlert {

    @Id private String rowKey;
    private Date buyDate;
    private String ticker;
    private double buyPrice;
    private Date sellHalfDate;
    private double sellHalfPrice;
    private String sellHalfFlag;
    private Date sellAllDate;
    private double sellAllPrice;
    private String sellAllFlag;

    public SwingAlert() {}

    @Column(name="row_key", nullable = false)
    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    @Column(name="buy_date", nullable = false)
    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    @Column(name="ticker", nullable = false)
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Column(name="buy_price", nullable = false)
    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Column(name="sell_half_date", nullable = false)
    public Date getSellHalfDate() {
        return sellHalfDate;
    }

    public void setSellHalfDate(Date sellHalfDate) {
        this.sellHalfDate = sellHalfDate;
    }

    @Column(name="sell_half_price", nullable = false)
    public double getSellHalfPrice() {
        return sellHalfPrice;
    }

    public void setSellHalfPrice(double sellHalfPrice) {
        this.sellHalfPrice = sellHalfPrice;
    }

    @Column(name="sell_half_flag", nullable = false)
    public String getSellHalfFlag() {
        return sellHalfFlag;
    }

    public void setSellHalfFlag(String sellHalfFlag) {
        this.sellHalfFlag = sellHalfFlag;
    }

    @Column(name="sell_all_date", nullable = false)
    public Date getSellAllDate() {
        return sellAllDate;
    }

    public void setSellAllDate(Date sellAllDate) {
        this.sellAllDate = sellAllDate;
    }

    @Column(name="sell_all_price", nullable = false)
    public double getSellAllPrice() {
        return sellAllPrice;
    }

    public void setSellAllPrice(double sellAllPrice) {
        this.sellAllPrice = sellAllPrice;
    }

    @Column(name="sell_all_flag", nullable = false)
    public String getSellAllFlag() {
        return sellAllFlag;
    }

    public void setSellAllFlag(String sellAllFlag) {
        this.sellAllFlag = sellAllFlag;
    }

    @Override
    public String toString() {
        return "rowKey: " + rowKey + " buyDate: " + buyDate + " ticker: " + ticker + " buyPrice: " + buyPrice
                + "HALF Date: " + sellHalfDate + " Price: " + sellHalfPrice + " Flag: " + sellHalfFlag
                + "HALF Date: " + sellAllDate + " Price: " + sellAllPrice + " Flag: " + sellAllFlag;
    }

    public static HelloMessageService.StrategyEnum TradeWithinSwingDates(List<SwingAlert> swingAlerts, Trade openTrade, Trade sellTrade, int lag) {

        for (SwingAlert swingAlert : swingAlerts) {
            SwingDateRange sdr = new SwingDateRange(swingAlert, 0);
            if (SwingDateRange.WithinRange(sdr, openTrade.getDate()) && SwingDateRange.WithinRange(sdr, sellTrade.getDate())) {
                return HelloMessageService.StrategyEnum.SWING;
            }
        }

        return HelloMessageService.StrategyEnum.SIDE_BET;
    }
}
