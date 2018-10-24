package handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Logger;

import com.beust.jcommander.JCommander;

import commom.AddBirdOption;
import commom.Enumdemo;
import main.Client;
import main.SeverOption;
import until.Bird;
import until.Birds;
import until.Sight;
import until.Sights;

/**
 * Sever.java
 * 
 * Version 1.0
 * 
 * 03-10-2018
 * 
 * Modification
 * 
 * DATE------ AUTHOR ---DESCRIPTIONS
 *  ---------------------------------------
 *  03-10-2018 Tuan, Tran Cong Create
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

	public UserThread() {
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
				System.out.println(clientMessage);
				serverOption.broadcast(serverMessage, this);
				AddBirdOption bOption = new AddBirdOption();
				JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
				switch (Enumdemo.option.valueOf(bOption.getCmd())) {
				case addbird:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					AddBirdHandler.execute(bOption);
					for (int i = 0; i < Birds.birds1.birds.size(); i++) {
						for (int j = 1 + i; j < Birds.birds1.birds.size(); j++) {
							if (Birds.birds1.birds.get(i).getName().equals(Birds.birds1.birds.get(j).getName())) {
								Birds.birds1.birds.remove(j);
								String repo = "Fail ! Name bird is Duplicate ";
								writer.println(repo);
							} else{
								String repo = "OK ! Add bird complete ";
								writer.println(repo);
							}
						}
						if(Birds.birds1.birds.size() == 1){
							String repo = "OK ! Add bird complete ";
							writer.println(repo);
						}
					}
					break;
				case addsight:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					AddSightHandler.execute(bOption);
					String repon = "complete";
					writer.println(repon);
					socket.close();
					break;
				case quit:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					serverOption.removeUser(userName, this);
					socket.close();
					serverMessage = userName + " has quitted.";
					System.exit(0);
					break;
				case listbirds:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					writer.println("listbirds");
					writer.println(Birds.birds1.birds.size());
					Collections.sort(Birds.birds1.birds, new Comparator<Bird>() {
						@Override
						public int compare(Bird o1, Bird o2) {
							 return o1.getName().compareTo(o2.getName());
						}
					});
					writer.println(String.format("%-20s %-20s %-20s %-20s", "Name", "Color", "Height", "Weight"));
					for (Bird bird : Birds.birds1.birds) {
						writer.println(String.format("%-20s %-20s %-20s %-20s", bird.getName(), bird.getColor(),
								bird.getHeight(), bird.getWeight()));
					}
					
					break;
				case remove:
					String client = reader.readLine();
					int count1 = 0;
					System.out.println("Client : name bird must be remove : " + client);
					for (int i = 0; i < Birds.birds1.birds.size(); i++){
						if(client.equals(Birds.birds1.birds.get(i).getName())){
							Birds.birds1.birds.remove(i);
							count1++;
						}
					}
					if(count1 > 0){
						writer.println("Ok! Bird have been delete complete!");
					}else{
						writer.println("Can not delete Bird");
					}
					break;
				case listsights:
					JCommander.newBuilder().addObject(bOption).build().parse(clientMessage.split(" "));
					writer.println("listsights");
					String name = reader.readLine();
					String dateStar = reader.readLine();
					String dateEnd = reader.readLine();
					int count = 0;
					ArrayList<Sight> list = new ArrayList<>();
					for (Sight sight : Sights.listSight) {
						Date datetime = new SimpleDateFormat("dd/mm/yyyy").parse(sight.getTime());
						Date start = new SimpleDateFormat("dd/mm/yyyy").parse(dateStar);
						Date end = new SimpleDateFormat("dd/mm/yyyy").parse(dateEnd);
						if (sight.getName() != null && (sight.getName().matches(".*" + name + ".*"))) {
							if (datetime.after(start) && datetime.before(end)) {
								count++;
							list.add(sight);
							}
						}
					}
					System.out.println(list.size());
					writer.println(count);
					writer.println(String.format("%-20s %-20s ", "Name", "Time"));
					Collections.sort(list, new HandlerNameSight());
					Collections.sort(list, new HandlerTimeSights());
					for (Sight sight : list) {
						writer.println(String.format("%-20s %-20s", sight.getName() ,sight.getTime()));
					}
				
				default:
					break;
				}
			} while (!clientMessage.equals("addbird") || !clientMessage.equals("addsight")
					|| !clientMessage.equals("quit") || !clientMessage.equals("remove")
					|| !clientMessage.equals("listbirds") || !clientMessage.equals("listsights"));

			serverOption.removeUser(userName, this);
			socket.close();

			serverMessage = userName + " has quitted.";
			serverOption.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Client is close : " + ex.getMessage());

		} catch (IllegalArgumentException e) {
			LOGGER.warning("input not valid: ");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sends a list of online users to the newly connected user.
	 */
	void printUsers() {
		if (serverOption.hasUsers()) {
			writer.println(serverOption.getUserNames());
		} else {
			writer.println("");
		}
	}

	/**
	 * Sends a message to the client.
	 */
	public void sendMessage(String message) {
		writer.println(message);
	}
}