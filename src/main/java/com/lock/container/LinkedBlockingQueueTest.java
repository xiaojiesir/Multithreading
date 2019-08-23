package com.lock.container;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueTest {

	public static void main(String[] args) {
		BlockingQueue<String> strs = new LinkedBlockingQueue<>();
		Random r = new Random();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 100; i++) {

					try {
						strs.put("a" + i);
						TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, "p1").start();

		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (true) {
						try {
							System.out.println(Thread.currentThread().getName()+"take-"+strs.take());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}, "c" + i).start();

		}
	}
}
