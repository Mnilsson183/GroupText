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
public class ServerHandler implements Runnable {

	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private EditorBuffer editorBuffer;

	ServerHandler(Socket socket, EditorBuffer editorBuffer) throws IOException {
		this.socket = socket;
		this.editorBuffer = editorBuffer;
		try {
			this.output = new PrintWriter(socket.getOutputStream(), true);
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			throw e;
		}
	}

	ServerHandler(String serverAddress, int port, EditorBuffer editorBuffer) throws IOException {
		this(new Socket(serverAddress, port), editorBuffer);
	}

	public void send(String message) {
		if (editorBuffer == null) return;


		output.println(message);
	}

	public void run() {
		String message;
		try {
			while ((message = input.readLine()) != null) {
				System.out.println("Received from server: " + message);
				if (message.charAt(0) == ':') {
					editorBuffer.setState(message.substring(1));
				} else {
					editorBuffer.applyTransformation(new EditorAction(message));
				}
			}
		} catch (IOException e) {
			System.out.println("Error readng from server");
		}
	}
}
