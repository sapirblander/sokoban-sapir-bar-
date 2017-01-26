package controller;

import model.Model;
import view.SokobanDisplay;

public class DisplayCommand implements Commands {
	private Model model;
	private SokobanDisplay sokobanDisplay;

	public DisplayCommand(Model model, SokobanDisplay sokobanDisplay) {
		this.model = model;
		this.sokobanDisplay = sokobanDisplay;
	}

	public void execute() {
		if (model.getCurrentLevel() == null) {
			System.out.println("Level is null in displayCommand");
			return;
		}
		if (model.getCurrentLevel().getSokoban() == null) {
			System.out.println("Level never initiated displayCommand");
			return;
		}
		if (sokobanDisplay == null) {
			System.out.println("sokobanDisplay is null in displayCommand");
			return;
		}
		sokobanDisplay.display(model.getCurrentLevel());
	}

	@Override
	public void setParams(String params) {
	}
}
