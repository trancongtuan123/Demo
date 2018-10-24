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
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import handler.UserThread;
import until.Bird;
import until.Birds;
import until.Sight;
import until.Sights;
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
	public String url = System.getProperty("user.home");
	
	@Parameter(names = "-procount", description = "procount", required = false)
	int procount = 2; // procount
	public String folder = url;
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}
	private Set<UserThread> userThreads = new HashSet<>();
	private Set<String> userNames = new HashSet<>();
	static volatile boolean keepRunning = true; 
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

	/**
	 * method excute method create thread when client conection complete handler
	 * client connection server read or write file auto save
	 */
	public void execute(String args[]) {
		SeverOption severOption = new SeverOption();
		JCommander.newBuilder().addObject(severOption).build().parse(args);
		int port = severOption.port;
		int maxConnections = severOption.procount;
		try {
			port = severOption.port;
			folder = severOption.url;
			maxConnections = severOption.procount;
		
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			LOGGER.info("Command line arguments missing or faulty.  Launching with program defaults.");
		}
		boolean createLogFile = false;
		if (createLogFile) {
			attachLogFileHandler();
		}
		String refuseNewConnectionMessage = "The server limit of " + maxConnections
				+ ((maxConnections == 1) ? " connection" : " connections")
				+ " has been reached.  Please try again, later.";
		System.out.println("Server is listening on port " + port);
		try (ServerSocket socketRequestListener = new ServerSocket(port)) {
			LOGGER.info("Server started on port: " + socketRequestListener.getLocalPort()
					+ ".  Maximum simultaneous users: " + maxConnections);
		
			ReadFile.finFile(folder);
			Timer timer = new Timer();
			Thread.sleep(3000);
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					SeverOption severOption = new SeverOption();
					severOption.runAutoSaveFile(folder);
				}
			},0,60*5000)
			;

	        Runtime r = Runtime.getRuntime();
	        r.addShutdownHook(new Thread() {
	            public void run() {
	            	Birds.save(folder);
	        		Sights.addSight(folder);
	        		System.out.println("data will be saved in: " +folder);
	            }
	        });
	 
	        System.out.println("Now main sleeping... press ctrl+c to exit");
	        try {
	            Thread.sleep(3000);
	        } catch (Exception e) {
	        e.printStackTrace();
	}
		 
		   	
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
					LOGGER.warning("SORRY " + remoteMachine + ".  Number of current connections: " + numActiveSockets);
				}
			}
		} catch (IOException e) {
			LOGGER.warning("fail : sever is exist ");
			System.exit(0);
		} catch (TransformerFactoryConfigurationError e1) {
			LOGGER.warning("fail" + e1.getMessage());
		} catch (NullPointerException e) {
			LOGGER.warning("fail" + e.getMessage());
		} catch (ParserConfigurationException e) {
			LOGGER.warning("fail" + e.getMessage());
		} catch (TransformerException e) {
			LOGGER.warning("fail" + e.getMessage());
		} catch (InterruptedException e) {
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
	public String getUrl() {
		return url;
	}
	public void runAutoSaveFile(String url) {
		System.out.println("auto save file");
		Birds.save(url);
		Sights.addSight(url);
	}
}
