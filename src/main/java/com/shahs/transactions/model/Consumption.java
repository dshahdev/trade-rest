package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Consumption implements Comparable{

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
    public void setAllocatedQty(int allocatedQty) {
        this.allocatedQty = allocatedQty;
    }

    @Column(name="available_qty", nullable = false)
    public int getAvailableQty() {
        return availableQty;
    }
    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    @Override
    public int compareTo(Object o) {
        long buyId = ((Consumption)o).getId();
        return (this.getId() == buyId ? 0 : (this.getId() < buyId ? -1 : 1));
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

    public static Consumption newInstance(Consumption c) {
        Consumption cNew = new Consumption();
        cNew.setId(c.getId());
        cNew.setTicker(c.getTicker());
        cNew.setAllocatedQty(c.getAllocatedQty());
        cNew.setAvailableQty(c.getAvailableQty());
        cNew.setOriginalQty(c.getOriginalQty());

        return cNew;
    }
}
