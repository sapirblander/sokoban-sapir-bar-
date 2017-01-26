package displayers;

import storage.Storage;
import levels.Level;

public class MyDisplay implements SokobanDisplay 
{
	public void display(Level level) 
	{
		Storage[][] sokoban = level.getSokoban();
		for (int i = 0; i < sokoban.length; i++) {
			for (int j = 0; j < sokoban[0].length; j++) 
			{
				switch (sokoban[i][j].getStorageKind()) 
				{
				case "Wall":
					System.out.print('#');
					break;
				case "Box":
					System.out.print('@');
					break;
				case "Player":
					System.out.print('A');
					break;
				case "Target":
					System.out.print('o');
					break;
				default:
					System.out.print(' ');
					break;
				}
			}
			System.out.print('\n');
		}
	}
}
