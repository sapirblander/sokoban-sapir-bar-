package commands;

import policies.SokobanPolicy;
import levels.Level;

public class MoveCommand implements Commands 
{
	private Level level;
	private SokobanPolicy sokobanPolicy;
	private String move;

	public MoveCommand(Level level, SokobanPolicy sokobanPolicy) 
	{
		super();
		this.level = level;
		this.sokobanPolicy = sokobanPolicy;
	}

	@Override
	public void execute() 
	{
		if (level == null) 
		{
			System.out.println("Level is null in MoveCommand");
			return;
		}
		if (!level.isGameOn()) 
		{
			System.out.println("Game is off");
			return;
		}
		if (sokobanPolicy.checkMove(level, move))
		{
			System.out.println("Good Move");
			level.isComplete(level);
		}
		else
			System.out.println("Bad Move");
	}

	public String getMove() 
	{
		return move;
	}

	public void setMove(String move) 
	{
		this.move = move;
	}
}
