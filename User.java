import java.io.*;

class User implements Serializable
{
	private String userName;
	private String password;
	public User(String name, String pass)
	{
		userName = name;
		password = pass;
	}
	public String getUserName()
	{
		return userName;
	}
	public String getPassword()
	{
		return password;
	}
	public void setUserName(String name)
	{
		userName = name;
	}
	public void setPassword(String pass)
	{
		password = pass;
	}
}
