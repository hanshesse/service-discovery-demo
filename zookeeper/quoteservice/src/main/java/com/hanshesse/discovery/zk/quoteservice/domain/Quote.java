package com.hanshesse.discovery.zk.quoteservice.domain;


/**
 * Basic representation for a stock quote.
 */
public class Quote {

    private String symbol;

    private String value;

    public Quote(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

