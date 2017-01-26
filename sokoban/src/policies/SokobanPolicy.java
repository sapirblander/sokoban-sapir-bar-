package policies;

import levels.Level;

public interface SokobanPolicy 
{
	public boolean checkMove(Level level, String move);
}
