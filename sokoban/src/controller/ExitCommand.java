package controller;

public class ExitCommand implements Commands {
	private Controller controller;

	public ExitCommand(Controller controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void execute() {
		controller.stop();
	}

	@Override
	public void setParams(String params) {
	}
}
