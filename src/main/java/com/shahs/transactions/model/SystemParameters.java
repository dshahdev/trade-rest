package com.shahs.transactions.model;

import java.sql.Date;
import java.util.HashMap;

public class SystemParameters {

    private String lastDateStr;
    private Date lastDate;
    private HashMap<String, String> renamedTickerMap = new HashMap<String, String>();

    public HashMap<String, String> getRenamedTickerMap() {
        return renamedTickerMap;
    }

    public void setRenamedTickerMap(HashMap<String, String> renamedTickerMap) {
        this.renamedTickerMap = renamedTickerMap;
    }

    public String getLastDateStr() {
        return lastDateStr;
    }

    public void setLastDateStr(String lastDateStr) {
        this.lastDateStr = lastDateStr;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }


}
