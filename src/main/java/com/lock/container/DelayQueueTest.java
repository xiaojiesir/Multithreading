package com.lock.container;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {

	static class Mytask implements Delayed{

		long runningTime;
		Mytask(long rt){
			this.runningTime = rt;
		}
		@Override
		public int compareTo(Delayed o) {
			// TODO Auto-generated method stub
			if(this.getDelay(TimeUnit.MILLISECONDS)<o.getDelay(TimeUnit.MILLISECONDS)){
				return -1;
			}else if(this.getDelay(TimeUnit.MILLISECONDS)>o.getDelay(TimeUnit.MILLISECONDS)){
				return 1;
			}else{
				
				return 0;
			}
		}

		@Override
		public long getDelay(TimeUnit unit) {
			// TODO Auto-generated method stub
			return unit.convert(runningTime-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}
		@Override
		public String toString() {
			return "" + runningTime;
		}
		
		
		
	}
	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		
		Mytask t1 = new Mytask(start+1000);
		Mytask t2 = new Mytask(start+2000);
		Mytask t3 = new Mytask(start+1500);
		Mytask t4 = new Mytask(start+2500);
		Mytask t5 = new Mytask(start+1500);
		BlockingQueue<Mytask> strs = new DelayQueue<>();
		strs.put(t1);
		strs.put(t2);
		strs.put(t3);
		strs.put(t4);
		strs.put(t5);
		System.out.println(strs);
		for (int i = 0; i < 5; i++) {
			System.out.println(strs.take());
		}
	}
}
