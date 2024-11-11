package com.mycompany.app.editor.logic;

import java.util.Vector;

import com.mycompany.app.editor.render.GroupTextRender;

/*
 * Editor class
 * Holds all of the data for all users attatched to a session via a get method
 * Primary user is the user that the promgram is being currently run on ie
 * primary user holds the data that the client needs to render
 *
 */
public class Editor {

	private Vector<UserEditor> users;

	private void processKeyIn(char c) {
		
	}

	public void runEditor () {
		GroupTextRender renderer = new GroupTextRender();
		while (true) {
			

			renderer.render(this);
		}
	}

	public UserEditor getPrimaryUser() {
		return this.users.get(0);
	}

	public UserEditor getUser(int index) {
		return this.users.get(index);
	}
}
