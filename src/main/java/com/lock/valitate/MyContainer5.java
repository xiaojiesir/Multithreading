package com.lock.valitate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import sun.util.locale.provider.TimeZoneNameUtility;

/**
 * 
 * @ClassName: MyContainer1
 * @Description: 实现一个容器，提供两个方法add,size,
 *               写两个线程，线程一添加10个元素到容器中，线程二监控元素的个数，当个数到5的时候，线程2给出提示并结束
 * 
 *               以下代码虽然能完成需求，但是第二个线程的死循环是很浪费cpu的，而且打印出来的时候size可能已经是6了。
 *               这里使用wait和notify做到，wait会释放锁，而notify不会释放锁，但是使用这个方法必须保证二线程先执行
 *               也就是让二线程先监听
 * 
 *               执行以下代码，会发现输出结果不是等到size=5时t2退出，而是t1结束是t2才会收到通知退出
 *               因为notify不会释放锁，导致t2拿不到锁不能执行，所以可以在notify后面加wait释放锁，线程二执行完以后释放锁，在notify
 *               执行线程一，这样的步骤显得很繁琐 所以使用await和countdown方法代替wait和notify
 *               countdown不涉及锁定，当count的值为0时当前线程继续进行
 *               当不涉及同步，只是涉及线程通信的时候，用synchronized+wait/notify显得太重了
 *               可以考虑用countdownlatch/cyclicbarrier/semaphore
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class MyContainer5 {

	// 添加volatile 可见性
	volatile List lists = new ArrayList<>();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {
		MyContainer5 myContainer1 = new MyContainer5();

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("t2启动");

				if (myContainer1.size() != 5) {

					try {
						countDownLatch.await();//不需要锁
						//也可以指定时间
						countDownLatch.await(5000, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				System.out.println("监测到size为五个了");

			}
		});
		thread2.start();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					myContainer1.add(new Object());
					System.out.println("add" + i);
					if (myContainer1.size() == 5) {

						countDownLatch.countDown();
					}
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
		});
		thread.start();

	}
}
