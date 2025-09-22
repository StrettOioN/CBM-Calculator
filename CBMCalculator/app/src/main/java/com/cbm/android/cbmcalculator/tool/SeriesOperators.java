package com.cbm.android.cbmcalculator.tool;

import java.util.function.IntToDoubleFunction;

public class SeriesOperators {
    public static double summation(int start, int end, IntToDoubleFunction f) { // ∑
        double sum = 0;
        for (int i = start; i <= end; i++) sum += f.applyAsDouble(i);
        return sum;
    }

    public static double product(int start, int end, IntToDoubleFunction f) { // ∏
        double prod = 1;
        for (int i = start; i <= end; i++) prod *= f.applyAsDouble(i);
        return prod;
    }

    public static double alternatingSum(int start, int end, IntToDoubleFunction f) { // ∓
        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += (i % 2 == 0 ? -1 : 1) * f.applyAsDouble(i);
        }
        return sum;
    }
}
