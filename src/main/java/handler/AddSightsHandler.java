package handler;

import java.util.Scanner;

import commom.StringProcess;
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
public class AddSightsHandler {
	public static Sight sight() {
		Sight emp;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		emp = new Sight();
		System.out.println("input name bird");
		String name = sc.nextLine();
		emp.setName(name);
		System.out.println("input local");
		String local = sc.nextLine();
		while (StringProcess.notVaild(local)) {
			System.out.println("please input local ");
			local = sc.nextLine();
		}
		emp.setLocal(local);
		System.out.println("input time");
		String time = sc.nextLine();
		while (StringProcess.notVaildDate(time)) {
			System.out.println("please input dd/mm/yyyy");
			time = sc.nextLine();
		}
		emp.setTime(time);
		return emp;
	}
}
