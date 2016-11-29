package com.diosoft.diosofttest.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static BigDecimal createBD(Integer n){
        return new BigDecimal(n).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal createBD(Double n){
        return new BigDecimal(n).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal createBD(Long n){
        return new BigDecimal(n).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static String currencyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(n);
    }
}
