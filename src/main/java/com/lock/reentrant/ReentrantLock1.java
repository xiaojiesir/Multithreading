package com.lock.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @ClassName: ReentrantLock1
 * @Description: 使用ReentrantLock可以完成synchronized的功能 需要注意的是，一定要手动释放锁
 *               synchronized遇到异常，jvm或自动释放锁，但是lock必须手动释放锁
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class ReentrantLock1 {

	Lock lock = new ReentrantLock();

	void m1() {
		try {
			System.out.println(lock);
			lock.lock();// synchronized(this)
			for (int i = 0; i < 5; i++) {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	void m2() {
		System.out.println(lock);
		lock.lock();// synchronized(this)

		System.out.println("m2");
		lock.unlock();
	}

	public static void main(String[] args) {
		ReentrantLock1 t = new ReentrantLock1();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				t.m1();
			}
		}).start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				t.m2();
			}
		}).start();
	}

}
