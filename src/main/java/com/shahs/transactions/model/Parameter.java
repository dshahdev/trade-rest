package com.shahs.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="parameter")
public class Parameter {

    @Id  String paramKey;
    private String paramValue;

    public Parameter() {}

    public Parameter(String pk, String pv) {
        this.paramKey = pk;
        this.paramValue = pv;
    }

    @Column(name="param_key", nullable = false)
    public String getParamKey() {
        return this.paramKey;
    }
    public void setParamKey(String value) {
        this.paramKey = value;
    }

    @Column(name="param_value", nullable = false)
    public String getParamValue() {
        return this.paramValue;
    }
    public void setParamValue(String value) {
        this.paramValue = value;
    }

    @Override
    public String toString() {

        String s = "param_key: " + this.getParamKey() + " param_value:" + this.getParamValue();

        return s;
    }

}
