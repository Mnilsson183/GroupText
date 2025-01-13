package com.mycompany.app.editor.logic;

import java.awt.event.KeyEvent;
import java.util.Vector;

import com.mycompany.app.editor.render.GroupTextRender;

public class Editor {

	private Vector<EditorBuffer> editorBuffers;
	private EditorBuffer currBuffer;
	private GroupTextRender renderer;

	public Editor () {
		this.editorBuffers = new Vector<EditorBuffer>();
		this.editorBuffers.add(new EditorBuffer("localhost", 8080, this));
		this.currBuffer = editorBuffers.get(0);
	}

	public Editor (int port) {
		this.editorBuffers = new Vector<EditorBuffer>();
		this.editorBuffers.add(new EditorBuffer("localhost", port, this));
		this.currBuffer = editorBuffers.get(0);
	}

	public Editor (String serverAddress, int port) {
		this.editorBuffers = new Vector<EditorBuffer>();
		this.editorBuffers.add(buildnewBuffer(serverAddress, port));
		this.currBuffer = editorBuffers.get(0);
	}

	public void processKeyIn(KeyEvent event) {
		this.currBuffer.processKeyIn(event);
	}

	public EditorBuffer getCurrEditor() {
		return currBuffer;
	}

	private EditorBuffer buildnewBuffer(String serverAddress, int port) {
		EditorBuffer eBuffer = new EditorBuffer(serverAddress, port, this);
		eBuffer.init();

		return eBuffer;
	}

	public void render() {
		renderer.updateDisplay();
	}

	public void runEditor () {
		// run the rendering
		renderer = new GroupTextRender(this);
		renderer.setVisible(true);
	}
}
