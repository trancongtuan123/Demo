package main;

import java.util.logging.Logger;

import javax.xml.transform.TransformerFactoryConfigurationError;

import com.beust.jcommander.ParameterException;

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
public class Sever {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}

	public static void main(String[] args) {
		SeverOption option = new SeverOption();
		try {
			option.execute(args);
		} catch (TransformerFactoryConfigurationError e) {
			LOGGER.warning("fail" + e.getMessage());
		} catch (ParameterException e) {
			LOGGER.warning("input port or procount or data must be corret format");
		}
	} 
}

