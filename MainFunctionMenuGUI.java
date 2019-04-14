import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class MainFunctionMenuGUI extends JFrame implements ActionListener
{
	private JButton read;
	private JButton send;
	private JLabel title;
	private JLabel messageInfo;
	private ImageIcon readIcon;
	private ImageIcon sendIcon;
	private JPanel buttonPanel;
	private JPanel bottomPanel;
	private JPanel topPanel;
	private Container pane;
	private static ObjectInputStream objectIn;
	private static ObjectOutputStream objectOut;

	public MainFunctionMenuGUI()
	{
		addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					EMailClient.closeDown();
				}
			}
		);

		addWindowListener(
			new WindowAdapter()
			{
				public void windowOpened(WindowEvent e)
				{
					try
					{
						recieveMessagesFromServer();
						EMailClient.backupMail();
					}
					catch (ClassNotFoundException cnfe)
					{
						cnfe.printStackTrace();
					}
				}
			}
		);
		setTitle("Main Menu");
		readIcon = new ImageIcon("inbox.png");
		sendIcon = new ImageIcon("SendMail.png");
		read = new JButton("Read Email", readIcon);
		send = new JButton("Send Email", sendIcon);
		read.setVerticalTextPosition(SwingConstants.BOTTOM);
		read.setHorizontalTextPosition(SwingConstants.CENTER);
		send.setVerticalTextPosition(SwingConstants.BOTTOM);
    	send.setHorizontalTextPosition(SwingConstants.CENTER);
		title = new JLabel("Please Make Your Selection");
		title.setFont(new Font("Arial", Font.BOLD, 25));
		messageInfo = new JLabel("You Have 0 Messages");
		messageInfo.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 20));
		buttonPanel = new JPanel();
		bottomPanel = new JPanel();
		topPanel = new JPanel();
		pane = getContentPane();
		pane.setLayout(new FlowLayout());
		buttonPanel.setLayout(new FlowLayout());
		bottomPanel.setLayout(new GridLayout(1,1));
		topPanel.setLayout(new GridLayout(1,1));
		buttonPanel.add(read);
		buttonPanel.add(send);
		topPanel.add(title);
		bottomPanel.add(messageInfo);
		pane.add(topPanel);
		pane.add(buttonPanel);
		pane.add(bottomPanel);
		read.addActionListener(this);
		send.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			if(e.getSource() == send)
			{
				SendMailWindowGUI sendMailWinGUI = new SendMailWindowGUI();
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final int HEIGHT = 600;
				final int WIDTH = 600;
				sendMailWinGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
									     ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
				sendMailWinGUI.setVisible(true);
				setVisible(false);
			}
			if(e.getSource() == read)
			{
				InboxList inbox = new InboxList();
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final int HEIGHT = 550;
				final int WIDTH = 600;
				inbox.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
					            ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
				inbox.setVisible(true);
				setVisible(false);
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}

	}

	private void recieveMessagesFromServer() throws ClassNotFoundException
	{
		try
		{
			objectOut = new ObjectOutputStream(EMailClient.getLink().getOutputStream());
			String command = "RECIEVING";
			objectOut.writeObject(command);
			objectOut.flush();
			objectIn = new ObjectInputStream(EMailClient.getLink().getInputStream());
			String option = (String)objectIn.readObject();
			while(option.equals("SENDING"))
			{
				Email email = (Email)objectIn.readObject();
				EMailClient.addMail(email);
				option = (String)objectIn.readObject();
			}
			messageInfo.setText("You Have " + EMailClient.inboxSize() + " Messages");
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}



