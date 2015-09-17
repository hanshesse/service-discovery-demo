package com.hanshesse.discovery.consul.orderservice.domain;

/**
 * Representation of an order
 */
public class Order {

    private String symbol;

    private String value;

    private String status = "FULFILLED";

    public Order(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "symbol='" + symbol + '\'' +
                ", value='" + value + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
