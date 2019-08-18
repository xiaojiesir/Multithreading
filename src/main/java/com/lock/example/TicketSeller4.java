package com.lock.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: TicketSeller2
 * @Description: 虽然Vector是线程安全的，判断size>0的操作和 remove的操作都是原子性的，但是 连起来的步骤不是原子性的
 *               还是会出现问题,所以用synchronized来实现
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class TicketSeller4 {

	static Queue<String> tickets = new ConcurrentLinkedDeque<String>();
	static {
		for (int i = 0; i < 30; i++) {
			tickets.add("票号：" + i);
		}
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (true) {
						String s = tickets.poll();
						if (s == null) {
							break;
						} else {

							System.out.println("售出--" + s);
						}
					}
				}
			}).start();
		}
	}

}
