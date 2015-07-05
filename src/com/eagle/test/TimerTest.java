package com.eagle.test;

import java.util.Timer;
import java.util.TimerTask;


public class TimerTest {
	private  int i=0;
	TimerTest  increment() {
		i++;
		return this;
	}
	 void  prt(String s){
		System.out.println("Hello!"+s+i);
	}
	public static void main(String[] args) {
		final TimerTest test= new TimerTest();
		
		Timer t = new Timer();
		TimerTask tt=new  TimerTask() {
			
			@Override
			public void run() {
				test.increment().prt("choda ");;
				
			}
		};
//		t.scheduleAtFixedRate(tt, 0, 10000);
		t.schedule(tt, 0,3000);
	}
	
}
