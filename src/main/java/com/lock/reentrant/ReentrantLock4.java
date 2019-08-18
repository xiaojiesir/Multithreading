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
 *               
 *               ReentrantLock默认不公平锁，但是可以指定为公平锁
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class ReentrantLock4 {

	Lock lock = new ReentrantLock(true);

	void m1() {
		System.out.println(lock);
		for (int i = 0; i < 100; i++) {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName()+"获得锁");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
			
		}
	}

	

	public static void main(String[] args) {
		ReentrantLock4 t = new ReentrantLock4();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				t.m1();
			}
		}).start();
		
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				t.m1();
			}
		}).start();
	}

}
