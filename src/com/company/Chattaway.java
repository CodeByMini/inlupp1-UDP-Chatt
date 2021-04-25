package com.company;

import javax.swing.*;
import java.io.IOException;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Chattaway {

    public static void SendMessage(View view, UDPChatt udpChatt, User user, MulticastSocket socket, String message) {
        if(message != null){
            message = user.GetName() + message + "\n";
            udpChatt.SendPacket(socket, message);
        }else{
            if (view.GetNewMessageStatus()) {
                message = view.inText;
                message = user.GetName() + ": " + message + "\n";
                udpChatt.SendPacket(socket, message);
                view.SetNewMessageStatus(false);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        User user = new User(JOptionPane.showInputDialog("Skriv ditt namn: "));
        View view = new View(user.GetName());

        try {
            UDPChatt udpChatt = new UDPChatt("234.0.0.0", 1234);
            MulticastSocket socket = new MulticastSocket(udpChatt.GetPort());

            socket.joinGroup(udpChatt.GetInetAddress());
            view.SetConnectedStatus(true);
            SendMessage(view, udpChatt, user, socket, " connected to the chat");
            Thread listeningThread = new Thread(new Reader(socket, view, udpChatt));
            listeningThread.start();

            while (true) {
                if (!view.GetConnectedStatus()) {
                    SendMessage(view, udpChatt, user, socket, " disconnected from the chat");
                    view.textarea.append("You were disconnected from the chat");
                    socket.disconnect();
                    listeningThread.interrupt();
                    break;
                }
                SendMessage(view, udpChatt, user, socket, null);
            }
        } catch(UnknownHostException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}