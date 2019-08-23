package com.lock.container;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedTransferQueue;

public class TransferQueueTest {

	public static void main(String[] args) throws InterruptedException {

		LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
		//先启动消费者，不会阻塞
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
		strs.transfer("aaa");
		//后启动消费者，会阻塞
		/*new Thread(new Runnable() {

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
		}).start();*/
	}
}
