import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class EMailServer extends JFrame
{
	private static ServerSocket servSocket;
	private static final int PORT = 1234;
	private static Vector userNames;
	private static Vector mail;
	private static ObjectOutputStream fileOut;
	private static ObjectInputStream fileIn;
	private static ObjectOutputStream objectOut;
	private static ObjectInputStream objectIn;
	private static JTextArea messageWindow;

	public EMailServer()
	{
		addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					shutDownServer();
				}
			}
		);
		setTitle("E - Mail Server Information");
		messageWindow = new JTextArea(28,30);
		messageWindow.setWrapStyleWord(true);
		messageWindow.setLineWrap(true);
		messageWindow.setEditable(false);
		getContentPane().add(messageWindow, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(messageWindow));
	}

	public static void main(String[] args) throws IOException
	{
		userNames = new Vector();
		mail = new Vector();
		EMailServer serverGUI = new EMailServer();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int HEIGHT = 500;
		final int WIDTH = 400;
		serverGUI.setBounds(0,0,720,720);
		serverGUI.setVisible(true);
		startUpServer();
		try
		{
			servSocket = new ServerSocket(PORT);
		}
		catch (IOException ioe)
		{
			updateMessageWindow("Unable To Set Up Port");
			System.exit(1);
		}
		do
		{
			Socket client = servSocket.accept(); //Wait for client.
			updateMessageWindow("Accepting Incoming Connection...");
			ClientHandler handler = new ClientHandler(client);
			handler.start();
		}while (true); 
	}

	private static void startUpServer()
	{
		try
		{
			readInUserNamesFromServerFile();
			readInMailFromServerFile();
		}
		catch(IOException ioe)
		{
			updateMessageWindow("Error Reading From File");
		}
	}

	private static void readInUserNamesFromServerFile() throws IOException
	{
		fileIn = new ObjectInputStream(new FileInputStream("Usernames.dat"));
		try
		{
			do
			{
				User temp = (User)fileIn.readObject();
				addUser(temp);
			}while(true);
		}
		catch(EOFException eofe)
		{
			updateMessageWindow("User Names Read Succesfully");
			fileIn.close();
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}

	}

	
	private static void readInMailFromServerFile() throws IOException
	{
		
		fileIn = new ObjectInputStream(new FileInputStream("Mail.dat"));
		try
		{
			do
			{
				
				Email temp = (Email)fileIn.readObject();
				addMail(temp);
			}while(true);
		}
		catch(EOFException eofe)
		{
			
			updateMessageWindow("Mail Read Succesfully");
			fileIn.close();
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
	}

	private static void shutDownServer()
	{
		try
		{
			writeOutMailToServerFile();
			writeOutUsersToServerFile();
		}
		catch(IOException ioe)
		{
			updateMessageWindow("Error Writing To File");
		}
		System.exit(0);
	}

	private static void writeOutUsersToServerFile() throws IOException
	{
		fileOut = new ObjectOutputStream(new FileOutputStream("Usernames.dat"));
		int vectorSize = getUserNamesVectorSize();
		if(vectorSize > 0)
		{
			updateMessageWindow("Adding Users To Permament Storage");
			for(int count = 0; count < vectorSize; count++)
			{
				User user = (User)getUser(count);
				fileOut.writeObject(user);
			}
			fileOut.close();
		}
		else
		{
			updateMessageWindow("No Current Users!");
		}
	}

	private static void writeOutMailToServerFile() throws IOException
	{
		
		fileOut = new ObjectOutputStream(new FileOutputStream("Mail.dat"));
		int vectorSize = getMailSize();
		if(vectorSize > 0)
		{
			updateMessageWindow("Adding Mail To Permament Storage");
			for(int count = 0; count < vectorSize; count++)
			{
				Email mail = (Email)getEmail(count);
				fileOut.writeObject(mail);
			}
			fileOut.close();
		}
		else
		{
			updateMessageWindow("No Current Mail!");
		}
	}

	public static User getUser(int position)
	{
		return (User)userNames.elementAt(position);
	}
	public static int getUserNamesVectorSize()
	{
		return userNames.size();
	}
	public static Email getEmail(int position)
	{
		return (Email)mail.elementAt(position);
	}
	public static int getMailSize()
	{
		return mail.size();
	}

	public static void addUser(User newUser)
	{
		userNames.add(newUser);
	}
	public static void addMail(Email newMail)
	{
		mail.add(newMail);
	}
	public static void deleteMail(int position)
	{
		mail.removeElementAt(position);
	}
	public static void updateMessageWindow(String info)
	{
		messageWindow.setText(messageWindow.getText() + "\n" + info);
	}
}








