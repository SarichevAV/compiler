import analyzers.lexem.LexemAnalyzer;
import analyzers.syntax.SyntaxAnalyzer;
import exceptions.ExpectedException;
import exceptions.UnknownCharacterException;
import service.Reader;
import analyzers.lexem.models.Token;
import service.Writer;

import java.io.IOException;
import java.util.List;

public class Compiler {
    private static final String PATH_TO_SOURCE_CODE = "source.txt";
    private static final String PATH_TO_LEXEM_TABLE = "LexemTable.txt";
    private static final String PATH_TO_POSTFIX_RECORDS = "PostFixRecords.txt";
    private Writer writer = new Writer();
    private SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
    private PostfixRecordGenerator PFRecGen = new PostfixRecordGenerator();
    private Reader reader = new Reader();
    private LexemAnalyzer lexemAnalyzer = new LexemAnalyzer();


    public void startCompile() {
        try {
            String sourceCodeTxt = reader.readFile(PATH_TO_SOURCE_CODE);
            List<Token> tokens = lexemAnalyzer.analyze(sourceCodeTxt);
            writer.writeLexemTable(tokens, PATH_TO_LEXEM_TABLE);
            syntaxAnalyzer.analyze(tokens);
            List<String> postfixRecords = PFRecGen.generatePostfixRecords(tokens);
            writer.writeList(postfixRecords,PATH_TO_POSTFIX_RECORDS);


        } catch (ExpectedException | UnknownCharacterException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Compiler compiler = new Compiler();
        compiler.startCompile();
    }
}
