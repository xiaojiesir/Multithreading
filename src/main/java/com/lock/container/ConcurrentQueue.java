package com.lock.container;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentQueue {

	public static void main(String[] args) {

		Queue<String> strs = new ConcurrentLinkedDeque<>();
		for (int i = 0; i < 10; i++) {
			strs.offer("a" + i);

		}
		System.out.println(strs);
		System.out.println(strs.size());
		System.out.println(strs.poll());//get +remove
		System.out.println(strs.size());
		System.out.println(strs.peek());//get
		System.out.println(strs.size());
	}
}
