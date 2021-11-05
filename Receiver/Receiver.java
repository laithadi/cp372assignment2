import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class Receiver {

    public static void main(String[] args) throws IOException {
        String IPadd = args[0]; // IP address of the sender
        InetAddress sndrAdd = InetAddress.getByName(IPadd);
        int rcvPort = Integer.parseInt(args[1]); // port # used by the receiver to receive data from the sender
        int sndrPort = Integer.parseInt(args[2]); // port # used by the sender to receive ACKs from the receiver
        String rcvFileName = args[3];
        File resFile = new File(rcvFileName);
        FileOutputStream oFile = new FileOutputStream(resFile);
        DatagramSocket socket = new DatagramSocket(rcvPort);

        try {
            while (!isAlive(socket, sndrAdd, sndrPort)) {

            }
            receiveFile(oFile, socket, sndrPort, sndrAdd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void receiveFile(FileOutputStream outputFile, DatagramSocket socket, int sndrPort,
            InetAddress sndrAdd) throws IOException {

        int seqNum = 0;
        int prev = 0;
        int flag = 0;
        boolean timer = true;

        while (true) {
            
        
            byte[] rcvMessage = new byte[1024];
            byte[] fileData = new byte[1022];

            DatagramPacket incomingPacket = new DatagramPacket(rcvMessage, rcvMessage.length);
            socket.receive(incomingPacket);
            if (timer) {
                long startTime = System.currentTimeMillis();
                timer = false;
            }
                
            rcvMessage = incomingPacket.getData();
            
            seqNum = rcvMessage[0];
            flag = rcvMessage[1];

            if (seqNum == prev) {
                if (prev == 0) {
                    prev = 1;
                } else {
                    prev = 0;
                }
                System.arraycopy(rcvMessage, 2, fileData, 0, 1022);
                outputFile.write(fileData);
                System.out.println("Sending acknowledegment: " + seqNum);
            } else {
                System.out.println("Did not receive correct sequence number");
            }

            sendAck(seqNum, socket, sndrAdd, sndrPort);

            if (flag == 1) {
                System.out.println("Reached end of file!");
                outputFile.close();
                long endTime = System.currentTimeMillis();
                long timeElapsed = (endTime-startTime)/1000;
                System.out.println("Time Elapsed: " + timeElapsed);
                break;
            }

        }

    }

    public static boolean isAlive(DatagramSocket socket, InetAddress sndrAdd,int sndrPort) throws IOException {
        byte[] aliveornot = new byte[1];

        DatagramPacket incomingPacket = new DatagramPacket(aliveornot, aliveornot.length);
        socket.receive(incomingPacket);
        aliveornot = incomingPacket.getData();

        if (aliveornot[0] == 1) {
        	System.out.println("Ready to go!");
        	sendAck(1,socket,sndrAdd,sndrPort);
            return true;
        } else {
            return false;
        }
    }

    private static void sendAck(int prev, DatagramSocket socket, InetAddress sndrAdd, int sndrPort) throws IOException {
        byte[] ackPacket = new byte[1];
        ackPacket[0] = (byte) (prev);
        DatagramPacket ack = new DatagramPacket(ackPacket, ackPacket.length, sndrAdd, sndrPort);
        socket.send(ack);
    }

}
