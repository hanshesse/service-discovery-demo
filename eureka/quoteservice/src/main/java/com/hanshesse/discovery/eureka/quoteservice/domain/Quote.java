package com.hanshesse.discovery.eureka.quoteservice.domain;

/**
 * Representation of a quote
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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (symbol != null ? !symbol.equals(quote.symbol) : quote.symbol != null) return false;
        return !(value != null ? !value.equals(quote.value) : quote.value != null);

    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "symbol='" + symbol + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
