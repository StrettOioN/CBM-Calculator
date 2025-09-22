package com.cbm.android.cbmcalculator.calcation;

import com.cbm.android.cbmcalculator.tool.Tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calcation {
    public static BigDecimal calculate(String symbol, BigDecimal val1, BigDecimal val2) {
        switch (symbol) {
            case "-": {
                return val1.subtract(val2);
            } 
            case "*": {
                return val1.multiply(val2);
            }
            case "/": {
                return val1.divide(val2, RoundingMode.HALF_EVEN);
            }
            default:{
                return val1.add(val2);
            }
        }
    }

    public static BigDecimal evaluate(String expr) {
        BigDecimal ans = new BigDecimal(0);
        String n = "";
        String s = "+";
        char[] charArray = expr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            String t = c + "";
            if (Tools.isNumber(t)) {
                n += t;
            } else {
                s=t;
                ans = calculate(s, ans, new BigDecimal(n));
                n="";
            }
            if(i==charArray.length-1) ans = calculate(s, ans, new BigDecimal(n));
        }

        return ans;
    }

}
