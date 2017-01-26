package commands;

import levels.Level;

public class ExitCommand implements Commands 
{
	private Level level;

	public ExitCommand(Level level) 
	{
		super();
		this.level = level;
	}

	@Override
	public void execute() 
	{
		if (level == null) 
		{
			System.out.println("Level is null in ExitCommand");
			return;
		}
		level.setGameOn(false);
	}
}
