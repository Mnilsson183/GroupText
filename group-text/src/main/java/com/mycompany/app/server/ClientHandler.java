package com.mycompany.app.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler
 */
public class ClientHandler implements Runnable{

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
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
