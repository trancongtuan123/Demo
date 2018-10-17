package main;

import java.util.logging.Logger;

import javax.xml.transform.TransformerFactoryConfigurationError;

import com.beust.jcommander.JCommander;

public class Sever {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}
	public static void main(String[] args) {
		SeverOption option = new SeverOption();
		JCommander.newBuilder().addObject(option).build().parse(args);
		try {
			option.execute();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			LOGGER.warning("fail" + e.getMessage());
		}
	}
}
