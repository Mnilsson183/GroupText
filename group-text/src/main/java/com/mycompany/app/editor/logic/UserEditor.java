package com.mycompany.app.editor.logic;

import java.util.ArrayList;

/*
 * UserEditor class
 * Holds the state for a single user attatched to a session
 *
 */
public class UserEditor {
	private ArrayList<EditorWindow> windows;
	private int focusedWindowIndex;

	public UserEditor () {
		this.windows = new ArrayList<>();
		this.focusedWindowIndex = 0;
		addWindow(new EditorWindow());
	}

	public UserEditor (String initialFilename) {
		this.windows = new ArrayList<>();
		this.focusedWindowIndex = 0;
		addWindow(new EditorWindow(initialFilename));
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

	public ArrayList<String> getFocusedWindowData() {
		return this.windows.get(this.focusedWindowIndex).getData();
	}

	public int getFocusedWindowCursorX() {
		return this.windows.get(this.focusedWindowIndex).getCursorX();
	}

	public int getFocusedWindowCursorY() {
		return this.windows.get(this.focusedWindowIndex).getCursorY();
	}

	protected EditorWindow getFocusedWindow() {
		return this.windows.get(this.focusedWindowIndex);
	}

	public void addWindow(EditorWindow window) {
		this.windows.add(window);
	}
}
