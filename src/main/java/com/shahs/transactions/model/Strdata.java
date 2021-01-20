package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Strdata {
    @Id
    private String strdata;

    @Column(name="strdata", nullable = false)
    public String getStrdata() {
        return strdata;
    }

    public void setStrdata(String strdata) {
        this.strdata = strdata;
    }

}
