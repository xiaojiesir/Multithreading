package com.lock.ThreadPool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {

	static int[] nums = new int[1000000];
	static final int max_num = 50000;
	static Random r = new Random();
	static {
		for (int i = 0; i < nums.length; i++) {
			nums[i] = r.nextInt(100);
		}
		System.out.println(Arrays.stream(nums).sum());
	}

	// 无返回值 作用：将nums数组根据条件拆分
	static class AddTask extends RecursiveAction {
		int start, end;

		AddTask(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		protected void compute() {
			// TODO Auto-generated method stub
			if (end - start <= max_num) {
				long sum = 0L;
				for (int i = start; i < end; i++) {
					sum += nums[i];

				}
				System.out.println("from:" + start + " to:" + end + " = " + sum);
			} else {
				int middle = start + (end - start) / 2;
				AddTask subTask1 = new AddTask(start, middle);
				AddTask subTask2 = new AddTask(middle, end);
				subTask1.fork();
				subTask2.fork();
			}
		}
	}

	//API上面的例子
	/*static class SortTask extends RecursiveAction {
		final long[] array;
		final int lo, hi;

		SortTask(long[] array, int lo, int hi) {
			this.array = array;
			this.lo = lo;
			this.hi = hi;
		}

		SortTask(long[] array) {
			this(array, 0, array.length);
		}

		protected void compute() {
			if (hi - lo < THRESHOLD)
				sortSequentially(lo, hi);
			else {
				int mid = (lo + hi) >>> 1;
				invokeAll(new SortTask(array, lo, mid), new SortTask(array, mid, hi));
				merge(lo, mid, hi);
			}
		}
	}*/

	// 有返回值，作用：作用：将nums数组根据条件拆分，求和，join方法返回结果
	static class AddTask1 extends RecursiveTask<Long> {
		int start, end;

		AddTask1(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		protected Long compute() {
			// TODO Auto-generated method stub
			if (end - start <= max_num) {
				long sum = 0L;
				for (int i = start; i < end; i++) {
					sum += nums[i];

				}
				return sum;
			} else {
				int middle = start + (end - start) / 2;
				AddTask1 subTask1 = new AddTask1(start, middle);
				AddTask1 subTask2 = new AddTask1(middle, end);
				subTask1.fork();
				subTask2.fork();
				return subTask1.join() + subTask2.join();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ForkJoinPool fjp = new ForkJoinPool();
		AddTask1 task = new AddTask1(0, nums.length);
		fjp.execute(task);
		long result = task.join();
		System.out.println(result);
		System.in.read();
	}
}
