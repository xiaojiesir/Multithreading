package com.lock.ThreadPool;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @ClassName: MyContainer1
 * @Description: 一个固定容量的同步容器，有put，get方法以及getcount方法 能够支持两个生产者线程和10个消费者线程的阻塞使用
 *               使用wait和notify/notifyall实现
 *               
 *               使用lock和condition来实现 condition的方式更加精确的指定哪些线程唤醒
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class MyContainer2<T> {

	final private LinkedList<T> linkedList = new LinkedList<>();
	final private int MAX = 10;
	private int count = 0;

	private Lock lock = new ReentrantLock();
	private Condition producer = lock.newCondition();
	private Condition consumer = lock.newCondition();

	void put(T t) {
		try {
			lock.lock();
			while (linkedList.size() == MAX) { // 这里需要注意的是用while
												// 因为唤醒以后需要再次判断是否满了
				producer.await();
				linkedList.add(t);
				++count;

				consumer.signalAll();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	T get() {
		T t = null;
		try {
			lock.lock();
			while (linkedList.size() == 0) {

				consumer.await();
			}
			t = linkedList.removeFirst();
			count--;
			producer.signalAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return t;
	}

	public static void main(String[] args) {
		MyContainer1<String> c = new MyContainer1<>();

		// 消费者线程
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						System.out.println(c.get());
					}
				}
			}, "c" + i).start();
		}

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 生产者线程
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 25; j++) {
						c.put(Thread.currentThread().getName() + " " + j);
					}
				}
			}, "p" + i).start();
		}

	}
}
