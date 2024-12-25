package com.mycompany.app;

import com.mycompany.app.editor.logic.Editor;
import com.mycompany.app.server.EditorServer;

/**
 * Hello world!
 */
public class App {

    enum Argument {
        RUNSERVER,
        RUNEDITOR,
        PORT,
        SERVERADDRESS,
        HELP,
        OTHER,
    }

    public static void main(String[] args) {
        int port = 8080;
        String serverAddress = "localhost";
        Argument target = null;
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            Argument argument = parseArg(s);
            if (argument == Argument.HELP) {
                printHelp();
                return;
            }
            if (argument == Argument.RUNSERVER || argument == Argument.RUNEDITOR) {
                if (target != null) throw new IllegalArgumentException();
                target = argument;
            } else if (argument == Argument.PORT) {
                if (i + 1 == args.length) throw new IllegalArgumentException();
                else port = Integer.parseInt(args[++i]);
            } else if (argument == Argument.SERVERADDRESS) {
                if (i + 1 == args.length) throw new IllegalArgumentException();
                else serverAddress = args[++i];
            }
        }

        if (target == Argument.RUNEDITOR) {
            Editor editor = new Editor(serverAddress, port);
            editor.runEditor();
        } else if (target == Argument.RUNSERVER || target == null) {
            EditorServer editorServer = new EditorServer(port);
            editorServer.runServer();
        }
    }

    public static Argument parseArg(String s) {
        switch (s.toLowerCase()) {
            case "--server":
            case "-s":
                return Argument.RUNSERVER;
            case "--editor":
            case "-e":
                return Argument.RUNEDITOR;
            case "-p":
            case "--port":
                return Argument.PORT;
            case "--serveraddress":
            case "--address":
            case "-a":
                return Argument.SERVERADDRESS;
            case "--help":
            case "-h":
                return Argument.HELP;
            default:
                return Argument.OTHER;
        }
    }

    public static void printHelp() {
        System.out.println("Usage: java App [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -s, --server           Run the server (required for server-side operations)");
        System.out.println("  -e, --editor           Run the editor (client-side operation)");
        System.out.println("  -p, --port <port>      Specify the port for the server to listen on (default: 8080)");
        System.out.println("  --serveraddress <addr> Address of the server to connect to (default: localhost)");
        System.out.println("  -a, --address <addr>   Alias for --serveraddress");
        System.out.println("  -h, --help             Show this help message");
        System.out.println();
        System.out.println("Description:");
        System.out.println("This application allows multiple users to collaboratively edit a text file.");
        System.out.println("You can run the server to host the text file, or run the editor to connect to an existing server.");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java App --server -p 9090                 # Run the server on port 9090");
        System.out.println("  java App --editor --serveraddress localhost -p 8080  # Connect to the server at localhost on port 8080");
        System.out.println("  java App --help                           # Show this help message");
    }
}
