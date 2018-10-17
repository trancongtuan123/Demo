package handler;

import commom.AddBirdOption;
import until.Sight;
import until.Sights;

public class AddSightHandler {
	public static String execute(AddBirdOption bOption){
		Sight sight = Sight.create(bOption);
		Sights.listSight.add(sight);
		return sight.toString();
	}
}