package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import until.Birds;
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
 * DATE AUTHOR DESCRIPTIONS
 *  --------------------------------------- 0
 * 3-10-2018  Tuan, Tran Cong
 *  Create
 */
public class ReadFile {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}
	/**
	 * method fineFile
	 * @param source
	 * @param key
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 */
	public static void finFile(String source) throws ParserConfigurationException, TransformerException, IOException,
			TransformerFactoryConfigurationError {
		File file = new File(source);
		// file is exist
		try {
		if (file.exists()) {

			String urlBird = source + "/Birds.xml";
			File fileBird = new File(urlBird);
			String urlSight = source + "/Sights.xml";
			File fileSight = new File(urlSight);
			if (fileBird.exists()) {
				LOGGER.info("Have file Birds : ");
				Birds birds = Birds.readBirds(source + "/Birds.xml");
				birds.printBirds();
			} else {
				LOGGER.info("File Bird is not exist, system will create new file");
				fileBird.createNewFile();
				PrintWriter writer = new PrintWriter(fileBird.getAbsolutePath(), "UTF-8");
				writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				writer.println("<Birds>");
				writer.println("</Birds>");
				writer.close();
			}
			if (fileSight.exists()) {
				LOGGER.info("Have File Sight : ");
				Sights sights = Sights.readSight(source+ "/Sights.xml");
				sights.printSight();
			} else {
				LOGGER.info("File Sight is not exist, system will create new file");
				fileSight.createNewFile();
				PrintWriter writer1 = new PrintWriter(fileSight.getAbsolutePath(), "UTF-8");
				writer1.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				writer1.println("<Sights>");
				writer1.println("</Sights>");
				writer1.close();
			}
		}
		// file not exist
		if (!file.exists()) {
			if(file.mkdirs()){
			System.err.println("folder is not exists!");

			File file1 = new File(source + "/Birds.xml");

			file1.createNewFile();
			PrintWriter writer = new PrintWriter(file1.getAbsolutePath(), "UTF-8");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<Birds>");
			writer.println("</Birds>");
			writer.close();

			File file2 = new File(source + "/Sights.xml");
			file2.createNewFile();
			PrintWriter writer1 = new PrintWriter(file2.getAbsolutePath(), "UTF-8");
			writer1.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer1.println("<Sights>");
			writer1.println("</Sights>");
			writer1.close();
			}
		}
		} catch (IOException e) {
			LOGGER.warning("fail : can not add file ");
			System.exit(0);
		}
	}
}
