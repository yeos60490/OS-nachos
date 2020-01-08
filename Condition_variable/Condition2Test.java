package nachos.threads;

import java.util.ArrayList;

public class Condition2Test {

	public Condition2Test() {
	}
	
	private static class Test1 implements Runnable {
		Test1(String which, int all, ArrayList<Integer> check, Condition2 condition) {
		    this.which = which;
		    this.all = all;
		    this.check = check;
		    this.condition = condition;
		}
		
		public void run() {
			KThread.yield();
			System.out.println(which+ " thread is running");
			condition.setLockAcquire();
			check.add(1);

			if(condition.waitingCount > 0){
				if (all>0) {
					condition.wakeAll();
					System.out.println(which + " thread WakeAll threads");
				}
				
				else {
					condition.wake();
					System.out.println(which + " thread Wake one thread");
				}
			}
			condition.setLockRelease();
			
		}

		private String which;
		private int all;
		private Condition2 condition;
		private ArrayList<Integer> check;
	}
	
	private static class Test2 implements Runnable {
		Test2(String which, ArrayList<Integer> check, Condition2 condition) {
		    this.which = which;
		    this.check = check;
		    this.condition = condition;
		}
		
		public void run() {
			condition.setLockAcquire();
			if (check.size() == 0) {
				System.out.println(which + " thread Sleep");
				condition.sleep();
				System.out.println(which + " thread Wake");
			}
			if(check.size()!= 0){
				check.remove(0);
			}
			condition.setLockRelease();	
			KThread.yield();
			
		}

		private String which;
		private Condition2 condition;
		private ArrayList<Integer> check;
	}
	

	public static void selfTest() {
		ArrayList<Integer> check = new ArrayList<Integer>();
		Lock lock = new Lock();
		Condition2 condition = new Condition2(lock);
		
		KThread p = new KThread(new Test1("p1", 1, check, condition));
		KThread c = new KThread(new Test2("c1", check, condition));
		KThread c_2 = new KThread(new Test2("c2", check, condition));
		
		p.fork();
		c.fork();
		c_2.fork();
		
		
		
		/*
		KThread p = new KThread(new Test1("p1", 0, check, condition));
		KThread p_2 = new KThread(new Test1("p2", 1, check, condition));
		KThread c = new KThread(new Test2("c1", check, condition));
		KThread c_2 = new KThread(new Test2("c2", check, condition));
		KThread c_3 = new KThread(new Test2("c3", check, condition));
		
		p.fork();
		c.fork();
		c_2.fork();
		c_3.fork();
		c.join();
		p_2.fork();
		*/
	}

}
