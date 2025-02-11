package com.mycompany.app.editor.render;

import java.awt.Color;

abstract public class EditorTheme {
    Color cursorColor;
    Color textColor;

    Color themeBackgroundTones1;
    Color themeBackgroundTones2;

    Color contentTones1;
    Color contentTones2;

    Color accentColor1;
    Color accentColor2;
    Color accentColor3;
    Color accentColor4;

    Color commentColor;
    Color keywordColor;
    Color stringColor;
    Color numberColor;
    Color operatorColor;
    Color variableColor;
    Color functionColor;
    Color separatorColor;

    Color getCursorColor() {
        return cursorColor;
    }

    Color getTextColor() {
        return textColor;
    }

    Color getThemeBackgroundTones1() {
        return themeBackgroundTones1;
    }

    Color getThemeBackgroundTones2() {
        return themeBackgroundTones2;
    }

    Color getContentTones1() {
        return contentTones1;
    }

    Color getContentTones2() {
        return contentTones2;
    }

    Color getAccentColor1() {
        return accentColor1;
    }

    Color getAccentColor2() {
        return accentColor2;
    }

    Color getAccentColor3() {
        return accentColor3;
    }

    Color getAccentColor4() {
        return accentColor4;
    }

    Color getCommentColor() {
        return commentColor;
    }

    Color getKeywordColor() {
        return keywordColor;
    }

    Color getStringColor() {
        return stringColor;
    }

    Color getNumberColor() {
        return numberColor;
    }

    Color getOperatorColor() {
        return operatorColor;
    }

    Color getVariableColor() {
        return variableColor;
    }

    Color getFunctionColor() {
        return functionColor;
    }

    Color getSeparatorColor() {
        return separatorColor;
    }
}
