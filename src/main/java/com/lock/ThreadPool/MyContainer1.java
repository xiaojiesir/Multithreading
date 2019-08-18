package com.lock.ThreadPool;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: MyContainer1
 * @Description: 一个固定容量的同步容器，有put，get方法以及getcount方法 能够支持两个生产者线程和10个消费者线程的阻塞使用
 *               使用wait和notify/notifyall实现
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class MyContainer1<T> {

	final private LinkedList<T> linkedList = new LinkedList<>();
	final private int MAX = 10;
	private int count = 0;

	synchronized void put(T t) {
		while (linkedList.size() == MAX) { // 这里需要注意的是用while 因为唤醒以后需要再次判断是否满了
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		linkedList.add(t);
		++count;

		this.notifyAll();
	}

	synchronized T get() {
		T t = null;
		while (linkedList.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		t = linkedList.removeFirst();
		count--;
		this.notifyAll();
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
			},"c"+i).start();
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
						c.put(Thread.currentThread().getName()+" "+j);
					}
				}
			},"p"+i).start();
		}

	}
}
