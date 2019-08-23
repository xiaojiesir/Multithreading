package com.lock.container;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueTest {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			strs.add("a"+i);
		}
		//strs.put("aaa");//满了就会等待，程序阻塞
		//strs.add("aaa");//报异常
		strs.offer("aaa");//不报异常，但是不加入
		//strs.offer("aaa",1,TimeUnit.SECONDS);//不报异常，但是不加入
		System.out.println(strs);
	}
}
