package com.company;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPChatt extends Thread{

    private InetAddress inetAddress;
    private int port;


    UDPChatt(String inetAddress, int port) throws UnknownHostException {

        this.inetAddress = InetAddress.getByName(inetAddress);
        this.port = port;

    }
    public InetAddress GetInetAddress() {
        return this.inetAddress;
    }
    public int GetPort(){
        return this.port;
    }

    public boolean SendPacket(MulticastSocket socket, String message){
        try{
            byte[] byteArray = message.getBytes();
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, this.inetAddress, this.port);
            socket.send(packet);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}


