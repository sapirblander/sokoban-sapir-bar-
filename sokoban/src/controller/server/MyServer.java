package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
	private int port;
	private ClientHandler ch;
	private volatile boolean stop;

	public MyServer(int port, ClientHandler ch) {
		this.port = port;
		this.ch = ch;
		stop = false;
	}

	public ClientHandler getCh() {
		return ch;
	}

	public void setCh(ClientHandler ch) {
		this.ch = ch;
	}

	private void runServer() throws Exception {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Server is Up!!");
		server.setSoTimeout(1000);
		boolean firstTime = true;
		while (!stop) {
			try {
				if (firstTime) {
					firstTime = false;
					System.out.println("Waiting for users..");
				}
				Socket aClient = server.accept();
				System.out.println("Great! we got new user in");
				new Thread(new Runnable() { 								
					public void run() {
						try {
							ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
							aClient.close();
							System.out.println("Oww no! user left us..");
						} catch (IOException e) {
						}
					}
				}).start();
			} catch (SocketTimeoutException e) {
			}
		}
		server.close();
		System.out.println("Server is Down!!");
	}

	public void start() {
		new Thread(new Runnable() {
			public void run() {
				try {
					runServer();
				} catch (Exception e) {
				}
			}
		}).start();
	}

	public void stop() {
		stop = true;
	}
}
