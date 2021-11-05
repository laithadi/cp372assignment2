import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Client {
	private Sender sender = null;
	private JFrame frame;
	private JTextField IPAddress;
	private JTextField senderPort;
	private JTextField receiverPort;
	private JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	private JTextField filePath;
	private JLabel lblTimeout;
	private JTextField TIMEOUT;
	private JRadioButton rdbtnReliable;
	private JRadioButton rdbtnUnreliable;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnSend;
	private JButton btnIsalive;
	private JTextField numPacketsinOrder;
	private JLabel lblSentInorder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 509, 386);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		GridBagConstraints gbc_lblIpAddress = new GridBagConstraints();
		gbc_lblIpAddress.gridwidth = 2;
		gbc_lblIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblIpAddress.gridx = 1;
		gbc_lblIpAddress.gridy = 0;
		frame.getContentPane().add(lblIpAddress, gbc_lblIpAddress);
		
		JLabel lblSenderUdpPort = new JLabel("Sender UDP Port");
		GridBagConstraints gbc_lblSenderUdpPort = new GridBagConstraints();
		gbc_lblSenderUdpPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenderUdpPort.gridx = 5;
		gbc_lblSenderUdpPort.gridy = 0;
		frame.getContentPane().add(lblSenderUdpPort, gbc_lblSenderUdpPort);
		
		JLabel lblReceiverUdpPort = new JLabel("Receiver UDP Port");
		GridBagConstraints gbc_lblReceiverUdpPort = new GridBagConstraints();
		gbc_lblReceiverUdpPort.gridwidth = 3;
		gbc_lblReceiverUdpPort.insets = new Insets(0, 0, 5, 0);
		gbc_lblReceiverUdpPort.gridx = 8;
		gbc_lblReceiverUdpPort.gridy = 0;
		frame.getContentPane().add(lblReceiverUdpPort, gbc_lblReceiverUdpPort);
		
		IPAddress = new JTextField();
		GridBagConstraints gbc_IPAddress = new GridBagConstraints();
		gbc_IPAddress.gridwidth = 2;
		gbc_IPAddress.insets = new Insets(0, 0, 5, 5);
		gbc_IPAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_IPAddress.gridx = 1;
		gbc_IPAddress.gridy = 1;
		frame.getContentPane().add(IPAddress, gbc_IPAddress);
		IPAddress.setColumns(10);
		
		senderPort = new JTextField();
		GridBagConstraints gbc_senderPort = new GridBagConstraints();
		gbc_senderPort.insets = new Insets(0, 0, 5, 5);
		gbc_senderPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_senderPort.gridx = 5;
		gbc_senderPort.gridy = 1;
		frame.getContentPane().add(senderPort, gbc_senderPort);
		senderPort.setColumns(10);
		
		receiverPort = new JTextField();
		GridBagConstraints gbc_receiverPort = new GridBagConstraints();
		gbc_receiverPort.insets = new Insets(0, 0, 5, 0);
		gbc_receiverPort.gridwidth = 3;
		gbc_receiverPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_receiverPort.gridx = 8;
		gbc_receiverPort.gridy = 1;
		frame.getContentPane().add(receiverPort, gbc_receiverPort);
		receiverPort.setColumns(10);
		
		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = fileChooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					filePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
				
			}
		});
		GridBagConstraints gbc_btnSelectFile = new GridBagConstraints();
		gbc_btnSelectFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectFile.gridx = 1;
		gbc_btnSelectFile.gridy = 3;
		frame.getContentPane().add(btnSelectFile, gbc_btnSelectFile);
		
		lblTimeout = new JLabel("Timeout");
		GridBagConstraints gbc_lblTimeout = new GridBagConstraints();
		gbc_lblTimeout.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeout.gridx = 5;
		gbc_lblTimeout.gridy = 3;
		frame.getContentPane().add(lblTimeout, gbc_lblTimeout);
		
		rdbtnReliable = new JRadioButton("Reliable");
		rdbtnReliable.setSelected(true);
		buttonGroup.add(rdbtnReliable);
		GridBagConstraints gbc_rdbtnReliable = new GridBagConstraints();
		gbc_rdbtnReliable.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnReliable.gridx = 8;
		gbc_rdbtnReliable.gridy = 3;
		frame.getContentPane().add(rdbtnReliable, gbc_rdbtnReliable);
		
		filePath = new JTextField();
		GridBagConstraints gbc_filePath = new GridBagConstraints();
		gbc_filePath.insets = new Insets(0, 0, 5, 5);
		gbc_filePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_filePath.gridx = 1;
		gbc_filePath.gridy = 4;
		frame.getContentPane().add(filePath, gbc_filePath);
		filePath.setColumns(10);
		
		TIMEOUT = new JTextField();
		GridBagConstraints gbc_TIMEOUT = new GridBagConstraints();
		gbc_TIMEOUT.insets = new Insets(0, 0, 5, 5);
		gbc_TIMEOUT.fill = GridBagConstraints.HORIZONTAL;
		gbc_TIMEOUT.gridx = 5;
		gbc_TIMEOUT.gridy = 4;
		frame.getContentPane().add(TIMEOUT, gbc_TIMEOUT);
		TIMEOUT.setColumns(10);
		
		rdbtnUnreliable = new JRadioButton("Unreliable");
		buttonGroup.add(rdbtnUnreliable);
		GridBagConstraints gbc_rdbtnUnreliable = new GridBagConstraints();
		gbc_rdbtnUnreliable.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnUnreliable.gridx = 8;
		gbc_rdbtnUnreliable.gridy = 4;
		frame.getContentPane().add(rdbtnUnreliable, gbc_rdbtnUnreliable);
		
		btnIsalive = new JButton("ISALIVE?");
		btnIsalive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingWorker sw = new SwingWorker<Void,Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						String IP;
						int rcvPort;
						int sendPort;
						File file;
						int timeout;
						boolean unreliable;
						try {
							if(sender!=null) {
								sender.close();
							}
							IP = IPAddress.getText();
							rcvPort = Integer.parseInt(receiverPort.getText());
							sendPort = Integer.parseInt(senderPort.getText());
							timeout = Integer.parseInt(TIMEOUT.getText());
							unreliable = rdbtnUnreliable.isSelected();
							file = fileChooser.getSelectedFile();
							sender = new Sender(IP,rcvPort,sendPort,file,timeout,unreliable);
							
							if(sender.isAlive()) {
								numPacketsinOrder.setText("0");
								btnSend.setEnabled(true);
								btnIsalive.setEnabled(false);
								rdbtnReliable.setEnabled(false);
								rdbtnUnreliable.setEnabled(false);
								btnSelectFile.setEnabled(false);
								senderPort.setEditable(false);
								receiverPort.setEditable(false);
								IPAddress.setEditable(false);
								TIMEOUT.setEditable(false);
								JOptionPane.showMessageDialog(null, "Receiver Ready!");
							}else {
								JOptionPane.showMessageDialog(null, "Receiver Not Ready!");
							}
						}catch (NumberFormatException err) {
							System.out.println("Incorrect credentials");
							JOptionPane.showMessageDialog(null, "Incorrect Input");
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Incorrect Credentials");
						} catch (SocketException  e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Unable to connect");
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Receiver Not Ready!");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Receiver Not Ready!");
						}
						return null;
						
						
					}
					
				};
				sw.execute();
					
				
				
				
			}
		});
		GridBagConstraints gbc_btnIsalive = new GridBagConstraints();
		gbc_btnIsalive.insets = new Insets(0, 0, 5, 5);
		gbc_btnIsalive.gridx = 1;
		gbc_btnIsalive.gridy = 6;
		frame.getContentPane().add(btnIsalive, gbc_btnIsalive);
		
		btnSend = new JButton("SEND");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSend.setEnabled(false);
				SwingWorker sw = new SwingWorker<Void,Integer>(){
					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						byte[] byteArray = sender.readFiletoBytes();
						boolean end=false;
						int sequenceNum =0;
						int packetNum =0;
						int index=0;
						System.out.println("Unreliable mode: "+ rdbtnUnreliable.isSelected());
						
						for(int i=0;i<byteArray.length;i+=1022) {
							index=i;
							if((i+1022)>=byteArray.length) {
								end = true;
							}
							packetNum = sender.sendPacket(byteArray, index, sequenceNum, packetNum, end);
							sequenceNum = sequenceNum==1 ? 0:1;
							publish(packetNum);
							
						}
						return null;
						
					}
					@Override
					protected void process(List<Integer> packets) {
						numPacketsinOrder.setText(Integer.toString(packets.get(packets.size()-1)));
					}
					
					@Override
					protected void done() {
						try {
							JOptionPane.showMessageDialog(null, "File transferred");
						}catch(Exception e) {
							;
						}
						
					}
					
				};
				sw.execute();
				
				btnIsalive.setEnabled(true);
				btnSend.setEnabled(false);
				rdbtnReliable.setEnabled(true);
				rdbtnUnreliable.setEnabled(true);
				btnSelectFile.setEnabled(true);
				senderPort.setEditable(true);
				receiverPort.setEditable(true);
				IPAddress.setEditable(true);
				TIMEOUT.setEditable(true);
			}
				
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 5, 5);
		gbc_btnSend.gridx = 5;
		gbc_btnSend.gridy = 6;
		frame.getContentPane().add(btnSend, gbc_btnSend);
		
		lblSentInorder = new JLabel("# of sent in-order packets");
		GridBagConstraints gbc_lblSentInorder = new GridBagConstraints();
		gbc_lblSentInorder.gridwidth = 3;
		gbc_lblSentInorder.insets = new Insets(0, 0, 5, 5);
		gbc_lblSentInorder.gridx = 7;
		gbc_lblSentInorder.gridy = 6;
		frame.getContentPane().add(lblSentInorder, gbc_lblSentInorder);
		
		numPacketsinOrder = new JTextField();
		numPacketsinOrder.setEditable(false);
		GridBagConstraints gbc_numPacketsinOrder = new GridBagConstraints();
		gbc_numPacketsinOrder.insets = new Insets(0, 0, 0, 5);
		gbc_numPacketsinOrder.fill = GridBagConstraints.HORIZONTAL;
		gbc_numPacketsinOrder.gridx = 8;
		gbc_numPacketsinOrder.gridy = 7;
		frame.getContentPane().add(numPacketsinOrder, gbc_numPacketsinOrder);
		numPacketsinOrder.setColumns(10);
	}

}
