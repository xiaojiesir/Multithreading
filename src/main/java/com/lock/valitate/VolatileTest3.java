package com.lock.valitate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
* @ClassName: VolatileTest3
* @Description:解决同样的问题可以用更高效的方法，使用Atomxxx类
* Atomxxx类本身方法就是原子性的，但是不能保证多个方法连续调用都是原子性的
* @author fanghaijie
* @date 2019年8月17日
*
 */
public class VolatileTest3 {

	//volatile int count = 0;
	AtomicInteger count = new  AtomicInteger();
	/*synchronized*/ void m() {
		for (int i = 0; i < 1000; i++) {
			count.incrementAndGet();//count++;
		}
	}

	public static void main(String[] args) {
		VolatileTest2 valitateTest = new VolatileTest2();
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			 Thread thread = new Thread(new Runnable() {  
	                @Override  
	                public void run() {  
	                	valitateTest.m();  
	                }  
	            });  
			 threads.add(thread);  
			 thread.start(); 
		}
		
		for (Thread t : threads) {  
            try {  
                t.join();  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        } 
		System.out.println(valitateTest.count);
	}
}
