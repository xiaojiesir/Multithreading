package com.lock.example;

import java.util.ArrayList;
import java.util.List;

public class TicketSeller1 {

	static List<String> tickets = new ArrayList<String>();
	static {
		for (int i = 0; i < 10000; i++) {
			tickets.add("票号："+i);
		}
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(tickets.size()>0){
						System.err.println("售出：" +tickets.remove(0));
					}
				}
			}).start();
		}
	}





}
