package com.mycompany.app.server;

public class EditorAction {

    private int x;
    private int y;
    private String c;

    public EditorAction(int x, int y, String c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public EditorAction(int x, int y) {
        this.x = x;
        this.y = y;
        this.c = "";
    }

    // newline insert and delete shortcuts
    public EditorAction(int y, boolean isInsert) {
        this.x = -1;
        this.y = y;
        this.c = isInsert ? "0" : "";
    }

    public EditorAction(EditorAction act) {
        this.x = act.x;
        this.y = act.y;
        this.c = act.c;
    }

    public EditorAction(String str) {
        this(parseEditorAction(str));
    }

    public static EditorAction parseEditorAction(String str) {
        try {
            String[] comps = str.split(",");
            int x = Integer.parseInt(comps[0]);
            int y = Integer.parseInt(comps[1]);
            if (comps.length == 2) return new EditorAction(x, y, "");

            String c;
            if (comps[2].length() == 0) c = "";
            else if (comps[2].equals("//")) c = ",";
            else c = comps[2];

            return new EditorAction(x, y, c);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getEditorActionString(EditorAction action) {
        String s = "" + action.getX() + "," + action.getY();
        if (action.hasChar()) {
            if (action.getValue() != ",") s += "," + action.getValue();
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

    public String getValue() {
        return c;
    }

    public boolean hasChar() {
        return this.c != "";
    }

    public String toString() {
        return getEditorActionString(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        EditorAction action = (EditorAction)obj;
        if (this.x != action.x) return false;
        else if (this.y != action.y) return false;
        else if (!this.c.equals(action.c)) return false;
        return true;
    }
}
