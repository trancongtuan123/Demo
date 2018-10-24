package handler;

import commom.AddBirdOption;
import until.Sight;
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
public class AddSightHandler {
	public static String execute(AddBirdOption bOption){
		Sight sight = Sight.create(bOption);
		Sights.listSight.add(sight);
		return sight.toString();
	}
}