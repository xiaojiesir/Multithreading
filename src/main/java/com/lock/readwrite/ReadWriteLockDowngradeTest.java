package com.lock.readwrite;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
* @ClassName: ReadWriteLockDowngradeTest
* @Description: 锁降级（锁降级指当前线程把持住写锁再获取到读锁，随后释放先前拥有的写锁的过程。）
* 	原因：释放写锁以后，如果下个线程先得到写锁，那读锁就会堵塞，无法感知到数据的变化，所以需要在释放写锁之前先拿到读锁，堵塞其他线程的写锁
* @author fanghaijie
* @date 2019年8月17日
*
 */
public class ReadWriteLockDowngradeTest {

    private volatile boolean cacheValid = false;

    private int currentValue = 0;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private Lock readLock = lock.readLock();

    private Lock writeLock = lock.writeLock();

    public static void main(String[] args) {
    	ReadWriteLockDowngradeTest readWriteLockDowngradeTest = new ReadWriteLockDowngradeTest();
    	try {
			readWriteLockDowngradeTest.testLockDowngrading();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void testLockDowngrading() throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 100, TimeUnit.SECONDS, new ArrayBlockingQueue
                <Runnable>(10));
        for (int i = 0; i < 2; i ++){
            int finalI = i;
            executor.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setName("thread-" + finalI);
                    try {
                        start.await();
                        TimeUnit.SECONDS.sleep(finalI);
                        System.out.println("after sleep " + finalI + " seconds, excute " + Thread.currentThread().getName());
                        cacheValid = false;
                        processCachedData(finalI);
                        // processCachedDataDownGrading(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        end.countDown();
                    }

                }
            }));
        }
        start.countDown();
        end.await();

    }

    /**
     * 锁降级过程
     * @param num
     */
    private void processCachedDataDownGrading(int num){
        readLock.lock();
        if(!cacheValid){
            //必须先释放写锁
            readLock.unlock();
            writeLock.lock();
            try{
                //在更新数据之前做二次检查
                if(!cacheValid){
                    System.out.println(Thread.currentThread().getName() + " has updated!");
                    //将数据更新为和线程值相同，以便验证数据
                    currentValue = num;
                    cacheValid = true;
                    readLock.lock();
                }
            }finally {
                writeLock.unlock();
            }

        }

        try{
            //模拟5秒的处理时间，并打印出当前值，在这个过程中cacheValid可能被其他线程修改，锁降级保证其他线程写锁被阻塞，数据不被改变
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + ": " +  currentValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(lock.getReadHoldCount() > 0){
                readLock.unlock();
            }
        }
    }

    /**
     * 无锁降级的过程
     * @param num
     */
    private void processCachedData(int num){
        readLock.lock();
        if(!cacheValid){
            readLock.unlock();
            writeLock.lock();
            try{
                if(!cacheValid){
                    System.out.println(Thread.currentThread().getName() + " has updated!");
                    currentValue = num;
                    cacheValid = true;
                }
            }finally {
                writeLock.unlock();
            }
        }
        try{
            //模拟5秒的处理时间，并打印出当前值，在这个过程中cacheValid可能被其他线程修改，无锁降级过程，其他线程此时可能获取写锁，并更改书数据，导致后面的数据错误
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + ": " +  currentValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(lock.getReadHoldCount() > 0){
                readLock.unlock();
            }
        }
    }

}
