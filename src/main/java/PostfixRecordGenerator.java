import analyzers.lexem.models.Token;
import analyzers.lexem.models.TokenNames;
import exceptions.ExpectedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class PostfixRecordGenerator {
    private final static String SIGN_ASSIGN = ":=";
    private final static String SEMICOLON_DELIM = ";";
    private final static String RIGHT_BRACKET = ")";
    private final static String END_KWORD = "End";

    private List<String> postfixRecords;
    private Iterator<Token> tokens;
    private Token token;

    public PostfixRecordGenerator() {
        postfixRecords = new ArrayList<>();
    }

    public List<String> generatePostfixRecords(List<Token> tokenList) throws ExpectedException {
        tokens = tokenList.iterator();
        nextToken();
         while (!isEnd()) {
            ScrollToNextAsgn();
            nextToken();
            String record = generatePostfixRecord();
            postfixRecords.add("Expression on line №"+ token.getLineNumber() + ": " + record);
            nextToken();
        }
        return postfixRecords;
    }

    private String generatePostfixRecord() {
        StringBuilder builder = new StringBuilder();
        Stack<Token> stack = new Stack<>();
        while (!isSemiColon()) {
            if (isOperand())
                builder.append(token.getValue());
            else {
                while (!stack.isEmpty() && (priority(stack.lastElement(), token))) {
                    builder.append(stack.pop().getValue());
                }
                if (stack.isEmpty() || !token.getValue().equals(")")) {
                    stack.push(token);
                } else {
                    stack.pop();
                }
            }
            nextToken();
        }
        while (!stack.isEmpty())
            builder.append(stack.pop().getValue());
        return builder.toString();
    }

    private boolean priority(Token firstElement, Token value) {
        boolean result;
        if (isFirstHighSecondLow(firstElement,value)
                || isBothLow(firstElement,value)
                || isFirstOpSecondRBracket(firstElement,value))
            result = true;
        else result = false;
        return result;
    }

    private void ScrollToNextAsgn() throws ExpectedException {
        while (!isAsgn()) {
            nextToken();
            if (token == null)
                break;
        }
    }

    private boolean isEnd() {
        return token.isRightToken(TokenNames.KWORD)
                && token.isRightValue(END_KWORD);
    }

    private boolean isFirstOpSecondRBracket(Token first,Token second) {
        return isOperation(first) &&
                isRightBracket(second);
    }

    private boolean isBothLow(Token first,Token second) {
        return (first.isRightToken(TokenNames.OPERL)
                && second.isRightToken(TokenNames.OPERL));
    }

    private boolean isFirstHighSecondLow(Token first,Token second) {
        return first.isRightToken(TokenNames.OPERH)
                && second.isRightToken(TokenNames.OPERL);
    }

    private boolean isOperation(Token token) {
        return token.isRightToken(TokenNames.OPERL)
                || token.isRightToken(TokenNames.OPERH);
    }

    private boolean isRightBracket(Token token) {
        return token.isRightToken(TokenNames.DELIM)
                && token.isRightValue(RIGHT_BRACKET);
    }

    private boolean isOperand() {
        return token.isRightToken(TokenNames.IDENT)
                || token.isRightToken(TokenNames.CONST);
    }

    private boolean isSemiColon() {
        return token.isRightToken(TokenNames.DELIM)
                && token.isRightValue(SEMICOLON_DELIM);
    }

    private boolean isAsgn() throws ExpectedException {
        return token.isRightToken(TokenNames.ASGN)
                && token.isRightValue(SIGN_ASSIGN);
    }

    private void nextToken() {
        if (tokens.hasNext()) {
            token = tokens.next();
        } else {
            token = null;
        }
    }
}
