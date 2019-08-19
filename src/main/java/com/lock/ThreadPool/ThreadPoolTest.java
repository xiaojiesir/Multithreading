package com.lock.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 6; i++) {
			service.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				}
			});
		}
		System.out.println(service);
		service.shutdown();//关闭，但是需要等线程结束才可以正式关闭
		//service.shutdownNow();//立刻关闭，不管线程是否结束
		System.out.println(service.isTerminated());//线程是否执行完 
		System.out.println(service.isShutdown());//是否关闭状态
		System.out.println(service);
		TimeUnit.SECONDS.sleep(5);
		
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());
		System.out.println(service);
		
	}
}
