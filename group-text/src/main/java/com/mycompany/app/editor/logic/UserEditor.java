package com.mycompany.app.editor.logic;

import java.util.ArrayList;

/*
 * UserEditor class
 * Holds the state for a single user attatched to a session
 *
 */
public class UserEditor {
	private ArrayList<EditorBuffer> windows;
	private int focusedWindowIndex;

	public UserEditor () {
		this.windows = new ArrayList<>();
		this.focusedWindowIndex = 0;
		addWindow(new EditorBuffer());
	}

	public UserEditor (String initialFilename) {
		this.windows = new ArrayList<>();
		this.focusedWindowIndex = 0;
		addWindow(new EditorBuffer(initialFilename));
	}

	public ArrayList<String> getAllWindowFilenames() {
		ArrayList<String> filenames = new ArrayList<>();
		for (EditorBuffer win : windows) {
			filenames.add(win.getFilename());
		}
		return filenames;
	}

	public ArrayList<String> getAllWindowFileExtentions() {
		ArrayList<String> fileExt = new ArrayList<>();
		for (EditorBuffer win : windows) {
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

	protected EditorBuffer getFocusedWindow() {
		return this.windows.get(this.focusedWindowIndex);
	}

	public void addWindow(EditorBuffer window) {
		this.windows.add(window);
	}
}
