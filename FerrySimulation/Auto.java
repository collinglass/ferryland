

public class Auto extends Thread
{
	private int port;
	private Ferry fry;

	public Auto(int id, int prt, Ferry ferry)
	{
		this.port = prt;
		this.fry = ferry;
	}

	public void run() 
    {
		   int semBoard;  // Semaphore id for boarding

		   while (true) 
	       {
			// Delay
			try {sleep((int) (300*Math.random()));} catch (Exception e) { break;}
			System.out.println(getName() + " arrives at port " + port);
				// Chose semaphore for the port
			if(port == FerrySimulation.PORT0) semBoard = fry.semBoardPort0;
			else semBoard = fry.semBoardPort1;

			// Board
	  		try{fry.syncSys.waitsem(semBoard);} catch (InterruptedException e) { break; }
			System.out.println(getName() + " boards on the ferry at port " + port);
			fry.addLoad();  // increment the ferry load
			if(fry.getLoad() == FerrySimulation.MAXLOAD) fry.syncSys.signalsem(fry.semDepart);  // Advise the ferry to leave
			else fry.syncSys.signalsem(semBoard);  // signal the next auto
	 		
			// Arrive at the next port
			port = 1 - port ;   
			if(port == FerrySimulation.PORT0) semBoard = fry.semBoardPort0;
			else semBoard = fry.semBoardPort1;
			
			// disembark		
			try{fry.syncSys.waitsem(fry.semDisembark);} catch (InterruptedException e) { break; }
			System.out.println(getName() + " disembarks from ferry at port " + port);
			fry.reduceLoad();   // Reduce load
		   	if(fry.getLoad() == 0) fry.syncSys.signalsem(semBoard);  // signal to cars to board
			else fry.syncSys.signalsem(fry.semDisembark);  // signal next auto to disembark
			if(isInterrupted()) break;
		   }
		   System.out.println(getName()+" terminated");
	}
}
