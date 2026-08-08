package calculator;

public class ExpressionParser {

    private final String input;

    private int position = 0;


    public ExpressionParser(String input) {

        this.input = input;
    }


    // =========================================================
    // EXPRESSION
    // =========================================================

    public double parseExpression() {

        double value = parseTerm();


        while (position < input.length()) {

            char op =
                    input.charAt(position);


            if (op == '+') {

                position++;

                value += parseTerm();

            }

            else if (op == '-') {

                position++;

                value -= parseTerm();

            }

            else {

                break;
            }
        }


        return value;
    }


    // =========================================================
    // TERM
    // =========================================================

    private double parseTerm() {

        double value =
                parseFactor();


        while (position < input.length()) {

            char op =
                    input.charAt(position);


            if (op == '*') {

                position++;

                value *= parseFactor();
            }

            else if (op == '/') {

                position++;

                double divisor =
                        parseFactor();


                if (divisor == 0) {

                    throw new ArithmeticException(
                            "Cannot divide by zero"
                    );
                }


                value /= divisor;
            }

            else {

                break;
            }
        }


        return value;
    }


    // =========================================================
    // FACTOR
    // =========================================================

    private double parseFactor() {

        skipSpaces();


        // Negative number

        if (position < input.length()
                && input.charAt(position) == '-') {

            position++;

            return -parseFactor();
        }


        // Bracket

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


        // Number

        int start = position;


        while (position < input.length()
                && (
                Character.isDigit(
                        input.charAt(position)
                )
                        || input.charAt(position) == '.'
        )) {

            position++;
        }


        if (start == position) {

            throw new RuntimeException(
                    "Number expected"
            );
        }


        return Double.parseDouble(
                input.substring(
                        start,
                        position
                )
        );
    }


    // =========================================================
    // SPACE
    // =========================================================

    private void skipSpaces() {

        while (
                position < input.length()
                        && Character.isWhitespace(
                        input.charAt(position)
                )
        ) {

            position++;
        }
    }


    // =========================================================
    // REMAINING
    // =========================================================

    public boolean hasRemaining() {

        skipSpaces();

        return position < input.length();
    }
}