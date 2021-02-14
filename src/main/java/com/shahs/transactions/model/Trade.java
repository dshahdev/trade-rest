package com.shahs.transactions.model;

import com.shahs.transactions.util.MiscUtils;
import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="trade")
@SequenceGenerator(name="seq", initialValue=9, allocationSize=10)
public class Trade {
    private long id;
    private Date date;
    private String action;
    private String ticker;
    private int quantity;
    private double price;
    private double fees;
    private double amount;





    public Trade() {}

    public Trade(Date date, String action, String ticker, int qty, double price, double amount, double fees) {
        this.date = date;
        this.action = action;
        this.ticker = ticker;
        this.quantity = qty;
        this.price = price;
        this.amount = amount;
        this.fees = fees;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    public long getId() {
            return id;
    }
    public void setId(long id){
        this.id = id;
    }

    @Column(name="date", nullable = false)
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name="fees", nullable = false)
    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    @Column(name="action", nullable = false)
    public String getAction() {
        return this.action;

    }
    public void setAction(String action) {
        this.action = action;
    }

    @Column(name="ticker", nullable = false)
    public String getTicker() {
        return this.ticker;

    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Column(name="quantity", nullable = false)
    public int getQuantity() {
        return this.quantity;

    }
    public void setQuantity(int qty) {
        this.quantity = qty;
    }

    @Column(name="price", nullable = false)
    public double getPrice() {
        return this.price;

    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name="amount", nullable = false)
    public double getAmount() {
        return this.amount;

    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {

        String t = " Id:"+ this.getId() +
                " Date: " + this.getDate() +
                " Action: " + this.getAction() +
                " ticker: " + this.getTicker() +
                " quantity: " + this.getQuantity() +
                " price: " + this.getPrice() +
                " fees: " + this.getFees() +
                " amount: " + this.getAmount();

        return t;
    }

    public static Trade createTradeObj(String[] csvFile) {

        Trade newTrade = new Trade();

        Date cDate = MiscUtils.stringToSqlDate(csvFile[0],"MM/dd/yyyy");
        newTrade.setDate(cDate);
        newTrade.setAction(csvFile[1]);
        newTrade.setTicker(csvFile[2]);
        newTrade.setQuantity(Integer.parseInt(csvFile[4]));
        newTrade.setPrice(Double.parseDouble(csvFile[5]));
        newTrade.setFees(Double.parseDouble(csvFile[6].equals( "" ) ? "0.0": csvFile[6]));
        newTrade.setAmount(Double.parseDouble(csvFile[7]));
//            System.out.println("newtrade: "+newTrade);
        return newTrade;

    }
    public Trade[] sortTrades(Trade trade) {

        return null;
    }

    public void addSameLotTrade(Trade current) {
        this.quantity += current.getQuantity();
        this.amount += current.getAmount();
        this.fees += current.getFees();
        if (this.quantity != 0) {
            this.price = Math.abs(this.amount) / Math.abs(this.quantity);
        }
    }

}

