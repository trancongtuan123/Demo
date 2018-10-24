package handler;

import commom.AddBirdOption;
import until.Bird;
import until.Birds;

/**
 * Sever.java
 * 
 * Version 1.0
 * 
 * 03-10-2018
 * 
 * Modification
 * 
 * DATE AUTHOR DESCRIPTIONS --------------------------------------- 0 3-10-2018
 * Tuan, Tran Cong Create
 */
public class AddBirdHandler {

	public static String execute(AddBirdOption bOption) {
		Bird bird = Bird.create(bOption);
		Birds.birds1.getBirds().add(bird);
		return bird.toString();
	}
}
