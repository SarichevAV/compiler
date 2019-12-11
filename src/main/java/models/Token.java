package models;

public class Token {
    private int lineNumber;
    private int symbolNumber;
    private String value;
    private TokenNames tokenName;

    public Token(String value, TokenNames tokenName) {
        this.value = value;
        this.tokenName = tokenName;
    }

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

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRightToken(TokenNames expTokenNames) {
        return tokenName.equals(expTokenNames);
    }

    public boolean isRightValue(String expValue) {
        return value.equals(expValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (lineNumber != token.lineNumber) return false;
        if (symbolNumber != token.symbolNumber) return false;
        if (value != null ? !value.equals(token.value) : token.value != null)
            return false;
        return tokenName == token.tokenName;
    }

    @Override
    public int hashCode() {
        int result = lineNumber;
        result = 31 * result + symbolNumber;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (tokenName != null ? tokenName.hashCode() : 0);
        return result;
    }
}
