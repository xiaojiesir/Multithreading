package com.lock.condition;

import java.util.concurrent.locks.ReentrantLock;

public class BufferInterruptibly {

	private ReentrantLock lock = new ReentrantLock();

	public void write() {

		lock.lock();
		try {

			long startTime = System.currentTimeMillis();

			System.out.println("开始往这个buff写入数据…");

			for (;;)// 模拟要处理很长时间

			{

				if (System.currentTimeMillis()

						- startTime > Integer.MAX_VALUE)
					break;

			}

			System.out.println("终于写完了");

		} finally {
			lock.unlock();
		}

	}

	public void read() throws InterruptedException {
		//当通过lock.lockInterruptibly()这个方法去获取锁时，如果其他线程正在等待获取锁，则这个线程能够响应中断，
		//即中断线程的等待状态。也就使说，当两个线程同时通过lock.lockInterruptibly()想获取某个锁时，
		//假若此时线程A获取到了锁，而线程B只有等待，那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程。
		lock.lockInterruptibly();
		try {

			System.out.println("从这个buff读数据");

		} finally {

			lock.unlock();
		}

	}

}
