package com.hanshesse.discovery.consul.quoteservice.domain;

/**
 * Basic representation for a stock quote.
 */
public class Quote {

    private String symbol;

    private String value;

    public Quote() {
        this.symbol = "";
        this.value = "";
    }

    public Quote(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }
}
