package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class ClientGUI {
    private JTextField msg;
    private JButton btn_send;
    private JPanel MainPanel;
    private JTextArea display_msg;

    static PrintWriter pr = null;
    static Socket s = null;
    static int port = 5000;
    static InputStreamReader in = null;

    public ClientGUI() {

        //DefaultCaret caret = (DefaultCaret)display_msg.getCaret();
        //caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String msgout = "";
                    msgout = msg.getText().trim();
                    if(msgout!=null) {
                       display_msg.append(msgout + "\n");
                       ClientGUI monClientGUI = new ClientGUI();
                        assert pr != null;
                        monClientGUI.pr.println(msgout);
                        monClientGUI.pr.flush();
                    }
                }
                catch(Exception a){
                    a.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        //IHM
        JFrame frame = new JFrame("IHM");
        frame.setContentPane(new ClientGUI().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        try {
            s = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            assert s != null;
            pr = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in = new InputStreamReader(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert in != null;
        BufferedReader bf = new BufferedReader(in);
        String str = null;
        try {
            str = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server: " + str);
    }
}
