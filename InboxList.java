
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

public class InboxList extends JFrame implements ActionListener
{
	private JPanel buttonPanel;
	private JPanel listPanel;
	private JButton read;
	private JButton delete;
	private JButton unRead;
	private JButton unDelete;
	private JLabel title;
	private JList emailList;
	private Container pane;
	private Vector mailDescriptor;
	private int selectedMailNumber;
	private ObjectInputStream fileIn;
public InboxList()
	{
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
		setTitle("Inbox");
		pane = getContentPane();
		pane.setLayout(new BorderLayout());
		buttonPanel = new JPanel();
		listPanel = new JPanel();
		read = new JButton("Read Email");
		delete = new JButton("Delete Email");
		unRead = new JButton("Unread Email");
		unDelete = new JButton("Undelete Emails");
		title = new JLabel("Recieved Emails");
		mailDescriptor = new Vector();
		selectedMailNumber = 0;
		emailList = new JList();
		emailList.setVisibleRowCount(33);
		emailList.setFixedCellWidth(320);
		emailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		refreshListContents();
		buttonPanel.setLayout(new GridLayout(1,4));
		listPanel.setLayout(new BorderLayout());
		buttonPanel.add(read);
		buttonPanel.add(delete);
		buttonPanel.add(unRead);
		buttonPanel.add(unDelete);
		listPanel.add(title, BorderLayout.NORTH);
		listPanel.add(new JScrollPane(emailList), BorderLayout.CENTER);
		pane.add(listPanel, BorderLayout.CENTER);
		pane.add(buttonPanel, BorderLayout.SOUTH);
		read.addActionListener(this);
		delete.addActionListener(this);
		unRead.addActionListener(this);
		unDelete.addActionListener(this);
		ListSelectionHandler handler = new ListSelectionHandler();
		emailList.addListSelectionListener(handler);
	}

	private class ListSelectionHandler implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			selectedMailNumber = emailList.getSelectedIndex();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if(EMailClient.inboxSize() > 0 || e.getSource() == unDelete)
		{
			if(e.getSource() == read)
			{
				Email selectedMail = EMailClient.getMail(selectedMailNumber);
				EMailClient.deleteMail(selectedMailNumber);
				selectedMail.setRead(true);
				EMailClient.addMail(selectedMail);
				refreshListContents();
				ReadMailWindowGUI readMailWinGUI = new ReadMailWindowGUI(selectedMail);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final int HEIGHT = 600;
				final int WIDTH = 600;
				readMailWinGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
										 ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
				readMailWinGUI.setVisible(true);
			}
			if(e.getSource() == delete)
			{
				EMailClient.deleteMail(selectedMailNumber);
				refreshListContents();
				JOptionPane.showMessageDialog(pane, "Mail Deleted");
			}
			if(e.getSource() == unRead)
			{
				Email selectedMail = EMailClient.getMail(selectedMailNumber);
				if(selectedMail.getRead() == true)
				{
					EMailClient.deleteMail(selectedMailNumber);
					selectedMail.setRead(false);
					EMailClient.addMail(selectedMail);
					refreshListContents();
					JOptionPane.showMessageDialog(pane, "Mail Unread");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Mail is Unread");
				}
			}
			if(e.getSource() == unDelete)
			{
				EMailClient.restoreMail();
				refreshListContents();
				JOptionPane.showMessageDialog(pane, "Mail Undeleted");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No Curent Mail");
		}
	}

	public void refreshListContents()
	{
		mailDescriptor.removeAllElements();
		Email mail;
		String description;
		for(int count = 0; count < EMailClient.inboxSize(); count++)
		{
			mail = EMailClient.getMail(count);
			if(mail.getRead() == true)
			{
				description = ("    " + "FROM: " + mail.getSender() + "    " + "RE: "+ mail.getSubject() +
							   "    " + "ATTACHMENT: " + mail.getAttachmentName());
			}
			else
			{
				description = ("NEW " + "FROM: " + mail.getSender() + "    " + "RE: "+ mail.getSubject() +
							   "    " + "ATTACHMENT: " + mail.getAttachmentName());
			}
			mailDescriptor.add(description);
		}
		emailList.setListData(mailDescriptor);
	}
}