package main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import com.beust.jcommander.Parameter;

import handler.WriteThread;
import validator.Validateport;

public class ClientOption {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}
	public ClientOption() {
		super();
	}

	@Parameter(names = "-port", description = "port", required = false, validateWith = Validateport.class)
	int port = 3000;
	private String hostname;
	private String userName;

	public ClientOption(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public void execute() {
		try {
			Socket socket = new Socket(hostname, port);

			System.out.println("Connected to the chat server");
			LOGGER.info("input ---- addbird"
					+ "\n input ---- addsight"
					+ "\n input ---- remove"
					+ "\n input ---- quit"
					+ "\n input ---- listbirds"
					+ "\n input ---- listsights");
			new WriteThread(socket, this).start();

		} catch (UnknownHostException ex) {
			LOGGER.warning("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			LOGGER.warning("I/O Error: " + ex.getMessage());
		}
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

}
