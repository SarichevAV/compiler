import models.Token;
import models.TokenNames;
import service.TokensChecker;
import exceptions.ExpectedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class PostfixRecordGenerator {
    private static final String ASGN_VALUE = ":=";
    private static final String RIGHT_BRACKET = ")";


    private List<Token> postfixRecords;
    private Iterator<Token> tokens;
    private Token token;

    public PostfixRecordGenerator() {
        postfixRecords = new ArrayList<>();
    }

    public List<Token> generatePostfixRecords(List<Token> tokenList) throws ExpectedException {
        tokens = tokenList.iterator();
        nextToken();
        scrollToBegin();
        nextToken();
        while (!TokensChecker.isEnd(token)) {
            postfixRecords.add(token);
            nextToken();
            nextToken();
            handleExpression();
            nextToken();
        }
        return postfixRecords;
    }

    private void handleExpression() {
        Stack<Token> stack = new Stack<>();
        while (!TokensChecker.isSemiColon(token)) {
            if (TokensChecker.isOperand(token)) {
                postfixRecords.add(token);
            } else {
                while (!stack.isEmpty() && (priority(stack.lastElement(), token))) {
                    postfixRecords.add(stack.pop());
                }
                if (stack.isEmpty() || !token.getValue().equals(RIGHT_BRACKET)) {
                    stack.push(token);
                } else {
                    stack.pop();
                }
            }
            nextToken();
        }
        if (TokensChecker.isSemiColon(token))
        while (!stack.isEmpty()) {
            postfixRecords.add(stack.pop());
        }
        addAsgnToken();
    }

    private boolean priority(Token firstElement, Token value) {
        boolean result;
        if (TokensChecker.isFirstHighSecondLow(firstElement,value)
                || TokensChecker.isBothLow(firstElement,value)
                || TokensChecker.isFirstOpSecondRBracket(firstElement,value))
            result = true;
        else result = false;
        return result;
    }

    private void scrollToNextAsgn() throws ExpectedException {
        while (!TokensChecker.isAsgn(token)) {
            nextToken();
            if (token == null)
                break;
        }
    }

    private void scrollToBegin() {
        while (!TokensChecker.isBegin(token)) {
            nextToken();
        }
    }

    private void addAsgnToken() {
        Token token = new Token(ASGN_VALUE, TokenNames.ASGN);
        postfixRecords.add(token);
    }

    private void nextToken() {
        if (tokens.hasNext()) {
            token = tokens.next();
        } else {
            token = null;
        }
    }
}
