package com.mycompany.app.editor.logic;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import com.mycompany.app.editor.render.GroupTextRender;
import com.mycompany.app.server.EditorAction;

/*
 * Editor class
 * Holds all of the data for all users attatched to a session via a get method
 * Primary user is the user that the promgram is being currently run on ie
 * primary user holds the data that the client needs to render
 */
public class Editor {

	private Vector<UserEditor> users;
	String serverAddress;
	int port = 8000;
	Socket socket;
	ServerHandler serverHandler;
	EditorAction lastAction;
	GroupTextRender renderer;

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

	public void applyTransformation(String s) {
		try {
			EditorAction action = new EditorAction(s);
			if (action.equals(lastAction)) {
				System.out.println("this is my last action");
				return;
			}
			this.getPrimaryUser().getFocusedWindow().applyTranslation(action);
			renderer.updateDisplay();
		} catch (Exception e) {
			System.out.println("Exception while applying transformation ");
			e.printStackTrace();
		}
	}

	public void sendTransformation(EditorAction action) {
		serverHandler.send(action.toString());
		this.lastAction = action;
	}

	public void runEditor () {
		// run the rendering
		renderer = new GroupTextRender(this);
		renderer.setVisible(true);

		// connect to server
		try {
			System.out.println("Connecting to server " + serverAddress + " port: " + port);
			socket = new Socket(serverAddress, port);
			System.out.println("Connected to server");
			
			serverHandler = new ServerHandler(socket, this);

			serverHandler.send("Hello server");

			serverHandler.listen();
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
