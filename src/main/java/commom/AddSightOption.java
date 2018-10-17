package commom;

import com.beust.jcommander.Parameter;

import until.Sight;

public class AddSightOption {
	@Parameter(names = "-cmd", description = "comand", required = false)
	String cmd = "";
	@Parameter(names = "-local", description = "local", required = false)
	String local = "";
	@Parameter(names = "-time", description = "time", required = false)
	String time = "";
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
		return new Sight(this.local, this.time);
	}
}
