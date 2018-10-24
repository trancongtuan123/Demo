package handler;

import java.util.Scanner;

import commom.StringProcess;
import until.Bird;

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
public class AddBirdsHandler {
	public static Bird bird() {
		Bird emp;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		emp = new Bird();
		System.out.println("input name");
		String name = sc.nextLine();
		while (StringProcess.notVaild(name)) {
			System.out.println("please input name again");
			name = sc.nextLine();
		}
		emp.setName(name);
		System.out.println("input color");
		String color = sc.nextLine();
		while (StringProcess.notVaild(color)) {
			System.out.println("please input color again");
			color = sc.nextLine();
		}
		emp.setColor(color);
		System.out.println("input height");
		String height = sc.nextLine();
		while (StringProcess.notVaildNumber(height)) {
			System.out.println("height is integer please input agian....");
			height = sc.nextLine();
		}
		emp.setHeight(height);
		System.out.println("input weight");
		String weight = sc.nextLine();
		while (StringProcess.notVaildNumber(weight)) {
			System.out.println("weight is integer please input agian...");
			weight = sc.nextLine();
		}
		emp.setWeight(weight);
		return emp;
	}
}
