package nachos.threads;

import nachos.machine.*;

/**
 * An implementation of condition variables that disables interrupt()s for
 * synchronization.
 *
 * <p>
 * You must implement this.
 *
 * @see	nachos.threads.Condition
 */
public class Condition2 {
    /**
     * Allocate a new condition variable.
     *
     * @param	conditionLock	the lock associated with this condition
     *				variable. The current thread must hold this
     *				lock whenever it uses <tt>sleep()</tt>,
     *				<tt>wake()</tt>, or <tt>wakeAll()</tt>.
     */
    public Condition2(Lock conditionLock) {
	this.conditionLock = conditionLock;
	
	//add
	this.waitingThreads = ThreadedKernel.scheduler.newThreadQueue(true);
	this.waitingCount = 0;
	}
    
    /**
     * Atomically release the associated lock and go to sleep on this condition
     * variable until another thread wakes it using <tt>wake()</tt>. The
     * current thread must hold the associated lock. The thread will
     * automatically reacquire the lock before <tt>sleep()</tt> returns.
     */
    public void sleep() {
    	//add
    	Machine.interrupt().disable();
    	
	Lib.assertTrue(conditionLock.isHeldByCurrentThread());
	waitingCount ++;
	waitingThreads.waitForAccess(KThread.currentThread());
	
	conditionLock.release();
	KThread.sleep();
	conditionLock.acquire();
	
	Machine.interrupt().enable();
    }

    /**
     * Wake up at most one thread sleeping on this condition variable. The
     * current thread must hold the associated lock.
     */
    public void wake() {
	Lib.assertTrue(conditionLock.isHeldByCurrentThread());
	
	//add
	Machine.interrupt().disable();
	KThread thread = waitingThreads.nextThread();
    if(thread != null) {
        thread.ready();
        waitingCount --;
    }
    Machine.interrupt().enable();
    }

    /**
     * Wake up all threads sleeping on this condition variable. The current
     * thread must hold the associated lock.
     */
    public void wakeAll() {
	Lib.assertTrue(conditionLock.isHeldByCurrentThread());
	
	//add
	Machine.interrupt().disable();
	KThread thread = null;
	thread = waitingThreads.nextThread();
	
	while(thread != null) {
            thread.ready();
            thread = waitingThreads.nextThread();
	}
    Machine.interrupt().enable();
    }

    private Lock conditionLock;
    private ThreadQueue waitingThreads = null;
    public int waitingCount;
    public void setLockAcquire(){conditionLock.acquire();} 
    public void setLockRelease(){conditionLock.release();} 
}
