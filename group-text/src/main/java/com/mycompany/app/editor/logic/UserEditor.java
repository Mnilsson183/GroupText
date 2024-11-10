package com.mycompany.app.editor.logic;

import java.util.ArrayList;

/*
 * UserEditor class
 * Holds the state for a single user attatched to a session
 *
 */
public class UserEditor {
	private ArrayList<EditorWindow> windows;
	private int indexPrimary;

	public UserEditor () {
		this.windows = null;
		this.indexPrimary = -1;
	}

	public ArrayList<String> getAllWindowFilenames() {
		ArrayList<String> filenames = new ArrayList<>();
		for (EditorWindow win : windows) {
			filenames.add(win.getFilename());
		}
		return filenames;
	}

	public ArrayList<String> getAllWindowFileExtentions() {
		ArrayList<String> fileExt = new ArrayList<>();
		for (EditorWindow win : windows) {
			fileExt.add(win.getFileExtention());
		}
		return fileExt;
	}

	public ArrayList<String> getCurrWindowData() {
		return this.windows.get(this.indexPrimary).getData();
	}

	public int getCurrWindowCursorX() {
		return this.windows.get(this.indexPrimary).getCursorX();
	}

	public int getCurrWindowCursorY() {
		return this.windows.get(this.indexPrimary).getCursorY();
	}
}
