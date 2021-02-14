package com.shahs.transactions.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

@Embeddable
public class PositionViewCompositeKey implements Serializable {

//    private UUID id;
//
//    protected PositionId(){}
//
//    public PositionId( UUID id ) {
//        this.id = id;
//    }
//
//    public UUID getId() {
//        return id;
//    }

    private Date positionDate;
    private String ticker;

    public Date getPositionDate() { return positionDate; }
    public void setPositionDate(Date positionDate) {  this.positionDate = positionDate; }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
