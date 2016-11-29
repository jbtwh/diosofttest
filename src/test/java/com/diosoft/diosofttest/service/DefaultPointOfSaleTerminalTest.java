package com.diosoft.diosofttest.service;

import com.diosoft.diosofttest.util.Utils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DefaultPointOfSaleTerminalTest {

    PointOfSaleTerminal terminal;

    @Before
    public void init(){
        terminal = new DefaultPointOfSaleTerminal();

        Pair<Long, BigDecimal> nullPair=null;

        Map<String, Pair<BigDecimal, Pair<Long, BigDecimal>>> pricing = new HashMap<String, Pair<BigDecimal, Pair<Long, BigDecimal>>>();
        pricing.put("A", Pair.of(Utils.createBD(1.25), Pair.of(3L, Utils.createBD(3.00))));
        pricing.put("B", Pair.of(Utils.createBD(4.25), nullPair));
        pricing.put("C", Pair.of(Utils.createBD(1.00), Pair.of(6L, Utils.createBD(5.00))));
        pricing.put("D", Pair.of(Utils.createBD(0.75), nullPair));

        terminal.setPricing(pricing);

    }

    //ABCDABA; Verify the total price is $13.25
    @Test
    public void test1(){
        terminal.scan("A");
        terminal.scan("B");
        terminal.scan("C");
        terminal.scan("D");
        terminal.scan("A");
        terminal.scan("B");
        terminal.scan("A");

        assertEquals(terminal.calculateTotal().doubleValue(), 13.25, 0.001);
    }

    //CCCCCCC; Verify the total price is $6.00
    @Test
    public void test2(){
        terminal.scan("C");
        terminal.scan("C");
        terminal.scan("C");
        terminal.scan("C");
        terminal.scan("C");
        terminal.scan("C");
        terminal.scan("C");

        assertEquals(terminal.calculateTotal().doubleValue(), 6.00, 0.001);
    }

    //ABCD; Verify the total price is $7.25
    @Test
    public void test3(){
        terminal.scan("A");
        terminal.scan("B");
        terminal.scan("C");
        terminal.scan("D");

        assertEquals(terminal.calculateTotal().doubleValue(), 7.25, 0.001);
    }

    @Test
    public void test4() {
        assertEquals(terminal.calculateTotal().doubleValue(), 0.00, 0.001);
    }


    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException(){
        terminal.scan("G");

        terminal.calculateTotal();
    }
}
