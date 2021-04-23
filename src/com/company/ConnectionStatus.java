package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ConnectionStatus implements Runnable {

    private Model model;
    private UDPChatt udpChatt;
    private MulticastSocket socket;


    ConnectionStatus(MulticastSocket socket, Model model, UDPChatt udpChatt) throws UnknownHostException {
        this.model = model;
        this.socket = socket;
        this.udpChatt = udpChatt;
    }

    @Override
    public void run() {
        while(true){
            if(!model.GetConnectedStatus()){
                try {
                    socket.leaveGroup(udpChatt.GetInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}