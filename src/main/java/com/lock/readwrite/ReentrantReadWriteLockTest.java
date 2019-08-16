package com.lock.readwrite;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * 
 * @author onlyone
 */
public class ReentrantReadWriteLockTest {

    public static void main(String[] args) throws InterruptedException {

    	//CountDownLatch允许一个或多个线程一直等待直到其他线程执行完毕才开始执行。
        CountDownLatch countDownLatch = new CountDownLatch(2);//实例化一个倒计数器，count指定计数个数，这里就是2
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        // 启动20个读任务
        for (int i = 1; i <= 2; i++) {
            new Thread(new ReadTask(countDownLatch, lock)).start();
        }

        // 启动20个写任务
        for (int i = 1; i <= 2; i++) {
            new Thread(new WriteTask(countDownLatch, lock)).start();
        }

        //countDownLatch指定了2 所以要执行两次  才可以运行上面的线程，如果去掉一个 上面的线程会一直等待，除非线程被中断或超出了指定的等待时间。
        //对应ReadTask类和WriteTask类 countDownLatch.await();
        //即main方法中的countDownLatch.countDown();方法执行两次。ReadTask类和WriteTask类才能继续下去。
        //可以看下CountDownLatchTest.java类
        countDownLatch.countDown();
        countDownLatch.countDown();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }

}
