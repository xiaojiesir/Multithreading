package com.lock.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @ClassName: ReentrantLock1
 * @Description: 使用ReentrantLock可以完成synchronized的功能 需要注意的是，一定要手动释放锁
 *               synchronized遇到异常，jvm或自动释放锁，但是lock必须手动释放锁
 * 
 *               使用ReentrantLock可以进行尝试锁定trylock，这样无法锁定，或者在指定时间内无法锁定，
 *               线程可以决定是否继续等待
 * 
 *               ReentrantLock还可以使用lockInterruptibly方法，对线程interrupt方法做出响应，
 *               在一个线程等待锁的过程中，可以被打断
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class ReentrantLock3 {

	Lock lock = new ReentrantLock();

	void m1() {
		try {
			lock.lock();// synchronized(this)
			System.out.println("m1...start");
			TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
			System.out.println("m1...end");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	void m2() {

		try {
			// lock.lock();
			lock.lockInterruptibly();// 可以对interrupt方法做出响应
			System.out.println("m2...start");
			TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
			System.out.println("m2...end");

		} catch (Exception e) {
			System.out.println("interrupted");
		} finally {
			if (Thread.holdsLock(lock)) {// 判断是否锁定，否则报异常
				lock.unlock();
			}
		}

	}

	public static void main(String[] args) {
		ReentrantLock2 t = new ReentrantLock2();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				t.m1();
			}
		}).start();

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				t.m2();
			}
		});
		t2.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.interrupt();
	}

}
