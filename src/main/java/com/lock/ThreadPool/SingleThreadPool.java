package com.lock.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @ClassName: SingleThreadPool
 * @Description: (API介绍)public static ExecutorService newSingleThreadExecutor()
 *               创建一个使用从无界队列运行的单个工作线程的执行程序。
 *               （请注意，如果这个单个线程由于在关闭之前的执行过程中发生故障而终止，则如果需要执行后续任务，则新的线程将占用它。）任务保证顺序执行，并且不超过一个任务将被激活在任何给定的时间。
 *               与其他等效的newFixedThreadPool(1) newFixedThreadPool(1)
 *               ，返回的执行器保证不被重新配置以使用额外的线程。
 * @author fanghaijie
 * @date 2019年8月19日
 *
 */
public class SingleThreadPool {

	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++) {
			final int j = i;
			service.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(j + " " + Thread.currentThread().getName());
				}
			});
		}
	}
}
