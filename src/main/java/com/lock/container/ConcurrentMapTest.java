package com.lock.container;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

public class ConcurrentMapTest {
	public static void main(String[] args) {
		//Map<String,String> map = new ConcurrentHashMap<>();
		//Map<String,String> map = new ConcurrentSkipListMap<>();
		 Map<String,String> map = new HashMap<>();
		 // Map<String, String> map = new Hashtable<>();

		Random r = new Random();
		Thread[] ths = new Thread[100];
		CountDownLatch lath = new CountDownLatch(ths.length);

		long start = System.currentTimeMillis();
		for (int i = 0; i < ths.length; i++) {
			ths[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int j = 0; j < 10000; j++) {
						map.put("a" + r.nextInt(10000), "a" + r.nextInt(10000));
						lath.countDown();
					}
				}
			});
		}
		List<Thread> list = Arrays.asList(ths);
		for (Thread thread : list) {
			thread.start();
		}
		try {
			lath.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
