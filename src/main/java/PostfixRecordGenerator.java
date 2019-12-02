import analyzers.lexem.models.Token;
import analyzers.lexem.models.TokenNames;
import service.TokensChecker;
import exceptions.ExpectedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class PostfixRecordGenerator {

    private List<String> postfixRecords;
    private Iterator<Token> tokens;
    private Token token;

    public PostfixRecordGenerator() {
        postfixRecords = new ArrayList<>();
    }

    public List<String> generatePostfixRecords(List<Token> tokenList) throws ExpectedException {
        tokens = tokenList.iterator();
        nextToken();
         while (!TokensChecker.isEnd(token)) {
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
        while (!TokensChecker.isSemiColon(token)) {
            if (TokensChecker.isOperand(token))
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
        if (TokensChecker.isFirstHighSecondLow(firstElement,value)
                || TokensChecker.isBothLow(firstElement,value)
                || TokensChecker.isFirstOpSecondRBracket(firstElement,value))
            result = true;
        else result = false;
        return result;
    }

    private void ScrollToNextAsgn() throws ExpectedException {
        while (!TokensChecker.isAsgn(token)) {
            nextToken();
            if (token == null)
                break;
        }
    }

    private void nextToken() {
        if (tokens.hasNext()) {
            token = tokens.next();
        } else {
            token = null;
        }
    }
}
