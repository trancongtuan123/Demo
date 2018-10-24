package handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import commom.Enumdemo;
import commom.StringProcess;
import commom.Enumdemo.option;
import main.Client;
import main.ClientOption;
import until.Bird;
import until.Sight;

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
			BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String sever = read.readLine();
			System.out.println(sever);
			String userName = "";
			client.setUserName(userName);
			writer.println(userName);

			StringBuilder cmd = new StringBuilder();
			System.out.println("input message");
			text = reader.readLine();
			while(!text.equals("addbird") && !text.equals("addsight")
					&& !text.equals("quit") && !text.equals("remove")
					&& !text.equals("listbirds") && !text.equals("listsights")){
				LOGGER.info("you input not correct :"
						+ "\n input ---- addbird"
						+ "\n input ---- addsight"
						+ "\n input ---- remove"
						+ "\n input ---- quit"
						+ "\n input ---- listbirds"
						+ "\n input ---- listsights");
				System.out.println("please input agian");
				text = reader.readLine();
			}
			option test = Enumdemo.option.valueOf(text);
			switch (test) {
			case addbird:
				cmd.append("-cmd " + test.toString());
				Bird bird = AddBirdsHandler.bird();
				cmd.append(" " + bird.toString());
				System.out.println("name bird : " + bird.getName());
				break;
			case addsight:
				cmd.append("-cmd addsight");
				Sight sight = AddSightsHandler.sight();
				cmd.append(" " + sight.toString());
				break;
			case quit:
				cmd.append("-cmd quit");
				writer.println(cmd.toString());
				socket.close();
				writer.close();
				break;
			case remove:
				cmd.append("-cmd remove");
				writer.println(cmd.toString());
				System.out.println("input name bird you want remove");
				String client = reader.readLine();
				writer.println(client);
				break;
			case listsights:
				cmd.append("-cmd listsights");
				break;
			case listbirds:
				cmd.append("-cmd listbirds");
				break;
			default:
				break;
			}
			System.out.println("[" + userName + "]: ");
			writer.println(cmd.toString());
			Thread.sleep(3000);
			String readsever = read.readLine();
			System.out.println("Server: "+readsever);
			if("listbirds".equals(readsever)){
				String total = read.readLine();
				System.out.println("total birds: "+total);
				String listbirds = read.readLine();
				System.out.println("--------------------------------------"
						+ "------------------------------------------------");
				System.out.println(" | " + listbirds + " | ");
				System.out.println("----------------------------------------"
						+ "----------------------------------------------");
				for(int i = 0; i < Integer.parseInt(total); i++){
				System.out.println(" | " + read.readLine() + " | ");
				System.out.println("-------------------------------------------"
						+ "-------------------------------------------");
				}
			}
			if("listsights".equals(readsever)){
				System.out.println("input name bird");
				String name = reader.readLine();
				writer.println(name);
				System.out.println("input date start: ");
				String dateStart = reader.readLine();
				while (StringProcess.notVaildDate(dateStart)) {
					System.out.println("please input dd/mm/yyyy");
					dateStart = reader.readLine();
				}
				writer.println(dateStart);
				System.out.println("input date end: ");
				String dateEnd = reader.readLine();
				Date start = new SimpleDateFormat("dd/mm/yyyy").parse(dateStart);
				Date end = new SimpleDateFormat("dd/mm/yyyy").parse(dateEnd);
				while (StringProcess.notVaildDate(dateEnd)) {
					System.out.println("please input dd/mm/yyyy");
					dateEnd = reader.readLine();
					if (start.after(end)) {
						System.out.println("date end must be greater date start ");
						dateEnd = reader.readLine();
					}
				}
				writer.println(dateEnd);
				String havesight = read.readLine();
				System.out.println("total sights see : " + havesight);
				System.out.println("--------------------------------------------");
				System.out.println(" | " + read.readLine()+" | ");
				System.out.println("--------------------------------------------");
				for (int i = 0; i < Integer.parseInt(havesight); i++){
					System.out.println(" | " + read.readLine() +" | ");
					System.out.println("--------------------------------------------");
				}
			}
		} catch (IOException e) {
			LOGGER.warning("disconect");
		} catch (IllegalArgumentException e) {
			LOGGER.info("you input not correct :"
					+ "\n input ---- addbird"
					+ "\n input ---- addsight"
					+ "\n input ---- remove"
					+ "\n input ---- quit"
					+ "\n input ---- listbirds"
					+ "\n input ---- listsights");
		} catch (InterruptedException e) {
			LOGGER.warning("FAIL");
		} catch (ParseException e) {
			LOGGER.warning("FAIL");
		}
	}
}