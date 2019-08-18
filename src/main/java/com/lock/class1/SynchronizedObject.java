package com.lock.class1;

import com.lock.class1.SynchronizedClass.Task1;
import com.lock.class1.SynchronizedClass.Task2;

/**
 * 
* @ClassName: SynchronizedObject
* @Description: Synchronized(对象)
* @author fanghaijie
* @date 2019年8月18日
*
 */
public class SynchronizedObject {

	private Object o = new  Object();
	private Object o1 = new  Object();
	public void m1() {
		System.out.println("m1方法");
		System.out.println(o);
		synchronized (o) {
			System.out.println("m1方法获得锁");
			try {
				Thread.sleep(1200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("m1方法释放锁");
		}

	}

	public synchronized void m2() {
		System.out.println("m2方法");
		System.out.println(o);
		synchronized (o) {
			System.out.println("m2方法获得锁");
			try {
				Thread.sleep(1200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("m2方法释放锁");
		}

	}

	static class Task1 implements Runnable {

		private SynchronizedObject synchronizedMethod;

		public Task1(SynchronizedObject synchronizedMethod) {
			this.synchronizedMethod = synchronizedMethod;
		}

		@Override
		public void run() {
			System.out.println(synchronizedMethod);
			synchronizedMethod.m1();
		}
	}

	static class Task2 implements Runnable {

		private SynchronizedObject synchronizedMethod;

		public Task2(SynchronizedObject synchronizedMethod) {
			this.synchronizedMethod = synchronizedMethod;
		}

		@Override
		public void run() {
			System.out.println(synchronizedMethod);
			synchronizedMethod.m2();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SynchronizedObject syn = new SynchronizedObject();
		SynchronizedObject syn1 = new SynchronizedObject();
		new Thread(new Task1(syn)).start();
		new Thread(new Task2(syn1)).start();

		// 主线程阻塞，防止jvm提早退出
		Thread.sleep(150000);
	}

}
