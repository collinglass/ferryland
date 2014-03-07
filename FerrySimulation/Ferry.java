import java.io.IOException;

public class Ferry extends Thread
{
	private int port=0;  // Start at port 0
	private int load=0;  // Load is zero
	private int numCrossings;  // number of crossings to execute
	// Semaphores
	public CSI3131SyncSystem syncSys = new CSI3131SyncSystem();
	public int semBoardPort0;  // Semaphore for loading at port 0
	public int semBoardPort1;  // Semaphore loading at port 1
	public int semDisembark;  // Semaphore for disembarking
	public int semDepart;    // Semaphore the departure of the ferry


	public Ferry(int prt, int nbtours)
	{
		this.port = prt;
		// Semaphores created and removed to test CSISyncSystem throughly
		semBoardPort0 = syncSys.createSem("BoardPort0", 0);
		semBoardPort1 = syncSys.createSem("BoardPort1", 0);
		syncSys.removeSem(semBoardPort1);
		semDisembark = syncSys.createSem("Disembark", 0);
		semDepart = syncSys.createSem("Depart", 0);
		syncSys.removeSem(semDepart);
		// create the semaphores removed
		semBoardPort1 = syncSys.createSem("BoardPort1", 0);
		semDepart = syncSys.createSem("Depart", 0);
		System.out.println("Semaphore ids: semBoardPort0 = "+semBoardPort0+
				           ", semBoardPort1 = "+semBoardPort1+
				           ", semDisembark = "+semDisembark+
				           ", semDepart = "+semDepart);		
		numCrossings = nbtours;
	}

	public void run() 
    {
	   int i;
	   System.out.println("Start at port " + port + " with a load of " + load + " vehicles");
	   syncSys.signalsem(semBoardPort0);  // Signal to cars to board

	   // numCrossings crossings in our day
	   for(i=0 ; i < numCrossings ; i++)
       {
	    try { syncSys.waitsem(semDepart); } // Wait for the signal for departure
	    catch (InterruptedException ex) { break; }
		// The crossing
		System.out.println("Departure from port " + port + " with a load of " + load + " vehicles");
		System.out.println("Crossing " + i + " with a load of " + load + " vehicles");
		port = 1 - port;
		try {sleep((int) (100*Math.random()));} catch (Exception e) { }
		// Arrive at port
		System.out.println("Arrive at port " + port + " with a load of " + load + " vehicles");
		// Disembarkment and loading
		syncSys.signalsem(semDisembark);
	   }
	   System.out.println("Ferry finished last crossing");
		try {sleep(1000);} catch (Exception e) { }  // Let cars finish disembarking and block on semaphores
	}

	// methods to manipulate the load of the ferry
	public int getLoad()      { return(load); }
	public void addLoad()  { load = load + 1; }
	public void reduceLoad()  { load = load - 1 ; }
	
	public void cleanup()
	{
		// Cleans up the semaphores
		   // remove the semaphores
		syncSys.removeSem(semBoardPort0);
		syncSys.removeSem(semBoardPort1);
		syncSys.removeSem(semDisembark);
		syncSys.removeSem(semDepart);
		try { syncSys.syslog.close(); } catch (IOException e) { }
	}
}
