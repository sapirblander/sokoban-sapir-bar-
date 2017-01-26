package model.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyObjectLevelLoder implements LevelLoader 
{
	@Override
	public Level loadLevel(InputStream in) 
	{
		Level newLevel = null;
		try {
			newLevel = (Level) Serialization.deserialize(in);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newLevel;
	}

	@Override
	public void saveLevel(OutputStream out, Level level) 
	{
		try {
			Serialization.serialize(level, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
