package service;

import models.AssemblerCommand;

import java.io.IOException;
import java.util.List;

public class AssemblerCommandWriter extends Writer {
    private static final String COMMAND_PATTERN = "%s %s";

    public void
    writeAssemblyCode(List<AssemblerCommand> assemblerCommands, String path)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        for (AssemblerCommand command: assemblerCommands) {
            String argument = command.getArgument();
            if (argument == null) {
                argument = "";
            }
            sb.append(String.format(COMMAND_PATTERN,
                    command.getCommand(), argument));
            sb.append("\n");
        }
        writeString(sb.toString(), path);
    }
}
