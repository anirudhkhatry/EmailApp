import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FontChange extends JFrame
{
	private Font plainFont = new Font("TimesRoman", Font.PLAIN, 14);
	private Font boldFont = new Font("TimesRoman", Font.BOLD, 14);
	private Font italicFont = new Font("TimesRoman", Font.ITALIC, 14);
	private Font boldItalicFont = new Font("TimesRoman", Font.BOLD+Font.ITALIC, 14);
	private Font selectedFont;
	private JRadioButton plain = new JRadioButton("Plain", true);
	private JRadioButton bold = new JRadioButton("Bold", false);
	private JRadioButton italic = new JRadioButton("Italic", false);
	private JRadioButton boldItalic = new JRadioButton("Bold/Italic", false);
	private ButtonGroup fontGroup;
	private Container pane;
	
	public FontChange()
	{
			addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					try
					{
						ReadMailWindowGUI.setNewFont(selectedFont);
					}
					catch(NullPointerException npe)
					{
						JOptionPane.showMessageDialog(pane, "Display Font Changed");
					}
					try
					{
						SendMailWindowGUI.setNewFont(selectedFont);
					}
					catch(NullPointerException npe)
					{
						JOptionPane.showMessageDialog(pane, "Display Font Changed");
					}
				}
			}
		);
		setTitle("Change Current Font");
		pane = getContentPane();
		pane.setLayout(new FlowLayout());
		pane.add(plain);
		pane.add(bold);
		pane.add(italic);
		pane.add(boldItalic);
		RadioButtonHandler handler = new RadioButtonHandler();
		plain.addItemListener(handler);
		bold.addItemListener(handler);
		italic.addItemListener(handler);
		boldItalic.addItemListener(handler);
		fontGroup = new ButtonGroup();
		fontGroup.add(plain);
		fontGroup.add(bold);
		fontGroup.add(italic);
		fontGroup.add(boldItalic);
	}

	private class RadioButtonHandler implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			
			if (e.getSource() == plain)
				selectedFont = plainFont;
			else if(e.getSource() == bold)
				selectedFont = boldFont;
			else if(e.getSource() == italic)
				selectedFont = italicFont;
			else if(e.getSource() == boldItalic)
				selectedFont = boldItalicFont;
		}
	}
}



