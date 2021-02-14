package com.shahs.transactions.model;

import com.shahs.transactions.service.storage.HelloMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name="position")
@IdClass(PositionCompositeKey.class)
public class Position implements Comparable {

    @Autowired HelloMessageService helloMessageService;

    private long buyId;
    private Date positionDate;
    private String ticker;
    private int originalQty;
    private int availableQty;
    private int allocatedQty;

    public Position() {}

    public Position(long buyId, Date positionDate, String ticker, int originalQty, int availableQty, int allocatedQty) {
        this.buyId = buyId;
        this.positionDate = positionDate;
        this.ticker = ticker;
        this.originalQty = originalQty;
        this.availableQty = availableQty;
        this.allocatedQty = allocatedQty;
    }

    @Id
    @Column(name="buy_id", nullable = false)
    public long getBuyId() {
        return buyId;
    }
    public void setBuyId(long buyId){
        this.buyId = buyId;
    }

    @Id
    @Column(name="position_date", nullable = false)
    public Date getPositionDate() { return positionDate; }
    public void setPositionDate(Date positionDate) {  this.positionDate = positionDate; }

    @Column(name="ticker", nullable = false)
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) {  this.ticker = ticker; }

    @Column(name="original_qty", nullable = false)
    public int getOriginalQty() { return originalQty; }
    public void setOriginalQty(int originalQty) { this.originalQty = originalQty; }

    @Column(name="available_qty", nullable = false)
    public int getAvailableQty() {  return availableQty; }
    public void setAvailableQty(int availableQty) {  this.availableQty = availableQty; }

    @Column(name="allocated_qty", nullable = false)
    public int getAllocatedQty() {  return allocatedQty; }
    public void setAllocatedQty(int allocatedQty) { this.allocatedQty = allocatedQty;  }

    @Override
    public String toString() {

        String c = " id: "+ this.getBuyId()
                + " positionDate: " + this.getPositionDate()
                + " ticker: " + this.getTicker()
                + " originalQty: "+ this.getOriginalQty()
                + " allocatedQty: " + this.getAllocatedQty()
                + " availableQty: " + this.getAvailableQty();


        return c;
    }

    @Override
    public int compareTo(Object o) {

        long buyId = ((Position)o).getBuyId();
        int retValue = 0;

        if (this.getBuyId() == buyId) { retValue = 0; }
        if (this.getBuyId() < buyId) {
            if (HelloMessageService.costingMethod == HelloMessageService.ConsumeMethod.LIFO) {
                retValue = 1;
            } else {
                retValue = -1;
            }
        } else if (this.getBuyId() > buyId) {
            if (HelloMessageService.costingMethod == HelloMessageService.ConsumeMethod.LIFO) {
                retValue = -1;
            } else {
                retValue = 1;
            }
        }
        return retValue;
    }

    public static Position newInstance(Position c, Date overrideDate) {
        Position cNew = new Position();
        cNew.setBuyId(c.getBuyId());
        cNew.setPositionDate(overrideDate);
        cNew.setTicker(c.getTicker());
        cNew.setAllocatedQty(c.getAllocatedQty());
        cNew.setAvailableQty(c.getAvailableQty());
        cNew.setOriginalQty(c.getOriginalQty());

        return cNew;
    }

}

