package handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import commom.Enumdemo;
import commom.Enumdemo.option;
import main.Client;
import main.ClientOption;
import until.Bird;
import until.Sight;

/**
 * This thread is responsible for reading user's input and send it to the
 * server. It runs in an infinite loop until the user types 'bye' to quit.
 *
 * 
 */
public class WriteThread extends Thread {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(Client.class.getName());
	}
	private PrintWriter writer;
	private Socket socket;
	private ClientOption client;
	public static String text;

	public WriteThread(Socket socket, ClientOption client) {
		this.socket = socket;
		this.client = client;

		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			LOGGER.warning("Error getting output stream: " + ex.getMessage());
			
		}
	}

	public void run() {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("\nEnter your name: ");
			String userName = reader.readLine();
			client.setUserName(userName);
			writer.println(userName);

			StringBuilder cmd = new StringBuilder();
			System.out.println("input message");
			text = reader.readLine();
			option test = Enumdemo.option.valueOf(text);
				switch (test) {
				case addbird:
					cmd.append("-cmd " + test.toString());
					Bird bird = AddBirdsHandler.bird();
					cmd.append(" " + bird.toString());
					System.out.println("complete");
					System.out.println("name bird : " + bird.getName());
					break;
				case addsight:
					cmd.append("-cmd addsight");
					Sight sight = AddSightsHandler.sight();
					cmd.append(" " + sight.toString());
					System.out.println("complete");
					break;
				case quit:
					cmd.append("-cmd quit");
					writer.println(cmd.toString());
					socket.close();
					writer.close();
					break;
				case remove:
					break;
				case listsights:
					break;
				case listbirds:
					cmd.append("-cmd listbirds");
					break;
				default:
					break;
				}
			
			System.out.println("[" + userName + "]: ");
			writer.println(cmd.toString());
		} catch (IOException e) {
			LOGGER.warning("disconect");
		} catch (IllegalArgumentException e){
			LOGGER.warning("input not valid: " + text);
		}
	}
}