package com.mycompany.app.editor.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.mycompany.app.editor.logic.Config;
import com.mycompany.app.editor.logic.Editor;
import com.mycompany.app.editor.logic.EditorBuffer;
import com.mycompany.app.server.EditorAction;

public class GroupTextRender extends JFrame {
    private Editor editor;
    private JPanel buffersPanel;
    private JPanel editorPanel;
    private JPanel mainPanel;
    private Config config;
    private JList<String> buffersList;
    private DefaultListModel<String> listModel;

    public GroupTextRender(Editor editor) {
        this.editor = editor;
        this.config = editor.config;
        setTitle("Group Text");
        setSize(config.getResolutionWidth(), config.getResolutionHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buffersPanel = new JPanel();
        editorPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                render(g);
            }
        };

        listModel = new DefaultListModel<>();
        buffersList = new JList<>(listModel);
        buffersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        buffersList.addListSelectionListener(null);
        buffersPanel.add(new JScrollPane(buffersList));

        buffersPanel.setBackground(config.getThemeBackgroundTones1());

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(editorPanel, BorderLayout.CENTER);
        mainPanel.add(buffersPanel, BorderLayout.WEST);

        editorPanel.setBackground(config.getThemeBackgroundTones2());

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
        editorPanel.repaint(); // Trigger a repaint to update the display
    }

    public void updateDisplay() {
        editorPanel.repaint();
    }

    public void render(Graphics g) {
        EditorBuffer currEditorBuffer = editor.getCurrEditor();
        ArrayList<String> lines = currEditorBuffer.getData();
        int cursorX = currEditorBuffer.getCursorX();
        int cursorY = currEditorBuffer.getCursorY();

        g.setFont(new Font(config.getFontName(), config.getFontStyle(), config.getFontSize()));

        Scanner scanner = null;
        for (String line : lines) {
            scanner = new Scanner(line);
            scanner.useDelimiter(" ");
            int lineIndex = 0;
            while (scanner.hasNext()) {
                String s = scanner.next();
                Color highlightColor = highlightSelector(s);
                System.out.println(s + ":" + highlightColor);
                g.setColor(highlightColor);
                g.drawString(s, 10 + lineIndex * 7, 20 + lines.indexOf(line) * 15);
                lineIndex += s.length() + 1;
            }
        }
        if (scanner != null) scanner.close();

        // Draw cursor
        g.setColor(config.getCursorColor());
        g.fillRect(10 + cursorX * 7, 8 + cursorY * 15, 2, 14);
    }

    private Color highlightSelector(String s) {
        if (s.equals("Hello")) return config.getAccentColor1();
        if (s.equals("World")) return config.getAccentColor2();
        else return config.getTextColor();
    }
}
