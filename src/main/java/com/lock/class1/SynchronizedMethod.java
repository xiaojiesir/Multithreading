package com.lock.class1;

import com.lock.class1.SynchronizedClass.Task1;
import com.lock.class1.SynchronizedClass.Task2;

/**
 * 
* @ClassName: SynchronizedMethod
* @Description: 修饰非静态方法时，线程A访问对象A的非静态同步方法A时，其他线程可以访问该对象的非同步方法以及其他对象的任何方法
* @author fanghaijie
* @date 2019年8月15日
*
 */
public class SynchronizedMethod {

    public synchronized void m1() {
        System.out.println("m1方法获得锁");
        try {
            Thread.sleep(1200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m1方法释放锁");
    }

    public synchronized void m2() {
        System.out.println("m2方法获得锁");
        try {
            Thread.sleep(1200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m2方法释放锁");
    }

    static class Task1 implements Runnable {

    	private SynchronizedMethod synchronizedMethod;

        public Task1(SynchronizedMethod synchronizedMethod){
            this.synchronizedMethod = synchronizedMethod;
        }

        @Override
        public void run() {
        	System.out.println(synchronizedMethod);
        	synchronizedMethod.m1();
        }
    }

    static class Task2 implements Runnable {

        private SynchronizedMethod synchronizedMethod;

        public Task2(SynchronizedMethod synchronizedMethod){
            this.synchronizedMethod = synchronizedMethod;
        }

        @Override
        public void run() {
        	System.out.println(synchronizedMethod);
        	synchronizedMethod.m2();
        }
    }
    public static void main(String[] args) throws InterruptedException {
    	SynchronizedMethod syn = new SynchronizedMethod();
    	SynchronizedMethod syn1 = new SynchronizedMethod();
        new Thread(new Task1(syn)).start();
        new Thread(new Task2(syn1)).start();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }

}
