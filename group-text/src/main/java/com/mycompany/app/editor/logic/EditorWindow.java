package com.mycompany.app.editor.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.KeyEvent;

/**
 * EditorWindow
 */
public class EditorWindow {
	private int cursorX;
	private int cursorY;

	private String filename;
	private String fileExtention;

	private ArrayList<StringBuilder> data;

	EditorWindow () {
		// default window open
		this.cursorX = 0;
		this.cursorY = 0;

		this.filename = "NewFile.txt";
		//this.fileExtention = "txt";

		this.data = new ArrayList<>();
		this.data.add(new StringBuilder());
	}

	EditorWindow (String newFilename) {
		this.cursorX = 0;
		this.cursorY = 0;

		this.filename = new String(newFilename);
		//String[] components = filename.split(".");
		//this.fileExtention = components[components.length - 1];

		this.data = new ArrayList<>();
	}

	EditorWindow (EditorWindow window) {
		this.cursorX = window.getCursorX();
		this.cursorY = window.getCursorY();

		this.filename = window.getFilename();
		//this.fileExtention = window.getFileExtention();

		this.data = new ArrayList<>();
		for (StringBuilder sb : window.data) {
			this.data.add(new StringBuilder(sb));
		}
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

	public ArrayList<String> getData() {
		ArrayList<String> ret = new ArrayList<>();
		for (StringBuilder s : this.data) {
			ret.add(new String(s));
		}
		return ret;
	}




	public void processKeyIn(KeyEvent e) {
		// System.out.println("Key pressed: " + e.getKeyChar() + " Key-val pressed: " + (int)e.getKeyChar());
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_UP:
				this.moveCursorUp();
				break;
			case KeyEvent.VK_DOWN:
				this.moveCursorDown();
				break;
			case KeyEvent.VK_RIGHT:
				this.moveCursorRight();
				break;
			case KeyEvent.VK_LEFT:
				this.moveCursorLeft();
				break;

			case KeyEvent.VK_BACK_SPACE:
				this.removeCharacter();
				break;

			case KeyEvent.VK_ENTER:
				this.insertNewline();
				break;
			case KeyEvent.VK_TAB:
				System.out.println("Tab not implemented");
				break;

			case KeyEvent.VK_SHIFT:
				break;
			default:
				char keyChar = e.getKeyChar();
				this.insertCharacter(keyChar);
		}
	}

	protected void setCursorX(int x) {
		this.cursorX = x;
	}

	protected void setCursorY(int y) {
		this.cursorY = y;
	}

	protected void moveCursorUp() {
		if (this.cursorY == 0) return; // going up at the top
		this.cursorY--;
		if (this.cursorX > this.data.get(this.cursorY).length()) this.cursorX = this.data.get(this.cursorY).length();
	}

	protected void moveCursorRight() {
		if (this.cursorX == this.data.get(this.cursorY).length() ) {
			if (this.cursorY != this.data.size()-1) {
				this.cursorY++;
				this.cursorX = 0;
			} else return;
		} else {
			this.cursorX++;
		}
	}

	protected void moveCursorLeft() {
		if (this.cursorX == 0) return;
		else this.cursorX--;
	}

	protected void moveCursorDown() {
		if (this.cursorY == this.data.size()-1) return; // going down at the bottom
		this.cursorY++;
		if (this.cursorX > this.data.get(this.cursorY).length()) this.cursorX = this.data.get(this.cursorY).length();
	}

	protected void insertCharacter(char c, int x, int y) {
		if (y > this.data.size() || y < 0) throw new ArrayIndexOutOfBoundsException();
		if (x < 0 || x > this.data.get(y).length()) throw new ArrayIndexOutOfBoundsException();
		this.data.get(y).insert(x, c);
	}

	protected void insertCharacter(char c) {
		insertCharacter(c, this.cursorX, this.cursorY);
		this.cursorX++;
	}

	protected void insertChars(String s) {
		for (int i = 0; i < s.length(); i++) insertCharacter(s.charAt(i));
	}

	protected void removeCharacter(int x, int y) {
		if (y > this.data.size() || y < 0) throw new ArrayIndexOutOfBoundsException();
		if (x < 0 || x > this.data.get(y).length()) throw new ArrayIndexOutOfBoundsException();
		this.data.get(y).deleteCharAt(x);
	}

	protected void removeCharacter() {
		removeCharacter(this.cursorX-1, this.cursorY);
		this.cursorX--;
	}

	protected void insertNewline(int y) {
		if (y < 0) throw new ArrayIndexOutOfBoundsException();

		if (y > this.data.size()) this.data.add(new StringBuilder());
		else this.data.add(y, new StringBuilder());
	}

	protected void insertNewline() {
		this.data.add(this.cursorY+1, new StringBuilder());
		this.cursorY++;
		this.cursorX = 0;
	}

	protected void removeLine(int y) {
		if (y < 0 || y > this.data.size()) throw new ArrayIndexOutOfBoundsException();
		this.data.remove(y);
	}

	public boolean equals(EditorWindow window) {
		if (this.filename != window.filename) return false;
		if (this.cursorX != window.cursorX) return false;
		if (this.cursorY != window.cursorY) return false;
		if (this.data != window.data) return false;
		return true;
	}
}
