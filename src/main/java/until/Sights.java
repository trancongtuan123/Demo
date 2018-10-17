package until;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

import main.SeverOption;

/**
 * {@link Sights}.java
 * 
 * Version 1.0
 * 
 * 03-10-2018
 * 
 * Modification
 * 
 * DATE AUTHOR DESCRIPTIONS 
 * ---------------------------------------
 *  03-10-2018 Tuan, Tran Cong
 *   Create
 */
@XmlRootElement(name = "sights")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sights {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}

	@XmlElement(name = "Sight")
	public static List<Sight> listSight = new ArrayList<Sight>();

	public List<Sight> getListSight() {
		return listSight;
	}

	public void setListSight(List<Sight> listSight) {
		Sights.listSight = listSight;
	}

	static Sights sights = new Sights();

	 public static Sights inputSights() {
		Sight emp;

		sights.setListSight(new ArrayList<Sight>());
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("input number sight you want");
		String a = sc.nextLine();
		for (int i = 0; i < Integer.parseInt(a); i++) {

			emp = new Sight();

			System.out.println("input local");
			emp.local = sc.nextLine();
			System.out.println("input time");
			emp.time = sc.nextLine();

			sights.getListSight().add(emp);
		}
		return sights;
	}

	public static void addSight(String url) {
		try {
			JAXBContext context = JAXBContext.newInstance(Sights.class);
			Marshaller marshaller = context.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshaller.marshal(sights, System.out);
			marshaller.marshal(sights, new File(url + "/Sights.xml"));
		} catch (JAXBException e) {
			LOGGER.warning("fail" + e.getMessage());
		}
	}

	public static Sights readSight(String subXmlFile) {

		FileInputStream is;
		try {
			is = new FileInputStream(new File(subXmlFile));
			if (is != null) {
				JAXBContext jc;
				jc = JAXBContext.newInstance(Sights.class);
				Unmarshaller u = jc.createUnmarshaller();
				JAXBElement<Sights> root = u.unmarshal(new StreamSource(is), Sights.class);
				Sights result = root.getValue();
				return result;
			}
		} catch (FileNotFoundException e1) {
			LOGGER.warning("fail" + e1.getMessage());
			
		} catch (JAXBException e) {
			LOGGER.warning("fail" + e.getMessage());
		} 
		return null;
	}

	public void printSight() {
		for (Sight sight : Sights.listSight) {
			System.out.println(sight.toString());
		}
	}
}
