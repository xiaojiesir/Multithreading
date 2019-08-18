package com.lock.valitate;

import java.util.ArrayList;
import java.util.List;
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
 * @author fanghaijie
 * @date 2019年8月18日
 *
 */
public class MyContainer2 {

	//添加volatile 可见性
	volatile List lists = new ArrayList<>();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {
		MyContainer2 myContainer1 = new MyContainer2();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					myContainer1.add(new Object());
					System.out.println("add"+i);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		thread.start();

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					if(myContainer1.size()==5){
						break;
					}
				}
				System.out.println("监测到超过五个了");
			}
		});
		thread2.start();
	}
}
