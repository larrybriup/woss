package com.eagle.test;

import java.awt.List;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.LinkedList;

//时间戳之间的转换
public class TimestampTest {
	public static void main(String[] args) {
		long t1=System.currentTimeMillis();
		System.out.println("hehe");
		System.out.println("hehe");
		System.out.println("hehe");
		long t2=System.currentTimeMillis();
		
		System.out.println("t1="+t1+"\nt2="+t2);
		long t3=t2-t1;
		System.out.println("t2-t1="+t3);
		
		
		Timestamp ts1= new Timestamp(t1);
		System.out.println(ts1);
		
		System.out.println(ts1.getTime());
		
		Timestamp ts2= new Timestamp(t2);
		System.out.println(ts2.getHours());
		System.out.println(ts2.getDate());
//		Timestamp ts3=ts2-ts1;
		
		long t4=0;//79888;
		Timestamp ts4=new Timestamp(t4);
		System.out.println(ts4);
		System.out.println(ts4.getMinutes()+"fen"+ts4.getSeconds());
		System.out.println(t4/60000+"fen"+t4%60000);
		
		System.out.println("========================");
		//Calendar
		Calendar c= Calendar.getInstance();
		System.out.println(c.getFirstDayOfWeek());
		System.out.println(c.getTime());
		System.out.println();
		
		Object o="o";
		LinkedList<Object> l= new LinkedList<Object>();
//		l.add(o);
		System.out.println(l.size());
	}
}
