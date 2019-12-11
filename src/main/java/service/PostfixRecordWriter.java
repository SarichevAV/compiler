package service;

import models.Token;
import exceptions.ExpectedException;

import java.io.IOException;
import java.util.List;

public class PostfixRecordWriter extends Writer {
    public void writePostfixRecord(List<Token> tokens, String path) throws IOException, ExpectedException {
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            sb.append(token.getValue());
            if (TokensChecker.isAsgn(token)) {
                sb.append("\n");
            }
        }
        writeString(sb.toString(),path);
    }
}
