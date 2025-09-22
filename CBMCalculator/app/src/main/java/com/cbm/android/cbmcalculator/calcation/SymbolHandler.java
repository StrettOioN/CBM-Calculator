package com.cbm.android.cbmcalculator.calcation;

public interface SymbolHandler {
    boolean supports(String symbol);
    Object evaluate(String symbol, Object... args);
}