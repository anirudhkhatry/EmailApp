import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

class LoginWindowGUI extends JFrame implements ActionListener
{
	private JPanel buttonPanel;
	private JPanel inputPanel;
	private JButton login;
	private JLabel labelPassword;
	private JLabel labelUsername;
	private JPasswordField textPassword;
	private JTextField textUsername;
	private Container pane;
	private static ObjectOutputStream objectOut;
	public LoginWindowGUI()
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
		setTitle("Login User");
		pane = getContentPane();
		pane.setLayout(new BorderLayout());
		buttonPanel = new JPanel();
		inputPanel = new JPanel();
		login = new JButton("Login");
		labelPassword = new JLabel("Password");
		labelUsername = new JLabel("Username");
		textPassword = new JPasswordField(10);
		textUsername = new JTextField(10);
		buttonPanel.setLayout(new GridLayout(1,1));
		inputPanel.setLayout(new GridLayout(2,2));
		buttonPanel.add(login, BorderLayout.CENTER);
		inputPanel.add(labelUsername);
		inputPanel.add(textUsername);
		inputPanel.add(labelPassword);
		inputPanel.add(textPassword);
		pane.add(inputPanel, BorderLayout.NORTH);
		pane.add(buttonPanel, BorderLayout.SOUTH);
		login.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		String userName = textUsername.getText();
		String password = new String(textPassword.getPassword());
		boolean validUser = checkUserExists(userName, password);
		if(validUser == true)
		{
			JOptionPane.showMessageDialog(pane, "Login Accepted");
			setVisible(false);
			sendUserNameToClient(userName, password);
			sendUserNameToServer(userName, password);
			MainFunctionMenuGUI mainFunctionMenuGUI = new MainFunctionMenuGUI();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int HEIGHT = 350;
			final int WIDTH = 500;
			mainFunctionMenuGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
								          ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
			mainFunctionMenuGUI.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(pane, "Incorrect Login");
			textUsername.setText("");
			textPassword.setText("");
		}
	}

	public boolean checkUserExists(String userName, String password)
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
			if(extractedUser.equals(userName) && extractedPassword.equals(password))
			{
				exists = true;
				break;
			}
		}
		return exists;
	}

	private void sendUserNameToServer(String userName, String Password)
	{
		try
		{
			Boolean userStatus = Boolean.FALSE;
			objectOut = new ObjectOutputStream(EMailClient.getLink().getOutputStream());
			User user = new User(userName, Password);
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
	}
}

