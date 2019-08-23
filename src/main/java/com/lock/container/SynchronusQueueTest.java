package com.lock.container;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronusQueueTest {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<String> strs = new SynchronousQueue<>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println(strs.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		strs.put("aaa");//阻塞，等待消费者消费
		//strs.add("aaa");//报错
		System.out.println(strs.size());

	}
}
