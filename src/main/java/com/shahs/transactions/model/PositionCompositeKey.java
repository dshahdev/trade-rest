package com.shahs.transactions.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

@Embeddable
public class PositionCompositeKey implements Serializable {

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
    private long buyId;

    public Date getPositionDate() { return positionDate; }
    public void setPositionDate(Date positionDate) {  this.positionDate = positionDate; }

    public long getBuyId() {  return buyId; }
    public void setBuyId(long buyId) {  this.buyId = buyId; }
}
