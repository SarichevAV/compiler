import analyzer.LexemAnalyzer;
import service.Reader;
import analyzer.SourceCode;
import analyzer.models.Token;
import service.Writer;

import java.util.List;

public class Compiler {
    private final static String LEXEM_PATTERN = "%s (%d, %d): %s\n";

    public List<Token> lexAnalyzer(String pathSource) {
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
        Writer writer = new Writer("lexemTable.txt");
        writer.write(sb.toString());
    }

    public static void main(String[] args) {
        Compiler compiler = new Compiler();
        List<Token> tokens = compiler.lexAnalyzer("source.txt");
        compiler.writeLexemTable(tokens);
    }
}
