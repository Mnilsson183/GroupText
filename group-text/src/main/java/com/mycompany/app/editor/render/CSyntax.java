package com.mycompany.app.editor.render;

import java.util.Vector;

public class CSyntax extends Syntax {

    String[] keywords = {
        "auto", "break", "case", "char", "const", "continue", "default", "do",
        "double", "else", "enum", "extern", "float", "for", "goto", "if",
        "int", "long", "register", "return", "short", "signed", "sizeof", "static",
        "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while"
    };

    String[] operators = {
        "+", "-", "*", "/", "%", "++", "--", "==", "!=", ">", "<", ">=", "<=",
        "&&", "||", "!", "&", "|", "^", "~", "<<", ">>", ">>>", "<<<", "+=", "-=",
        "*=", "/=", "%=", "&=", "|=", "^=", "<<=", ">>=", ">>>=", "<<<=", "?", ":",
        "=", "+", "-", "*", "/", "%", "&", "|", "^", "<<", ">>", ">>>", "<<<"
    };

    String[] separators = {
        "(", ")", "{", "}", "[", "]", ";", ",", "."
    };

    Vector<String> variables = new Vector<>();


    @Override
    public Highlight[] getHighlightColor(String s) {
        Highlight[] highlighting = new Highlight[s.length()];

        String word = "";
        boolean isQuote = false;
        for (int i = 0; i < s.length(); i++) {
            highlighting[i] = Highlight.DEFAULT;
            if (isQuote) {
                if (s.charAt(i) == '"') {
                    isQuote = false;
                    highlighting[i] = Highlight.STRING;
                } else {
                    highlighting[i] = Highlight.STRING;
                }
            } else {
                if (s.charAt(i) == '"') {
                    isQuote = true;
                    highlighting[i] = Highlight.STRING;
                } else if (Character.isDigit(s.charAt(i))) {
                    highlighting[i] = Highlight.NUMBER;
                } else if (s.charAt(i) == '/') {
                    if (i + 1 < s.length() && s.charAt(i + 1) == '/') {
                        for (int j = i; j < s.length(); j++) {
                            highlighting[j] = Highlight.COMMENT;
                        }
                        break;
                    }
                } else {
                    word += s.charAt(i);
                    if (i + 1 == s.length() || !Character.isLetter(s.charAt(i + 1))) {
                        if (word.equals("true") || word.equals("false")) {
                            for (int j = i - word.length() + 1; j <= i; j++) {
                                highlighting[j] = Highlight.KEYWORD;
                            }
                        } else if (word.equals("null")) {
                            for (int j = i - word.length() + 1; j <= i; j++) {
                                highlighting[j] = Highlight.DEFAULT;
                            }
                        } else {
                            for (String keyword : keywords) {
                                if (word.equals(keyword)) {
                                    for (int j = i - word.length() + 1; j <= i; j++) {
                                        highlighting[j] = Highlight.KEYWORD;
                                    }
                                    break;
                                }
                            }
                            for (String operator : operators) {
                                if (word.equals(operator)) {
                                    for (int j = i - word.length() + 1; j <= i; j++) {
                                        highlighting[j] = Highlight.OPERATOR;
                                    }
                                    break;
                                }
                            }
                            for (String separator : separators) {
                                if (word.equals(separator)) {
                                    for (int j = i - word.length() + 1; j <= i; j++) {
                                        highlighting[j] = Highlight.SEPARATOR;
                                    }
                                    break;
                                }
                            }
                            for (String variable : variables) {
                                if (word.equals(variable)) {
                                    for (int j = i - word.length() + 1; j <= i; j++) {
                                        highlighting[j] = Highlight.VARIABLE;
                                    }
                                    break;
                                }
                            }
                        }
                        word = "";
                    }
                }
            }
        }

        return highlighting;
    }

    @Override
    public void registerVariable(String s) {
        variables.add(s);
    }
    
}
