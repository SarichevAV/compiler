package analyzers.lexem.models;

public class Token {
    private int lineNumber;
    private int symbolNumber;
    private String value;
    private TokenNames tokenName;

    public Token(int lineNumber, int symbolNumber, String value) {
        this.lineNumber = lineNumber;
        this.symbolNumber = symbolNumber;
        this.value = value;
    }

    public Token(int lineNumber, int symbolNumber, String value, TokenNames tokenName) {
        this.lineNumber = lineNumber;
        this.symbolNumber = symbolNumber;
        this.value = value;
        this.tokenName = tokenName;
    }

    public void setTokenName(TokenNames tokenName) {
        this.tokenName = tokenName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getValue() {
        return value;
    }

    public TokenNames getTokenName() {
        return tokenName;
    }

    public int getSymbolNumber() {
        return symbolNumber;
    }

    public boolean isRightToken(TokenNames expTokenNames) {
        return tokenName.equals(expTokenNames);
    }

    public boolean isRightValue(String expValue) {
        return value.equals(expValue);
    }
}
