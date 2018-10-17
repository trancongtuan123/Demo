package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.beust.jcommander.Parameter;

import handler.UserThread;
import until.Bird;
import until.Sight;
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
public class SeverOption {

	@Parameter(names = "-port", description = "port", required = false, validateWith = Validateport.class)
	int port = 3000; // port
	@Parameter(names = "-data", description = "data", required = false)
	public String url = "D:/Tuan"; // data
	@Parameter(names = "-procount", description = "procount", required = false)
	int procount = 2; // procount
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}
	private Set<UserThread> userThreads = new HashSet<>();
	private Set<String> userNames = new HashSet<>();
	Bird bird;
	Sight sight;

	public SeverOption() {
		super();
	}

	public SeverOption(int port, String url, int procount) {
		super();
		this.port = port;
		this.url = url;
		this.procount = procount;
	}

	public void execute() {
		SeverOption severOption = new SeverOption();
		int port = severOption.port;
		String folder = severOption.url;
		int maxConnections = severOption.procount;
		try {
			port = severOption.port;
			folder = severOption.url;
			maxConnections = severOption.procount;
			System.out.println("Server is listening on port " + port);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			LOGGER.info("Command line arguments missing or faulty.  Launching with program defaults.");
		}
		try {
			ReadFile.finFile(folder);
			Timer timer = new Timer();
			timer.schedule(new AutoSaveFile(), 0, 60 * 500);
			Thread.sleep(3000);
		} catch (ParserConfigurationException e2) {
			LOGGER.warning("fail" + e2.getMessage());
		} catch (TransformerException e2) {
			LOGGER.warning("fail" + e2.getMessage());
		} catch (IOException e2) {
			LOGGER.warning("fail" + e2.getMessage());
		} catch (TransformerFactoryConfigurationError e2) {
			LOGGER.warning("fail" + e2.getMessage());
		} catch (InterruptedException e) {
			LOGGER.warning("fail" + e.getMessage());
		}
		boolean createLogFile = false;
		if (createLogFile) {
			attachLogFileHandler();
		}
		String refuseNewConnectionMessage = "The server limit of " + maxConnections
				+ ((maxConnections == 1) ? " connection" : " connections")
				+ " has been reached.  Please try again, later.";

		try (ServerSocket socketRequestListener = new ServerSocket(port)) {
			LOGGER.info("Server started on port: " + socketRequestListener.getLocalPort()
					+ ".  Maximum simultaneous users: " + maxConnections);
			while (true) {
				// the following call blocks until a connection is made
				Socket socket = socketRequestListener.accept();
				InetAddress remoteMachine = socket.getInetAddress();
				// String remoteHost = remoteMachine.getHostName();
				LOGGER.info("Incoming connection request from " + remoteMachine);

				int numActiveSockets = Thread.activeCount() - 2;
				if (numActiveSockets < maxConnections) {
					new Thread(new UserThread(socket, this)).start();
					numActiveSockets++;
					LOGGER.info("HELLO " + remoteMachine + ".  Number of current connections: " + numActiveSockets);
				} else {
					PrintWriter out = new PrintWriter(socket.getOutputStream());
					out.println(refuseNewConnectionMessage);
					out.close();
					socket.close();
					LOGGER.warning("SORRY " + remoteMachine 
							+ ".  Number of current connections: " + numActiveSockets);
				}
			}
		} catch (IOException e) {
			LOGGER.warning("fail" + e.getMessage());
		} catch (TransformerFactoryConfigurationError e1) {
			LOGGER.warning("fail" + e1.getMessage());
		} catch (NullPointerException e) {
			LOGGER.warning("fail" + e.getMessage());
		} catch (Exception e) {
			LOGGER.warning("fail" + e.getMessage());
		}
	}

	/**
	 * Delivers a message from one user to others (broadcasting)
	 */
	public void broadcast(String message, UserThread excludeUser) {
		for (UserThread aUser : userThreads) {
			if (aUser != excludeUser) {
				aUser.sendMessage(message);
			}
		}
	}

	/**
	 * Stores username of the newly connected client.
	 */
	public void addUserName(String userName) {
		userNames.add(userName);
	}

	/**
	 * When a client is disconneted, removes the associated username and
	 * UserThread
	 */
	public void removeUser(String userName, UserThread aUser) {
		boolean removed = userNames.remove(userName);
		if (removed) {
			userThreads.remove(aUser);
			System.out.println("The user " + userName + " quitted");
		}
	}

	public Set<String> getUserNames() {
		return this.userNames;
	}

	/**
	 * Returns true if there are other users connected (not count the currently
	 * connected user)
	 */
	public boolean hasUsers() {
		return !this.userNames.isEmpty();
	}

	private static void attachLogFileHandler() {
		String datedFile = LocalDateTime.now().toString();
		try {
			FileHandler fh = new FileHandler(datedFile + ".log");
			LOGGER.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		} catch (SecurityException e) {
			LOGGER.warning(e.getMessage());
		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
		}
	}
}
