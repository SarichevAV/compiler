package exceptions;

public class TokenIsNotIdentifiedException extends Exception {
    private static final String TOKEN_NOT_INIT_PATTERN =
            "Token \"%s\" is not initialized";

    public TokenIsNotIdentifiedException(String ident) {
        super(String.format(TOKEN_NOT_INIT_PATTERN, ident));
    }
}
