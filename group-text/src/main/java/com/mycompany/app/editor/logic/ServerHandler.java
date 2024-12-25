package com.mycompany.app.editor.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.mycompany.app.server.EditorAction;

/**
 * ServerHandler
 */
public class ServerHandler {

	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private Editor editor;

	ServerHandler(Socket socket, Editor editor) throws IOException {
		this.socket = socket;
		this.editor = editor;
		try {
			this.output = new PrintWriter(socket.getOutputStream(), true);
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			throw e;
		}
	}

	public void send(String message) {
		output.println(message);
	}

	public void listen() {
		String message;
		try {
			while ((message = input.readLine()) != null) {
				System.out.println("Received from server: " + message);
				editor.applyTransformation(message);
			}
		} catch (IOException e) {
			System.out.println("Error readng from server");
		}
	}
}
