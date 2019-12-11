package service;

import models.Token;

import java.io.IOException;
import java.util.List;

public class LexemTableWriter extends Writer {
    private final static String LEXEM_PATTERN = "%s (%d, %d): %s\n";

    public void writeLexemTable(List<Token> tokens, String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            sb.append(String.format(LEXEM_PATTERN,
                    token.getTokenName().getName(),
                    token.getLineNumber(),
                    token.getSymbolNumber(),
                    token.getValue()));
        }
        writeString(sb.toString(),path);
    }
}
