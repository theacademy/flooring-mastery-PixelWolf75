package com.mthree.model;

import java.math.BigDecimal;

public class Tax {
    String state;
    String stateAbr;
    BigDecimal taxRate;

    public Tax(String state, String stateAbr, BigDecimal taxRate)
    {
        this.state = state;
        this.stateAbr = stateAbr;
        this.taxRate = taxRate;
    }

    public String toString()
    {
        return "State:" + this.state + ", Abbreviation:" + this.stateAbr + ", TaxRate:" + this.taxRate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAbr() {
        return stateAbr;
    }

    public void setStateAbr(String stateAbr) {
        this.stateAbr = stateAbr;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
