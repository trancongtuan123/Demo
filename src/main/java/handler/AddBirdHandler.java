package handler;

import commom.AddBirdOption;
import until.Bird;
import until.Birds;

public class AddBirdHandler {

	public static String execute(AddBirdOption bOption) {
		Bird bird = Bird.create(bOption);
		Birds.birds1.getBirds().add(bird);
		return bird.toString();
	}
}
