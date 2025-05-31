package com.neopragma.rpngui;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class RPNGUI extends JPanel {
    private static final JFrame frame = new JFrame("RPN Calculator");
    private final JLabel currentValueLabel = new JLabel();
    private final JLabel currentValueText = new JLabel();
    private final JLabel expressionLabel = new JLabel();
    private final JLabel expressionText = new JLabel();
    private final JTextField inputField = new JTextField(20);
    private static final String PROMPT_VALUE =
            "Enter a number or operator (+, -, *, /, %, P), or C to clear, Q to quit";
    private static final String READY_PROMPT = "          Ready: ";

    private RPN calculator;

    public RPNGUI() {
        calculator = new RPN();
        Box mainBox = Box.createVerticalBox();
        add(mainBox);
        initializeLabels();

        Box inputBox = Box.createVerticalBox();
        Box promptBox = Box.createHorizontalBox();
        JLabel prompt = new JLabel(PROMPT_VALUE);
        promptBox.add(Box.createHorizontalGlue());
        promptBox.add(prompt);
        promptBox.add(Box.createHorizontalGlue());
        inputBox.add(promptBox);
        inputBox.add(inputField);
        mainBox.add(inputBox);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        JButton enterButton = new JButton("Enter");
        enterButton.setToolTipText("Enter a value into the calculator");
        enterButton.addActionListener( enterEvent -> {
            handleEnterEvent();
        });
        enterButton.getInputMap()
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                        "enterValue");
        enterButton.getActionMap().put("enterValue", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                // pass entered value into the calculator
                System.out.println("The Enter key was pressed");
            }
        });
        frame.getRootPane().setDefaultButton(enterButton);
        buttonBox.add(enterButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setToolTipText("Clear the calculator's memory");
        clearButton.addActionListener( clearEvent -> {
            clearTheCalculator();
        });
        clearButton.getInputMap()
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                        "clearValue");
        clearButton.getActionMap().put("clearValue", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                clearTheCalculator();
            }
        });

        buttonBox.add(clearButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setToolTipText("Exit the calculator");
        exitButton.addActionListener( exitEvent -> {
            exitTheApplication();
        });
        exitButton.getInputMap()
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                        "exitValue");
        exitButton.getActionMap().put("exitValue", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                clearTheCalculator();
            }
        });
        buttonBox.add(exitButton);
        buttonBox.add(Box.createHorizontalGlue());
        mainBox.add(buttonBox);

        Box currentValueBox = Box.createHorizontalBox();
        currentValueBox.add(currentValueLabel);
        currentValueBox.add(currentValueText);
        currentValueBox.add(Box.createHorizontalGlue());
        mainBox.add(currentValueBox);

        Box expressionBox = Box.createHorizontalBox();
        expressionBox.add(expressionLabel);
        expressionBox.add(expressionText);
        expressionBox.add(Box.createHorizontalGlue());
        mainBox.add(expressionBox);
    }

    private void handleEnterEvent() {
        String trimmedInputValue = inputField.getText().trim();
        if (trimmedInputValue.equalsIgnoreCase("q")) {
            exitTheApplication();
        } else {
            if (trimmedInputValue.equalsIgnoreCase("c")) {
                clearTheCalculator();
            } else {
                String result = calculator.enter(trimmedInputValue);
                currentValueLabel.setText("  Current value: ");
                currentValueText.setText(result);
                expressionLabel.setText("      Expression: ");
                expressionText.setText(expressionText.getText() + " " + trimmedInputValue);
                inputField.setText("");
            }
        }
    }

    private void initializeLabels() {
        expressionLabel.setText(READY_PROMPT);
        expressionText.setText(" ");
        currentValueLabel.setText(" ");
        currentValueText.setText(" ");
    }

    private void clearTheCalculator() {
        calculator.enter("c");
        initializeLabels();
        inputField.setText(" ");
    }

    private void exitTheApplication() {
        frame.dispose();
    }

    private static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RPNGUI contentPane = new RPNGUI();
        contentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}