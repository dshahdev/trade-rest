package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Consumption {

    private long id;
    private String ticker;
    private int originalQty;
    private int availableQty;
    private int allocatedQty;

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    @Column(name="allocated_qty", nullable = false)
    public int getAllocatedQty() {
        return allocatedQty;
    }

    @Column(name="available_qty", nullable = false)
    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public void setAllocatedQty(int allocatedQty) {
        this.allocatedQty = allocatedQty;
    }
    @Override
    public String toString() {
        String c = " id: "+ this.getId()
                + " ticker: " + this.getTicker()
                + " originalQty: "+ this.getOriginalQty()
                + " allocatedQty: " + this.getAllocatedQty()
                + " availableQty: " + this.getAvailableQty();


        return c;
    }

}
