package com.mycompany.app.editor.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import com.mycompany.app.editor.logic.Config;
import com.mycompany.app.editor.logic.Editor;
import com.mycompany.app.editor.logic.EditorBuffer;
import com.mycompany.app.server.EditorAction;

public class GroupTextRender extends JFrame {
    private JTextArea displayArea;
    private Editor editor;
    private JPanel mainPanel;
    private Config config;

    public GroupTextRender(Editor editor) {
        this.editor = editor;
        this.config = editor.config;
        setTitle("Custom Text Editor");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                render(g);
            }
        };
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                processKeyInput(e);
            }
        });

        add(mainPanel);
    }

    private void processKeyInput(KeyEvent event) {
        this.editor.processKeyIn(event);
        mainPanel.repaint(); // Trigger a repaint to update the display
    }

    public void updateDisplay() {
        mainPanel.repaint();
    }


    public void render(Graphics g) {
        EditorBuffer currEditorBuffer = editor.getCurrEditor();
        ArrayList<String> lines = currEditorBuffer.getData();
        int cursorX = currEditorBuffer.getCursorX();
        int cursorY = currEditorBuffer.getCursorY();

        g.setColor(config.getTextColor());
        g.setFont(new Font(config.getFontName(), config.getFontStyle(), config.getFontSize()));
        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), 10, 20 + i * 15);
        }

        // Draw cursor
        g.setColor(config.getCursorColor());
        g.fillRect(10 + cursorX * 7, 8 + cursorY * 15, 2, 14);
    }
}
