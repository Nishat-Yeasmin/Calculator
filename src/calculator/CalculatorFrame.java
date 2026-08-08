package calculator;

import javax.swing.*;
import java.awt.*;

public class CalculatorFrame extends JFrame {

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

    CalculatorController controller;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public CalculatorFrame() {

        setTitle("Calculator");

        setSize(450, 650);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        getContentPane().setBackground(
                new Color(18, 18, 18)
        );

        createDisplay();

        createButtons();

        controller =
                new CalculatorController(this);

        setVisible(true);
    }


    // =========================================================
    // DISPLAY
    // =========================================================

    private void createDisplay() {

        JPanel displayPanel = new JPanel();

        displayPanel.setLayout(
                new GridLayout(2, 1)
        );

        displayPanel.setBackground(
                new Color(18, 18, 18)
        );

        displayPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 10, 20
                )
        );


        expressionLabel = new JLabel("");

        expressionLabel.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        expressionLabel.setForeground(
                new Color(150, 150, 150)
        );

        expressionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        25
                )
        );


        resultLabel = new JLabel("0");

        resultLabel.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        resultLabel.setForeground(Color.WHITE);

        resultLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        45
                )
        );


        displayPanel.add(expressionLabel);
        displayPanel.add(resultLabel);

        add(
                displayPanel,
                BorderLayout.NORTH
        );
    }


    // =========================================================
    // BUTTONS
    // =========================================================

    private void createButtons() {

        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(
                        5,
                        4,
                        10,
                        10
                )
        );

        panel.setBackground(
                new Color(18, 18, 18)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );


        // Numbers

        for (int i = 0; i <= 9; i++) {

            numberButtons[i] =
                    ButtonFactory.createNumberButton(
                            String.valueOf(i)
                    );

            panel.add(numberButtons[i]);
        }

        panel.removeAll();


        // Special buttons

        clearButton =
                ButtonFactory.createSpecialButton("AC");

        bracketButton =
                ButtonFactory.createSpecialButton("()");

        percentButton =
                ButtonFactory.createSpecialButton("%");

        backspaceButton =
                ButtonFactory.createSpecialButton("←");


        // Operators

        divButton =
                ButtonFactory.createOperatorButton("÷");

        mulButton =
                ButtonFactory.createOperatorButton("×");

        subButton =
                ButtonFactory.createOperatorButton("−");

        addButton =
                ButtonFactory.createOperatorButton("+");


        // Dot

        dotButton =
                ButtonFactory.createNumberButton(".");


        // Equal

        equalButton =
                ButtonFactory.createEqualButton("=");


        // =====================================================
        // ROW 1
        // =====================================================

        panel.add(clearButton);
        panel.add(bracketButton);
        panel.add(percentButton);
        panel.add(divButton);


        // =====================================================
        // ROW 2
        // =====================================================

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);


        // =====================================================
        // ROW 3
        // =====================================================

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);


        // =====================================================
        // ROW 4
        // =====================================================

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);


        // =====================================================
        // ROW 5
        // =====================================================

        panel.add(numberButtons[0]);
        panel.add(dotButton);
        panel.add(backspaceButton);
        panel.add(equalButton);


        add(
                panel,
                BorderLayout.CENTER
        );
    }
}