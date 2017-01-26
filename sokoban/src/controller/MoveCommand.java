package controller;

import model.Model;

public class MoveCommand implements Commands {
	private String move;
	private Model model;

	public MoveCommand(Model model) {
		this.setModel(model);
	}

	@Override
	public void execute() {
		if (model.getCurrentLevel() == null) {
			System.out.println("Level is null in MoveCommand");
			return;
		}
		if (!model.getCurrentLevel().isGameOn()) {
			System.out.println("Game is off");
			return;
		}
		if (model.getCurrentPolicy().checkMove(model.getCurrentLevel(), move)) {
			System.out.println("Good Move");
			model.getCurrentLevel().isComplete();
		} else
			System.out.println("Bad Move");
		model.checkChanges();
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public void setParams(String params) {
		move = params;
	}
}
