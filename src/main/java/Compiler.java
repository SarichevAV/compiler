import analyzers.lexem.LexemAnalyzer;
import analyzers.lexem.SourceCode;
import analyzers.syntax.SyntaxAnalyzer;
import exceptions.ExpectedException;
import exceptions.UnknownCharacterException;
import service.Reader;
import analyzers.lexem.models.Token;
import service.Writer;

import java.io.IOException;
import java.util.List;

public class Compiler {
    private final static String LEXEM_PATTERN = "%s (%d, %d): %s\n";
    private Writer writer;

    public Compiler() {
        writer = new Writer();
    }

    public void initCompiler() {
        try {
            writer.setPath("lexemTable.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Token> lexAnalyzer(String pathSource) throws UnknownCharacterException {
        Reader reader = new Reader(pathSource);
        String sourceCodeTxt = reader.readFile();
        SourceCode sourceCode = new SourceCode(sourceCodeTxt);
        LexemAnalyzer lexemAnalyzer = new LexemAnalyzer(sourceCode);
        List<Token> tokens = lexemAnalyzer.lexer();
        return tokens;
    }

    public void writeLexemTable(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            sb.append(String.format(LEXEM_PATTERN,
                    token.getTokenName().getName(),
                    token.getLineNumber(),
                    token.getSymbolNumber(),
                    token.getValue()));
        }
        writer.write(sb.toString());
    }

    public void writePostFixRecords(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            sb.append(String.format(LEXEM_PATTERN,
                    token.getTokenName().getName(),
                    token.getLineNumber(),
                    token.getSymbolNumber(),
                    token.getValue()));
        }
        writer.write(sb.toString());
    }

    public void startCompile() {
        try {
            List<Token> tokens = lexAnalyzer("source.txt");
            writeLexemTable(tokens);
            SyntaxAnalyzer sa = new SyntaxAnalyzer(tokens);
            sa.analyze();
            PostfixRecordGenerator PFRecGen = new PostfixRecordGenerator(tokens);
            List<String> postfixRecords = PFRecGen.generatePostfixRecords();
            writer.setPath("PostFixRecords.txt");
            writer.write(postfixRecords);
        } catch (ExpectedException | UnknownCharacterException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Compiler compiler = new Compiler();
        compiler.initCompiler();
        compiler.startCompile();
    }
}
