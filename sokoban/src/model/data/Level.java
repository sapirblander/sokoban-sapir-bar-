package model.data;

import java.io.Serializable;

public class Level implements Serializable {
	private static final long serialVersionUID = 1L;
	private int levelNum;
	private int numOfSteps;
	private double time;
	private boolean gameOn;
	private boolean gameUpdated;

	private Storage[][] sokoban;
	private boolean[][] targets; //Should never be changed
	private Position playerPosition;

	public Level() {
		levelNum = 0;
		numOfSteps = 0;
		time = 0;
		gameOn = false;
	}

	public void movePlayer(String move) {
		if (targets[playerPosition.getLine()][playerPosition.getColumn()]) {
			// Target
			sokoban[playerPosition.getLine()][playerPosition.getColumn()] = new Target();
		} else
			// Space
			sokoban[playerPosition.getLine()][playerPosition.getColumn()] = new Space();
		// Get new position for the player
		playerPosition = Position.getNewPosition(playerPosition, move);
		// Box
		if (sokoban[playerPosition.getLine()][playerPosition.getColumn()].getStorageKind() == "Box") {
			Position newBoxPosition = Position.getNewPosition(playerPosition, move);
			sokoban[newBoxPosition.getLine()][newBoxPosition.getColumn()] = new Box();
		}
		// Player
		sokoban[playerPosition.getLine()][playerPosition.getColumn()] = new Player();
		setGameUpdated(true);
		numOfSteps++;
	}

	public boolean isComplete() {
		boolean finishGame = true;
		for (int i = 0; i < targets.length && finishGame; i++)
			for (int j = 0; j < targets[0].length; j++) {
				if (targets[i][j] && sokoban[i][j].getStorageKind() != "Box") {
					finishGame = false;
				}
			}
		if (finishGame) {
			System.out.println("Level completed");
			setGameOn(false);
		}
		return finishGame;
	}

	public boolean[][] getTargets() {
		return targets;
	}

	public void setTargets(boolean[][] targets) {
		this.targets = targets;
	}

	public Storage[][] getSokoban() {
		return sokoban;
	}

	public void setSokoban(Storage[][] sokoban) {
		this.sokoban = sokoban;
	}

	public Position getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(Position playerPosition) {
		this.playerPosition = playerPosition;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	public int getNumOfSteps() {
		return numOfSteps;
	}

	public void setNumOfSteps(int numOfSteps) {
		this.numOfSteps = numOfSteps;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public boolean isGameOn() {
		return gameOn;
	}

	public void setGameOn(boolean gameOn) {
		this.gameOn = gameOn;
		setGameUpdated(true);
	}

	public boolean isGameUpdated() {
		return gameUpdated;
	}

	public void setGameUpdated(boolean gameUpdated) {
		this.gameUpdated = gameUpdated;
	}
}
