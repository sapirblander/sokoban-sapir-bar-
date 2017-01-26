package model.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class MyTextLevelLoader implements LevelLoader 
{
	public Level loadLevel(InputStream in) 
	{
	
		Level newLevel = new Level();
		byte[] bytes = new byte[500];
		int readByte = 0;
		try {
			readByte = in.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		bytes = Arrays.copyOf(bytes, readByte);
		int rows = 0, columns = 0;
		boolean firstRow = true;
		for (int i = 0; i < bytes.length; i++) 
		{
			if (bytes[i] == '\n' || bytes[i] == '\r')
				firstRow = false;
			if (bytes[i] == '\n')
				rows++;
			if (firstRow)
				columns++;
		}
		Storage[][] sokoban = new Storage[rows][columns];
		boolean[][] targets = new boolean[rows][columns];
		Position playerPosition = null;
		rows = 0;
		columns = 0;
		for (int i = 0; i < bytes.length; i++) 
		{
			switch (bytes[i])
			{
			case '\r':
				break;
			case '\n':
				rows++;
				columns = 0;
				break;
			case '#':
				sokoban[rows][columns++] = new Wall();
				break;
			case '@':
				sokoban[rows][columns++] = new Box();
				break;
			case 'A':
				playerPosition = new Position(rows, columns);
				sokoban[rows][columns++] = new Player();
				break;
			case 'o':
				targets[rows][columns] = true;
				sokoban[rows][columns++] = new Target();
				break;
			default:
				sokoban[rows][columns++] = new Space();
				break;
			}
		}
		newLevel.setPlayerPosition(playerPosition);
		newLevel.setSokoban(sokoban);
		newLevel.setTargets(targets);
		return newLevel;
	}

	@Override
	public void saveLevel(OutputStream out, Level level) 
	{
		Storage[][] sokoban = level.getSokoban();
		for (int i = 0; i < sokoban.length; i++) 
		{
			try {
				for (int j = 0; j < sokoban[0].length; j++) 
				{
					switch (sokoban[i][j].getStorageKind()) 
					{
					case "Wall":
						out.write('#');
						break;
					case "Box":
						out.write('@');
						break;
					case "Player":
						out.write('A');
						break;
					case "Target":
						out.write('o');
						break;
					default:
						out.write(' ');
						break;
					}
				}
				out.write('\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
