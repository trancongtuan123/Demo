package handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import com.beust.jcommander.JCommander;

import commom.AddBirdOption;
import commom.Enumdemo;
import main.Client;
import main.SeverOption;
import until.Birds;

/**
 * This thread handles connection for each connected client, so the server
 * can handle multiple clients at the same time.
 *
 *
 */
public class UserThread extends Thread {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(Client.class.getName());
	}
	private Socket socket;
	private SeverOption serverOption;
	private PrintWriter writer;
	public static String clientMessage;

	public UserThread(Socket socket, SeverOption serveroption) {
		this.socket = socket;
		this.serverOption = serveroption;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);

			printUsers();

			String userName = reader.readLine();
			serverOption.addUserName(userName);

			String serverMessage = "New user connected: " + userName;
			serverOption.broadcast(serverMessage, this);

		

			do {
				clientMessage = reader.readLine();
				serverMessage = "[" + userName + "]: " + clientMessage;
				System.out.println( clientMessage );
				serverOption.broadcast(serverMessage, this);
				AddBirdOption bOption = new AddBirdOption();
				JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
				switch (Enumdemo.option.valueOf(bOption.getCmd())) {
				case addbird:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					AddBirdHandler.execute(bOption);
					String repo = "complete";
					writer.println(repo);
					socket.close();
					break;
				case addsight:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					AddSightHandler.execute(bOption);
					String repos = "complete";
					writer.print(repos);
					reader.readLine();
					socket.close();
					break;
				case quit:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					serverOption.removeUser(userName, this);
					socket.close();
					serverMessage = userName + " has quitted.";
					serverOption.broadcast(serverMessage, this);
					break;
				case listbirds:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					Birds birds1 = Birds.readBirds(serverOption.url + "/Birds.xml");
					birds1.printBirds();
					break;
				default:
					break;
				}
			} while (!clientMessage.equals("bye"));

			serverOption.removeUser(userName, this);
			socket.close();

			serverMessage = userName + " has quitted.";
			serverOption.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
		} catch (IllegalArgumentException e){
			LOGGER.warning("input not valid: ");
		}
	}

	/**
	 * Sends a list of online users to the newly connected user.
	 */
	void printUsers() {
		if (serverOption.hasUsers()) {
			writer.println("Connected users: " + serverOption.getUserNames());
		} else {
			writer.println("No other users connected");
		}
	}

	/**
	 * Sends a message to the client.
	 */
	public void sendMessage(String message) {
		writer.println(message);
	}
}