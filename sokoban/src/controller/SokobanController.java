package controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import controller.server.MyDisplay;
import controller.server.MyServer;
import model.Model;
import view.View;

public class SokobanController implements Controller {
	private Model model;
	private View view;
	private MyServer myServer;
	private Map<String, Commands> commands;
	private BlockingQueue<Commands> queue = new ArrayBlockingQueue<Commands>(10);;
	private boolean isStopped = false;

	public SokobanController(Model model, MyServer myServer) {
		this.setModel(model);
		this.setMyServer(myServer);

		commands = new HashMap<String, Commands>();
		commands.put("Move", new MoveCommand(model));
		commands.put("Load", new LoadCommand(model));
		commands.put("Save", new SaveCommand(model));
		commands.put("Exit", new ExitCommand(this));
		commands.put("Display", new DisplayCommand(model, new MyDisplay(this.myServer)));
	}

	public SokobanController(Model model, View view) {
		this.setModel(model);
		this.view = view;

		commands = new HashMap<String, Commands>();
		commands.put("Move", new MoveCommand(model));
		commands.put("Load", new LoadCommand(model));
		commands.put("Save", new SaveCommand(model));
		commands.put("Exit", new ExitCommand(this));
		commands.put("Display", new DisplayCommand(model, view.getCurrentDisplayer()));
	}

	public void insertCommand(Commands command) {
		try {
			queue.put(command);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		if (myServer != null)
			myServer.start();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!isStopped) {
					try {
						Commands cmd = queue.poll(1, TimeUnit.SECONDS);
						if (cmd != null)
							cmd.execute();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	@Override
	public void stop() {
		isStopped = true;
		if (myServer != null)
			myServer.stop();
	}

	@Override
	public void update(Observable o, Object arg) {
		@SuppressWarnings("unchecked")
		List<String> params = (List<String>) arg;
		String commandKey = params.get(0);
		Commands c = commands.get(commandKey);
		if (c == null) {
			if (myServer != null) {
				PrintWriter printWriter = new PrintWriter(myServer.getCh().getOutputStreamToClient());
				printWriter.println("Command " + commandKey + " not found");
				printWriter.flush();
			} else
				view.displayError("Command " + commandKey + " not found");
			return;
		}
		String secondParam = "";
		if (params.size() >= 2)
			secondParam = params.get(1);
		c.setParams(secondParam);
		insertCommand(c);
	}

	public MyServer getMyServer() {
		return myServer;
	}

	public void setMyServer(MyServer myServer) {
		this.myServer = myServer;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
}
