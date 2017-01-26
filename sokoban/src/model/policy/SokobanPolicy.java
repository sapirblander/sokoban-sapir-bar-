package model.policy;

import model.data.Level;

public interface SokobanPolicy 
{
	public boolean checkMove(Level level, String move);
}
