package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import until.Birds;
import until.Sights;

public class ReadFile {
	public String name ; //name bird
	public String color ; // color bird
	public String weight ; // weight of bird
	public String height; // height of bird
	public String local ; // local sight
	public String time; //time sight
		
	/**
	 * method fineFile
	 * @param source
	 * @param key
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 */
	public static void finFile(String source) throws ParserConfigurationException, TransformerException,
			IOException, TransformerFactoryConfigurationError {
		File file = new File(source);
		//file is exist
		if (file.exists()) {
			if (file.isFile() && file.getName().equals("Birds.xml")) {
				
				Birds birds = Birds.readBirds(source);
				birds.printBirds();
				
				/*if(file.getName().equals("Sights.xml")){
				Sights sights = Sights.readSight(source);
				sights.printSight();
				}*/
			}
			if (file.isFile() && file.getName().equals("Sights.xml")) {
				Sights sights = Sights.readSight(source);
				sights.printSight();
			}
			//list file
			File[] listFile = file.listFiles();
			if (listFile != null) {
				for (File f : listFile) {
					finFile(f.getAbsolutePath());
				}
			}
		}
		//file not exist
		if (!file.exists()) {
			System.err.println("file is not exists!");
			if (file.mkdirs()) {
				
			File file1 = new File(source+"/Birds.xml");
			
			file1.createNewFile();
			PrintWriter writer = new PrintWriter(file1.getAbsolutePath(), "UTF-8");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<Birds>");
			writer.println("</Birds>");
			writer.close();
			
			File file2 = new File(source+"/Sights.xml")
					;
			file2.createNewFile();
			PrintWriter writer1 = new PrintWriter(file2.getAbsolutePath(), "UTF-8");
			writer1.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer1.println("<Sights>");
			writer1.println("</Sights>");
			writer1.close();
			} 
		} else {
			System.out.println("soure not exist");
		}
	}
	public static void main(String[] args) {
		try {
			ReadFile.finFile("D:/Tuan");
		} catch (ParserConfigurationException | TransformerException | IOException
				| TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
