package com.lock.condition;

public class WriterInterruptibly extends Thread {

	private BufferInterruptibly buff;

	public WriterInterruptibly(BufferInterruptibly buff) {

		this.buff = buff;

	}

	@Override

	public void run() {

		buff.write();

	}

}
