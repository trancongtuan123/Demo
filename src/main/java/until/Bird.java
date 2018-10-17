package until;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import commom.AddBirdOption;

/**
 * {@link Bird}.java
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
@XmlRootElement(name = "bird")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bird {

	public String name;
	public String color;
	public String weight;
	public String height;


	public Bird(String name, String color, String weight, String height) {
		super();
		this.name = name;
		this.color = color;
		this.weight = weight;
		this.height = height;
	}

	public Bird() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
        return name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "-name " + this.name + " -color " + this.color + " -height " + this.height + " -weight " + this.weight;
    }

	public static Bird create(AddBirdOption bOption) {
		return new Bird(bOption.getName(), bOption.getColor(), bOption.getHeight(), bOption.getWeight());
	}
}
