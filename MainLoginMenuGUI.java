import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class MainLoginMenuGUI extends JFrame implements ActionListener
{
	private JPanel buttonPanel;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JButton login;
	private JButton create;
	private JLabel title;
	private JLabel picLabel;
	private ImageIcon javaMailPic;
	private Container pane;
	public MainLoginMenuGUI()
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
		setTitle("Email");
		pane = getContentPane();
		pane.setLayout(new FlowLayout());
		buttonPanel = new JPanel();
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		login = new JButton("Login Existing User  ");
		create = new JButton("Create New User  ");
		javaMailPic = new ImageIcon("Email.jpg");
		title = new JLabel("Mail");
		picLabel = new JLabel(javaMailPic);
		title.setFont(new Font("Arial", Font.BOLD, 30));
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(login);
		buttonPanel.add(create);
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(title);
		topPanel.setLayout(new BorderLayout());
		topPanel.add(picLabel, BorderLayout.NORTH);
		pane.add(topPanel);
		pane.add(buttonPanel);
		pane.add(bottomPanel);
		login.addActionListener(this);
		create.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == login)
		{
			LoginWindowGUI login = new LoginWindowGUI();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int HEIGHT = 400;
			final int WIDTH = 500;
			login.setBounds(0,0,700,650);
			login.setVisible(true);
			setVisible(false);
		}

		else
		{
			CreateUserGUI create = new CreateUserGUI();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int HEIGHT = 500;
			final int WIDTH = 500;
			create.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
						     ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
			create.setVisible(true);
			setVisible(false);
		}
	}
}







