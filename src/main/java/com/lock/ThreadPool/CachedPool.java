package com.lock.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: CachedPool
 * @Description: (API介绍)public static ExecutorService newCachedThreadPool()
 *               创建一个根据需要创建新线程的线程池，但在可用时将重新使用以前构造的线程。 这些池通常会提高执行许多短暂异步任务的程序的性能。
 *               调用execute将重用以前构造的线程（如果可用）。 如果没有可用的线程，将创建一个新的线程并将其添加到该池中。
 *               未使用六十秒的线程将被终止并从缓存中删除。 因此，长时间保持闲置的池将不会消耗任何资源。
 * @author fanghaijie
 * @date 2019年8月19日
 *
 */
public class CachedPool {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		System.out.println(service);
		for (int i = 0; i < 2; i++) {
			service.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
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
		TimeUnit.SECONDS.sleep(80);
		System.out.println(service);

	}
}
