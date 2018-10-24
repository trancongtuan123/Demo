package commom;

import com.beust.jcommander.Parameter;

import until.Sight;

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
public class AddSightOption {
	@Parameter(names = "-cmd", description = "comand", required = false)
	String cmd = "";
	@Parameter(names = "-local", description = "local", required = false)
	String local = "";
	@Parameter(names = "-time", description = "time", required = false)
	String time = "";
	@Parameter(names = "-name", description = "name", required = false)
	String name = "";

	public String getName() {
		return name;
	}

	public String getCmd() {
		return cmd;
	}

	public String getLocal() {
		return local;
	}

	public String getTime() {
		return time;
	}

	public Sight getSight() {
		return new Sight(this.local, this.time, this.name);
	}
}
