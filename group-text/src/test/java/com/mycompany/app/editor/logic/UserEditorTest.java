package com.mycompany.app.editor.logic;

import com.mycompany.app.editor.logic.UserEditor;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

public class UserEditorTest {

	@Test
	public void getAllWindowFilenamesTest() {
		UserEditor uEditor = new UserEditor("test1.txt");
		uEditor.addWindow(new EditorWindow("test2.txt"));

		ArrayList<String> filenames = uEditor.getAllWindowFilenames();
		assertEquals("test1.txt", filenames.get(0));
		assertEquals("test2.txt", filenames.get(1));
	}

}
