package analyzers.syntax;

import analyzers.lexem.models.Token;
import analyzers.lexem.models.TokenNames;
import exceptions.ExpectedException;

import java.util.Iterator;
import java.util.List;

public class SyntaxAnalyzer {
    private final static String INTEGER_KWORD = "Integer";
    private final static String BEGIN_KWORD = "Begin";
    private final static String END_KWORD = "End";
    private final static String SEMICOLON_DELIM = ";";
    private final static String SIGN_ASSIGN = ":=";
    private final static String SIGN_MINUS = "-";
    private final static String LEFT_BRACKET = "(";
    private final static String RIGHT_BRACKET = ")";

    private Iterator<Token> tokens;
    private Token token;

    public void analyze(List<Token> tokenList) throws ExpectedException {
        tokens = tokenList.iterator();
        nextToken();
        program();
    }

    private void program() throws ExpectedException {
        integerKword();
        listVars();
        nextToken();
        beginKword();
        listAssigns();
        endKword();
        System.out.println("The syntax is correct");
    }

    private void integerKword() throws ExpectedException {
        if (!(token.isRightToken(TokenNames.KWORD)
                && token.isRightValue(INTEGER_KWORD))) {
            throw new ExpectedException(token, INTEGER_KWORD);
        }
        nextToken();
    }

    private void beginKword() throws ExpectedException {
        if (!(token.isRightToken(TokenNames.KWORD)
                && token.isRightValue(BEGIN_KWORD))) {
            throw new ExpectedException(token, BEGIN_KWORD);
        }
        nextToken();
    }

    private void endKword() throws ExpectedException {
        if (!(token.isRightToken(TokenNames.KWORD)
                && token.isRightValue(END_KWORD))) {
            throw new ExpectedException(token, END_KWORD);
        }
    }

    private void listVars() throws ExpectedException {
        identifier();
        delimiter();
        if (!token.isRightValue(SEMICOLON_DELIM)) {
            nextToken();
            listVars();
        }
    }

    private void identifier() throws ExpectedException {
        if (!token.isRightToken(TokenNames.IDENT)) {
            throw new ExpectedException(token, TokenNames.IDENT);
        }
        nextToken();
    }

    // Analyze delimiter , and ;
    private void delimiter() throws ExpectedException {
        if (!token.isRightToken(TokenNames.DELIM)) {
            if (token.isRightToken(TokenNames.KWORD)) {
                throw new ExpectedException(token, SEMICOLON_DELIM);
            }
            throw new ExpectedException(token, TokenNames.DELIM);
        }
    }

    // Analyze list of assignments
    private void listAssigns() throws ExpectedException {
        identifier();
        asgn();
        maybeMinus();
        subexp();
        semicolonInEndExpression();
        if (token.isRightToken(TokenNames.IDENT)) {
            listAssigns();
        }
    }

    private boolean isOperation() {
        return token.isRightToken(TokenNames.OPERL)
                || token.isRightToken(TokenNames.OPERH);
    }

    private void subexp() throws ExpectedException {
        if (isleftBracket()) {
            nextToken();
            maybeMinus();
            subexp();
            rightBracket();
            if (isOperation()) {
                nextToken();
                subexp();
            }
        } else if (isOperand()) {
            nextToken();
            if (isOperation()) {
                nextToken();
                subexp();
            }
        } else {
            throw new ExpectedException(token,"OPERAND or LEFT BRACKET");
        }
    }

    private void semicolonInEndExpression() throws ExpectedException {
        if (!(token.isRightToken(TokenNames.DELIM)
                && token.isRightValue(SEMICOLON_DELIM))) {
            throw new ExpectedException(token, SEMICOLON_DELIM);
        }
        nextToken();
    }

    private void asgn() throws ExpectedException {
        if (!token.isRightToken(TokenNames.ASGN)) {
            throw new ExpectedException(token, SIGN_ASSIGN);
        }
        nextToken();
    }

    private void maybeMinus() {
        if (token.isRightToken(TokenNames.OPERL)
                && token.isRightValue(SIGN_MINUS)) {
            nextToken();
        }
    }

    private void rightBracket() throws ExpectedException {
        if (!(token.isRightToken(TokenNames.DELIM)
                && token.isRightValue(RIGHT_BRACKET))) {
            throw new ExpectedException(token, RIGHT_BRACKET);
        }
        nextToken();
    }

    private boolean isOperand(){
        return token.isRightToken(TokenNames.IDENT)
                || token.isRightToken(TokenNames.CONST);
    }

    private boolean isleftBracket() throws ExpectedException {
        return token.isRightToken(TokenNames.DELIM)
                && token.isRightValue(LEFT_BRACKET);
    }

    private void nextToken() {
        if (tokens.hasNext()) {
            token = tokens.next();
        } else {
            token = null;
            System.err.println("The next token not found");
        }
    }
}