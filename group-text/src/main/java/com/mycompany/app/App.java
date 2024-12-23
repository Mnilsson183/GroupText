package com.mycompany.app;

import com.mycompany.app.editor.logic.Editor;
import com.mycompany.app.server.EditorServer;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        if (args.length != 0) {
            if (args[0].equals("--server") || args[0].equals("-s")) {
                EditorServer server = new EditorServer();
            } else {
                Editor editor = new Editor();
                editor.runEditor();
            }
        } else {
            Editor editor = new Editor();
            editor.runEditor();
        }
    }
}
