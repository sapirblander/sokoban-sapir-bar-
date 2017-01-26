package commands;

import java.util.Scanner;
import levels.Level;
import policies.MySokobanPolicy;
import displayers.MyDisplay;

public class CLI 
{
	public void Cli()
	{
		Level myLevel = new Level();
		LoadCommand loadCommand = new LoadCommand(myLevel);
		SaveCommand saveCommand = new SaveCommand(myLevel);
		MoveCommand moveCommand = new MoveCommand(myLevel,
				new MySokobanPolicy());
		DisplayCommand displayCommand = new DisplayCommand(myLevel,
				new MyDisplay());
		ExitCommand exitCommand = new ExitCommand(myLevel);
		String cmd = "";
		Scanner scanner = new Scanner(System.in);
		boolean neverLoad = true;
		System.out.println("Welcom to sokoban");
		System.out.println("-----------------");
		System.out.println("Your options are:");
		System.out.println("Load");
		System.out.println("Save");
		System.out.println("Move(Up,Down,Left,Right)");
		System.out.println("Display");
		System.out.println("Exit");
		System.out.println("-----------------");
		while (myLevel.isGameOn() || neverLoad) 
		{
			System.out.println("Enter next move:");
			cmd = scanner.nextLine();
			String header = cmd;
			String footer = "";
			if (cmd.indexOf(" ") != -1) 
			{
				header = cmd.substring(0, cmd.indexOf(" "));
				footer = cmd.substring(cmd.indexOf(" ") + 1);
			}
			switch (header) 
			{
			case "Load":
			case "LOAD":
			case "load":
				if (neverLoad)
					neverLoad = false;
				loadCommand.setFile(footer);
				loadCommand.execute();
				break;
			case "Save":
			case "SAVE":
			case "save":
				saveCommand.setFile(footer);
				saveCommand.execute();
				break;
			case "Move":
			case "MOVE":
			case "move":
				moveCommand.setMove(footer);
				moveCommand.execute();
				break;
			case "Exit":
			case "EXIT":
			case "exit":
				neverLoad = false;
				System.out.println("ByeBye");
				exitCommand.execute();
				break;
			case "Display":
			case "DISPLAY":
			case "display":
				displayCommand.execute();
				break;
			default:
				System.out.println("Wrong input");
				break;
			}
		}
		scanner.close();
	}
}
