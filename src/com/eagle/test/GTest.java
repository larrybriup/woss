package com.eagle.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GTest {
	public static void main(String[] args) {
//		GatherImpl g= new GatherImpl();
//		System.out.println("wait...");
//		long b=System.currentTimeMillis();
//		Collection<BIDR> gather = g.gather();
//		System.out.println(gather);
//		long a=System.currentTimeMillis();
//		System.out.println(a-b);
////		System.out.println(g.getUserLog());
		
		Date date = new Date();
		SimpleDateFormat sf= new SimpleDateFormat();
		sf.applyPattern("yyyy年MM月dd日");
		String str = sf.format(date);
		System.out.println(str);
	}
}
