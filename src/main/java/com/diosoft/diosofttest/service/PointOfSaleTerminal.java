package com.diosoft.diosofttest.service;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.Map;

public interface PointOfSaleTerminal {
    public void setPricing(Map<String, Pair<BigDecimal, Pair<Long, BigDecimal>>> prices); //key - product code, value - price for each and volume price (can be null)
    public void scan(String product);
    public BigDecimal calculateTotal();
}
