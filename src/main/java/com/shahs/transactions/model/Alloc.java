package com.shahs.transactions.model;

import javax.persistence.*;

@Entity
@Table(name="alloc")
@SequenceGenerator(name="allocseq", initialValue=9, allocationSize=10)
public class Alloc {
    private long id;
    private long sellTradeId;
    private long buyTradeId;
    private int allocatedQty;

    public Alloc(){}

    public Alloc(long sti, long bti, int allocatedQty) {
        this.sellTradeId = sti;
        this.buyTradeId = bti;
        this.allocatedQty = allocatedQty;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="allocseq")
    public long getId() {
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    @Column(name="sell_trade_id", nullable = false)
    public long getSellTradeId() {
        return this.sellTradeId;
    }
    public void setSellTradeId(long value) {
        this.sellTradeId = value;
    }

    @Column(name="buy_trade_id", nullable = false)
    public long getBuyTradeId() {
        return this.buyTradeId;
    }
    public void setBuyTradeId(long value) {
        this.buyTradeId = value;
    }

    @Column(name="allocated_qty", nullable = false)
    public int getAllocatedQty() {
        return this.allocatedQty;
    }
    public void setAllocatedQty(int value) {
        this.allocatedQty = value;
    }

    @Override
    public String toString() {

        String ac = "id: "+this.getId()
                    + "sellTradeId: " + this.getSellTradeId()
                    + " buyTradeId:" + this.getBuyTradeId()
                    + "allocatedQty" + this.getAllocatedQty();

        return ac;
    }
}
