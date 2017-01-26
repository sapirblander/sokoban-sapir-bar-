package controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Scanner;

public class CLIClient extends Observable implements ClientHandler {

	private OutputStream outputStreamToClient;

	private void readInputAndSend(BufferedReader in, PrintWriter out, String exitStr) {
		try {
			String line;
			while (!(line = in.readLine()).equals(exitStr)) {
				out.println(line);
				out.flush();
			}
			out.println(line);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Thread aSyncReadInputsAndSend(BufferedReader in, PrintWriter out, String exitStr) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				readInputAndSend(in, out, exitStr);
			}
		});
		t.start();
		return t;
	}

	public void start(String ip, int port) { //This function runs at the client side
		try {
			Socket theServer = new Socket(ip, port);
			System.out.println("connected to server");
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			PrintWriter outToScreen = new PrintWriter(System.out);
			Thread t1 = aSyncReadInputsAndSend(userInput, outToServer, "Exit");
			Thread t2 = aSyncReadInputsAndSend(serverInput, outToScreen, "Close");

			t1.join();
			t2.join(); // wait for threads to end

			userInput.close();
			serverInput.close();
			outToServer.close();
			outToScreen.close();
			theServer.close();
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) { //This function runs at the server side
		this.outputStreamToClient = outToClient;

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(inFromClient)));
		PrintWriter printWriter = new PrintWriter(outToClient);
		String cmd;
		printWriter.println("Welcom to sokoban");
		printWriter.println("-----------------");
		printWriter.println("Your options are:");
		printWriter.println("Load");
		printWriter.println("Save");
		printWriter.println("Move(Up,Down,Left,Right)");
		printWriter.println("Display");
		printWriter.println("Exit");
		printWriter.println("-----------------");
		printWriter.flush();

		while (!sc.hasNext("Exit")) {
			cmd = sc.nextLine();
			String header = cmd;
			String footer = "";
			if (cmd.indexOf(" ") != -1) {
				header = cmd.substring(0, cmd.indexOf(" "));
				footer = cmd.substring(cmd.indexOf(" ") + 1);
			}
			setChanged();
			notifyObservers(Arrays.asList(header, footer));
		}
		printWriter.println("Thanks for playing");
		printWriter.flush();
		printWriter.println("Close");
		printWriter.flush();
	}

	@Override
	public OutputStream getOutputStreamToClient() {
		return outputStreamToClient;
	}
}
