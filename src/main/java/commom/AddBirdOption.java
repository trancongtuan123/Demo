package commom;

import com.beust.jcommander.Parameter;

import until.Bird;
import until.Sight;

public class AddBirdOption {
	@Parameter(names = "-cmd", description = "comand", required = false)
	String cmd = "";
	@Parameter(names = "-name", description = "name", required = false)
	String name = "";
	@Parameter(names = "-color", description = "color", required = false)
	String color = "";
	@Parameter(names = "-height", description = "height", required = false)
	String height = "";
	@Parameter(names = "-weight", description = "weight", required = false)
	String weight = "";
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

	public AddBirdOption() {
	}
	
	public Bird getBird() {
		return new Bird(this.name, this.color, this.height, this.weight);
	}
	public Sight getSight() {
		return new Sight(this.local, this.time);
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public String getHeight() {
		return height;
	}

	public String getWeight() {
		return weight;
	}
}