import java.io.*;
import java.net.*;


public class Sender {
	private InetAddress rcvAddress;
    private int rcvPort;
    private int sendPort;
    private DatagramSocket socket;
    private File file;
    private boolean unreliable;
    private int timeout;
    
    public Sender(String IP, int rcvPort, int sendPort,File file, int timeout,boolean unreliable) throws UnknownHostException, SocketException {
    	
    		this.rcvAddress = InetAddress.getByName(IP);
    		this.sendPort = sendPort;
        	this.rcvPort = rcvPort;
        	this.socket = new DatagramSocket(this.sendPort);
        	this.file = file;
        	this.timeout = timeout;
        	this.unreliable = unreliable;
        	
    	
    	
    	
    }
    
    public void close() {
    	this.socket.close();
    }
    public boolean isAlive() throws IOException {
    	byte[] message = new byte[1];
    	message[0] = (byte) 1;
    	int alive;
    	DatagramPacket handshake = new DatagramPacket(message, message.length,this.rcvAddress,this.rcvPort);
    	this.socket.send(handshake);
    	try {
    		byte[] ACK = new byte[1];
    		DatagramPacket ACKpack = new DatagramPacket(ACK,ACK.length);
    		this.socket.setSoTimeout(this.timeout);
			this.socket.receive(ACKpack);
			alive = ACK[0];
			if(alive==1) {
				return true;
			}
    	}catch (SocketTimeoutException e) {
    		System.out.println("Receiver not ready");
    		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    public int sendPacket(byte[] byteArray,int index,int sequenceNum,int packetNum,boolean end) throws IOException {
    	byte[] message = new byte[1024];
    	message[0] = (byte) sequenceNum;
    	message[1] = end ? (byte) 1: (byte) 0;
    	if(!end) {
    		System.arraycopy(byteArray, index, message, 2, message.length-2);
    	}else {
    		System.arraycopy(byteArray, index, message, 2, byteArray.length-index);
    		//System.out.println("End of file");
    	}
    	DatagramPacket data = new DatagramPacket(message,message.length,this.rcvAddress,this.rcvPort);
    	if(this.unreliable && packetNum%10==0){
    		//System.out.println(String.format("Dropping packet #:%d in unreliable mode", packetNum));
    		;
    	}else {
    		//System.out.println("Sending packet #: "+ packetNum);
    		this.socket.send(data);
    	}
    	packetNum++;
    	//System.out.println("Sequence Number: "+ sequenceNum);
    	
    	boolean received;
    	int ackSequence=0;
    	while(true) {
    		byte[] ACK = new byte[1];
    		DatagramPacket ACKpack = new DatagramPacket(ACK,ACK.length);
    		try {
    			socket.setSoTimeout(this.timeout);
    			socket.receive(ACKpack);
    			ackSequence = ACK[0];
    			received = true;
    			//System.out.println("Acknowledgement number: " + ACK[0]);
    		}catch (SocketTimeoutException e) {
    			received = false;
    			//System.out.println("ACK not received");
    		}
    		
    		if(ackSequence == sequenceNum && received) {
    			//System.out.println("ACK received: Sequence Num: " + ackSequence);
    			break;
    		}else {
    			socket.send(data);
    			packetNum++;
    			//System.out.println("Resending packet");
    		}
    	}
    	return packetNum;
    	
    	
    }
    
    
    public byte[] readFiletoBytes() {
    	FileInputStream inputStream = null;
    	byte[] bArray = new byte[(int) this.file.length()];
    	try {
    		inputStream = new FileInputStream(this.file);
    		inputStream.read(bArray);
    		inputStream.close();
    	}catch (IOException e) {
    		System.out.println("Unable to read file to bytes");
    	}
    	return bArray;
    }
    
}
