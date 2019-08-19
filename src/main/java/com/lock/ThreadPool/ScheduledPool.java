package com.lock.ThreadPool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: ScheduledPool
 * @Description: (API介绍)public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
 *               
 *               创建一个线程池，可以调度命令在给定的延迟之后运行，或定期执行。 
 *               
 *              ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                       long initialDelay,
                                       long period,
                                       TimeUnit unit)
 *               command- 要执行的任务 
 *               initialDelay - 延迟第一次执行的时间 
 *               period - 连续执行之间的时期 
 *               unit - initialDelay和period参数的时间单位
 * @author fanghaijie
 * @date 2019年8月19日
 *
 */
public class ScheduledPool {

	public static void main(String[] args) {
		// ExecutorService service = Executors.newScheduledThreadPool(4);
		ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
		service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			}
		}, 0, 500, TimeUnit.MILLISECONDS);//
	}
}
