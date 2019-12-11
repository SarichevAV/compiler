import analyzers.lexem.LexemAnalyzer;
import analyzers.syntax.SyntaxAnalyzer;
import exceptions.ExpectedException;
import exceptions.TokenIsNotIdentifiedException;
import exceptions.UnknownCharacterException;
import models.AssemblerCommand;
import models.CommandNames;
import service.AssemblerCommandWriter;
import service.LexemTableWriter;
import service.PostfixRecordWriter;
import service.Reader;
import models.Token;

import java.io.IOException;
import java.util.List;

public class Compiler {

    private static final String PATH_TO_SOURCE_CODE = "source.txt";
    private static final String PATH_TO_LEXEM_TABLE = "LexemTable.txt";
    private static final String PATH_TO_POSTFIX_RECORDS = "PostFixRecords.txt";
    private static final String PATH_TO_ASSEMBLY_COMMAND = "assembly.txt";

    private LexemTableWriter lexemTableWriter = new LexemTableWriter();
    private PostfixRecordWriter postfixRecordWriter = new PostfixRecordWriter();
    private AssemblerCommandWriter assemblerCommandWriter =
            new AssemblerCommandWriter();
    private SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
    private PostfixRecordGenerator PFRecGen = new PostfixRecordGenerator();
    private AssemblyGenerator assemblyGenerator = new AssemblyGenerator();
    private Reader reader = new Reader();
    private LexemAnalyzer lexemAnalyzer = new LexemAnalyzer();


    public void startCompile() {
        try {
            String sourceCodeTxt = reader.readFile(PATH_TO_SOURCE_CODE);
            List<Token> tokens = lexemAnalyzer.analyze(sourceCodeTxt);
            lexemTableWriter.writeLexemTable(tokens, PATH_TO_LEXEM_TABLE);
            syntaxAnalyzer.analyze(tokens);
            List<Token> postfixRecords = PFRecGen.generatePostfixRecords(tokens);
            postfixRecordWriter.writePostfixRecord(postfixRecords,
                    PATH_TO_POSTFIX_RECORDS);
            List<AssemblerCommand> commands =
                    assemblyGenerator.generate(postfixRecords);
            assemblerCommandWriter.writeAssemblyCode(commands,
                    PATH_TO_ASSEMBLY_COMMAND);
        } catch (ExpectedException | UnknownCharacterException e) {
            System.out.print(e.getMessage());
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TokenIsNotIdentifiedException e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Compiler compiler = new Compiler();
        compiler.startCompile();
    }
}
