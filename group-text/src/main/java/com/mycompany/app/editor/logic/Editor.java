package com.mycompany.app.editor.logic;

import java.util.ArrayList;

import com.mycompany.app.editor.render.GroupTextRender;

public class Editor {
	private ArrayList<EditorWindow> windows;
	private int indexPrimary;

	public Editor () {
		this.windows = null;
		this.indexPrimary = -1;
	}

	private void processKeyIn(char c) {
		
	}

	public void runEditor () {
		GroupTextRender renderer = new GroupTextRender();
		while (true) {
			

			renderer.render(this);
		}
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
