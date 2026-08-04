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


    double num1;
    double num2;
    double result;

    String operator = "";
    String expression = "";


    public CalculatorFrame() {


        setTitle("Calculator");

        setSize(450,650);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10,10));

        getContentPane().setBackground(new Color(25,25,25));

        // ================= DISPLAY PANEL =================

        JPanel displayPanel = new JPanel();

        displayPanel.setLayout(new GridLayout(2,1));

        displayPanel.setBackground(new Color(25,25,25));


        expressionLabel = new JLabel("");

        expressionLabel.setHorizontalAlignment(JLabel.RIGHT);

        expressionLabel.setForeground(Color.GRAY);

        expressionLabel.setFont(
                new Font("Arial",Font.PLAIN,25)
        );


        resultLabel = new JLabel("0");

        resultLabel.setHorizontalAlignment(JLabel.RIGHT);

        resultLabel.setForeground(Color.WHITE);

        resultLabel.setFont(
                new Font("Arial",Font.BOLD,45)
        );


        displayPanel.add(expressionLabel);

        displayPanel.add(resultLabel);


        add(displayPanel,BorderLayout.NORTH);


        // ================= BUTTON PANEL =================


        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new GridLayout(5,4,10,10));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(20,20,20,20)
        );


        buttonPanel.setBackground(
                new Color(25,25,25)
        );



        // Number Buttons

        for(int i=0;i<=9;i++){

            numberButtons[i] =
                    new JButton(String.valueOf(i));

            numberButtons[i].setFont(
                    new Font("Arial",Font.BOLD,25)
            );

            numberButtons[i].addActionListener(this);

        }

        addButton = createButton("+");
        subButton = createButton("-");
        mulButton = createButton("*");
        divButton = createButton("/");

        equalButton = createButton("=");

        clearButton = createButton("AC");

        dotButton = createButton(".");



        // Button order


        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(divButton);


        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(mulButton);


        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(subButton);


        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(dotButton);
        buttonPanel.add(equalButton);
        buttonPanel.add(addButton);


        buttonPanel.add(clearButton);


        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());

        mainPanel.setPreferredSize(
                new Dimension(450,600)
        );


        mainPanel.setBackground(
                new Color(25,25,25)
        );


        mainPanel.add(buttonPanel, BorderLayout.CENTER);


        add(mainPanel, BorderLayout.CENTER);



        setVisible(true);

    }



    // Button design

    private JButton createButton(String text){

        JButton button = new JButton(text);


        button.setFont(
                new Font("Arial",Font.BOLD,25)
        );


        button.setBackground(
                new Color(70,70,70)
        );


        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );


        button.addActionListener(this);

        button.addMouseListener(new java.awt.event.MouseAdapter(){

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e){

                button.setBackground(
                        new Color(120,120,120)
                );

            }


            @Override
            public void mouseExited(java.awt.event.MouseEvent e){

                button.setBackground(
                        new Color(70,70,70)
                );

            }

        });

        return button;

    }

    private String formatNumber(double number){

        if(number == (int)number){
            return String.valueOf((int)number);
        }
        else{
            return String.valueOf(number);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e){


        Object source = e.getSource();



        // Number

        for(int i=0;i<=9;i++){

            if(source == numberButtons[i]){

                expression += i;

                if(!operator.equals("")){
                    expressionLabel.setText(
                            formatNumber(num1) + " " + operator + " " + expression
                    );
                }
                else{
                    expressionLabel.setText(expression);
                }

            }

        }


        // Dot

        if(source == dotButton){
            expression += ".";

            if(!operator.equals("")){
                expressionLabel.setText(
                        formatNumber(num1) + " " + operator + " " + expression
                );
            }
            else{
                expressionLabel.setText(expression);
            }
        }

        // Operators


        if(source == addButton ||
                source == subButton ||
                source == mulButton ||
                source == divButton){


            num1 =
                    Double.parseDouble(expression);


            if(source == addButton)
                operator="+";


            else if(source == subButton)
                operator="-";


            else if(source == mulButton)
                operator="*";


            else if(source == divButton)
                operator="/";

            expression = "";


            expressionLabel.setText(
                    formatNumber(num1) + " " + operator
            );

        }

        // Equal


        if(source == equalButton){


            num2 = Double.parseDouble(expression);

            switch(operator){

                case "+":
                    result=num1+num2;
                    break;


                case "-":
                    result=num1-num2;
                    break;


                case "*":
                    result=num1*num2;
                    break;


                case "/":

                    if(num2==0){

                        resultLabel.setText("Error");
                        System.out.println(formatNumber(num1) + "/" + formatNumber(num2) + " = Error" );

                        return;

                    }

                    result=num1/num2;

                    break;

            }
//
            resultLabel.setText(formatNumber(result));

            System.out.println(
                    formatNumber(num1) + " "
                            + operator + " "
                            + formatNumber(num2)
                            + " = "
                            + formatNumber(result)
            );

        }

        // Clear

        if(source == clearButton){


            expression="";

            expressionLabel.setText("");

            resultLabel.setText("0");


            num1=0;
            num2=0;
            result=0;

            operator="";
        }
    }
}