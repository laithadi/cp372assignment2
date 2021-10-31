import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Client {

	private JFrame frame;
	private JTextField IPAddress;
	private JTextField senderPort;
	private JTextField receiverPort;
	private JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	private JTextField filePath;
	private JLabel lblTimeout;
	private JTextField timeout;
	private JRadioButton rdbtnReliable;
	private JRadioButton rdbtnUnreliable;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnSend;
	private JButton btnIsalive;
	private JTextField textField;
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
		
		timeout = new JTextField();
		GridBagConstraints gbc_timeout = new GridBagConstraints();
		gbc_timeout.insets = new Insets(0, 0, 5, 5);
		gbc_timeout.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeout.gridx = 5;
		gbc_timeout.gridy = 4;
		frame.getContentPane().add(timeout, gbc_timeout);
		timeout.setColumns(10);
		
		rdbtnUnreliable = new JRadioButton("Unreliable");
		buttonGroup.add(rdbtnUnreliable);
		GridBagConstraints gbc_rdbtnUnreliable = new GridBagConstraints();
		gbc_rdbtnUnreliable.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnUnreliable.gridx = 8;
		gbc_rdbtnUnreliable.gridy = 4;
		frame.getContentPane().add(rdbtnUnreliable, gbc_rdbtnUnreliable);
		
		btnSend = new JButton("SEND");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 5, 5);
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 6;
		frame.getContentPane().add(btnSend, gbc_btnSend);
		
		btnIsalive = new JButton("ISALIVE?");
		GridBagConstraints gbc_btnIsalive = new GridBagConstraints();
		gbc_btnIsalive.insets = new Insets(0, 0, 5, 5);
		gbc_btnIsalive.gridx = 5;
		gbc_btnIsalive.gridy = 6;
		frame.getContentPane().add(btnIsalive, gbc_btnIsalive);
		
		lblSentInorder = new JLabel("# of sent in-order packets");
		GridBagConstraints gbc_lblSentInorder = new GridBagConstraints();
		gbc_lblSentInorder.gridwidth = 3;
		gbc_lblSentInorder.insets = new Insets(0, 0, 5, 5);
		gbc_lblSentInorder.gridx = 7;
		gbc_lblSentInorder.gridy = 6;
		frame.getContentPane().add(lblSentInorder, gbc_lblSentInorder);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 8;
		gbc_textField.gridy = 7;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
	}

}
