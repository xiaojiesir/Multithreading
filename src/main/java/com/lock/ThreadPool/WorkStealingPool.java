package com.lock.ThreadPool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: WorkStealingPool
 * @Description: (API介绍)public static ExecutorService newWorkStealingPool() 使用所有
 *               available processors作为其目标并行级别创建一个工作窃取线程池。
 *               
 *               意思是主动会去吧线程拿过来执行，前面的线程池需要我们手动放进去，这个线程池主动拿线程执行，
 *               为什么是守护线程呢，因为需要不断运行，只要虚拟机不退出，就不会退出，有线程就拿过来执行，所以需要一直存在
 *               用dubeg可以查看线程是否是 daemon线程
 * @author fanghaijie
 * @date 2019年8月19日
 *
 */
public class WorkStealingPool {
	public static void main(String[] args) throws IOException {
		ExecutorService service = Executors.newWorkStealingPool();
		System.out.println(Runtime.getRuntime().availableProcessors());// 电脑几核
		service.execute(new R(1000));
		service.execute(new R(2000));
		service.execute(new R(2000));//dubeg： Daemon Thread [ForkJoinPool-1-worker-3] (Running)	
		service.execute(new R(2000));
		service.execute(new R(2000));
		// 产生的是精灵线程(守护线程，后台线程)，如果不加下面代码，主线程不阻塞的话，看不到输出
		System.in.read();
	}

	static class R implements Runnable {
		int time;

		R(int t) {
			this.time = t;

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				TimeUnit.MILLISECONDS.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(time + " " + Thread.currentThread().getName());
		}
	}

}
