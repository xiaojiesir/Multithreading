package com.lock.ThreadLocal;

import java.util.concurrent.TimeUnit;
/**
 * 
* @ClassName: ThreadLocalTest
* @Description: ThreadLocal是使用空间换时间，synchronized使用时间换空间
*  mybatis中的sqlsession实现线程安全，底层就是使用了ThreadLocal，
*  切换数据源也是用了ThreadLocal
* @author fanghaijie
* @date 2019年8月18日
*
 */
public class ThreadLocalTest {

	//volatile static Person p = new Person();
	
	static ThreadLocal<Person> tl = new ThreadLocal<>();

	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//System.out.println(p.name);
				System.out.println(tl.get());
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//p.name = "lisi";
				tl.set(new Person());
			}
		}).start();
	}
}

class Person {
	String name = "zhangsan";
}
