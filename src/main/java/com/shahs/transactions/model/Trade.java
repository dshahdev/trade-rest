package com.shahs.transactions.model;

import org.hibernate.annotations.Columns;

import javax.persistence.*;

@Entity
@Table(name="trade")
public class Trade {
    private long id;
    private String date;
    private String action;
    private String ticker;
    private int quantity;
    private double price;
    private double fees;
    private double amount;





    public Trade() {}

    public Trade(String date, String action, String ticker, int qty, double price, double amount, double fees) {
        this.date = date;
        this.action = action;
        this.ticker = ticker;
        this.quantity = qty;
        this.price = price;
        this.amount = amount;
        this.fees = fees;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
            return id;
    }
    public void setId(long id){
        this.id = id;
    }

    @Column(name="date", nullable = false)
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
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


}

