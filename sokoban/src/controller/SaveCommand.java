package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import model.Model;
import model.data.LevelLoader;
import model.data.MyObjectLevelLoder;
import model.data.MyTextLevelLoader;
import model.data.MyXMLLevelLoader;

public class SaveCommand implements Commands 
{
	private HashMap<String, LevelLoader> myLoaders;
	private String file;
	private Model model;

	public SaveCommand(Model model) 
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
		if (!model.getCurrentLevel().isGameOn()) 
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
		levelLoader.saveLevel(out, model.getCurrentLevel());
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

	@Override
	public void setParams(String params) 
	{
		file = params;
	}
}
