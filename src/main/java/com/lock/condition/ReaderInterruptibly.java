package com.lock.condition;

public class ReaderInterruptibly extends Thread {

	private BufferInterruptibly buff;

	public ReaderInterruptibly(BufferInterruptibly buff) {

		this.buff = buff;

	}

	@Override

	public void run() {

		try {
			buff.read();//可以收到中断的异常，从而有效退出
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("我不读了，行了吧");
		}
		System.out.println("读结束");

	}

}
