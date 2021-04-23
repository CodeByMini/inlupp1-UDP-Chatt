package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Reader extends Thread {

    private InetAddress inetAddress;
    private MulticastSocket socket;
    private int port;
    private Model model;


    Reader(InetAddress inetAddress, int port, MulticastSocket socket, Model model) throws UnknownHostException {
        this.inetAddress = inetAddress;
        this.port = port;
        this.socket = socket;
        this.model = model;
    }

    @Override
    public void run() {
        byte[] byteBuffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(byteBuffer, byteBuffer.length, this.inetAddress, this.port);
        String message = null;
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1);
                socket.receive(packet);
                message = new String(byteBuffer, 0, packet.getLength(), "UTF-8");
                System.out.println(message);
                this.model.textarea.append(message);
            }
            catch (InterruptedException e){
                break;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}