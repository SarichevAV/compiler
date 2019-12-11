package exceptions;

import models.Token;
import models.TokenNames;

public class ExpectedException extends Exception {
    private static final String EXPECTED_VALUE_PATTERN =
            "Error:(%d, %d) expected \"%s\" but get \"%s\"\n";
    private static final String NOT_FOUND_PATTERN =
            "Error:(%d, %d) %s not found\n";

    public ExpectedException(Token token, String expected) {
        super(String.format(EXPECTED_VALUE_PATTERN, token.getLineNumber(),
                token.getSymbolNumber(), expected, token.getValue()));
    }

    public ExpectedException(Token token, TokenNames tokenNames) {
        super(String.format(NOT_FOUND_PATTERN, token.getLineNumber(),
                token.getSymbolNumber(), tokenNames.getName()));
    }
}
