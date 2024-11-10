package com.mycompany.app.editor.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.editor.logic.Editor;
/**
 * GroupText
 */
public class GroupTextRender {

	private JFrame mainFrame;
    private JMenu windowMenu;
    private List<JPanel> openPanels = new ArrayList<>();
    private JPanel mainContentPanel; // Main content panel that holds the "windows"
    private CardLayout cardLayout; // CardLayout for switching between "windows"

	public GroupTextRender () {
		// Set up the main application frame
        mainFrame = new JFrame("Main Application");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the menu bar and window menu
        JMenuBar menuBar = new JMenuBar();
        windowMenu = new JMenu("Open Windows");
        menuBar.add(windowMenu);

		// Add button to the menu bar using JToolBar
		JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton newWindowButton = new JButton("Open New Window");
        toolBar.add(newWindowButton);
        menuBar.add(toolBar); // Add the toolbar to the menu bar
        mainFrame.setJMenuBar(menuBar);

        // Set up the main layout (this will hold the panels representing "windows")
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainFrame.add(mainContentPanel, BorderLayout.CENTER);

		// Register ActionListener for the button


        // Show the main window
        mainFrame.setVisible(true);

	}

	public void render(Editor editor) {
		ArrayList<String> lines = editor.getCurrWindowData();
		ArrayList<String> filenames = editor.getAllWindowFilenames();
		int CursorX = editor.getCurrWindowCursorX();
		int CursorY = editor.getCurrWindowCursorY();

	}
}
