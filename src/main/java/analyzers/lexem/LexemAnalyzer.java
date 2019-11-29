package analyzers.lexem;

import analyzers.lexem.models.Token;
import analyzers.lexem.models.TokenNames;
import exceptions.UnknownCharacterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexemAnalyzer {
    private final static List<String> KEY_WORDS =
            new ArrayList<>(Arrays.asList("Integer", "Begin", "End"));
    private List<Token> tokens;
    private SourceCode source;
    private States state;

    public LexemAnalyzer(SourceCode source) {
        this.source = source;
        tokens = new ArrayList<>();
        state = States.H;
    }

    public List<Token> lexer() throws UnknownCharacterException {
        while (!source.isEnd()) {
            checkState();
        }
        return tokens;
    }

    private void checkState() throws UnknownCharacterException {
        source.setLexemNumberCurrentIndex();
        switch (state) {
            case H:
                handlH();
                break;
            case ID:
                handlID();
                break;
            case ASGN:
                handlASGN();
                break;
            case DLM:
                handlDLM();
                break;
            case NM:
                handlNM();
                break;
            case ERR:
                handlERR();
                break;
            case OPH:
                handlOPH();
                break;
            case OPL:
                handlOPL();
                break;
        }
    }

    private void handlH() {
        char c = source.getCurr();
        while ((c == ' ') || (c == '\t') || (c == '\n') || (c== '\r')) {
            if (c == '\n') {
                source.nextLine();
            }
            c = source.next();
        }
        if ((c >= 'A') && (c <= 'Z') || (c >= 'a') && (c <= 'z')) {
            state = States.ID;
        } else if ((c >= '0') && (c <= '9')) {
            state = States.NM;
        } else if ((c == '*') || (c == '/')) {
            state = States.OPH;
        } else if ((c == '+') || (c == '-')) {
            state = States.OPL;
        } else if (c == ':') {
            state = States.ASGN;
        } else {
            state = States.DLM;
        }
    }

    private void handlID() {
        char c = source.getCurr();
        StringBuilder str = new StringBuilder();
        str.append(c);
        c = source.next();
        while ((c >= 'A') && (c <= 'Z') || (c >= 'a') && (c <= 'z')) {
            str.append(c);
            c = source.next();
        }
        Token token = new Token(source.getLineN(),
                source.getLexemN(), str.toString());
        if (isKeyWord(str.toString())) {
            token.setTokenName(TokenNames.KWORD);
        } else {
            token.setTokenName(TokenNames.IDENT);
        }
        tokens.add(token);
        state = States.H;
    }

    private void handlASGN() {
        char c = source.next();
        if (c == '=') {
            Token token = new Token(source.getLineN(), source.getLexemN(),
                    ":=", TokenNames.ASGN);
            tokens.add(token);
            state = States.H;
            source.next();
        } else {
            state = States.ERR;
        }
    }

    private void handlDLM() {
        char c = source.getCurr();
        if ((c == '(') || (c == ')') || (c == ';') || (c == ',')) {
            tokens.add(new Token(source.getLineN(), source.getLexemN(),
                    Character.toString(c), TokenNames.DELIM));
            state = States.H;
            source.next();
        } else {
            state = States.ERR;
        }
    }

    private void handlNM() {
        StringBuilder sb = new StringBuilder();
        sb.append(source.getCurr());
        char c = source.next();
        while ((c >= '0') && (c <= '9')) {
            sb.append(c);
            c = source.next();
        }
        tokens.add(new Token(source.getLineN(), source.getLexemN(),
                sb.toString(), TokenNames.CONST));
        state = States.H;
    }

    private void handlOPH() {
        tokens.add(new Token(source.getLineN(), source.getLexemN(),
                Character.toString(source.getCurr()), TokenNames.OPERH));
        state = States.H;
        source.next();
    }

    private void handlOPL() {
        tokens.add(new Token(source.getLineN(), source.getLexemN(),
                Character.toString(source.getCurr()), TokenNames.OPERL));
        state = States.H;
        source.next();
    }

    private void handlERR() throws UnknownCharacterException {
        throw new UnknownCharacterException(source.getLineN(),
                source.getLexemN(), source.getCurr());
    }

    private boolean isKeyWord(String str) {
        return KEY_WORDS.contains(str);
    }
}