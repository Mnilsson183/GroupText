package com.mycompany.app.editor.logic;

import java.util.ArrayList;
import java.util.Vector;

/**
 * EditorWindow
 */
public class EditorWindow {
	private int cursorX;
	private int cursorY;

	private String filename;
	private String fileExtention;

	private ArrayList<String> data;

	EditorWindow () {
		// default window open
	}

	EditorWindow (String filename) {
		this.cursorX = 0;
		this.cursorY = 0;

		this.filename = new String(filename);
		String[] components = filename.split(".");
		this.fileExtention = components[components.length - 1];

		// parse the data out of the file

	}

	EditorWindow (EditorWindow window) {
		this.cursorX = window.getCursorX();
		this.cursorY = window.getCursorY();

		this.filename = window.getFilename();
		this.fileExtention = window.getFileExtention();

		// method for getting the data
	}

	public int getCursorX () {
		return this.cursorX;
	}

	public int getCursorY () {
		return this.cursorY;
	}

	public String getFilename() {
		return new String(this.filename);
	}

	public String getFileExtention() {
		return new String(this.fileExtention);
	}

	public String getLine(int lineNumber) {
		return new String(this.data.get(lineNumber));
	}

	// TODO 
	public Vector<String> getData() {
		return null;
	}






	// window editing methods

	protected void setCursorX(int x) {
		this.cursorX = x;
	}

	protected void setCursorY(int y) {
		this.cursorY = y;
	}

	protected void insertCharacter (char c, int x, int y) {
		
	}

	protected void removeCharacter(int x, int y) {

	}

	protected void insertNewline(int y) {

	}

	protected void removeLine(int x) {

	}
}
