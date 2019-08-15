package com.lock.class1;

import com.lock.class1.SynchronizedClass.Task1;
import com.lock.class1.SynchronizedClass.Task2;

/**
 * 
* @ClassName: SynchronizedThis
* @Description: Synchronized修饰this，当前对象，这里的this指的是执行这段代码的对象，synchronized得到的锁就是this这个对象的锁。
* 修饰this和修饰非静态方法一样。线程A访问对象A的带有同步代码块的方法A时，其他线程可以访问该对象的非同步方法和其他对象的所有方法。
* @author fanghaijie
* @date 2019年8月15日
*
 */
public class SynchronizedThis {

	public void m1() {
		System.out.println("m1方法");
		synchronized (this) {
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

		synchronized (this) {
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

		private SynchronizedThis synchronizedMethod;

		public Task1(SynchronizedThis synchronizedMethod) {
			this.synchronizedMethod = synchronizedMethod;
		}

		@Override
		public void run() {
			System.out.println(synchronizedMethod);
			synchronizedMethod.m1();
		}
	}

	static class Task2 implements Runnable {

		private SynchronizedThis synchronizedMethod;

		public Task2(SynchronizedThis synchronizedMethod) {
			this.synchronizedMethod = synchronizedMethod;
		}

		@Override
		public void run() {
			System.out.println(synchronizedMethod);
			synchronizedMethod.m2();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SynchronizedThis syn = new SynchronizedThis();
		SynchronizedThis syn1 = new SynchronizedThis();
		new Thread(new Task1(syn)).start();
		new Thread(new Task2(syn1)).start();

		// 主线程阻塞，防止jvm提早退出
		Thread.sleep(150000);
	}

}
