package commands;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import levels.Level;
import levels.LevelLoader;
import levels.MyObjectLevelLoder;
import levels.MyTextLevelLoader;
import levels.MyXMLLevelLoader;

public class SaveCommand implements Commands 
{
	private HashMap<String, LevelLoader> myLoaders;
	private String file;
	private Level myLevel;

	public SaveCommand(Level myLevel) 
	{
		super();
		this.setMyLevel(myLevel);
		myLoaders = new HashMap<String, LevelLoader>();
		myLoaders.put("xml", new MyXMLLevelLoader());
		myLoaders.put("txt", new MyTextLevelLoader());
		myLoaders.put("obj", new MyObjectLevelLoder());
	}

	@Override
	public void execute() 
	{
		if (myLevel == null) 
		{
			System.out.println("Level is null in LoadCommand");
			return;
		}
		if (!myLevel.isGameOn()) 
		{
			System.out.println("Game is off");
			return;
		}
		if (!file.contains(".")) 
		{
			System.out.println("Wrong file extension in LoadCommand");
			return;
		}
		String extesion = file.substring(file.indexOf(".") + 1);
		LevelLoader levelLoader = myLoaders.get(extesion);
		if (levelLoader == null) 
		{
			System.out.println("File extension doesn't exist in LoadCommand");
			return;
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		levelLoader.saveLevel(out, myLevel);
	}

	public HashMap<String, LevelLoader> getMyLoaders() 
	{
		return myLoaders;
	}

	public void setMyLoaders(HashMap<String, LevelLoader> myLoaders) 
	{
		this.myLoaders = myLoaders;
	}

	public String getFile() 
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	public Level getMyLevel() 
	{
		return myLevel;
	}

	public void setMyLevel(Level myLevel) 
	{
		this.myLevel = myLevel;
	}
}
