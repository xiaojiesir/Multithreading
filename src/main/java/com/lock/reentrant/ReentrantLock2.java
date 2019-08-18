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
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class ReentrantLock2 {

	Lock lock = new ReentrantLock();

	void m1() {
		try {
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

	/**
	 * 
	 * @Title: m2 @Description: 使用trylock进行尝试锁定，不管锁定与否，方法都将继续执行
	 * 可以根据trylock的返回值来判定释放锁定
	 * 也可以指定trylock的时间，由于trylock(time)抛出异常，所以要注意unlock的处理，必须放到finally中 @param
	 * 参数 @return void 返回类型 @throws
	 */
	void m2() {

		/*
		 * boolean locked =lock.tryLock(); 
		 * System.out.println("m2..."+locked);
		 * if(locked){ lock.unlock(); }
		 */
		boolean locked = false;
		try {
			locked = lock.tryLock(2, TimeUnit.SECONDS);
			System.out.println("m2..." + locked);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (locked) {
				lock.unlock();
			}
		}

	}

	public static void main(String[] args) {
		ReentrantLock3 t = new ReentrantLock3();
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
