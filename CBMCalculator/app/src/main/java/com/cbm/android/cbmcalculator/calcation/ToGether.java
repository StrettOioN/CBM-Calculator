package com.cbm.android.cbmcalculator.calcation;

import android.content.Context;

import com.cbm.android.cbmcalculator.calcation.OperatorInfo;
import com.cbm.android.cbmcalculator.calcation.Token;
import com.cbm.android.cbmcalculator.settings.AppSettings;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ToGether {
    private WeakReference<Context> wrc;
    public ToGether() {}
    public ToGether(Context c) {wrc = new WeakReference<>(c);}

    public static List<Token> toRPN(List<Token> tokens, boolean bodmas) {
        Stack<Token> operatorStack = new Stack<>();
        List<Token> output = new ArrayList<>();

        for (Token token : tokens) {
            switch (token.type) {
                case NUMBER, SYMBOL -> output.add(token);
                case OPERATOR -> {
                    while (!operatorStack.isEmpty()) {
                        Token top = operatorStack.peek();
                        if (top.type != Token.Type.OPERATOR) break;

                        if(bodmas) {
                            OperatorInfo curr = OperatorInfo.OPERATORS.get(token.value);
                            OperatorInfo topOp = OperatorInfo.OPERATORS.get(top.value);

                            if ((curr.rightAssociative && curr.precedence < topOp.precedence) ||
                                    (!curr.rightAssociative && curr.precedence <= topOp.precedence)) {
                            } else break;
                        }
                        output.add(operatorStack.pop());
                    }
                    operatorStack.push(token);
                }
                case PARENTHESIS -> {
                    if (token.value.equals("(")) {
                        operatorStack.push(token);
                    } else {
                        while (!operatorStack.isEmpty() && !operatorStack.peek().value.equals("(")) {
                            output.add(operatorStack.pop());
                        }
                        operatorStack.pop(); // Remove "("
                    }
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            output.add(operatorStack.pop());
        }

        return output;
    }
}