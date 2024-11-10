package com.mycompany.app.editor.render;

import java.util.ArrayList;

import com.mycompany.app.editor.logic.Editor;
import com.mycompany.app.editor.logic.UserEditor;
/**
 * GroupText
 */
public class GroupTextRender {

	public GroupTextRender () {
		
	}

	public void render(Editor editor) {
		UserEditor primaryUser = editor.getPrimaryUser();
		ArrayList<String> lines = primaryUser.getCurrWindowData();
		ArrayList<String> filenames = primaryUser.getAllWindowFilenames();
		int CursorX = primaryUser.getCurrWindowCursorX();
		int CursorY = primaryUser.getCurrWindowCursorY();

	}
}
