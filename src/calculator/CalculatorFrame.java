package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorFrame extends JFrame implements ActionListener {

    JLabel expressionLabel;
    JLabel resultLabel;

    JButton[] numberButtons = new JButton[10];

    JButton addButton;
    JButton subButton;
    JButton mulButton;
    JButton divButton;
    JButton equalButton;
    JButton clearButton;
    JButton dotButton;
    JButton backspaceButton;
    JButton bracketButton;
    JButton percentButton;

    String expression = "";

    boolean justCalculated = false;

    public CalculatorFrame() {

        setTitle("Calculator");
        setSize(450, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(18, 18, 18));

        // =====================================================
        // DISPLAY
        // =====================================================

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 1));
        displayPanel.setBackground(new Color(18, 18, 18));

        displayPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 10, 20)
        );

        // Upper line
        expressionLabel = new JLabel("");
        expressionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        expressionLabel.setForeground(new Color(150, 150, 150));
        expressionLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        // Lower line
        resultLabel = new JLabel("0");
        resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 45));

        displayPanel.add(expressionLabel);
        displayPanel.add(resultLabel);

        add(displayPanel, BorderLayout.NORTH);

        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(new Color(18, 18, 18));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // =====================================================
        // NUMBER BUTTONS
        // =====================================================

        for (int i = 0; i <= 9; i++) {
            numberButtons[i] =
                    createNumberButton(String.valueOf(i));
        }

        // =====================================================
        // OTHER BUTTONS
        // =====================================================

        clearButton = createSpecialButton("AC");
        bracketButton = createSpecialButton("()");
        percentButton = createSpecialButton("%");

        divButton = createOperatorButton("÷");
        mulButton = createOperatorButton("×");
        subButton = createOperatorButton("−");
        addButton = createOperatorButton("+");

        dotButton = createNumberButton(".");

        // Use arrow instead of ⌫ because Arial may show rectangle
        backspaceButton = createSpecialButton("←");

        // Use Segoe UI Symbol for the arrow
        backspaceButton.setFont(
                new Font("Segoe UI Symbol", Font.PLAIN, 28)
        );

        equalButton = createEqualButton("=");

        // =====================================================
        // BUTTON ORDER
        // =====================================================

        // Row 1
        buttonPanel.add(clearButton);
        buttonPanel.add(bracketButton);
        buttonPanel.add(percentButton);
        buttonPanel.add(divButton);

        // Row 2
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(mulButton);

        // Row 3
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subButton);

        // Row 4
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(addButton);

        // Row 5
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(dotButton);
        buttonPanel.add(backspaceButton);
        buttonPanel.add(equalButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }


    // =========================================================
    // ROUND BUTTON
    // =========================================================

    private JButton createRoundButton(
            String text,
            Color background,
            Color foreground
    ) {

        JButton button = new JButton(text) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 =
                        (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(
                        getModel().isRollover()
                                ? background.brighter()
                                : background
                );

                g2.fillOval(
                        0,
                        0,
                        getWidth(),
                        getHeight()
                );

                super.paintComponent(g2);

                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };

        button.setFont(
                new Font("Arial", Font.PLAIN, 25)
        );

        button.setForeground(foreground);
        button.setBackground(background);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.addActionListener(this);

        return button;
    }


    // =========================================================
    // NUMBER BUTTON
    // =========================================================

    private JButton createNumberButton(String text) {

        return createRoundButton(
                text,
                new Color(52, 52, 54),
                Color.WHITE
        );
    }


    // =========================================================
    // SPECIAL BUTTON
    // =========================================================

    private JButton createSpecialButton(String text) {

        return createRoundButton(
                text,
                new Color(20, 92, 137),
                Color.WHITE
        );
    }


    // =========================================================
    // OPERATOR BUTTON
    // =========================================================

    private JButton createOperatorButton(String text) {

        return createRoundButton(
                text,
                new Color(20, 92, 137),
                Color.WHITE
        );
    }


    // =========================================================
    // EQUAL BUTTON
    // =========================================================

    private JButton createEqualButton(String text) {

        return createRoundButton(
                text,
                new Color(103, 210, 135),
                Color.BLACK
        );
    }


    // =========================================================
    // FORMAT NUMBER
    // =========================================================

    private String formatNumber(double number) {

        if (number == (long) number) {
            return String.valueOf((long) number);
        } else {
            return String.valueOf(number);
        }
    }


    // =========================================================
    // ACTION PERFORMED
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();


        // =====================================================
        // NUMBER BUTTONS
        // =====================================================

        for (int i = 0; i <= 9; i++) {

            if (source == numberButtons[i]) {

                // If calculation is already completed,
                // start a new calculation
                if (justCalculated) {

                    expression = "";
                    expressionLabel.setText("");
                    justCalculated = false;
                }

                expression += i;

                resultLabel.setText(
                        displayExpression(expression)
                );

                return;
            }
        }


        // =====================================================
        // DOT
        // =====================================================

        if (source == dotButton) {

            if (justCalculated) {

                expression = "";
                expressionLabel.setText("");
                justCalculated = false;
            }

            // Find the current number after the last operator
            String currentNumber = getCurrentNumber();

            if (!currentNumber.contains(".")) {

                if (expression.isEmpty()
                        || isOperator(expression.charAt(expression.length() - 1))
                        || expression.endsWith("(")) {

                    expression += "0.";

                } else {

                    expression += ".";
                }
            }

            resultLabel.setText(
                    displayExpression(expression)
            );

            return;
        }


        // =====================================================
        // OPERATOR BUTTONS
        // =====================================================

        if (source == addButton ||
                source == subButton ||
                source == mulButton ||
                source == divButton) {

            String newOperator;

            if (source == addButton) {
                newOperator = "+";
            } else if (source == subButton) {
                newOperator = "-";
            } else if (source == mulButton) {
                newOperator = "*";
            } else {
                newOperator = "/";
            }


            // If result was just calculated,
            // continue calculation from result
            if (justCalculated) {

                expression = resultLabel.getText();
                justCalculated = false;
            }


            if (expression.isEmpty()) {

                // Don't allow operator at the beginning
                if (!newOperator.equals("-")) {
                    return;
                }

            } else {

                char last =
                        expression.charAt(expression.length() - 1);

                // Replace previous operator
                if (isOperator(last)) {

                    expression =
                            expression.substring(
                                    0,
                                    expression.length() - 1
                            );
                }

                // Don't put operator directly after (
                if (expression.endsWith("(")
                        && !newOperator.equals("-")) {
                    return;
                }
            }

            expression += newOperator;

            resultLabel.setText(
                    displayExpression(expression)
            );

            return;
        }


        // =====================================================
        // EQUAL
        // =====================================================

        if (source == equalButton) {

            if (expression.isEmpty()) {
                return;
            }

            try {

                // Remove unnecessary operator at the end
                while (!expression.isEmpty()
                        && isOperator(
                        expression.charAt(expression.length() - 1))) {

                    expression =
                            expression.substring(
                                    0,
                                    expression.length() - 1
                            );
                }

                if (expression.isEmpty()) {
                    return;
                }

                double result =
                        evaluateExpression(expression);

                String formattedResult =
                        formatNumber(result);

                // Upper line = complete expression
                expressionLabel.setText(
                        displayExpression(expression)
                );

                // Lower line = result
                resultLabel.setText(
                        formattedResult
                );


// Show calculation in console
                System.out.println(
                        displayExpression(expression)
                                + " = "
                                + formattedResult
                );



                // Save result for next calculation
                expression = formattedResult;

                justCalculated = true;

            } catch (Exception ex) {

                resultLabel.setText("Error");
                justCalculated = true;
            }

            return;
        }


        // =====================================================
        // CLEAR
        // =====================================================

        if (source == clearButton) {

            expression = "";

            expressionLabel.setText("");
            resultLabel.setText("0");

            justCalculated = false;

            return;
        }


        // =====================================================
        // BACKSPACE
        // =====================================================

        if (source == backspaceButton) {

            if (justCalculated) {

                expression = "";
                resultLabel.setText("0");
                expressionLabel.setText("");

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

                    resultLabel.setText("0");

                } else {

                    resultLabel.setText(
                            displayExpression(expression)
                    );
                }
            }

            return;
        }


        // =====================================================
        // PERCENTAGE
        // =====================================================

        if (source == percentButton) {

            if (!expression.isEmpty()) {

                try {

                    String currentNumber =
                            getCurrentNumber();

                    if (!currentNumber.isEmpty()) {

                        double value =
                                Double.parseDouble(currentNumber);

                        value = value / 100;

                        String newValue =
                                formatNumber(value);

                        expression =
                                expression.substring(
                                        0,
                                        expression.length()
                                                - currentNumber.length()
                                );

                        expression += newValue;

                        resultLabel.setText(
                                displayExpression(expression)
                        );
                    }

                } catch (Exception ex) {

                    resultLabel.setText("Error");
                }
            }

            return;
        }


        // =====================================================
        // BRACKET
        // =====================================================

        if (source == bracketButton) {

            handleBracket();

            resultLabel.setText(
                    displayExpression(expression)
            );

            return;
        }
    }


    // =========================================================
    // BRACKET HANDLING
    // =========================================================

    private void handleBracket() {

        if (justCalculated) {

            expression = "";
            expressionLabel.setText("");
            justCalculated = false;
        }

        if (expression.isEmpty()) {

            expression = "(";
            return;
        }

        char last =
                expression.charAt(expression.length() - 1);


        // If last character is operator or (
        // then open bracket
        if (isOperator(last) || last == '(') {

            expression += "(";
            return;
        }


        // If last character is a digit or )
        // then close bracket
        if (Character.isDigit(last)
                || last == ')'
                || last == '%') {

            int open =
                    countCharacter(expression, '(');

            int close =
                    countCharacter(expression, ')');

            if (open > close) {

                expression += ")";
            }
        }
    }


    // =========================================================
    // EXPRESSION EVALUATOR
    // =========================================================

    private double evaluateExpression(String text) {

        String cleaned =
                text.replace("×", "*")
                        .replace("÷", "/")
                        .replace("−", "-");

        Parser parser =
                new Parser(cleaned);

        double value =
                parser.parseExpression();

        if (parser.hasRemaining()) {
            throw new RuntimeException("Invalid expression");
        }

        return value;
    }


    // =========================================================
    // RECURSIVE DESCENT PARSER
    // =========================================================

    private static class Parser {

        private final String input;
        private int position = 0;

        Parser(String input) {
            this.input = input;
        }


        double parseExpression() {

            double value =
                    parseTerm();

            while (position < input.length()) {

                char op =
                        input.charAt(position);

                if (op == '+') {

                    position++;

                    value += parseTerm();

                } else if (op == '-') {

                    position++;

                    value -= parseTerm();

                } else {

                    break;
                }
            }

            return value;
        }


        double parseTerm() {

            double value =
                    parseFactor();

            while (position < input.length()) {

                char op =
                        input.charAt(position);

                if (op == '*') {

                    position++;

                    value *= parseFactor();

                } else if (op == '/') {

                    position++;

                    double divisor =
                            parseFactor();

                    if (divisor == 0) {
                        throw new ArithmeticException(
                                "Cannot divide by zero"
                        );
                    }

                    value /= divisor;

                } else {

                    break;
                }
            }

            return value;
        }


        double parseFactor() {

            skipSpaces();

            // Handle negative number
            if (position < input.length()
                    && input.charAt(position) == '-') {

                position++;

                return -parseFactor();
            }


            // Handle brackets
            if (position < input.length()
                    && input.charAt(position) == '(') {

                position++;

                double value =
                        parseExpression();

                skipSpaces();

                if (position >= input.length()
                        || input.charAt(position) != ')') {

                    throw new RuntimeException(
                            "Missing closing bracket"
                    );
                }

                position++;

                return value;
            }


            // Handle number
            int start = position;

            while (position < input.length()
                    && (Character.isDigit(
                    input.charAt(position))
                    || input.charAt(position) == '.')) {

                position++;
            }

            if (start == position) {

                throw new RuntimeException(
                        "Number expected"
                );
            }

            return Double.parseDouble(
                    input.substring(start, position)
            );
        }


        void skipSpaces() {

            while (position < input.length()
                    && Character.isWhitespace(
                    input.charAt(position))) {

                position++;
            }
        }


        boolean hasRemaining() {

            skipSpaces();

            return position < input.length();
        }
    }


    // =========================================================
    // HELPER METHODS
    // =========================================================

    private boolean isOperator(char c) {

        return c == '+'
                || c == '-'
                || c == '*'
                || c == '/';
    }


    private String getCurrentNumber() {

        int i = expression.length() - 1;

        while (i >= 0
                && (Character.isDigit(expression.charAt(i))
                || expression.charAt(i) == '.')) {

            i--;
        }

        return expression.substring(i + 1);
    }


    private int countCharacter(
            String text,
            char character
    ) {

        int count = 0;

        for (char c : text.toCharArray()) {

            if (c == character) {
                count++;
            }
        }

        return count;
    }


    private String displayExpression(
            String text
    ) {

        return text
                .replace("*", "×")
                .replace("/", "÷")
                .replace("-", "−");
    }

}
