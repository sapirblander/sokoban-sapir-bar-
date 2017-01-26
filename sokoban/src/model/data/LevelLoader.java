package model.data;

import java.io.InputStream;
import java.io.OutputStream;

public interface LevelLoader 
{
	public Level loadLevel(InputStream in);
	public void saveLevel(OutputStream out, Level level);
}
