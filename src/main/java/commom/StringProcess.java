package commom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * StringProcess.java
 * 
 * Version 1.0
 * 
 * 03-10-2018
 * 
 * Modification
 * 
 * DATE			AUTHOR 		DESCRIPTIONS
 * --------------------------------------- 
 * 03-10-2018   Tuan, Tran Cong		Create
 */ 
public class StringProcess {
	/**
	 * Ham kiem tra xem xau co bao gom chi chu so hay khong
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean notVaildNumber(String s) {
		String regex = "[0-9]+";
		return (s.matches(regex)/* || "".equals(s) */) ? false : true;
	}
	/**
	 * Ham kiem tra xau rong hay khong
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean notVaild(String s) {
		if (s.trim() == null || s.trim().length() == 0)
			return true;
		return false;
	}
	/**
	 * Ham kiem tra xem number >= 1 && number <= 65535
	 * 
	 * @param s
	 * @return boolean
	 */
	public boolean notVaildGreaterThanZero(String s) {
		try {
			int number = Integer.parseInt(s);
			if (number >= 1 && number <= 65535)
				return true;
		} catch (NumberFormatException e) {
			/* System.out.println("input port bettwen 1 and 65535"); */
			return false;
		}
		return false;
	}
	/**
	 * Ham dinh dang ngay thang nam yyyy-MM-dd
	 * 
	 * @param date
	 * @return String
	 */
	public String formatDate(String date) {
		String regex = "^(\\d{1,2})/(\\d{1,2})/(\\d{4})$";
		if (date.matches(regex)) {
			String[] str = date.split("/");
			return str[2] + "-" + str[1] + "-" + str[0];
		} else {
			return "";
		}
	}
	/**
	 * Ham kiem tra xem xau co dung dinh dang Date dd/MM/yyyy hay khong
	 * @param s
	 * @return boolean
	 */
	public static  boolean notVaildDate(String s){
		if(notVaild(s)){
			return true;
		} else {
			try {
			    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			    formatter.setLenient(false);
			    formatter.parse(s);
			} catch (ParseException e) { 
			    return true;
			}
		}
		return false;
	}
}