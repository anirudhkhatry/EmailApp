import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler extends Thread
{
	private Socket client;
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;

	public ClientHandler(Socket socket)
	{
		client = socket;
	}

	public synchronized void run()
	{
		try
		{
			sendUserNamesToClient();
			String user = getUserNameFromClient();
			setName(user);
			char operation;
			do
			{
				objectIn = new ObjectInputStream(client.getInputStream());
				String option = (String)objectIn.readObject();
				operation = option.charAt(0);
				switch (operation)
				{
					case 'S': 	recieveMessagesFromClient();
								break;
					case 'R':	sendMessagesToClient();
								break;
					case 'Q':	EMailServer.updateMessageWindow("Disconnecting " + getName() + "...");
								break;
				}
			}while (operation != 'Q');
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
		finally
		{
			try
			{
				EMailServer.updateMessageWindow("Closing down connection...");
				client.close();
				EMailServer.updateMessageWindow("Client Disconnected Succesfully");
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}

	private synchronized void sendUserNamesToClient() throws IOException
	{
		objectOut = new ObjectOutputStream(client.getOutputStream());
		int vectorSize = EMailServer.getUserNamesVectorSize();
		objectOut.writeObject(new Integer(vectorSize));
		for(int count = 0; count < vectorSize; count++)
		{
			objectOut.writeObject((User)EMailServer.getUser(count));
		}
		objectOut.flush();
	}

	private synchronized String getUserNameFromClient() throws IOException, ClassNotFoundException
	{
		objectIn = new ObjectInputStream(client.getInputStream());
		User user = (User)objectIn.readObject();
		Boolean objectNewUser = (Boolean)objectIn.readObject();
		boolean newUser = objectNewUser.booleanValue();
		EMailServer.updateMessageWindow("..." + user.getUserName() + " is now connected to server");
		if (newUser == true)
		{
			EMailServer.addUser(user);
		}
		return (user.getUserName());
	}

	private synchronized void recieveMessagesFromClient()
	{
		try
		{
			Email email = (Email)objectIn.readObject();
			EMailServer.addMail(email);
			EMailServer.updateMessageWindow("Recieving Outgoing Mail From " + email.getSender());
			EMailServer.updateMessageWindow(email.getSender() + " Sending Mail To " + email.getRecipient());
			EMailServer.updateMessageWindow("Mail Recieved From " + email.getSender()
												+ " Stored In Outbox For Delivery To " + email.getRecipient());
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
	}

	private synchronized void sendMessagesToClient()
	{
		try
		{
			objectOut = new ObjectOutputStream(client.getOutputStream());
			EMailServer.updateMessageWindow("Retrieving Messages For " + getName());
			int vectorSize = EMailServer.getMailSize();
			for(int count = 0; count < vectorSize; count++)
			{
				if(getName().equals(EMailServer.getEmail(count).getRecipient()))
				{
					objectOut.writeObject("SENDING");
					objectOut.flush();
					objectOut.writeObject(EMailServer.getEmail(count));
					objectOut.flush();
					EMailServer.deleteMail(count);
					count--;
					vectorSize--;
				}
			}
			objectOut.writeObject("END");
			objectOut.flush();
			EMailServer.updateMessageWindow("Messages sent to " + getName());
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}

