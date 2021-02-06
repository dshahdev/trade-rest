package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="portfolio_return_date_view")
public class PortfolioReturn {

    @Id private Date positionDate;
    private double eodMv;
    private double eodInv;
    private double unrealized;
    private double sodMv;
    private double sodInv;
    private double realized;

    @Column(name="position_date", nullable = false)
    public Date getPositionDate() {
        return positionDate;
    }

    public void setDate(Date positionDate) {
        this.positionDate = positionDate;
    }

    @Column(name="eod_mv", nullable = false)
    public double getEodMv() {
        return eodMv;
    }

    public void setEodMv(double eodMv) {
        this.eodMv = eodMv;
    }

    @Column(name="eod_inv", nullable = false)
    public double getEodInv() {
        return eodInv;
    }

    public void setEodInv(double eodInv) {
        this.eodInv = eodInv;
    }

    @Column(name="unrealized", nullable = false)
    public double getUnrealized() {
        return unrealized;
    }

    public void setUnrealized(double unrealized) {
        this.unrealized = unrealized;
    }

    @Column(name="sod_mv", nullable = false)
    public double getSodMv() {
        return sodMv;
    }

    public void setSodMv(double sodMv) {
        this.sodMv = sodMv;
    }

    @Column(name="sod_inv", nullable = false)
    public double getSodInv() {
        return sodInv;
    }

    public void setSodInv(double sodInv) {
        this.sodInv = sodInv;
    }

    @Column(name="realized", nullable = false)
    public double getRealized() {
        return realized;
    }

    public void setRealized(double realized) {
        this.realized = realized;
    }
}
