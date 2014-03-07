import java.util.ArrayList; 
/**
 * Author: Collin Glass
 * Student Number: 6006546
 */

public class CSI3131Semaphore 
{
	private int semval = 0;
	CSI3131SyncSystem syncSys;
	String name;
	// List of the names of Threads in the wait set of the semaphore
	ArrayList<String> threadNamesInWaitQueue = new ArrayList<String>(0);  
	
	// Constructor
	public CSI3131Semaphore (String nm, int initialValue, CSI3131SyncSystem sys)
	{
		// Set semaphore value from given value
		semval = initialValue;
		// Set semaphore system to given SyncSystem
		syncSys = sys;
		// Name semaphore
		name = nm;
	}
	
	// Wait semaphore
	public synchronized void waitsem() throws InterruptedException
	{
		String logmsg;
		semval--;
		// if sem value is less than 0
		if(semval < 0) 
		{
			// log the message
           logmsg = "waitsem: Sempahore " + name + ", on thread " + Thread.currentThread().getName() + 
			        ", with semval = " + semval + ", needs to wait on " + name + " queue";
		   // Add to the thread names in queue
		   threadNamesInWaitQueue.add(Thread.currentThread().getName());
		   // Log all
		   syncSys.logSemMsg(logmsg,threadNamesInWaitQueue);  
		   // Wait
		   wait();
		  	// Once done waiting, log message
		   logmsg = "waitsem: Sempahore " + name + ", on thread " + Thread.currentThread().getName() + 
				     " with semval = " + semval + "is finished waiting on " + name + " queue";
		   // Log all
		   syncSys.logSemMsg(logmsg,threadNamesInWaitQueue);
		}
		else 
		{
			// Semvalue was not less than 0 so semaphore did not have to wait
		   logmsg = "waitsem: Sempahore " + name + " on thread " + Thread.currentThread().getName() +  
				          "with semval = " + semval + " did not have to wait on " + name + " queue";
		   // Log all
		   syncSys.logSemMsg(logmsg,threadNamesInWaitQueue);
		}
	}

	// Signal semaphore
	public synchronized void signalsem()
	{
		String logmsg;
		semval++;
		// If the semval is below or equal to 0
	   	if(semval <= 0) 
	    {
	    	// Notify all resources
	   		notify();
	   		// remove from queue
		    threadNamesInWaitQueue.remove(0); // ArrayList tracks names of Threads in wait set
	   	}
	   	// Create log message
 		logmsg = "signalsem: Sempahore " + name + " on thread " + Thread.currentThread().getName() + 
				 "with semval = " + semval + " got signaled on "+name+" queue";
		// Log message
		syncSys.logSemMsg(logmsg,threadNamesInWaitQueue);		
	}
	
	// Return Semaphore value
	public int getSemval() {
		return semval; 
	}
}
