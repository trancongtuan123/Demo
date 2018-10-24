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
 * {@link Birds}.java
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
@XmlRootElement(name = "birds")
@XmlAccessorType(XmlAccessType.FIELD)
public class Birds {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}
	public static Birds birds1 = new Birds();
    @XmlElement(name = "bird")
    public List<Bird> birds = new ArrayList<Bird>();

    public static Birds readBirds(String subXmlFile) {
        FileInputStream is;
        try {
            is = new FileInputStream(new File(subXmlFile));
            if (is != null) {
                JAXBContext jc;
                jc = JAXBContext.newInstance(Birds.class);
                Unmarshaller u = jc.createUnmarshaller();
                JAXBElement<Birds> root = u.unmarshal(new StreamSource(is), Birds.class);
                Birds result = root.getValue();
                return result;
            }
        } catch (FileNotFoundException e1) {
            System.out.println(e1.getMessage());
            LOGGER.warning("fail" + e1.getMessage());
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
            LOGGER.warning("fail" + e.getMessage());
        }
        return null;
    }

    public List<Bird> getBirds() {
		return birds;
	}

	public void setBirds(List<Bird> birds) {
		this.birds = birds;
	}

	public void printBirds() {
        for (Bird bird : this.birds) {
            System.out.println(bird.toString());
            System.out.println();
        }
    }
	 public static Birds listBird()  {
		Bird emp;

		birds1.setBirds(new ArrayList<Bird>());
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("input number Birds you want");
		String a = sc.nextLine();
		for (int i = 0; i < Integer.parseInt(a); i++) {

			emp = new Bird();

			System.out.println("input name");
			emp.name = sc.nextLine();
			System.out.println("input color");
			emp.color = sc.nextLine();
			System.out.println("input height");
			emp.height = sc.nextLine();
			System.out.println("input weight");
			emp.weight = sc.nextLine();
			birds1.getBirds().add(emp);
		}
		return birds1;
	}

	 public static void save(String url) {
			try {
				JAXBContext context = JAXBContext.newInstance(Birds.class);
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.marshal(birds1, new File(url + "/Birds.xml"));
			} catch (JAXBException e) {
				LOGGER.warning("save fail because file " + e.getMessage());
			}
		}
}
