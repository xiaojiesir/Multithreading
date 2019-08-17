package com.lock.valitate;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: VolatileTest
 * @Description: volatile关键字，使一个变量在多个线程间可见
 *  AB线程都用到了一个变量，java默认是A线程中保留一份copy。这样如果B线程修改了该变量，A线程未必知道
 *  使用volatile关键字，会让所有线程都会读到变量的修改值。
 * 
 *  在下面的代码中，running是存在与堆内存的VolatileTest对象中
 *  当线程A开始运行的时候，会把running值从内存中读到A线程的工作区，在运行过程中直接使用这个copy，并不会每次都去
 *  读取堆内存，这样，当主线程修改running的值之后，A线程感知不到，所有不会停止运行。
 * 
 *  使用volatile，竟会强制所有的线程都去堆内存中读取running的值
 * 
 *  volatile并不能保证多个线程共同修改running变量时锁带来的不一致问题，也就是说volatile不能替代synchronized
 *  volatile只保证可见性，不保证原子性，synchronized既保证可见性，有保证原子性
 * @author fanghaijie
 * @date 2019年8月17日
 *
 */
public class VolatileTest {

	/* volatile */ boolean running = true;

	void m() {
		System.out.println("m.start");
		while (running) {
			//如果加了这段，cpu可能会有空去堆内存中读取running的值，随缘
			/*try {
				TimeUnit.SECONDS.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
		System.out.println("m.end");
	}

	public static void main(String[] args) {
		VolatileTest valitateTest = new VolatileTest();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				valitateTest.m();
			}
		}).start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		valitateTest.running = false;
	}
}
