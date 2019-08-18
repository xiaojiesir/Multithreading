package com.lock.class1;

import java.util.concurrent.TimeUnit;

import com.lock.class1.SynchronizedClass.Task1;
import com.lock.class1.SynchronizedClass.Task2;

/**
 * 
* @ClassName: SynchronizedObject1
* @Description: 锁定某对象o，如果o的属性发生改变，不影响锁的使用
* 但是如果o变成其他对象，则锁定的对象发生改变
* 因为锁住的是堆内存的对象，不是栈的引用，所有应该避免将锁定对象的引用变成另外的对象
* 不要用字符串当作锁的对象
* @author fanghaijie
* @date 2019年8月18日
*
 */
public class SynchronizedObject1 {

	private Object o = new  Object();
	void m() {
		
		synchronized (o) {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			}
			
		}

	}

	

	static class Task1 implements Runnable {

		private SynchronizedObject1 synchronizedMethod;

		public Task1(SynchronizedObject1 synchronizedMethod) {
			this.synchronizedMethod = synchronizedMethod;
		}

		@Override
		public void run() {
			synchronizedMethod.m();
		}
	}

	

	public static void main(String[] args) throws InterruptedException {
		SynchronizedObject1 syn = new SynchronizedObject1();
		new Thread(new Task1(syn)).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread(new Task1(syn)).start();
		syn.o = new Object();//锁对象发生改变，第二个线程会执行，注释掉这行，第二个线程不会执行
		// 主线程阻塞，防止jvm提早退出
		Thread.sleep(150000);
	}

}
