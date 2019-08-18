package com.lock.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
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
public class TicketSeller3 {

	static List<String> tickets = new ArrayList<String>();
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
						synchronized (tickets) {
							if (tickets.size() <= 0) {
								break;
							}
							try {
								TimeUnit.MILLISECONDS.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("售出--" + tickets.remove(0));
						}

					}
				}
			}).start();
		}
	}

}
