package model.data;

import java.io.Serializable;

public class Position implements Serializable
{
	private static final long serialVersionUID = 1L;
	int column;
	int line;
	
	public Position(int x,int y) 
	{
		this.line=x;
		this.column=y;
	}

	public static Position getNewPosition(Position position, String move) 
	{
		int newLine = position.getLine();
		int newColumn = position.getColumn();
		switch (move) 
		{
		case "UP":
		case "up":
		case "Up":
			newLine--;
			break;
		case "DOWN":
		case "down":
		case "Down":
			newLine++;
			break;
		case "LEFT":
		case "Left":
		case "left":
			newColumn--;
			break;
		case "RIGHT":
		case "Right":
		case "right":
			newColumn++;
			break;
		}
		return new Position(newLine, newColumn);
	}
	
	public int getColumn() 
	{
		return column;
	}

	public void setColumn(int column) 
	{
		this.column = column;
	}

	public int getLine() 
	{
		return line;
	}

	public void setLine(int line) 
	{
		this.line = line;
	}
}
