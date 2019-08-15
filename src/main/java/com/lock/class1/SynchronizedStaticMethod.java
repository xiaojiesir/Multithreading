package com.lock.class1;

/**
 * synchronized修饰静态方法
 * 
 * @author onlyone
 * 修饰静态方法时，和修饰类一样，给这个类加锁，类的所有实例化出来的对象用的是同一把锁。
 * 所以，线程A访问静态同步方法时，其他线程可以访问非静态同步方法和非同步方法，不可以访问静态同步方法。
 */
public class SynchronizedStaticMethod {

    public synchronized static void m1() {
        System.out.println("m1方法获得锁");
        try {
            Thread.sleep(1200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m1方法释放锁");
    }

    public synchronized static void m2() {
        System.out.println("m2方法获得锁");
        try {
            Thread.sleep(1200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m2方法释放锁");
    }

    static class Task1 implements Runnable {

        @Override
        public void run() {
            m1();
        }
    }

    static class Task2 implements Runnable {

        @Override
        public void run() {
            m2();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Task1()).start();
        new Thread(new Task2()).start();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }

}
