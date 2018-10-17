package validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class Validateport implements IParameterValidator {
	@Override
	public void validate(String name, String value) throws ParameterException {
		int port = Integer.parseInt(value);
		if(1 >= port || port >= 65535){
			throw new ParameterException("port " + port + " should be between 1 and 65535");
		}
	}
}
