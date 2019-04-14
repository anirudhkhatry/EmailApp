import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

class RecipientList extends JFrame
{
	private JList recipientList;
	private Container pane;
	private String[] names;
public RecipientList()
	{
		addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					SendMailWindowGUI.setRecipientArray(recipientList.getSelectedValues());
				}
			}
		);
		setTitle("Select Multiple Recipients");
		pane = getContentPane();
		pane.setLayout(new FlowLayout());
		getUserNames();
		recipientList = new JList(names);
		recipientList.setVisibleRowCount(22);
		recipientList.setFixedCellWidth(250);
		recipientList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		pane.add(new JScrollPane(recipientList));
	}

	public void getUserNames()
	{
		names = new String[EMailClient.getUserNamesSize()];
		for(int count = 0; count < names.length; count++)
		{
			names[count] = EMailClient.getName(count);
		}
	}
}



