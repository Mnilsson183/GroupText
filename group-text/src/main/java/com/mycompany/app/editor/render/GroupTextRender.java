package com.mycompany.app.editor.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import com.mycompany.app.editor.logic.Editor;
import com.mycompany.app.editor.logic.UserEditor;

public class GroupTextRender extends JFrame {
    private JTextArea displayArea;
    private Editor editor;
    private JPanel mainPanel;

    public GroupTextRender(Editor editor) {
        this.editor = editor;
        setTitle("Custom Text Editor");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(null) { // Use null layout
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                render(g); // Custom rendering method
            }
        };
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                processKeyInput(e.getKeyChar());
            }
        });

        add(mainPanel);
    }

    private void processKeyInput(char keyChar) {
        System.out.println("Key pressed: " + keyChar);
        this.editor.processKeyIn(keyChar);
        mainPanel.repaint(); // Trigger a repaint to update the display
    }

    public void render(Graphics g) {
        UserEditor primaryUser = editor.getPrimaryUser();
        ArrayList<String> lines = primaryUser.getFocusedWindowData();
        ArrayList<String> filenames = primaryUser.getAllWindowFilenames();
        int cursorX = primaryUser.getFocusedWindowCursorX();
        int cursorY = primaryUser.getFocusedWindowCursorY();

        // Implement your custom rendering here
        // For example:
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), 10, 20 + i * 15);
        }

        // Draw cursor
        g.setColor(Color.RED);
        g.fillRect(10 + cursorX * 7, 8 + cursorY * 15, 2, 14); // Adjust based on your font metrics
    }

    public void updateDisplay() {
        mainPanel.repaint();
    }
}
