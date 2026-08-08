package calculator;

public class CalculatorLogic {


    // =========================================================
    // CALCULATE
    // =========================================================

    public double calculate(
            String expression
    ) {

        String cleaned =
                expression
                        .replace("×", "*")
                        .replace("÷", "/")
                        .replace("−", "-");


        ExpressionParser parser =
                new ExpressionParser(cleaned);


        double result =
                parser.parseExpression();


        if (parser.hasRemaining()) {

            throw new RuntimeException(
                    "Invalid expression"
            );
        }


        return result;
    }


    // =========================================================
    // FORMAT NUMBER
    // =========================================================

    public String formatNumber(
            double number
    ) {

        if (number == (long) number) {

            return String.valueOf(
                    (long) number
            );
        }


        return String.valueOf(number);
    }


    // =========================================================
    // DISPLAY EXPRESSION
    // =========================================================

    public String displayExpression(
            String text
    ) {

        return text
                .replace("*", "×")
                .replace("/", "÷")
                .replace("-", "−");
    }
}