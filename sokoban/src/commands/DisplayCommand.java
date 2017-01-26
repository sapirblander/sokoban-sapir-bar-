package commands;

import displayers.SokobanDisplay;
import levels.Level;

public class DisplayCommand implements Commands 
{
	private Level level;
	private SokobanDisplay sokobanDisplay;

	public DisplayCommand(Level level, SokobanDisplay sokobanDisplay) 
	{
		this.level = level;
		this.sokobanDisplay = sokobanDisplay;
	}

	public void execute() 
	{
		if (level == null) 
		{
			System.out.println("Level is null in displayCommand");
			return;
		}
		if (sokobanDisplay == null) 
		{
			System.out.println("sokobanDisplay is null in displayCommand");
			return;
		}
		if (level.isGameOn())
			sokobanDisplay.display(level);
		else
			System.out.println("Game is off");
	}
}
