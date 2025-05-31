package com.neopragma.rpn;

import java.util.Stack;
import java.util.regex.Pattern;

public class RPN {
    private Stack<Double> stack;
    private Pattern numerals = Pattern.compile("^-?\\d*\\.?\\d+$");
    private Pattern operators = Pattern.compile("[+\\-*/%Pp]+");
    private final static String ILLEGAL_ARGUMENT_MESSAGE = "Unrecognized input value: %s";
    private final static String UNRECOGNIZED_OPERAND_MESSAGE = "Unrecognized operand: %s";

    public RPN() {
        clearStack();
    }

    public String enter(String val) {
        String inputVal = val.strip().toLowerCase();

        if (inputVal.equals("c")) {
            clearStack();
        } else {
            if (numerals.matcher(inputVal).matches()) {
                stack.push(Double.parseDouble(inputVal));
            } else {
                if (operators.matcher(inputVal).matches()) {
                    performOperation(inputVal);
                } else {
                    throw new IllegalArgumentException(
                            String.format(ILLEGAL_ARGUMENT_MESSAGE, inputVal));
                }
            }
        }
        return String.valueOf(stack.peek());
    }

    private void performOperation(String op) {
        Double operand2 = stack.pop();
        Double operand1 = stack.pop();
        Double result;
        switch (op) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
            case "%":
                result = operand1 % operand2;
                break;
            case "p":
                result = Math.pow(operand1, operand2);
                break;
            default:
                throw new IllegalArgumentException(
                        String.format(UNRECOGNIZED_OPERAND_MESSAGE, op));
        }
        this.stack.push(result);
    }

    private void clearStack() {
        this.stack = new Stack();
        this.stack.push(0.0D);
    }

}