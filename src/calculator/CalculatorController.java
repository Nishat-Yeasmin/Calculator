package calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController
        implements ActionListener {


    private final CalculatorFrame frame;

    private final CalculatorLogic logic;


    private String expression = "";

    private boolean justCalculated = false;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public CalculatorController(
            CalculatorFrame frame
    ) {

        this.frame = frame;

        this.logic =
                new CalculatorLogic();


        addListeners();
    }


    // =========================================================
    // ADD LISTENERS
    // =========================================================

    private void addListeners() {

        for (JButton button :
                frame.numberButtons) {

            button.addActionListener(this);
        }


        frame.addButton.addActionListener(this);
        frame.subButton.addActionListener(this);
        frame.mulButton.addActionListener(this);
        frame.divButton.addActionListener(this);

        frame.equalButton.addActionListener(this);
        frame.clearButton.addActionListener(this);
        frame.dotButton.addActionListener(this);
        frame.backspaceButton.addActionListener(this);
        frame.bracketButton.addActionListener(this);
        frame.percentButton.addActionListener(this);
    }


    // =========================================================
    // ACTION
    // =========================================================

    @Override
    public void actionPerformed(
            ActionEvent e
    ) {

        Object source =
                e.getSource();


        // NUMBER

        for (int i = 0; i <= 9; i++) {

            if (source ==
                    frame.numberButtons[i]) {

                handleNumber(i);

                return;
            }
        }


        // DOT

        if (source == frame.dotButton) {

            handleDot();

            return;
        }


        // OPERATOR

        if (source == frame.addButton
                || source == frame.subButton
                || source == frame.mulButton
                || source == frame.divButton) {

            handleOperator(source);

            return;
        }


        // EQUAL

        if (source == frame.equalButton) {

            handleEqual();

            return;
        }


        // CLEAR

        if (source == frame.clearButton) {

            handleClear();

            return;
        }


        // BACKSPACE

        if (source ==
                frame.backspaceButton) {

            handleBackspace();

            return;
        }


        // PERCENT

        if (source ==
                frame.percentButton) {

            handlePercent();

            return;
        }


        // BRACKET

        if (source ==
                frame.bracketButton) {

            handleBracket();
        }
    }


    // =========================================================
    // NUMBER
    // =========================================================

    private void handleNumber(
            int number
    ) {

        if (justCalculated) {

            expression = "";

            frame.expressionLabel.setText("");

            justCalculated = false;
        }


        expression += number;


        showExpression();
    }


    // =========================================================
    // DOT
    // =========================================================

    private void handleDot() {

        if (justCalculated) {

            expression = "";

            frame.expressionLabel.setText("");

            justCalculated = false;
        }


        String current =
                getCurrentNumber();


        if (!current.contains(".")) {

            if (expression.isEmpty()
                    || isOperator(
                    expression.charAt(
                            expression.length() - 1
                    ))
                    || expression.endsWith("(")) {

                expression += "0.";

            }

            else {

                expression += ".";
            }
        }


        showExpression();
    }


    // =========================================================
    // OPERATOR
    // =========================================================

    private void handleOperator(
            Object source
    ) {

        String operator;


        if (source == frame.addButton) {

            operator = "+";

        }

        else if (source ==
                frame.subButton) {

            operator = "-";

        }

        else if (source ==
                frame.mulButton) {

            operator = "*";

        }

        else {

            operator = "/";
        }


        if (justCalculated) {

            expression =
                    frame.resultLabel.getText();

            justCalculated = false;
        }


        if (expression.isEmpty()) {

            if (!operator.equals("-")) {

                return;
            }
        }


        else {

            char last =
                    expression.charAt(
                            expression.length() - 1
                    );


            if (isOperator(last)) {

                expression =
                        expression.substring(
                                0,
                                expression.length() - 1
                        );
            }


            if (expression.endsWith("(")
                    && !operator.equals("-")) {

                return;
            }
        }


        expression += operator;


        showExpression();
    }


    // =========================================================
    // EQUAL
    // =========================================================

    private void handleEqual() {

        if (expression.isEmpty()) {

            return;
        }


        try {

            removeLastOperator();


            if (expression.isEmpty()) {

                return;
            }


            double result =
                    logic.calculate(
                            expression
                    );


            String formatted =
                    logic.formatNumber(
                            result
                    );


            frame.expressionLabel.setText(
                    logic.displayExpression(
                            expression
                    )
            );


            setResultText(formatted);


            // Console

            System.out.println(
                    logic.displayExpression(
                            expression
                    )
                            + " = "
                            + formatted
            );


            expression = formatted;

            justCalculated = true;


        } catch (Exception ex) {

            frame.expressionLabel.setText(
                    logic.displayExpression(
                            expression
                    )
            );


            setResultText("Error");


            System.out.println(
                    logic.displayExpression(
                            expression
                    )
                            + " = Error"
            );


            justCalculated = true;
        }
    }


    // =========================================================
    // CLEAR
    // =========================================================

    private void handleClear() {

        expression = "";

        frame.expressionLabel.setText("");

        setResultText("0");

        justCalculated = false;
    }


    // =========================================================
    // BACKSPACE
    // =========================================================

    private void handleBackspace() {

        if (justCalculated) {

            expression = "";

            frame.expressionLabel.setText("");

            setResultText("0");

            justCalculated = false;

            return;
        }


        if (!expression.isEmpty()) {

            expression =
                    expression.substring(
                            0,
                            expression.length() - 1
                    );


            if (expression.isEmpty()) {

                setResultText("0");
            }

            else {

                showExpression();
            }
        }
    }


    // =========================================================
    // PERCENT
    // =========================================================

    private void handlePercent() {

        if (expression.isEmpty()) {

            return;
        }


        try {

            String current =
                    getCurrentNumber();


            if (!current.isEmpty()) {

                double value =
                        Double.parseDouble(current);


                value /= 100;


                String newValue =
                        logic.formatNumber(value);


                expression =
                        expression.substring(
                                0,
                                expression.length()
                                        - current.length()
                        );


                expression += newValue;


                showExpression();
            }

        } catch (Exception ex) {

            setResultText("Error");
        }
    }


    // =========================================================
    // BRACKET
    // =========================================================

    private void handleBracket() {

        if (justCalculated) {

            expression = "";

            frame.expressionLabel.setText("");

            justCalculated = false;
        }


        if (expression.isEmpty()) {

            expression = "(";

            showExpression();

            return;
        }


        char last =
                expression.charAt(
                        expression.length() - 1
                );


        if (isOperator(last)
                || last == '(') {

            expression += "(";

        }

        else if (
                Character.isDigit(last)
                        || last == ')'
                        || last == '%'
        ) {

            int open =
                    countCharacter(
                            expression,
                            '('
                    );


            int close =
                    countCharacter(
                            expression,
                            ')'
                    );


            if (open > close) {

                expression += ")";
            }
        }


        showExpression();
    }


    // =========================================================
    // SHOW EXPRESSION
    // =========================================================

    private void showExpression() {

        setResultText(
                logic.displayExpression(
                        expression
                )
        );
    }


    // =========================================================
    // RESULT FONT
    // =========================================================

    private void setResultText(
            String text
    ) {

        frame.resultLabel.setText(text);


        int length =
                text.length();


        int size;


        if (length <= 8) {

            size = 45;

        }

        else if (length <= 12) {

            size = 38;

        }

        else if (length <= 16) {

            size = 32;

        }

        else if (length <= 20) {

            size = 27;

        }

        else {

            size = 23;
        }


        frame.resultLabel.setFont(
                new java.awt.Font(
                        "Arial",
                        java.awt.Font.BOLD,
                        size
                )
        );
    }


    // =========================================================
    // HELPER
    // =========================================================

    private boolean isOperator(
            char c
    ) {

        return c == '+'
                || c == '-'
                || c == '*'
                || c == '/';
    }


    private String getCurrentNumber() {

        int i =
                expression.length() - 1;


        while (i >= 0
                && (
                Character.isDigit(
                        expression.charAt(i)
                )
                        || expression.charAt(i) == '.'
        )) {

            i--;
        }


        return expression.substring(i + 1);
    }


    private int countCharacter(
            String text,
            char character
    ) {

        int count = 0;


        for (char c :
                text.toCharArray()) {

            if (c == character) {

                count++;
            }
        }


        return count;
    }


    private void removeLastOperator() {

        while (
                !expression.isEmpty()
                        && isOperator(
                        expression.charAt(
                                expression.length() - 1
                        )
                )
        ) {

            expression =
                    expression.substring(
                            0,
                            expression.length() - 1
                    );
        }
    }
}