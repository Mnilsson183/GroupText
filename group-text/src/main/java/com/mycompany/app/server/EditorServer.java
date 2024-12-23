package com.mycompany.app.server;

import com.mycompany.app.server.EditorAction;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class EditorServer {
    private Vector<String> lines;
    int port;

    public EditorServer() {
        this.lines = new Vector<>();
        this.port = 8080;
    }

    public EditorServer(int port) {
        this.lines = new Vector<>();
        this.port = port;
    }

    public void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Server is listening on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public EditorServer(String s) {
        String[] slines = s.split("\n");
        this.lines = new Vector<>(slines.length);
        for (int i = 0; i < slines.length; i++) {
            this.lines.add(slines[i]);
        }
    }

    public void applyFunction(EditorAction eAction) {
        applyFunction(eAction, this.lines);
    }

    public static void applyFunction(EditorAction eAction, Vector<String> lines) {
        // delete
        // if y = some int in range and x = -1 delete line
        if (!eAction.hasChar()) {
            if (eAction.getY() < 0) throw new IndexOutOfBoundsException("Attempt to index out of bounds on the y");
            if (eAction.getY() >= lines.size()) throw new IndexOutOfBoundsException("Attempt to index out of bounds on the y");
            if (eAction.getX() < -1) throw new IndexOutOfBoundsException("Attempt to index out of bounds on the x");
            if (eAction.getX() >= lines.get(eAction.getY()).length()) throw new IndexOutOfBoundsException("Attempt to index out of bounds on the x");

            if (eAction.getX() == -1) {
                lines.remove(eAction.getY());
            } else {
                String line = lines.get(eAction.getY());
                String newLine = line.substring(0, eAction.getX()) + line.substring(eAction.getX()+1);
                lines.set(eAction.getY(), newLine);
            }

        // insert
        // if y = some int in range and x = -1 add line
        } else {
            if (eAction.getY() < 0) throw new IndexOutOfBoundsException("Attempt to index out of bounds on the y");
            if (eAction.getY() >= lines.size()) throw new IndexOutOfBoundsException("Attempt to index out of bounds on the y");
            if (eAction.getX() < -1) throw new IndexOutOfBoundsException("Attempt to index out of bounds on the x");
            if (eAction.getX() > lines.get(eAction.getY()).length()) throw new IndexOutOfBoundsException("Attempt to index out of bounds on the x");

            if (eAction.getX() == -1) {
                lines.insertElementAt("", eAction.getY());
            } else {
                String line = lines.get(eAction.getY());
                String newLine = line.substring(0, eAction.getX()) + eAction.getChar() + line.substring(eAction.getX());
                lines.set(eAction.getY(), newLine);
            }
        }
    }

    public Vector<String> getLines() {
        return this.lines;
    }

    public String getLinesToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.lines.size(); i++) {
            sb.append(this.lines.get(i));
            if(i != this.lines.size()-1) sb.append("\n");
        }
        return sb.toString();
    }
}
