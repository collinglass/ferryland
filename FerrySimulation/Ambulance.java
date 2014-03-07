


public class Ambulance extends Thread
{
	private int port;
	private Ferry fry;

	public Ambulance(int prt, Ferry ferry)
	{
		this.port = prt;
		this.fry = ferry;
	}

	public void run() 
    {
		   int semBoard;  // Semaphore id for boarding

		   while (true) 
	       {
			// Wait
			try {sleep((int) (1000*Math.random()));} catch (Exception e) { break;}
			System.out.println(getName()+" arrives at port " + port);
				// Choose semaphore for the port
			if(port == FerrySimulation.PORT0) semBoard = fry.semBoardPort0;
			else semBoard = fry.semBoardPort1;

			// Board
	  		try{fry.syncSys.waitsem(semBoard);} catch (InterruptedException e) { break; }
			System.out.println(getName()+" boards the ferry at port " + port);
			fry.addLoad();  // increment the load  
			fry.syncSys.signalsem(fry.semDepart);  // Advise the ferry to leave
	 		
			// Arrive at the next port
			port = 1 - port ;   
			if(port == FerrySimulation.PORT0) semBoard = fry.semBoardPort0;
			else semBoard = fry.semBoardPort1;
			
			//Disembarking	
			try{fry.syncSys.waitsem(fry.semDisembark);} catch (InterruptedException e) { break; }
			System.out.println(getName()+" disembarks the ferry at port " + port);
			fry.reduceLoad();   // Reduce load
		   	if(fry.getLoad() == 0) fry.syncSys.signalsem(semBoard);  // signal to cars to board
			else fry.syncSys.signalsem(fry.semDisembark);  // signal car to disembark

			if(isInterrupted()) break;
		   }
		   System.out.println(getName()+" terminates.");
	}
}
