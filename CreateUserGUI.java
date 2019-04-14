import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class CreateUserGUI extends JFrame implements ActionListener
{
	private JPanel buttonPanel;
	private JPanel inputPanel;
	private JButton create;
	private JCheckBox show;
	private JLabel labelPassword;
	private JLabel labelUsername;
	private JPasswordField textPassword;
	private JTextField textUsername;
	private Container pane;
	private static ObjectOutputStream objectOut;
	public CreateUserGUI()
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
		setTitle("Create New User");
		pane = getContentPane();
		pane.setLayout(new BorderLayout());
		buttonPanel = new JPanel();
		inputPanel = new JPanel();
		create = new JButton("Create New User");
		labelPassword = new JLabel("Password");
		labelUsername = new JLabel("Username");
		textPassword = new JPasswordField(10);
		textUsername = new JTextField(10);
		show=new JCheckBox();
		buttonPanel.setLayout(new GridLayout(1,1));
		inputPanel.setLayout(new GridLayout(2,2));
		buttonPanel.add(create, BorderLayout.CENTER);
		buttonPanel.add(show, BorderLayout.CENTER);
		inputPanel.add(labelUsername);
		inputPanel.add(textUsername);
		inputPanel.add(labelPassword);
		inputPanel.add(textPassword);
		pane.add(inputPanel, BorderLayout.NORTH);
		pane.add(buttonPanel, BorderLayout.SOUTH);
		create.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		String userName = textUsername.getText();
		String password = new String(textPassword.getPassword());
		boolean userExists = checkUserExists(userName, password);
		if(userExists == false)
		{
			sendUserNameToServer(userName, password);
			sendUserNameToClient(userName, password);
			JOptionPane.showMessageDialog(pane, "New User Accepted");
			setVisible(false);
			MainFunctionMenuGUI mainFunctionMenuGUI = new MainFunctionMenuGUI();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int HEIGHT = 700;
			final int WIDTH = 500;
			mainFunctionMenuGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
								          ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
			mainFunctionMenuGUI.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(pane, "Sorry User Already Exists");
			textUsername.setText("");
			textPassword.setText("");
		}
			
	}
	
	public void checkSelected(ItemEvent e){
		if (e.getStateChange() == ItemEvent.SELECTED) {
            textPassword.setEchoChar('*');
        } else {
             textPassword.setEchoChar((char) 0);
        }
	}
	


	public static boolean checkUserExists(String userName, String password)
	{
		Vector userNames = EMailClient.getUserNames();
		int vectorSize = userNames.size();
		int vectorPosition;
		String extractedUser;
		String extractedPassword;
		boolean exists = false;
		for(vectorPosition = 0; vectorPosition < vectorSize; vectorPosition++)
		{
			User temp = (User)userNames.elementAt(vectorPosition);
			extractedUser = temp.getUserName();
			extractedPassword = temp.getPassword();
			if(extractedUser.equals(userName))
			{
				exists = true;
				break;
			}
		}
		return exists;
	}

	private static void sendUserNameToServer(String userName, String password)
	{
		try
		{
			Boolean userStatus = Boolean.TRUE;
			objectOut = new ObjectOutputStream(EMailClient.getLink().getOutputStream());
			User user = new User(userName, password);
			objectOut.writeObject(user);
			objectOut.writeObject(userStatus);
			objectOut.flush();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	private void sendUserNameToClient(String userName, String password)
	{
		User user = new User(userName, password);
		EMailClient.setUser(user);
		EMailClient.addUser(user);
	}
}