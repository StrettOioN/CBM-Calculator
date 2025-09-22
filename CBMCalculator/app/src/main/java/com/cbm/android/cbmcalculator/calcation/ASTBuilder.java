package com.cbm.android.cbmcalculator.calcation;

import java.util.List;
import java.util.Stack;

public class ASTBuilder {
    public static ExprNode build(List<Token> rpn) {
        Stack<ExprNode> stack = new Stack<>();

        for (Token token : rpn) {
            switch (token.type) {
                case NUMBER -> stack.push(new NumberNode(Double.parseDouble(token.value)));
                case SYMBOL -> stack.push(new SymbolNode(token.value));
                case OPERATOR -> {
                    ExprNode b = stack.pop();
                    ExprNode a = stack.pop();
                    stack.push(new OperatorNode(token.value, a, b));
                }
            }
        }

        return stack.pop();
    }
}