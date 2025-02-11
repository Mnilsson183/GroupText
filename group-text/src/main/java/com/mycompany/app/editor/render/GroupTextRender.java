package com.mycompany.app.editor.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import com.mycompany.app.editor.logic.Config;
import com.mycompany.app.editor.logic.Editor;
import com.mycompany.app.editor.logic.EditorBuffer;

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

        // add a plus button to add a new buffer that brings up a dialog menu
        JButton addBuffer = new JButton("+");
        addBuffer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Object[] options = {"Connect to Server", "Host Local Server"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an option", "New Buffer",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                // Connect to Server
                JTextField serverAddressField = new JTextField();
                JTextField portField = new JTextField();
                Object[] message = {
                    "Server Address:", serverAddressField,
                    "Port:", portField
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Connect to Server", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                String serverAddress = serverAddressField.getText();
                String port = portField.getText();
                if (!serverAddress.isEmpty() && !port.isEmpty()) {
                    String bufferName = serverAddress + ":" + port;
                    listModel.addElement(bufferName);
                    editor.buildnewBuffer(serverAddress, Integer.parseInt(port));
                }
                }
            } else if (choice == 1) {
                // Host Local Server
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    throw new UnsupportedOperationException("File selection not implemented yet");
                } catch (UnsupportedOperationException ex) {
                    ex.printStackTrace();
                }
                }
            }
            }
        });

        buffersPanel.setLayout(new BoxLayout(buffersPanel, BoxLayout.Y_AXIS));
        buffersPanel.add(addBuffer);


        listModel = new DefaultListModel<>();
        buffersList = new JList<>(listModel);
        buffersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        buffersList.addListSelectionListener(null);
        buffersPanel.add(new JScrollPane(buffersList));

        buffersPanel.setBackground(config.getTheme().getThemeBackgroundTones1());

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(editorPanel, BorderLayout.CENTER);
        mainPanel.add(buffersPanel, BorderLayout.WEST);

        editorPanel.setBackground(config.getTheme().getThemeBackgroundTones2());

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

        EditorTheme theme = config.getTheme();

        for (String line : lines) {
            Syntax.Highlight[] highlighting = currEditorBuffer.getSyntax().getHighlightColor(line);
            for (int i = 0; i < line.length(); i++) {
                g.setColor(theme.getTextColor());
                switch (highlighting[i]) {
                    case DEFAULT:
                        g.setColor(theme.getTextColor());
                        break;
                    case COMMENT:
                        g.setColor(theme.getCommentColor());
                        break;
                    case STRING:
                        g.setColor(theme.getStringColor());
                        break;
                    case NUMBER:
                        g.setColor(theme.getNumberColor());
                        break;
                    case KEYWORD:
                        g.setColor(theme.getKeywordColor());
                        break;
                    case OPERATOR:
                        g.setColor(theme.getOperatorColor());
                        break;
                    case SEPARATOR:
                        g.setColor(theme.getSeparatorColor());
                        break;
                    case VARIABLE:
                        g.setColor(theme.getVariableColor());
                        break;
                }
                g.drawString(String.valueOf(line.charAt(i)), 10 + i * 7, 20 + lines.indexOf(line) * 15);
            }
        }

        // Draw cursor
        g.setColor(theme.getCursorColor());
        g.fillRect(10 + cursorX * 7, 8 + cursorY * 15, 2, 14);
    }
}
