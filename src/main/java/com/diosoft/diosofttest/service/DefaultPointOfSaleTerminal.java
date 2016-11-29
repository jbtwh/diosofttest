package com.diosoft.diosofttest.service;

import com.diosoft.diosofttest.util.Utils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DefaultPointOfSaleTerminal implements PointOfSaleTerminal {

    private final Map<String, Long> cart = new HashMap<String, Long>(); //cart that contains products. key - product code, value - amount of products

    private Map<String, Pair<BigDecimal, Pair<Long, BigDecimal>>> prices; //key - product code, value - price for each and volume price (can be null)

    @Override
    public void setPricing(Map<String, Pair<BigDecimal, Pair<Long, BigDecimal>>> prices) {
        this.prices=prices;
    }

    //if cart already contains product code - increment, else init with 0 and increment
    @Override
    public void scan(String product) {
        final Long count = cart.containsKey(product) ? cart.get(product) : 0;
        cart.put(product, count + 1);
    }

    //checkout and clear
    @Override
    public BigDecimal calculateTotal() {
        BigDecimal total = Utils.createBD(0);

        for (Map.Entry<String, Long> e : cart.entrySet()) {
            final String code = e.getKey();
            final Long amount = e.getValue();

            final Pair<BigDecimal, Pair<Long, BigDecimal>> price =  prices.get(code);
            Assert.notNull(price, "no price for product with code "+code);

            final BigDecimal singlePrice = price.getLeft();
            final Pair<Long, BigDecimal> volumePrice  = price.getRight();

            if (volumePrice!=null) total = total.add(volumePrice.getRight().multiply(Utils.createBD(amount / volumePrice.getLeft())).add(singlePrice.multiply(Utils.createBD(amount % volumePrice.getLeft()))));  // ($3.00 * 10/3) + ($1.25 * 10%3)
            else total = total.add(singlePrice.multiply(Utils.createBD(amount)));  // $1.25 * 10
        }
        cart.clear();

        return total;
    }
}
