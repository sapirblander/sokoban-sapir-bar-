package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import model.Model;
import model.data.Level;
import model.data.LevelLoader;
import model.data.MyObjectLevelLoder;
import model.data.MyTextLevelLoader;
import model.data.MyXMLLevelLoader;

public class LoadCommand implements Commands 
{
	private HashMap<String, LevelLoader> myLoaders;
	private String file;
	private Model model;
	
	public LoadCommand(Model model) 
	{
		super();
		this.model = model;
		myLoaders = new HashMap<String, LevelLoader>();
		myLoaders.put("xml", new MyXMLLevelLoader());
		myLoaders.put("txt", new MyTextLevelLoader());
		myLoaders.put("obj", new MyObjectLevelLoder());
	}

	@Override
	public void execute() 
	{
		if (model.getCurrentLevel() == null) 
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
		model.getCurrentLevel().setLevelNum(newLevel.getLevelNum());
		model.getCurrentLevel().setPlayerPosition(newLevel.getPlayerPosition());
		model.getCurrentLevel().setSokoban(newLevel.getSokoban());
		model.getCurrentLevel().setTargets(newLevel.getTargets());
		model.getCurrentLevel().setGameOn(true);
		model.getCurrentLevel().setNumOfSteps(0);
		model.checkChanges();
	}

	public String getFile() 
	{
		return file;
	}

	public void setFile(String file) 
	{
		this.file = file;
	}
	
	boolean isFileExist(String filePathString)
	{
		File f = new File(filePathString);
		if(f.exists() && !f.isDirectory()) 
		    return true;
		return false;
	}

	@Override
	public void setParams(String params)
	{
		file = params;
	}
}
