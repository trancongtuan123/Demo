package validator;

import java.util.logging.Logger;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import main.SeverOption;

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
public class Validateport implements IParameterValidator {
	final static Logger LOGGER; // destination for error messages
	static {
		LOGGER = Logger.getLogger(SeverOption.class.getName());
	}
	@Override
	public void validate(String name, String value) throws ParameterException {
		try{
		int port = Integer.parseInt(value);
		if(1 >= port || port >= 65535){
			throw new ParameterException("port " + port + " should be between 1 and 65535");
		}
		} catch(NumberFormatException e){
			LOGGER.warning("input port must be integer");
		} catch (ParameterException e ){
			LOGGER.warning("port should be between 1 and 65535");
		}
	}
}
