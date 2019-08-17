package com.lock.valitate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 
* @ClassName: VolatileTest2
* @Description:验证volatile只保证可见性，不保证原子性，
* 下面代码理论上应该是10000，但实际打印出来少于10000，因为A线程读取count为100，B线程读取也为100，A线程count+1，这样堆内存的count
* 为101，但是B线程已经读取count100，加完以后，堆内存又为101，CAS的ABA问题，可用synchronized对m加锁保证原子性
* @author fanghaijie
* @date 2019年8月17日
*
 */
public class VolatileTest2 {

	volatile int count = 0;

	synchronized void m() {
		for (int i = 0; i < 1000; i++) {
			count++;
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
