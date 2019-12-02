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
    private Writer writer = new Writer();
    private SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
    private PostfixRecordGenerator PFRecGen = new PostfixRecordGenerator();


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


    public void startCompile() {
        try {
            List<Token> tokens = lexAnalyzer("source.txt");
            writer.writeLexemTable(tokens);
            syntaxAnalyzer.analyze(tokens);
            List<String> postfixRecords = PFRecGen.generatePostfixRecords(tokens);
            writer.writeList(postfixRecords,"PostFixRecords.txt");
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
