package com.mycompany.app.server;

public class EditorAction {

    private int x;
    private int y;
    private char c;

    public EditorAction(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public EditorAction(int x, int y) {
        this.x = x;
        this.y = y;
        this.c = '\u0000';
    }

    // newline insert and delete shortcuts
    public EditorAction(int y, boolean isInsert) {
        this.x = -1;
        this.y = y;
        this.c = isInsert ? '\u0000' : '0';
    }

    public EditorAction(EditorAction act) {
        this.x = act.x;
        this.y = act.y;
        this.c = act.c;
    }

    public EditorAction(String str) {
        this(parseEditorAction(str));
    }

    public static EditorAction parseEditorAction(String str) throws IllegalArgumentException {
        try {
            String[] comps = str.split(",");
            int x = Integer.parseInt(comps[0]);
            int y = Integer.parseInt(comps[1]);
            if (comps.length == 2) return new EditorAction(x, y, '\u0000');

            char c;
            if (comps[2].length() == 0) c = '\u0000';
            else if (comps[2].equals("//")) c = ',';
            else c = comps[2].charAt(0);

            return new EditorAction(x, y, c);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static String getEditorActionString(EditorAction action) {
        String s = "" + action.getX() + "," + action.getY();
        if (action.hasChar()) {
            if (action.getChar() != ',') s += "," + action.getChar();
            else s += "," + "//";
        } 
        return s;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getChar() {
        return c;
    }

    public boolean hasChar() {
        return this.c != '\u0000';
    }

    public String toString() {
        return getEditorActionString(this);
    }
}
