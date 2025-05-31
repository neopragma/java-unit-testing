package com.neopragma.rpnconsole;

import java.util.Scanner;
import com.neopragma.rpn.RPN;

public class RPNConsole {

    private RPN rpn;
    private static final String CURRENT_VALUE = "Current value: %s";
    private static final String PROMPT = "Enter: ";

    public RPNConsole() {
        this.rpn = new RPN();
    }

    private int run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the RPN calculator!");

        String currentValue = "0.0";
        String inputValue = "";
        System.out.println(String.format(CURRENT_VALUE, currentValue));
        do {
            System.out.print(PROMPT);
            inputValue = scanner.nextLine().trim().toLowerCase();
            if (inputValue.equals("q")) {
                break;
            }
            currentValue = rpn.enter(inputValue);
            System.out.println(String.format(CURRENT_VALUE, currentValue));
        } while (true);
        return 0;
    }

    public static void main(String[] args) {
        RPNConsole app = new RPNConsole();
        int status = app.run();
        System.exit(status);
    }
}
