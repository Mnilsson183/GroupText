package com.mycompany.app.editor.logic;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import com.mycompany.app.editor.render.GroupTextRender;
import com.mycompany.app.server.EditorAction;

/*
 * Editor class
 * Holds all of the data for all users attatched to a session via a get method
 * Primary user is the user that the promgram is being currently run on ie
 * primary user holds the data that the client needs to render
 *
 */
public class Editor {

	private Vector<UserEditor> users;
	String serverAddress;
	int port = 8000;
	Socket socket;
	BufferedReader inputFromServer;
	PrintWriter outputToServer;

	public Editor () {
		this.users = new Vector<>();
		this.users.add(new UserEditor());
		this.serverAddress = "localhost";
		this.port = 8080;
	}

	public Editor (int port) {
		this.users = new Vector<>();
		this.users.add(new UserEditor());
		this.serverAddress = "localhost";
		this.port = port;
	}

	public Editor (String serverAddress, int port) {
		this.users = new Vector<>();
		this.users.add(new UserEditor());
		this.serverAddress = serverAddress;
		this.port = port;
	}

	public void processKeyIn(KeyEvent event) {
		EditorAction action = this.getPrimaryUser().getFocusedWindow().processKeyIn(event);
		if (action != null) sendTransformation(action);
	}

	public void sendTransformation(EditorAction action) {
		outputToServer.println(action.toString());
	}

	public void runEditor () {
		// run the rendering
		GroupTextRender renderer = new GroupTextRender(this);
		renderer.setVisible(true);

		// connect to server
		try {
			socket = new Socket(serverAddress, port);
			System.out.println("Connecting to server");

			inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputToServer = new PrintWriter(socket.getOutputStream(), true);

			outputToServer.println("Hello Server");

			String serverResponse = inputFromServer.readLine();
			System.out.println("Server: " + serverResponse);
		} catch (IOException e) {
			System.err.println("Client exception: " + e.getMessage());
			e.printStackTrace();
		}
		

		//while (true) {
		//	

		//	//renderer.render();
		//}
	}

	public UserEditor getPrimaryUser() {
		return this.users.get(0);
	}

	public UserEditor getUser(int index) {
		return this.users.get(index);
	}
}
