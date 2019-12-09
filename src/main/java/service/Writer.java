package service;

import analyzers.lexem.models.Token;

import java.io.*;
import java.util.List;

public class Writer {
    private BufferedWriter bw;
    private final static String LEXEM_PATTERN = "%s (%d, %d): %s\n";

    public void setPath(String path) throws IOException {
        File file = new File(path);
        bw = new BufferedWriter(new FileWriter(file));
    }

    public void writeString(String text, String path) throws IOException {
            setPath(path);
            bw.write(text);
            bw.flush();
    }

    public void writeList(List<String> text,String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String temp : text) {
            sb.append(temp);
            sb.append("\n");
        }
        writeString(sb.toString(),path);
    }

    public void writeLexemTable(List<Token> tokens,String path) throws IOException {
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
