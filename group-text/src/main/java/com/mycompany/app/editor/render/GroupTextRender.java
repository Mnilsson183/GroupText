package com.mycompany.app.editor.render;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.mycompany.app.editor.logic.Editor;
import com.mycompany.app.editor.logic.UserEditor;
/**
 * GroupText
 */
public class GroupTextRender extends JFrame{
	private JTextArea displayArea;
	private Editor editor;

	public GroupTextRender (Editor editor) {
		this.editor = editor;
		setTitle("Custom Text Editor");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        	displayArea = new JTextArea();
        	displayArea.setEditable(false);  // Prevent direct editing

		// Add KeyListener to the frame
        	addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				processKeyInput(e.getKeyChar());
			}
		});

		setFocusable(true);
        	add(new JScrollPane(displayArea), BorderLayout.CENTER);
	}

	private void processKeyInput(char keyChar) {
		this.editor.processKeyIn(keyChar);
	}


	public void render() {
		UserEditor primaryUser = editor.getPrimaryUser();
		ArrayList<String> lines = primaryUser.getCurrWindowData();
		ArrayList<String> filenames = primaryUser.getAllWindowFilenames();
		int CursorX = primaryUser.getCurrWindowCursorX();
		int CursorY = primaryUser.getCurrWindowCursorY();
		System.out.println("GroupTextRender Render not implimented");
	}
}
