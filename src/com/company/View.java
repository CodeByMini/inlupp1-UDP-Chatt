package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static javax.swing.ScrollPaneConstants.*;


public class View extends JFrame implements ActionListener {
    JPanel top = new JPanel();
    JPanel center = new JPanel();
    JPanel bottom = new JPanel();

    JLabel topLabel = new JLabel();
    JButton logoutButton = new JButton("Logout");
    JTextField text = new JTextField(50);
    JTextArea textarea = new JTextArea(25, 55);
    JScrollPane scrollPane = new JScrollPane(textarea);
    JButton sendButton = new JButton("Send");

    private boolean newMessageStatus = false;
    private boolean connectedStatus = false;
    String inText;

    View(String userName) {
        this.setTitle("Chattaway");
        this.topLabel.setText(userName + " chattar");

        logoutButton.addActionListener(this);
        top.add(logoutButton);
        top.add(topLabel);

        textarea.setEditable(false);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        center.add(scrollPane);

        sendButton.addActionListener(this);
        text.addActionListener(this);
        bottom.add(text);
        bottom.add(sendButton);

        this.setLayout(new BorderLayout());
        this.add(top, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
        text.requestFocusInWindow();
        setSize(800, 600);
        setLocation(250, 250);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                text.requestFocus();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton || e.getSource() == text) {
            this.inText = text.getText();
            this.newMessageStatus = true;
            this.text.setText("");
            textarea.setCaretPosition(textarea.getDocument().getLength());
        }
        if (e.getSource() == logoutButton) {
            logoutButton.setVisible(false);
            topLabel.setText("Logged Out");
            this.SetConnectedStatus(false);
            try {
                Thread.sleep(1);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }


    public synchronized boolean GetNewMessageStatus() {
        return this.newMessageStatus;
    }

    public synchronized void SetNewMessageStatus(boolean bool) {
        this.newMessageStatus = bool;
    }

    public synchronized boolean GetConnectedStatus() {
        return this.connectedStatus;
    }

    public synchronized void SetConnectedStatus(boolean bool) {
        this.connectedStatus = bool;
    }
}