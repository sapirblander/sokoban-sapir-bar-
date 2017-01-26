package commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import levels.Level;
import levels.LevelLoader;
import levels.MyObjectLevelLoder;
import levels.MyTextLevelLoader;
import levels.MyXMLLevelLoader;

public class LoadCommand implements Commands 
{
	private HashMap<String, LevelLoader> myLoaders;
	private String file;
	private Level myLevel;
	public LoadCommand(Level myLevel) 
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
		if (!file.contains(".")) 
		{
			System.out.println("Wrong file extension in LoadCommand");
			return;
		}
		if (!(isFileExist(file)))
		{
			System.out.println("File is not exsist");
			return;
		}
		String extesion = file.substring(file.indexOf(".") + 1);
		LevelLoader levelLoader = myLoaders.get(extesion);
		if (levelLoader == null) {
			System.out.println("File extension doesn't exist in LoadCommand");
			return;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Level newLevel = levelLoader.loadLevel(in);
		myLevel.setLevelNum(newLevel.getLevelNum());
		myLevel.setPlayerPosition(newLevel.getPlayerPosition());
		myLevel.setSokoban(newLevel.getSokoban());
		myLevel.setTargets(newLevel.getTargets());
		myLevel.setGameOn(true);
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
	
	boolean isFileExist(String filePathString)
	{
		File f = new File(filePathString);
		if(f.exists() && !f.isDirectory()) 
		    return true;
		return false;
	}
}
