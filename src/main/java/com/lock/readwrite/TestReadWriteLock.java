package com.lock.readwrite;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 
* @ClassName: TestReadWriteLock
* @Description: TestReadWriteLock是ReadWriteLock接口的实现类–可重入的读写锁。
* 	读锁可以在没有写锁的时候被多个线程同时持有，写锁是独占的(排他的)。 每次只能有一个写线程，但是可以有多个线程并发地读数据。
* 	且    读-读能共存，读-写不能共存，写-写不能共存。非公平模式(默认)
* 
* @author fanghaijie
* @date 2019年8月16日
*
 */
public class TestReadWriteLock {

    public static void main(String[] args){
        ReadWriteLockDemo rwd = new ReadWriteLockDemo();
		//启动100个读线程
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    rwd.get();
                }
            }).start();
        }
        //写线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                rwd.set((int)(Math.random()*101));
            }
        },"Write").start();
    }
}

class ReadWriteLockDemo{
	//模拟共享资源--Number
    private int number = 0;
	// 实际实现类--ReentrantReadWriteLock，默认非公平模式
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();//可重入读写锁

    //读
    public void get(){
    	String name = Thread.currentThread().getName();

        System.out.println(name + " 尝试请求read锁,,,,,,,");
    	//使用读锁
        readWriteLock.readLock().lock();
        try {
        	 System.out.println(name + " 已拿到read锁，number : "+number);
        }finally {
        	System.out.println(name + " 释放read锁,,,,,,,");
            readWriteLock.readLock().unlock();
        }
    }
    //写
    public void set(int number){
    	 String name = Thread.currentThread().getName();

         System.out.println(name + " 尝试请求write锁,,,,,,,");
        readWriteLock.writeLock().lock();
        try {
        	System.out.println(name + " 已拿到write锁，开始处理业务");
            this.number = number;
            System.out.println("number : "+number);
        }finally {
        	System.out.println(name + " 释放write锁,,,,,,,");
            readWriteLock.writeLock().unlock();
        }
    }
}
