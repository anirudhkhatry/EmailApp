import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class SendMailWindowGUI extends JFrame implements ActionListener
{
	private ImageIcon mailPic;
	private JLabel labSubject;
	private JLabel labTo;
	private JLabel labAttachment;
	private JLabel labPic;
	private JButton sendAttachment;
	private JButton send;
	private JButton changeFont;
	private JButton multipleRecipients;
	private JPanel buttonPanel;
	private JPanel headerPanel;
	private JPanel headerPanelInfo;
	private JPanel headerPanelGraphic;
	private JPanel messagePanel;
	private Container pane;
	private static JTextField textSubject;
	private static JTextField textTo;
	private static JTextField textAttachment;
	private static JTextArea textMessage;
	private static JComboBox comboTo;
	private String recipient;
	private String[] names;
	private static Object[] recipientNames;
	private boolean multiple = false;
	private Email email;

	public SendMailWindowGUI() throws IOException
	{
		
		email = new Email();
		names = new String[EMailClient.getUserNamesSize()];
		for(int count = 0; count < names.length; count++)
		{
			names[count] = EMailClient.getName(count);
		}
		recipient = names[0];
		addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					MainFunctionMenuGUI mainFunctionMenuGUI = new MainFunctionMenuGUI();
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					final int HEIGHT = 400;
					final int WIDTH = 600;
					mainFunctionMenuGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
											      ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
					mainFunctionMenuGUI.setVisible(true);
				}
			}
		);
		
		setTitle("Send E-Mail");
		mailPic = new ImageIcon("Mail.png");
		labSubject = new JLabel("SUBJECT:");
		labTo = new JLabel("TO:");
		labAttachment = new JLabel("ATTACHMENT:");
		labPic = new JLabel(mailPic);
		textSubject = new JTextField(15);
		textAttachment = new JTextField(15);
		textMessage = new JTextArea(30, 49);
		textAttachment.setEditable(false);
		sendAttachment = new JButton("Send Attachment");
		send = new JButton("Send Mail");
		multipleRecipients = new JButton("Multiple Recipients");
		changeFont = new JButton("Change Display Font");
		comboTo = new JComboBox(names);
		buttonPanel = new JPanel();
		messagePanel = new JPanel();
		headerPanel = new JPanel();
		headerPanelInfo = new JPanel();
		headerPanelGraphic = new JPanel();
		pane = getContentPane();
		buttonPanel.setLayout(new GridLayout(1,4));
		messagePanel.setLayout(new FlowLayout());
		headerPanelInfo.setLayout(new GridLayout(3,2));
		headerPanelGraphic.setLayout(new BorderLayout());
		headerPanel.setLayout(new BorderLayout());
		pane.setLayout(new BorderLayout());
		buttonPanel.add(send);
		buttonPanel.add(sendAttachment);
		buttonPanel.add(multipleRecipients);
		buttonPanel.add(changeFont);
		messagePanel.add(textMessage);
		headerPanelInfo.add(labTo);
		headerPanelInfo.add(comboTo);
		headerPanelInfo.add(labSubject);
		headerPanelInfo.add(textSubject);
		headerPanelInfo.add(labAttachment);
		headerPanelInfo.add(textAttachment);
		headerPanelGraphic.add(labPic, BorderLayout.CENTER);
		headerPanel.add(headerPanelInfo, BorderLayout.WEST);
		headerPanel.add(headerPanelGraphic, BorderLayout.EAST);
		pane.add(messagePanel, BorderLayout.CENTER);
		pane.add(buttonPanel, BorderLayout.SOUTH);
		pane.add(headerPanel, BorderLayout.NORTH);
		send.addActionListener(this);
		sendAttachment.addActionListener(this);
		multipleRecipients.addActionListener(this);
		changeFont.addActionListener(this);
		ComboHandler handler = new ComboHandler();
		comboTo.addItemListener(handler);
	}

	public SendMailWindowGUI(Email email) throws IOException
	{
		this.email = email;
		email.setRead(false);
		names = new String[EMailClient.getUserNamesSize()];
		for(int count = 0; count < names.length; count++)
		{
			names[count] = EMailClient.getName(count);
		}
		recipient = names[0];
		addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					MainFunctionMenuGUI mainFunctionMenuGUI = new MainFunctionMenuGUI();
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					final int HEIGHT = 350;
					final int WIDTH = 500;
					mainFunctionMenuGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
							    				  ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
					mainFunctionMenuGUI.setVisible(true);
				}
			}
		);
		setTitle("Send E-Mail");
		mailPic = new ImageIcon("Mail.png");
		labSubject = new JLabel("SUBJECT:");
		labTo = new JLabel("TO:");
		labAttachment = new JLabel("ATTACHMENT:");
		labPic = new JLabel(mailPic);
		textSubject = new JTextField(15);
		textAttachment = new JTextField(15);
		textMessage = new JTextArea(30, 53);
		textAttachment.setEditable(false);
		textSubject.setText(email.getSubject());
		textAttachment.setText(email.getAttachmentName());
		textMessage.setText(email.getContent());
		sendAttachment = new JButton("Send Attachment");
		send = new JButton("Send E-Mail");
		multipleRecipients = new JButton("Multiple Recipients");
		changeFont = new JButton("Change Display Font");
		comboTo = new JComboBox(names);
		buttonPanel = new JPanel();
		messagePanel = new JPanel();
		headerPanel = new JPanel();
		headerPanelInfo = new JPanel();
		headerPanelGraphic = new JPanel();
		pane = getContentPane();
		buttonPanel.setLayout(new GridLayout(1,4));
		messagePanel.setLayout(new FlowLayout());
		headerPanelInfo.setLayout(new GridLayout(3,2));
		headerPanelGraphic.setLayout(new BorderLayout());
		headerPanel.setLayout(new BorderLayout());
		pane.setLayout(new BorderLayout());
		buttonPanel.add(send);
		buttonPanel.add(sendAttachment);
		buttonPanel.add(multipleRecipients);
		buttonPanel.add(changeFont);
		messagePanel.add(textMessage);
		headerPanelInfo.add(labTo);
		headerPanelInfo.add(comboTo);
		headerPanelInfo.add(labSubject);
		headerPanelInfo.add(textSubject);
		headerPanelInfo.add(labAttachment);
		headerPanelInfo.add(textAttachment);
		headerPanelGraphic.add(labPic, BorderLayout.CENTER);
		headerPanel.add(headerPanelInfo, BorderLayout.WEST);
		headerPanel.add(headerPanelGraphic, BorderLayout.EAST);
		pane.add(messagePanel, BorderLayout.CENTER);
		pane.add(buttonPanel, BorderLayout.SOUTH);
		pane.add(headerPanel, BorderLayout.NORTH);
		send.addActionListener(this);
		sendAttachment.addActionListener(this);
		multipleRecipients.addActionListener(this);
		changeFont.addActionListener(this);
		ComboHandler handler = new ComboHandler();
		comboTo.addItemListener(handler);
	}

	private class ComboHandler implements ItemListener
	{
		public void itemStateChanged(ItemEvent iE)
		{
			recipient = (names[comboTo.getSelectedIndex()]);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == send)
		{
			if(multiple == true)
			{
				for(int count = 0; count < recipientNames.length; count++)
				{
					recipient = (String)recipientNames[count];
					sendEmail();
				}
				JOptionPane.showMessageDialog(pane, "E-Mail Sent");
			}
			else
			{
				sendEmail();
				JOptionPane.showMessageDialog(pane, "E-Mail Sent");
			}
			setVisible(false);
			MainFunctionMenuGUI mainFunctionMenuGUI = new MainFunctionMenuGUI();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int HEIGHT = 350;
			final int WIDTH = 500;
			mainFunctionMenuGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
										  ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
			mainFunctionMenuGUI.setVisible(true);
		}
		if(e.getSource() == sendAttachment)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = chooser.showOpenDialog(this);
			if(result == JFileChooser.CANCEL_OPTION)
				return;
			File temp = chooser.getSelectedFile();
			if(temp == null || temp.getName().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				email.setAttachment(temp);
				email.setAttachmentName(temp.getName());
				textAttachment.setText(temp.getName());
			}
		}
		if(e.getSource() == multipleRecipients)
		{
			RecipientList recipient = new RecipientList();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int HEIGHT = 475;
			final int WIDTH = 300;
			recipient.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
								((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
			recipient.setVisible(true);
			multiple = true;
		}
		if(e.getSource() == changeFont)
		{
			FontChange change = new FontChange();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int HEIGHT = 70;
			final int WIDTH = 300;
			change.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
		    				 ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
			change.setVisible(true);
		}
	}

	public static void setRecipientArray(Object[] temp)
	{
		recipientNames = temp;
	}

	private void sendEmail()
	{
		try
		{
			ObjectOutputStream objectOut = new ObjectOutputStream(EMailClient.getLink().getOutputStream());
			String option = "SENDING";
			objectOut.writeObject(option);
			objectOut.flush();
			email.setSender(EMailClient.getCurrentUserName());
			email.setRecipient(recipient);
			email.setSubject(textSubject.getText());
			email.setContent(textMessage.getText());
			objectOut.writeObject(email);
			objectOut.flush();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public static void setNewFont(Font font)
	{
		textSubject.setFont(font);
		comboTo.setFont(font);
		textAttachment.setFont(font);
		textMessage.setFont(font);
	}
}



