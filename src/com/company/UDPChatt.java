package com.company;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

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

    public String GetPacket(MulticastSocket socket){
        byte[] byteBuffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(byteBuffer, byteBuffer.length, this.inetAddress, this.port);
        String message = null;
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            message = new String(byteBuffer, 0, packet.getLength(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return message;
    }

}


