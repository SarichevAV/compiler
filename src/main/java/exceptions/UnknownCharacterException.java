package exceptions;

public class UnknownCharacterException extends Exception {
    private static final String EXCEPTION_PATTERN =
            "Unknown character (%d, %d) : '%c'\n";

    public UnknownCharacterException(int numberLine, int numberSymbol,
                                     char symbol) {
        super(String.format(EXCEPTION_PATTERN, numberLine,
                numberSymbol, symbol));
    }
}
