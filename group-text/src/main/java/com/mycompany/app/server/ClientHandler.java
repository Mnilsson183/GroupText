package com.mycompany.app.server;

import com.mycompany.app.server.EditorServer;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



/**
 * ClientHandler
 */
public class ClientHandler implements Runnable{

    private Socket clientSocket;
    private EditorServer editorServer;

    public ClientHandler(Socket socket, EditorServer editorServer) {
        this.clientSocket = socket;
        this.editorServer = editorServer;
    }

    @Override
    public void run() {
        try (
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String clientMessage;

            while((clientMessage = input.readLine()) != null) {
                System.out.println("Received: " + clientMessage);
                output.println("Server received" + clientMessage);
                try {
                    editorServer.applyFunction(new EditorAction(clientMessage));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid transformation" + clientMessage);
                }
                System.out.println(editorServer.getLinesToString());
            }
        } catch (IOException e) {
            System.out.println("ClientHandler exception");
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
