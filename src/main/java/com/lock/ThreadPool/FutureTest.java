package com.lock.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//Callable和runnable的区别是Callable有返回值 可以抛出异常，runnable没有返回值，无法抛出异常
		FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
			public Integer call()  {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 1000;
			}
		});
		new Thread(task).start();
		
		System.out.println(task.get());//阻塞  等线程完成才返回
		
		ExecutorService service = Executors.newFixedThreadPool(5);
		Future<Integer> f = service.submit(new Callable<Integer>() {
			public Integer call()  {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 1;
			}
		});
		
		//System.out.println(f.get());
		System.out.println(f.isDone());//任务是否执行完
		System.out.println(f.get());
		System.out.println(f.isDone());
		
		
	}
}
