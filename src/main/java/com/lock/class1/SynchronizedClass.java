package com.lock.class1;


/**
 * synchronized (类.class)
 * 
 * @author onlyone
 * 当synchronized修饰一个类的时候，是给这个类加锁，类的所有实例化出来的对象用的是同一把锁。
 */
public class SynchronizedClass {

    public void m1() {
        synchronized (SynchronizedClass.class) {
            System.out.println("m1方法获得锁");
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("m1方法释放锁");
        }
    }

    public void m2() {
        synchronized (SynchronizedClass.class) {
            System.out.println("m2方法获得锁");
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("m2方法释放锁");

        }
    }

    static class Task1 implements Runnable {

        private SynchronizedClass synchronizedClass;

        public Task1(SynchronizedClass synchronizedClass){
            this.synchronizedClass = synchronizedClass;
        }

        @Override
        public void run() {
            synchronizedClass.m1();
        }
    }

    static class Task2 implements Runnable {

        private SynchronizedClass synchronizedClass;

        public Task2(SynchronizedClass synchronizedClass){
            this.synchronizedClass = synchronizedClass;
        }

        @Override
        public void run() {
            synchronizedClass.m2();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedClass syn = new SynchronizedClass();
        SynchronizedClass syn1 = new SynchronizedClass();
        new Thread(new Task1(syn)).start();
        new Thread(new Task2(syn1)).start();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }

}
