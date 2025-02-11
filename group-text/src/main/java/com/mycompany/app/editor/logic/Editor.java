package com.mycompany.app.editor.logic;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import com.mycompany.app.editor.render.GroupTextRender;

public class Editor {

	private Vector<EditorBuffer> editorBuffers;
	private EditorBuffer currBuffer;
	private GroupTextRender renderer;
	protected boolean headless;
	public Config config;
	protected String configLocation = "config.gt";

	public Editor () {
		this.editorBuffers = new Vector<EditorBuffer>();
		this.editorBuffers.add(new EditorBuffer("localhost", 8080, this));
		this.currBuffer = editorBuffers.get(0);
		this.config = new Config();
	}

	public Editor (int port) {
		this.editorBuffers = new Vector<EditorBuffer>();
		this.editorBuffers.add(new EditorBuffer("localhost", port, this));
		this.currBuffer = editorBuffers.get(0);
		this.config = new Config();
	}

	public Editor (String serverAddress, int port) {
		this.editorBuffers = new Vector<EditorBuffer>();
		this.editorBuffers.add(buildnewBuffer(serverAddress, port));
		this.currBuffer = editorBuffers.get(0);
		this.config = new Config();
	}

	protected Editor (boolean headless) {
		this.editorBuffers = new Vector<EditorBuffer>();
		this.editorBuffers.add(new EditorBuffer("localhost", 8080, this));
		this.currBuffer = editorBuffers.get(0);

		this.headless = headless;
		this.config = new Config();
	}

	protected Config getConfig() {
		Config config = readConfig();
		if (config == null) return new Config();
		else return config;

	}

	public void processKeyIn(KeyEvent event) {
		this.currBuffer.processKeyIn(event);
	}

	public EditorBuffer getCurrEditor() {
		return currBuffer;
	}

	public EditorBuffer buildnewBuffer(String serverAddress, int port) {
		EditorBuffer eBuffer = new EditorBuffer(serverAddress, port, this);
		eBuffer.init();

		return eBuffer;
	}

	protected Config readConfig() {
		Config config;
		try {
			config = Config.parseStringToObject(configLocation);
			return config;
		} catch (Exception e) {
			System.out.println("Failed to read config from file");
			return null;
		}
	}

	protected void writeConfig() {
		try {
			System.out.println("Writing config");
			FileWriter fileWriter = new FileWriter(new File(configLocation));
			fileWriter.write(config.toString());
			fileWriter.close();
		} catch (Exception e) {
			System.out.println("Unable to write config to file");
		}
	}

	public void render() {
		writeConfig();
		if (headless) return;
		renderer.updateDisplay();
	}

	public void runEditor () {
		// run the rendering
		if (headless) {
			renderer = null;
			return;
		}
		renderer = new GroupTextRender(this);
		renderer.setVisible(true);
	}
}
