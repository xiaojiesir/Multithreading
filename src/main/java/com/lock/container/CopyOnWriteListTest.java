package com.lock.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @ClassName: CopyOnWriteListTest
 * @Description: 写诗复制容器，多线程下，写时效率低，读时效率高，适合写少读多环境
 * @author fanghaijie
 * @date 2019年8月23日
 *
 */
public class CopyOnWriteListTest {

	public static void main(String[] args) {
		List<String> lists = 
				//new ArrayList<String>();
				new Vector<>();
		// new CopyOnWriteArrayList<>();
		Random r = new Random();
		Thread[] ths = new Thread[100];
		for (int i = 0; i < ths.length; i++) {
			ths[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int j = 0; j < 1000; j++) {
						lists.add("a" + r.nextInt(1000));
					}
				}
			});
		}

		runAndComputerTime(ths);
	}

	private static void runAndComputerTime(Thread[] ths) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		List<Thread> lists = Arrays.asList(ths);
		for (Thread thread : lists) {
			thread.start();
		}
		for (Thread thread : lists) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
