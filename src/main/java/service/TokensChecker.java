package service;

import analyzers.lexem.models.Token;
import analyzers.lexem.models.TokenNames;
import exceptions.ExpectedException;

public class TokensChecker {
    private final static String END_KWORD = "End";
    private final static String SEMICOLON_DELIM = ";";
    private final static String SIGN_ASSIGN = ":=";
    private final static String LEFT_BRACKET = "(";
    private final static String RIGHT_BRACKET = ")";

    public static boolean isOperation(Token token) {
        return token.isRightToken(TokenNames.OPERL)
                || token.isRightToken(TokenNames.OPERH);
    }

    public static boolean isOperand(Token token){
        return token.isRightToken(TokenNames.IDENT)
                || token.isRightToken(TokenNames.CONST);
    }

    public static boolean isleftBracket(Token token) throws ExpectedException {
        return token.isRightToken(TokenNames.DELIM)
                && token.isRightValue(LEFT_BRACKET);
    }

    public static boolean isFirstOpSecondRBracket(Token first,Token second) {
        return TokensChecker.isOperation(first) &&
                isRightBracket(second);
    }

    public static boolean isBothLow(Token first,Token second) {
        return (first.isRightToken(TokenNames.OPERL)
                && second.isRightToken(TokenNames.OPERL));
    }

    public static boolean isFirstHighSecondLow(Token first,Token second) {
        return first.isRightToken(TokenNames.OPERH)
                && second.isRightToken(TokenNames.OPERL);
    }


    public static boolean isRightBracket(Token token) {
        return token.isRightToken(TokenNames.DELIM)
                && token.isRightValue(RIGHT_BRACKET);
    }

    public static boolean isEnd(Token token) {
        return token.isRightToken(TokenNames.KWORD)
                && token.isRightValue(END_KWORD);
    }

    public static boolean isSemiColon(Token token) {
        return token.isRightToken(TokenNames.DELIM)
                && token.isRightValue(SEMICOLON_DELIM);
    }

    public static boolean isAsgn(Token token) throws ExpectedException {
        return token.isRightToken(TokenNames.ASGN)
                && token.isRightValue(SIGN_ASSIGN);
    }
}
