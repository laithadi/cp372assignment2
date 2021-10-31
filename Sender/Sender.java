import java.io.*;
import java.net.*;


public class Sender {
	private String IP;
    private int rcvPort;
    private int sendPort;
    private DatagramSocket connection;
    private FileInputStream fis = null;
    private BufferedInputStream bis = null;
    private final static String CRLF = "\r\n";
    
    public Sender(String IP, int rcvPort, int sendPort) {
    	try {
    		this.sendPort = sendPort;
        	this.rcvPort = rcvPort;
        	this.connection = new DatagramSocket(this.rcvPort);
    	} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    public void send(File file) {
    	try {
			this.fis = new FileInputStream(file);
			this.bis = new BufferedInputStream(this.fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}
