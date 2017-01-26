package levels;

import java.io.Serializable;

import storage.Box;
import storage.Player;
import storage.Space;
import storage.Storage;
import storage.Target;

public class Level implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int levelNum;
	private int numOfSteps;
	private double time;
	private boolean gameOn;

	private Storage[][] sokoban;
	private boolean[][] targets;
	private Position playerPosition;

	public Level() 
	{
		levelNum = 0;
		numOfSteps = 0;
		time = 0;
		gameOn = false;
	}

	public void movePlayer(String move) 
	{
		if (targets[playerPosition.getLine()][playerPosition.getColumn()]) 
		{
			// Target
			sokoban[playerPosition.getLine()][playerPosition.getColumn()] = new Target();
		} else
			// Space
			sokoban[playerPosition.getLine()][playerPosition.getColumn()] = new Space();
		// Get new position for the player
		playerPosition = Position.getNewPosition(playerPosition, move);
		// Box
		if (sokoban[playerPosition.getLine()][playerPosition.getColumn()]
				.getStorageKind() == "Box") {
			Position newBoxPosition = Position.getNewPosition(playerPosition,
					move);
			sokoban[newBoxPosition.getLine()][newBoxPosition.getColumn()] = new Box();
		}
		// Player
		sokoban[playerPosition.getLine()][playerPosition.getColumn()] = new Player();
	}
	
	public void isComplete(Level level)
	{
		int flag=1;
		for(int i=0;i<level.targets.length;i++)
			for (int j = 0; j < level.targets[0].length; j++)
			{
				if(level.targets[i][j]==true)
				{
					if(level.sokoban[i][j].getStorageKind()=="Box")
						level.targets[i][j]=false;
					else flag=0;
				}
			}
		if(flag==0)
			return;
		else
			System.out.println("Level completed");
	}

	public boolean[][] getTargets() 
	{
		return targets;
	}

	public void setTargets(boolean[][] targets) 
	{
		this.targets = targets;
	}

	public Storage[][] getSokoban() 
	{
		return sokoban;
	}

	public void setSokoban(Storage[][] sokoban)
	{
		this.sokoban = sokoban;
	}

	public Position getPlayerPosition() 
	{
		return playerPosition;
	}

	public void setPlayerPosition(Position playerPosition) 
	{
		this.playerPosition = playerPosition;
	}

	public int getLevelNum()
	{
		return levelNum;
	}

	public void setLevelNum(int levelNum)
	{
		this.levelNum = levelNum;
	}

	public int getNumOfSteps() 
	{
		return numOfSteps;
	}

	public void setNumOfSteps(int numOfSteps) 
	{
		this.numOfSteps = numOfSteps;
	}

	public double getTime() 
	{
		return time;
	}

	public void setTime(double time)
	{
		this.time = time;
	}
	public boolean isGameOn() 
	{
		return gameOn;
	}

	public void setGameOn(boolean gameOn) 
	{
		this.gameOn = gameOn;
	}
}
