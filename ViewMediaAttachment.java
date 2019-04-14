import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.media.*;

public class ViewMediaAttachment extends JFrame implements ControllerListener
{
	private Container pane;
	private byte[] attachment;
	private Player player;
	private FileOutputStream fileOut;
	private File file;

	public ViewMediaAttachment(byte[] attachment, String name)
	{
		try
		{
			addWindowListener(
				new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						file.delete();
					}
				}
			);
			this.attachment = attachment;
			
			fileOut = new FileOutputStream(name);
			fileOut.write(attachment);
			file = new File(name);
			setTitle("Java Media Player");
			pane = getContentPane();
			player = Manager.createPlayer(file.toURL());
			player.addControllerListener(this);
			player.start();
		}
		catch(FileNotFoundException fnfe)
		{
			JOptionPane.showMessageDialog(null, "File Not Found");
		}
		catch(Exception e2)
		{
			e2.printStackTrace();
		}
	}

	public void controllerUpdate(ControllerEvent e)
	{
		if (e instanceof RealizeCompleteEvent)
		{
			Component visualComponent = player.getVisualComponent();
			if(visualComponent != null)
			{
				pane.add(visualComponent, BorderLayout.CENTER);
			}
			Component controlsComponent = player.getControlPanelComponent();
			if(controlsComponent != null)
			{
				pane.add(controlsComponent, BorderLayout.SOUTH);
			}
			pane.doLayout();
		}
	}
}
