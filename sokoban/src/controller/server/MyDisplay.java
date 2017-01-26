package controller.server;

import java.io.PrintWriter;

import model.data.Level;
import model.data.Storage;
import view.SokobanDisplay;

public class MyDisplay implements SokobanDisplay 
{
	public MyDisplay(MyServer myServer) {
		super();
		this.myServer = myServer;
	}

	private MyServer myServer;
	
	public void display(Level level) 
	{
		PrintWriter out = new PrintWriter(this.myServer.getCh().getOutputStreamToClient());
		if(level == null){
			out.println("There is no level to Display");
			out.flush();
			return;
		}
		
		Storage[][] sokoban = level.getSokoban();
		for (int i = 0; i < sokoban.length; i++) {
			for (int j = 0; j < sokoban[0].length; j++) 
			{
				switch (sokoban[i][j].getStorageKind()) 
				{
				case "Wall":
					out.print('#');
					break;
				case "Box":
					out.print('@');
					break;
				case "Player":
					out.print('A');
					break;
				case "Target":
					out.print('o');
					break;
				default:
					out.print(' ');
					break;
				}
				out.flush();
			}
			out.print('\n');
			out.flush();
			
		}
	}
}
