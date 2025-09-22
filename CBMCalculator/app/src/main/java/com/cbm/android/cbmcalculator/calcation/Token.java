package com.cbm.android.cbmcalculator.calcation;

public class Token {
    public enum Type { NUMBER, OPERATOR, FUNCTION, SYMBOL, PARENTHESIS }
    public final Type type;
    public final String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }
}