package com.mycompany.app.editor.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import com.mycompany.app.server.EditorAction;

/**
 * EditorWindow
 */
public class EditorWindow {
	private int cursorX;
	private int cursorY;

	private String filename;
	private String fileExtention;

	private ArrayList<StringBuilder> data;
	private EditorAction secondaryAction = null;

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

	public EditorAction getSecondaryAction() {
		return this.secondaryAction;
	}

	public boolean hasSecondaryAction() {
		return this.secondaryAction != null;
	}

	public void nullSecondaryAction() {
		this.secondaryAction = null;
	}


	public void applyTranslation(EditorAction action) {
        	// if y = some int in range and x = -1 delete line
        	if (!action.hasChar()) {

        	    if (action.getX() == -1) {
        	        data.remove(action.getY());
        	    } else {
        	        String line = data.get(action.getY()).toString();
        	        StringBuilder newLine = new StringBuilder(line.substring(0, action.getX()) + line.substring(action.getX()+1));
        	        data.set(action.getY(), newLine);
        	    }

        	// insert
        	// if y = some int in range and x = -1 add line
        	} else {

        	    if (action.getX() == -1) {
        	        data.add(action.getY(), new StringBuilder(""));
        	    } else {
        	        String line = data.get(action.getY()).toString();
        	        String newLine = line.substring(0, action.getX()) + action.getValue() + line.substring(action.getX());
        	        data.set(action.getY(), new StringBuilder(newLine));
        	    }
        	}

	}

	public EditorAction processKeyIn(KeyEvent e) {
		System.out.println("Key pressed: " + e.getKeyChar() + " Key-val pressed: " + (int)e.getKeyChar());
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_UP:
				this.moveCursorUp();
				return null;
			case KeyEvent.VK_DOWN:
				this.moveCursorDown();
				return null;
			case KeyEvent.VK_RIGHT:
				this.moveCursorRight();
				return null;
			case KeyEvent.VK_LEFT:
				this.moveCursorLeft();
				return null;

			case KeyEvent.VK_BACK_SPACE:
				return this.backspace();

			case KeyEvent.VK_ENTER:
				return this.insertNewline();
			case KeyEvent.VK_TAB:
				System.out.println("Tab not implemented");
				return null;

			case KeyEvent.VK_SHIFT:
				return null;
			default:
				char keyChar = e.getKeyChar();
				return this.insertCharacter(keyChar);
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

	protected EditorAction insertCharacter(char c) {
		insertCharacter(c, this.cursorX, this.cursorY);
		this.cursorX++;
		return new EditorAction(this.cursorX-1, this.cursorY, "" + c);
	}

	protected void insertChars(String s) {
		for (int i = 0; i < s.length(); i++) insertCharacter(s.charAt(i));
	}

	protected void removeCharacter(int x, int y) {
		if (y > this.data.size() || y < 0) throw new ArrayIndexOutOfBoundsException();
		if (x < 0 || x > this.data.get(y).length()) throw new ArrayIndexOutOfBoundsException();
		this.data.get(y).deleteCharAt(x);
	}

	protected EditorAction backspace() {
		if (this.cursorX == 0) {
			if (this.cursorY != 0) {
				StringBuilder sb = data.get(cursorY);
				if (sb.length() != 0) {
					secondaryAction = new EditorAction(data.get(cursorY-1).length(), cursorY-1, sb.toString());
					data.get(cursorY-1).append(sb);
				}
				int sbLength = sb.length();
				this.data.remove(this.cursorY);
				this.cursorX = sbLength;
				return new EditorAction((this.cursorY--), false);
			} else return null;
		} else {
			removeCharacter(this.cursorX-1, this.cursorY);
			this.cursorX--;
			return new EditorAction(this.cursorX, this.cursorY);
		}
	}

	protected EditorAction insertNewline(int y) {
		if (y < 0) throw new ArrayIndexOutOfBoundsException();
		if (y > this.data.size()) this.data.add(new StringBuilder());
		else this.data.add(y, new StringBuilder());
		this.cursorX = 0;
		return new EditorAction(this.cursorY, true);
	}

	protected EditorAction insertNewline() {
		// post increments cursor y
		return insertNewline((this.cursorY++)+1);
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
