package com.lock.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ParallelComputer {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		long start = System.currentTimeMillis();
		List<Integer> results = getPrime(1, 200000);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		final int cpuCoreNum = 8;
		ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);

		Mytask t1 = new Mytask(1, 80000);
		Mytask t2 = new Mytask(80001, 130000);
		Mytask t3 = new Mytask(130001, 170000);
		Mytask t4 = new Mytask(170001, 200000);
		Future<List<Integer>> f1 = service.submit(t1);
		Future<List<Integer>> f2 = service.submit(t2);
		Future<List<Integer>> f3 = service.submit(t3);
		Future<List<Integer>> f4 = service.submit(t4);
		start = System.currentTimeMillis();
		f1.get();
		f2.get();
		f3.get();
		f4.get();
		end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	static class Mytask implements Callable<List<Integer>> {
		int startPos, endPos;

		public Mytask(int s, int e) {
			this.endPos = e;
			this.startPos = s;
		}

		@Override
		public List<Integer> call() throws Exception {
			// TODO Auto-generated method stub
			List<Integer> r = getPrime(startPos, endPos);
			return r;
		}

	}

	private static List<Integer> getPrime(int start, int end) {
		List<Integer> results = new ArrayList<Integer>();

		for (int i = start; i <= end; i++) {
			if (isPrime(i)) {
				results.add(i);

			}

		}
		return results;
	}

	private static boolean isPrime(int num) {
		// TODO Auto-generated method stub
		for (int i = 2; i <= num / 2; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
}
