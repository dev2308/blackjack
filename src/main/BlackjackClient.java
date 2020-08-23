package main;

import main.genericstuff.Command;
import main.genericstuff.GenericClient;

import java.io.IOException;
import java.util.Scanner;

public class BlackjackClient extends GenericClient {
	private String name;
	private InputThread inputThread;

	public BlackjackClient(String name) {
		this.name = name;
	}

	@Override
	public void start(String address, int port) throws IOException {
		super.start(address, port);
		try {
			sendMessageToServer(Command.HANDLE_NAME, name);
		} catch (IOException e) {
			stop();
			throw e;
		}
		inputThread = new InputThread();
		inputThread.start();
	}

	@Override
	public void stop() {
		super.stop();
		if (inputThread != null) {
			inputThread.interrupt();
		}
	}

	@Override
	protected void handleMessageFromServer(Command command, String message) {
		System.out.println(message);
	}

	private class InputThread extends Thread {
		private Scanner scanner = new Scanner(System.in);
		public void run() {
			while (!isInterrupted()) {
				try {
					sendMessageToServer(Command.HANDLE_INPUT, scanner.nextLine());
				} catch (IOException e) {
					e.printStackTrace();
					inputThread = null;
					BlackjackClient.this.stop();
					return;
				}
			}
		}
	}
}
