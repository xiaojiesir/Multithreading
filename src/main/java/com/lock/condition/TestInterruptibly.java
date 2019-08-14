package com.lock.condition;

/**
 * 
* @ClassName: TestInterruptibly
* @Description: 之前的synchronized加锁执行读写操作，会导致读操作一直等待，
* 		所以我们可以用ReentrantLock的lock机制，忽略中断锁和响应中断锁，
* 		也可以与Condition的配合使用，(具体看Testclass类)Condition为ReentrantLock锁的等待和释放提供控制逻辑。
* 		使用ReentrantLock加锁之后，可以通过它自身的Condition.await()方法释放该锁，
* 		线程在此等待Condition.signal()方法，然后继续执行下去。await方法需要放在while循环中，
* 		因此，在不同线程之间实现并发控制，还需要一个volatile的变量，boolean是原子性的变量，我们类中利用队列长度的比较进行。
* @author fanghaijie
* @date 2019年8月12日
*
 */
public class TestInterruptibly {

	public static void main(String[] args) {

		BufferInterruptibly buff = new BufferInterruptibly();

		final WriterInterruptibly writer = new WriterInterruptibly(buff);

		final ReaderInterruptibly reader = new ReaderInterruptibly(buff);

		writer.start();

		reader.start();

		new Thread(new Runnable() {

			@Override

			public void run() {

				long start = System.currentTimeMillis();

				for (;;) {

					// 等5秒钟去中断读

					if (System.currentTimeMillis()

							- start > 5000) {

						System.out.println("不等了，尝试中断");
						//因为reader这个线程执行的read方法有lock.lockInterruptibly()可以中断reader的等待锁
						reader.interrupt();

						break;

					}

				}

			}

		}).start();

	}

}