package view;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SokobanViewController extends Observable implements View, Initializable {

	@FXML
	LevelGraphicDisplay levelGraphicDisplay;
	private Media media = new Media(new File("./resources/music.mp3").toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media);

	public SokobanViewController() {
		super();
	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open sokoban file");
		fc.setInitialDirectory(new File("./levels"));
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {
			setChanged();
			notifyObservers(Arrays.asList("Load", chosen.getAbsolutePath()));
			mediaPlayer.play();
		}
	}

	public void saveFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("save sokoban file");
		fc.setInitialDirectory(new File("./levels"));
		File chosen = fc.showSaveDialog(null);
		if (chosen != null) {
			setChanged();
			notifyObservers(Arrays.asList("Save", chosen.getAbsolutePath()));
		}

	}

	public void exitFile() {
		Platform.exit();
		setChanged();
		notifyObservers(Arrays.asList("Save", "saved/autoSave.txt"));
		setChanged();
		notifyObservers(Arrays.asList("Exit"));
	}

	@Override
	public SokobanDisplay getCurrentDisplayer() {
		return levelGraphicDisplay;
	}

	@Override
	public void displayError(String msg) {
		System.out.println(msg);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		levelGraphicDisplay.display(null);
		levelGraphicDisplay.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> levelGraphicDisplay.requestFocus());
		levelGraphicDisplay.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.UP) {
					setChanged();
					notifyObservers(Arrays.asList("Move", "Up"));
				}
				if (event.getCode() == KeyCode.DOWN) {
					setChanged();
					notifyObservers(Arrays.asList("Move", "Down"));
				}
				if (event.getCode() == KeyCode.RIGHT) {
					setChanged();
					notifyObservers(Arrays.asList("Move", "Right"));
				}
				if (event.getCode() == KeyCode.LEFT) {
					setChanged();
					notifyObservers(Arrays.asList("Move", "Left"));
				}
			}
		});
	}
}
