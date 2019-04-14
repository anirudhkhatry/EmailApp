import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class EMailClient
{
	private static InetAddress host;
	private static final int PORT = 1234;
	private static Socket link;
	private static Vector inbox;
	private static Vector backupInbox;
	private static Vector userNames;
	private static User user;
	private static ObjectInputStream objectIn;
	private static ObjectOutputStream objectOut;

	public static void main(String[] args) throws IOException
	{
		try
		{
			host = InetAddress.getLocalHost();
			link = new Socket(host, PORT);
			userNames = new Vector();
			inbox = new Vector();
			backupInbox = new Vector();
			getUserNamesFromServer();
			MainLoginMenuGUI mainLoginMenuGUI = new MainLoginMenuGUI();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int HEIGHT = 650;
			final int WIDTH = 500;
			mainLoginMenuGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
							           ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
			mainLoginMenuGUI.setVisible(true);
		}
		catch(UnknownHostException uhe)
		{
			System.out.println("Host ID Not Found");
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	private static void getUserNamesFromServer() throws IOException
	{
		try
		{
			objectIn = new ObjectInputStream(link.getInputStream());
			Integer vectorSizeText = (Integer)objectIn.readObject();
			int vectorSize = vectorSizeText.intValue();
			for(int count = 0; count < vectorSize; count++)
			{
				userNames.add((User)objectIn.readObject());
				User temp = (User)userNames.elementAt(count);
			}
		}
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
	}

	public static void closeDown()
	{
		try
		{
			for(int count = 0; count < inbox.size(); count++)
			{
				returnMail(getMail(count));
				inbox.removeElementAt(count);
				count--;
			}
			ObjectOutputStream objectOut = new ObjectOutputStream(link.getOutputStream());
			String option = "QUITTING";
			objectOut.writeObject(option);
			objectOut.flush();
			link.close();
			System.exit(0);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	private static void returnMail(Email mail)
	{
		try
		{
			ObjectOutputStream objectOut = new ObjectOutputStream(link.getOutputStream());
			String option = "SENDING";
			objectOut.writeObject(option);
			objectOut.flush();
			objectOut.writeObject(mail);
			objectOut.flush();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public static void backupMail()
	{
		int vectorSize = inboxSize();
		backupInbox.removeAllElements();
		for(int count = 0; count < vectorSize; count++)
		{
			Email temp = getMail(count);
			backupInbox.add(temp);
		}
	}

	public static void restoreMail()
	{
		int vectorSize = backupInbox.size();
		inbox.removeAllElements();
		for(int count = 0; count < vectorSize; count++)
		{
			Email temp = (Email)backupInbox.elementAt(count);
			inbox.add(temp);
		}
	}

	public static String getCurrentUserName()
	{
		return user.getUserName();
	}
	public static Vector getUserNames()
	{
		return userNames;
	}
	public static int getUserNamesSize()
	{
		return userNames.size();
	}
	public static String getName(int position)
	{
		User temp = (User)userNames.elementAt(position);
		return temp.getUserName();
	}
	public static Email getMail(int position)
	{
		return (Email)inbox.elementAt(position);
	}
	public static Socket getLink()
	{
		return link;
	}
	public static Vector getMailVector()
	{
		return inbox;
	}
	public static Vector getBackupVector()
	{
		return backupInbox;
	}
	public static int inboxSize()
	{
		return inbox.size();
	}

	public static void setUser(User newUser)
	{
		user = newUser;
	}
	public static void addUser(User newUser)
	{
		userNames.add(newUser);
	}
	public static void addMail(Email email)
	{
		inbox.add(email);
	}
	public static void setBackupVector(Vector vector)
	{
			backupInbox = vector;
	}
	public static void setInbox(Vector vector)
	{
		inbox = vector;
	}
	public static void deleteMail(int position)
	{
		inbox.removeElementAt(position);
	}

	public static void deleteAll()
	{
		inbox.removeAllElements();
	}
}
