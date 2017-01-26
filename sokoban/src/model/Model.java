package model;

import model.data.Level;
import model.policy.SokobanPolicy;

public interface Model 
{
	public Level getCurrentLevel();
	public void checkChanges();
	public SokobanPolicy getCurrentPolicy();
}
