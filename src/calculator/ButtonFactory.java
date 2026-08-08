package calculator;

import javax.swing.*;
import java.awt.*;

public class ButtonFactory {


    // =========================================================
    // ROUND BUTTON
    // =========================================================

    public static JButton createRoundButton(
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
                new Font(
                        "Arial",
                        Font.PLAIN,
                        25
                )
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

        return button;
    }


    // =========================================================
    // NUMBER
    // =========================================================

    public static JButton createNumberButton(
            String text
    ) {

        return createRoundButton(
                text,
                new Color(52, 52, 54),
                Color.WHITE
        );
    }


    // =========================================================
    // SPECIAL
    // =========================================================

    public static JButton createSpecialButton(
            String text
    ) {

        JButton button =
                createRoundButton(
                        text,
                        new Color(20, 92, 137),
                        Color.WHITE
                );

        if (text.equals("←")) {

            button.setFont(
                    new Font(
                            "Segoe UI Symbol",
                            Font.PLAIN,
                            28
                    )
            );
        }

        return button;
    }


    // =========================================================
    // OPERATOR
    // =========================================================

    public static JButton createOperatorButton(
            String text
    ) {

        return createRoundButton(
                text,
                new Color(20, 92, 137),
                Color.WHITE
        );
    }


    // =========================================================
    // EQUAL
    // =========================================================

    public static JButton createEqualButton(
            String text
    ) {

        return createRoundButton(
                text,
                new Color(103, 210, 135),
                Color.BLACK
        );
    }
}