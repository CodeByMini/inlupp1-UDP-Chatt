package com.company;

import javax.swing.*;
import java.io.IOException;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
//import java.util.Scanner;

public class Chattaway {

    public static synchronized void SendMessage(Model model, UDPChatt udpChatt, User user, MulticastSocket socket) {
        if (model.GetNewMessageStatus()) {
            String message;
            message = model.inText;//scanner.nextLine();
            message = user.GetName() + ": " + message + "\n";
            udpChatt.SendPacket(socket, message);
            model.SetNewMessageStatus(false);
            //}
        }
    }

    public static void main(String[] args) throws InterruptedException {
        User user = new User(JOptionPane.showInputDialog("Skriv ditt namn: "));
        Model model = new Model(user.GetName());

        //Scanner scanner = new Scanner(System.in);

        try {
            UDPChatt udpChatt = new UDPChatt("234.0.0.0", 1234);
            MulticastSocket socket = new MulticastSocket(udpChatt.GetPort());
            Thread listeningThread = new Thread(new Reader(udpChatt.GetInetAddress(), udpChatt.GetPort(), socket, model));

            if (!model.GetConnectedStatus()) {
                socket.joinGroup(udpChatt.GetInetAddress());
                model.SetConnectedStatus(true);
                listeningThread.start();
            }
            //Thread connectionThread = new Thread(new ConnectionStatus(socket, model, udpChatt));
            //connectionThread.start();

            while (true) {
                SendMessage(model, udpChatt, user, socket);
                if (!model.GetConnectedStatus()) {
                    listeningThread.interrupt();
                    socket.disconnect();
                    break;
                }

            }
                } catch(UnknownHostException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }/* catch (InterruptedException e) {
            e.printStackTrace();
        }*/
            }
        }