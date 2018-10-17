package main;

import com.beust.jcommander.JCommander;

public class Client {
	public static void main(String[] args) {
		ClientOption option = new ClientOption();
		JCommander.newBuilder().addObject(option).build().parse(args);
		option.execute();
	}
}
