import java.io.*;
import java.net.*;


public class Sender {
	private InetAddress IP;
    private int rcvPort;
    private int sendPort;
    private DatagramSocket socket;
    private final static String CRLF = "\r\n";
    
    public Sender(String IP, int rcvPort, int sendPort) {
    	try {
    		this.IP = InetAddress.getByName(IP);
    		this.sendPort = sendPort;
        	this.rcvPort = rcvPort;
        	this.socket = new DatagramSocket(this.rcvPort);
    	} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    public void send(File file,int timeout) {
    	try {
			byte[] byteArray = readFiletoBytes(file);
			boolean end=false;
			int sequenceNum=0;
			
			for(int i =0;i<byteArray.length;i=i+1022) {
				byte[] message = new byte[1024];
				message[0] = (byte) sequenceNum;
				
				if((i+1022)>=byteArray.length) {
					end=true;
					message[1] = (byte) 1;
				}else {
					message[1] = (byte) 0;
				}
				
				if(!end) {
					System.arraycopy(byteArray, i, message, 2, message.length-2);
				}else {
					System.arraycopy(byteArray, i, message, 2, byteArray.length-i);
				}
				DatagramPacket data = new DatagramPacket(message,message.length,this.IP,this.rcvPort);
				this.socket.send(data);
				System.out.println("Sequence Number: " + sequenceNum );
				
				boolean received;
				int ackSequence=0;
				while(true) {
					byte[] ACK = new byte[1];
					DatagramPacket ACKpack = new DatagramPacket(ACK,ACK.length);
					try {
						socket.setSoTimeout(timeout);
						socket.receive(ACKpack);
						ackSequence = ACK[0];
						received = true;
					}catch (SocketTimeoutException e) {
						received = false;
					}
					
					if(ackSequence == sequenceNum && received) {
						System.out.println("ACK received: Sequence num: " + ackSequence);
						break;
					}else {
						socket.send(data);
						System.out.println("Resending packet");
					}
					
					
					
				}
				sequenceNum = sequenceNum==1 ? 0 : 1;
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private byte[] readFiletoBytes(File file) {
    	FileInputStream inputStream = null;
    	byte[] bArray = new byte[(int) file.length()];
    	try {
    		inputStream = new FileInputStream(file);
    		inputStream.read(bArray);
    		inputStream.close();
    	}catch (IOException e) {
    		System.out.println("Unable to read file to bytes");
    	}
    	return bArray;
    }
    
}
