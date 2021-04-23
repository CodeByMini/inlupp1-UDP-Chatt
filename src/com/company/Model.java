package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Model extends JFrame implements ActionListener {
    JPanel top = new JPanel();
    JPanel center = new JPanel();
    JPanel bottom = new JPanel();


    JLabel topLabel = new JLabel();
    JButton logoutButton = new JButton("Logout");
    JTextField text = new JTextField(50);
    JTextArea textarea = new JTextArea(35, 55);
    JButton sendButton = new JButton("Send");

    private boolean newMessageStatus = false;
    private boolean connectedStatus = false;
    String inText;

    Model(String userName) {
        this.setTitle("Chattaway");
        this.topLabel.setText(userName + " chattar");

        logoutButton.addActionListener(this);
        top.add(logoutButton);
        top.add(topLabel);

        center.add(textarea);

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
        setLocation(0, 0);
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
            //this.textarea.append(this.inText + "\n");
            this.newMessageStatus = true;
            this.text.setText("");
        }
        if (e.getSource() == logoutButton) {
            logoutButton.setText("Exit");
            topLabel.setText("Logged Out");
            this.SetConnectedStatus(false);
        }
    }


    public boolean GetNewMessageStatus() {
        return this.newMessageStatus;
    }

    public void SetNewMessageStatus(boolean bool) {
        this.newMessageStatus = bool;
    }

    public boolean GetConnectedStatus() {
        return this.connectedStatus;
    }

    public void SetConnectedStatus(boolean bool) {
        this.connectedStatus = bool;
    }
}