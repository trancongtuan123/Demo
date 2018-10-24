package main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import handler.WriteThread;
import validator.Validateport;

/**
 * Sever.java
 * 
 * Version 1.0
 * 
 * 03-10-2018
 * 
 * Modification
 * 
 * DATE AUTHOR DESCRIPTIONS
 *  --------------------------------------- 0
 * 3-10-2018  Tuan, Tran Cong
 *  Create
 */
public class ClientOption {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}
	public ClientOption() {
		super();
	}

	@Parameter(names = "-ports", description = "port", required = false, validateWith = Validateport.class)
	int port = 3000;
	private String hostname;
	private String userName;

	public ClientOption(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public void execute(String[] args) {
		ClientOption option = new ClientOption();
		JCommander.newBuilder().addObject(option).build().parse(args);
		int port = option.port;
		try {
			System.out.println(port);
			Socket socket = new Socket(hostname, port);

			System.out.println("Connected to the chat server");
			LOGGER.info("please input :"
					+ "\n input ---- addbird"
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
