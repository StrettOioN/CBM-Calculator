package com.cbm.android.cbmcalculator.tool;

import java.math.BigDecimal;

public class Tools {
    public static boolean isNumber(String text) {
        try {
            BigDecimal bd = new BigDecimal(text);
            return !(bd.doubleValue()+"").isEmpty();
        } catch (Exception e){e.printStackTrace();}
        return false;
    }
}
