package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.data.Level;
import model.data.Storage;
import view.SokobanDisplay;

public class LevelGraphicDisplay extends Canvas implements SokobanDisplay {

	private StringProperty wallImageSrc;
	private StringProperty playerImageSrc;
	private StringProperty boxImageSrc;
	private StringProperty targetImageSrc;
	private StringProperty spaceImageSrc;
	private TimeThread thread;
	boolean firstDisplay = true;

	public LevelGraphicDisplay() {
		super();
		this.wallImageSrc = new SimpleStringProperty();
		this.playerImageSrc = new SimpleStringProperty();
		this.boxImageSrc = new SimpleStringProperty();
		this.targetImageSrc = new SimpleStringProperty();
		this.spaceImageSrc = new SimpleStringProperty();
	}

	@Override
	public void display(Level level) {
		GraphicsContext gc = getGraphicsContext2D();
		if (level == null) {
			return;
		}
		double canvasH = getHeight();
		double canvasW = getWidth();
		double header = canvasH * 0.1;
		double footer = canvasH * 0.1;
		gc.clearRect(0, header, canvasW, canvasH);
		canvasH = canvasH - footer - header;
		double storageH = canvasH / level.getSokoban().length;
		double storageW = canvasW / level.getSokoban()[0].length;

		String textString = "Level Number: " + level.getLevelNum() + " | Number of Steps: " + level.getNumOfSteps();
		gc.fillText(textString, 0, header + canvasH + footer / 2);
		Storage[][] sokoban = level.getSokoban();
		Image img = null;
		for (int i = 0; i < sokoban.length; i++) {
			for (int j = 0; j < sokoban[i].length; j++) {
				try {
					switch (sokoban[i][j].getStorageKind()) {
					case "Wall":
						img = new Image(new FileInputStream(wallImageSrc.get()));
						break;
					case "Box":
						img = new Image(new FileInputStream(boxImageSrc.get()));
						break;
					case "Player":
						img = new Image(new FileInputStream(playerImageSrc.get()));
						break;
					case "Target":
						img = new Image(new FileInputStream(targetImageSrc.get()));
						break;
					default:
						img = new Image(new FileInputStream(spaceImageSrc.get()));
						break;
					}
					gc.drawImage(img, j * storageW, i * storageH + header, storageW, storageH);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		if (firstDisplay) {
			thread = new TimeThread(gc, getHeight(), getWidth());
			thread.start();
			firstDisplay = false;
		}
		if(!level.isGameOn()){
			thread.stopTime();
			firstDisplay = true;
		}
	}

	public String getPlayerImageSrc() {
		return playerImageSrc.get();
	}

	public void setPlayerImageSrc(String playerImageSrc) {
		this.playerImageSrc.set(playerImageSrc);
	}

	public String getBoxImageSrc() {
		return boxImageSrc.get();
	}

	public void setBoxImageSrc(String boxImageSrc) {
		this.boxImageSrc.set(boxImageSrc);
	}

	public String getTargetImageSrc() {
		return targetImageSrc.get();
	}

	public void setTargetImageSrc(String targetImageSrc) {
		this.targetImageSrc.set(targetImageSrc);
	}

	public String getSpaceImageSrc() {
		return spaceImageSrc.get();
	}

	public void setSpaceImageSrc(String spaceImageSrc) {
		this.spaceImageSrc.set(spaceImageSrc);
	}

	public String getWallImageSrc() {
		return wallImageSrc.get();
	}

	public void setWallImageSrc(String wallImageSrc) {
		this.wallImageSrc.set(wallImageSrc);
	}

}
