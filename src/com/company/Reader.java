package com.company;

import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reader extends Thread {

    private MulticastSocket socket;
     private View view;
    private UDPChatt udpChatt;


    Reader(MulticastSocket socket, View view, UDPChatt udpChatt) throws UnknownHostException {
        this.socket = socket;
        this.view = view;
        this.udpChatt = udpChatt;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            String message;
            message= udpChatt.GetPacket(socket);
            String header =  new SimpleDateFormat("HH.mm.ss").format(new Date()) + " ";
            if(view.GetConnectedStatus()){
                view.textarea.append(header);
                view.textarea.append(message);
                view.textarea.setCaretPosition(view.textarea.getDocument().getLength());
            }else{
                socket.disconnect();
            }
        }
    }
}