package com.cbm.android.cbmcalculator.tool;

public class ExpressionSymbols {
    public static double sqrt(double x) {
        return Math.sqrt(x); // √
    }

    public static double exp(double n) {
        return Math.exp(n); // e^n
    }

    public static double factorial(int n) { // !
        if (n < 0) throw new IllegalArgumentException("Negative factorial not defined");
        double result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }
}
