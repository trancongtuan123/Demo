package main;

import java.util.TimerTask;

import until.Birds;
import until.Sights;

/**
 * AutoSaveFile.java
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
public class AutoSaveFile extends TimerTask {

	public void run() {
		SeverOption option = new SeverOption();
		System.out.println("auto save file");
		Birds.save(option.url);
		Sights.addSight(option.url);
	}
}