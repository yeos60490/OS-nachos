package nachos.threads;


import nachos.machine.*;

public class KThreadTest extends KThread{

	private static class PingTest implements Runnable {
		PingTest(int which) {
		    this.which = which;
		}
		
		public void run() {
		    for (int i=0; i<5; i++) {
			System.out.println("*** thread " + which + " looped "
					   + i + " times");
			KThread.yield();
		    }
		}

		private int which;
	}

	private static final char dbgThread = 't';

	public static void selfTest() {
		
		Lib.debug(dbgThread, "Enter KThreadTest.selfTest");

		KThread A, B, C, D;
		A = new KThread(new PingTest(1));
		B = new KThread(new PingTest(2));
		C = new KThread(new PingTest(3));
		D = new KThread(new PingTest(4));
		

		
		A.fork();
		B.fork();
		B.join();
		
		C.fork();
		B.join();

		D.fork();
				
	} 
}
