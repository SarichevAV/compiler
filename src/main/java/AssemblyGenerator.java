import exceptions.ExpectedException;
import exceptions.TokenIsNotIdentifiedException;
import models.AssemblerCommand;
import models.CommandNames;
import models.Token;
import service.TokensChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AssemblyGenerator {
    private final static String PLUS_SIGN = "+";
    private final static String MINUS_SIGN = "-";
    private final static String MULT_SIGN = "*";
    private final static String DIV_SIGN = "/";
    private final static String UNMUNIS_SIGN = "~";

    private List<AssemblerCommand> commands = new ArrayList<>();
    private HashMap<String, Integer> memory = new HashMap<>();
    private Iterator<Token> tokens;
    private Token token;
    private String currentIdent;
    private Integer lastAddress = 0;

    public List<AssemblerCommand> generate(List<Token> tokenList)
            throws ExpectedException, TokenIsNotIdentifiedException {
        tokens = tokenList.iterator();
        nextToken();
        while (!isNull(token)) {
            currentIdent = token.getValue();
            nextToken();
            handlExpression();
            initIdent();
            String adr = String.valueOf(memory.get(currentIdent));
            commands.add(new AssemblerCommand(CommandNames.STO, adr));
            nextToken();
        }
        return commands;
    }

    private void initIdent() {
        if (!memory.containsValue(currentIdent)) {
            lastAddress++;
            memory.put(currentIdent, lastAddress);
        }
    }

    private void handlExpression()
            throws ExpectedException, TokenIsNotIdentifiedException {
        while (!TokensChecker.isAsgn(token)) {
            switch (token.getTokenName()) {
                case IDENT:
                    handleIdent();
                    break;
                case CONST:
                    handleConst();
                    break;
                case OPERL:
                    handleOperation();
                    break;
                case OPERH:
                    handleOperation();
                    break;
            }
        }
    }

    private void handleIdent() throws TokenIsNotIdentifiedException {
        if (!memory.containsKey(token.getValue())) {
            throw new TokenIsNotIdentifiedException(token.getValue());
        }
        String adr = String.valueOf(memory.get(token.getValue()));
        commands.add(new AssemblerCommand(CommandNames.LOAD, adr));
        nextToken();
    }

    private void handleConst() {
        commands.add(new AssemblerCommand(CommandNames.LIT, token.getValue()));
        nextToken();
    }

    private void handleOperation() {
        switch (token.getValue()) {
            case PLUS_SIGN:
                commands.add(new AssemblerCommand(CommandNames.ADD));
                break;
            case MINUS_SIGN:
                commands.add(new AssemblerCommand(CommandNames.SUB));
                break;
            case MULT_SIGN:
                commands.add(new AssemblerCommand(CommandNames.MUL));
                break;
            case DIV_SIGN:
                commands.add(new AssemblerCommand(CommandNames.DIV));
                break;
            case UNMUNIS_SIGN:
                commands.add(new AssemblerCommand(CommandNames.NOT));
                break;
        }
        nextToken();
    }

    private boolean isNull(Token token) {
        return token == null;
    }

    private void nextToken() {
        if (tokens.hasNext()) {
            token = tokens.next();
        } else {
            token = null;
        }
    }
}
