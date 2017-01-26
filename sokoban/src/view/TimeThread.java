package view;

import javafx.scene.canvas.GraphicsContext;

public class TimeThread extends Thread {

	private GraphicsContext graphicsContext;
	private double H;
	private double W;
	private boolean go = true;

	public TimeThread(GraphicsContext graphicsContext, double H, double W) {
		this.graphicsContext = graphicsContext;
		this.H = H;
		this.W = W;
	}

	public void stopTime() {
		go = false;
	}

	@Override
	public void run(){
		super.run();
		int count = 0;
		double header = H * 0.1;
		while (go) {
			count++;
			String textString = "Game Time: " + count/10;
			graphicsContext.clearRect(0, 0, W, header);
			graphicsContext.fillText(textString, 0, 10);
			try {
				TimeThread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
