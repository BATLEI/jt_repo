package haha;

import java.util.Scanner;

public class TestDate {
	/**
	 * 输入一个xxxx-xx-xx日期，输出
	 * 上一年年末
	 * 上月月末
	 * 本月月末
	 * 上一天
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("请输入日期：");
		String date = new Scanner(System.in).nextLine();
		
		int year = Integer.valueOf(date.substring(0, 4));
		String date1 = String.valueOf(year-1)+"-12-31";
		System.out.println("上年年末：" + date1);
		
		Integer month = Integer.valueOf(date.substring(5, 7));
		if (month-1 == 0) {
			System.out.println("上月月末：" + date1);
		}else if(month-1 == 4||month-1 == 6||month-1 == 9||month-1 == 11) {
			
		}
		System.out.println("上月月末：");
		
	}
}
