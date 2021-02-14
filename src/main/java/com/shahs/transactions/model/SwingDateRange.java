package com.shahs.transactions.model;

import org.joda.time.DateTime;

import java.sql.Date;

public class SwingDateRange {

    private DateTime startDate;
    private DateTime endDate;

    public SwingDateRange() {}

    public SwingDateRange(SwingAlert a, int lag) {
        this.startDate = new DateTime(a.getBuyDate()).plusDays(lag);
        this.endDate = new DateTime(a.getSellAllFlag().equals("Y") ? a.getSellAllDate() : DateTime.now()).plusDays(lag);
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public static boolean WithinRange(SwingDateRange sdr, Date date) {
        DateTime testDate = new DateTime(date);
        boolean c1 = testDate.isAfter(sdr.getStartDate()) || testDate.isEqual(sdr.getStartDate());
        boolean c2 = testDate.isBefore(sdr.getEndDate()) || testDate.isEqual(sdr.getEndDate());
//        System.out.println("testDate " + testDate + " sdr.getStartDate " + sdr.getStartDate() + " sdr.getEndDate " + sdr.getEndDate() + " c1 " + c1 + " c2 " + c2);
        return c1 && c2;
    }
}
