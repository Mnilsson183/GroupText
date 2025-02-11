package com.mycompany.app.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class EditorServer implements Runnable{
    private Vector<String> lines;
    int port = 8080;
    Vector<ClientHandler> clients = new Vector<>();

    public EditorServer() {
        this.lines = new Vector<>();
        this.lines.add("");
    }

    public EditorServer(int port) {
        this.lines = new Vector<>();
        this.lines.add("");
        this.port = port;
    }

    public EditorServer(File file, int port) {
        this.lines = new Vector<>();
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("File not found");
        }

        while (scanner.hasNextLine()) {
            this.lines.add(scanner.nextLine());
        }
        scanner.close();
        this.port = port;
    }

    public void sendGroupMessage(String message) {
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).send(message);
        }
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Server is listening on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                clientHandler.send(":" + getLinesToString());
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
        try {
            applyFunction(eAction, this.lines);
            sendGroupMessage(eAction.toString());
        } catch (Exception e) {
            System.out.println("Error applying function");
        }
    }

    public static void applyFunction(EditorAction eAction, Vector<String> lines) {
        // delete
        // if y = some int in range and x = -1 delete line
        if (!eAction.hasChar()) {

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

            if (eAction.getX() == -1) {
                lines.add(eAction.getY(), "");
            } else {
                String line = lines.get(eAction.getY());
                String newLine = line.substring(0, eAction.getX()) + eAction.getValue() + line.substring(eAction.getX());
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
