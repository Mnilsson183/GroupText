package com.mycompany.app.editor.render;

import java.util.ArrayList;

import com.mycompany.app.editor.logic.Editor;
/**
 * GroupText
 */
public class GroupTextRender {

	public GroupTextRender () {
		
	}

	public void render(Editor editor) {
		ArrayList<String> lines = editor.getCurrWindowData();
		ArrayList<String> filenames = editor.getAllWindowFilenames();
		int CursorX = editor.getCurrWindowCursorX();
		int CursorY = editor.getCurrWindowCursorY();
		//this is a test
	}
}
