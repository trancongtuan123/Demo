package until;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import commom.AddBirdOption;

/**
 * {@link Sight}.java
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
@XmlRootElement(name = "Sights")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sight {
	
	@Override
	public String toString() {
		return "-local " + this.local + " -time " + this.time;
	}
	String local;
	String time;
	public void setLocal(String local) {
		this.local = local;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Sight(String local, String time) {
		this.local = local;
		this.time = time;
	}
	public Sight() {
		super();
	}
	public static Sight create(AddBirdOption sOption) {
		return new Sight(sOption.getLocal(), sOption.getTime());
	}
}