package boot;

import controller.SokobanController;
import controller.server.CLIClient;
import controller.server.MyServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MyModel;
import view.SokobanViewController;

public class Run extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Run.fxml"));
			Parent root = fxmlLoader.load();

			SokobanViewController view = fxmlLoader.getController();
			MyModel model = new MyModel();
			SokobanController controller = new SokobanController(model, view);

			view.addObserver(controller);
			model.addObserver(controller);
			controller.start();

			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
	        primaryStage.setOnCloseRequest(event -> view.exitFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length == 2) {
			if (args[0].equals("-server")) {

				MyModel model = new MyModel();
				CLIClient cliClient = new CLIClient();
				SokobanController controller = new SokobanController(model,
						new MyServer(Integer.valueOf(args[1]), cliClient));

				model.addObserver(controller);
				((CLIClient)(controller.getMyServer().getCh())).addObserver(controller);
				controller.start();
			}
		} else
			launch(args);
	}
}
