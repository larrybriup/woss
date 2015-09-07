package com.eagle.test;

public class SwitchTest {

	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8 };
		for (int i : a) {

			switch (i) {
				case 1:
					System.out.println(1);
					break;
				case 3:
					System.out.println(3);
					break;
				case 5:
					System.out.println(5);
				case 6:
					System.out.println(6);
					break;
				case 8:
					System.out.println(8);
					break;
				default:
					System.out.println("heh");
					break;
			}
		}
	}
}
