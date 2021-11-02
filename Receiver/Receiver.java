// package cp372assignment2.Receiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Receiver {

    public static void main(String[] args) throws IOException {
        String IPadd = args[0]; // IP address of the Receiver
        int rcvPort = Integer.parseInt(args[1]); // port # used by the receiver to receive data from the sender
        int sndrPort = Integer.parseInt(args[2]); // port # used by the sender to receive ACKs from the receiver
        String rcvFileName = args[3];
        File resFile = new File(rcvFileName);
        FileOutputStream oFile = new FileOutputStream(resFile);
        DatagramSocket socket = new DatagramSocket(rcvPort);
        receiveFile(oFile, socket, sndrPort);
    }

    public static void receiveFile(FileOutputStream outputFile, DatagramSocket socket, int sndrPort)
            throws IOException {

        int seqNum = 0;
        int prev = 0;
        int flag = 0;

        while (true) {

            byte[] rcvMessage = new byte[1024];
            byte[] fileData = new byte[1021];

            DatagramPacket incomingPacket = new DatagramPacket(rcvMessage, rcvMessage.length);
            socket.receive(incomingPacket);
            rcvMessage = incomingPacket.getData();

            InetAddress sndrAdd = incomingPacket.getAddress();

            seqNum = rcvMessage[0];
            flag = rcvMessage[1];

            if (seqNum == prev) {
                if (prev == 0) {
                    prev = 1;
                } else {
                    prev = 0;
                }
                System.arraycopy(rcvMessage, 3, fileData, 0, 1021);
                outputFile.write(fileData);
            } else {
                // we didnt get the right seq
                sendAck(prev, socket, sndrAdd, sndrPort);
            }

            if (flag == 1) {
                // got the last datagram
                outputFile.close();
                break;
            }

        }

    }

    private static void sendAck(int prev, DatagramSocket socket, InetAddress sndrAdd, int sndrPort) throws IOException {
        byte[] ackPacket = new byte[2];
        ackPacket[0] = (byte) (prev >> 8);
        ackPacket[1] = (byte) (prev);
        DatagramPacket ack = new DatagramPacket(ackPacket, ackPacket.length, sndrAdd, sndrPort);
        socket.send(ack);
    }

}
