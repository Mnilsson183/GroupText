package com.mycompany.app.editor.render;

abstract public class Syntax {

    enum Highlight {
        KEYWORD, OPERATOR, SEPARATOR, VARIABLE, STRING, COMMENT, NUMBER, DEFAULT
    }

    abstract Highlight[] getHighlightColor(String s);
    abstract void registerVariable(String s);
}
