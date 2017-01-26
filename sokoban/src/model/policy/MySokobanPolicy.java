package model.policy;

import model.data.Level;
import model.data.Position;

public class MySokobanPolicy implements SokobanPolicy 
{
	@Override
	public boolean checkMove(Level level, String move) 
	{
		Position newPosition = Position.getNewPosition(level.getPlayerPosition(), move);
		int newLine = newPosition.getLine();
		int newColumn = newPosition.getColumn();
		if (newColumn < 0 || newColumn >= level.getSokoban()[0].length
				|| newLine < 0 || newLine >= level.getSokoban().length) 
		{
			return false;
		}
		if (level.getSokoban()[newLine][newColumn].getStorageKind() == "Wall")
			return false;
		if (level.getSokoban()[newLine][newColumn].getStorageKind() == "Box") 
		{
			newPosition = Position.getNewPosition(newPosition, move);
			newLine = newPosition.getLine();
			newColumn = newPosition.getColumn();		
			if (level.getSokoban()[newLine][newColumn].getStorageKind() == "Wall"
					|| level.getSokoban()[newLine][newColumn].getStorageKind() == "Box")
				return false;
		}
		level.movePlayer(move);
		return true;
	}
}
