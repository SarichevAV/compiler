package analyzer;

import exceptions.EndFileException;

public class SourceCode {
    private char[] code;
    private char curr;
    private int currIndex;

    private int preLinesLenght;
    private int lineN;
    private int lexemN;

    public SourceCode(String sourceCode) {
        code = sourceCode.toCharArray();
        currIndex = 0;
        lineN = 1;
        preLinesLenght = 0;
        curr = code[currIndex];
    }

    public boolean isEnd() {
        return currIndex == code.length;
    }

    public void setLexemNumberCurrentIndex() {
        lexemN = currIndex - preLinesLenght + 1;
    }

    public char getCurr() {
        return curr;
    }

    public int getLineN() {
        return lineN;
    }

    public int getLexemN() {
        return lexemN;
    }

    public char next() {
        currIndex++;
        if (isEnd()) {
            curr = '\uFFFF';
        } else {
            curr = code[currIndex];
        }
        return curr;
    }

    public void nextLine() {
        lineN++;
        preLinesLenght = currIndex;
    }
}
