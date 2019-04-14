import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class ReadMailWindowGUI extends JFrame implements ActionListener
{
	private ImageIcon mailPic;
	private JLabel labFrom;
	private JLabel labSubject;
	private JLabel labTo;
	private JLabel labAttachment;
	private JLabel labPic;
	private JButton viewAttachment;
	private JButton saveAttachment;
	private JButton forward;
	private JButton changeFont;
	private JPanel buttonPanel;
	private JPanel headerPanel;
	private JPanel headerPanelInfo;
	private JPanel headerPanelGraphic;
	private JPanel messagePanel;
	private static JTextField textFrom;
	private static JTextField textSubject;
	private static JTextField textTo;
	private static JTextField textAttachment;
	private static JTextArea textMessage;
	private Container pane;
	private Email email;

	public ReadMailWindowGUI(Email email)
	{
		this.email = email;
		setTitle("Read E-Mail");
		mailPic = new ImageIcon("Envelope.gif");
		labFrom = new JLabel("FROM:");
		labSubject = new JLabel("SUBJECT:");
		labTo = new JLabel("TO:");
		labAttachment = new JLabel("ATTACHMENT:");
		labPic = new JLabel(mailPic);
		textFrom = new JTextField(15);
		textSubject = new JTextField(15);
		textTo = new JTextField(15);
		textAttachment = new JTextField(15);
		textMessage = new JTextArea(30, 49);
		textFrom.setEditable(false);
		textSubject.setEditable(false);
		textTo.setEditable(false);
		textAttachment.setEditable(false);
		textMessage.setEditable(false);
		textFrom.setText(email.getSender());
		textSubject.setText(email.getSubject());
		textTo.setText(email.getRecipient());
		textAttachment.setText(email.getAttachmentName());
		textMessage.setText(email.getContent());
		viewAttachment = new JButton("View Attachment");
		saveAttachment = new JButton("Save Attachment");
		forward = new JButton("Forward Email");
		changeFont = new JButton("Change Display Font");
		buttonPanel = new JPanel();
		messagePanel = new JPanel();
		headerPanel = new JPanel();
		headerPanelInfo = new JPanel();
		headerPanelGraphic = new JPanel();
		pane = getContentPane();
		buttonPanel.setLayout(new GridLayout(1,4));
		messagePanel.setLayout(new FlowLayout());
		headerPanelInfo.setLayout(new GridLayout(4,2));
		headerPanelGraphic.setLayout(new BorderLayout());
		headerPanel.setLayout(new BorderLayout());
		pane.setLayout(new BorderLayout());
		buttonPanel.add(viewAttachment);
		buttonPanel.add(saveAttachment);
		buttonPanel.add(forward);
		buttonPanel.add(changeFont);
		messagePanel.add(textMessage);
		headerPanelInfo.add(labFrom);
		headerPanelInfo.add(textFrom);
		headerPanelInfo.add(labTo);
		headerPanelInfo.add(textTo);
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
		viewAttachment.addActionListener(this);
		saveAttachment.addActionListener(this);
		forward.addActionListener(this);
		changeFont.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == viewAttachment)
		{
			String name = textAttachment.getText();
			name = name.toLowerCase();
			if(name.endsWith(".txt"))
			{
				ViewTextAttachment attachment = new ViewTextAttachment(email.getAttachment(), email.getAttachmentName());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final int HEIGHT = 600;
				final int WIDTH = 600;
				attachment.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
									 ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
				attachment.setVisible(true);
			}
			
			if(name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".jpeg"))
			{
				
				ViewGraphicsAttachment attachment = new ViewGraphicsAttachment(email.getAttachment());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final int HEIGHT = 800;
				final int WIDTH = 800;
				attachment.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
					    			 ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
				attachment.setVisible(true);
			}
			if(name.endsWith(".mov") || name.endsWith(".au") || name.endsWith(".mpeg") || name.endsWith(".mpg"))
			{
				
				ViewMediaAttachment attachment = new ViewMediaAttachment(email.getAttachment(), email.getAttachmentName());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final int HEIGHT = 720;
				final int WIDTH = 720;
				attachment.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
					        		 ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
				attachment.setVisible(true);
			}
		}
		if(e.getSource() == saveAttachment)
		{
			if(textAttachment.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "No Attachment To Save");
			}
			else
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = chooser.showSaveDialog(this);
				if(result == JFileChooser.CANCEL_OPTION)
					return;
				File temp = chooser.getSelectedFile();
				if(temp.getName().equals(""))
				{
					JOptionPane.showMessageDialog(this, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					try
					{
						FileOutputStream fileOut = new FileOutputStream(temp);
						fileOut.write(email.getAttachment());
						fileOut.close();
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}
				}
			}
		}
		if(e.getSource() == forward)
		{
			try
			{
				
				SendMailWindowGUI sendMailWinGUI = new SendMailWindowGUI(email);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final int HEIGHT = 600;
				final int WIDTH = 600;
				sendMailWinGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
									     ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
				sendMailWinGUI.setVisible(true);
				setVisible(false);
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
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
	
	public static void setNewFont(Font font)
	{
		textFrom.setFont(font);
		textSubject.setFont(font);
		textTo.setFont(font);
		textAttachment.setFont(font);
		textMessage.setFont(font);
	}
}


