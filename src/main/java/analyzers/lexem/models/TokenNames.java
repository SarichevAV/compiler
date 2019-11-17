package analyzers.lexem.models;

public enum TokenNames {
    KWORD("KEY_WORD"),
    IDENT("IDENTIFIER"),
    CONST("CONSTANT"),
    DELIM("DELIMITER"),
    OPERH("OPERATION_HIGH"),
    OPERL("OPERATION_LOW"),
    ASGN("ASGN");

    String name;

    TokenNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
