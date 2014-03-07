

public class FerrySimulation
{
	// Configuration
    final static int PORT0 = 0;
    final static int PORT1 = 1;
    final static int MAXLOAD = 5;

    public static void main(String args[]) 
    {
    	final int NUM_CARS = 10;
    	int i;

    	Ferry fer = new Ferry(PORT0,10);
    	fer.setName("Ferry");
    	
    	Auto [] automobile = new Auto[NUM_CARS];
    	for (i=0 ; i<NUM_CARS ; i++)
    	{
    		automobile[i] = new Auto(i,PORT0,fer);
    		automobile[i].setName("Auto "+i);
    	}

    	Ambulance ambulance = new Ambulance(PORT0,fer);
    	ambulance.setName("Ambulance");

    	/* Start the threads */
    	fer.start();   // Start the ferry thread.
    	for (i=0; i<NUM_CARS; i++) automobile[i].start();  // Start automobile threads
    	ambulance.start();  // Start the ambulance thread.

    	try {fer.join();} catch(InterruptedException e) { }; // Wait until ferry terminates.
    	System.out.println("Ferry thread stopped.");
    	// Stop other threads.
    	for (i=0; i<NUM_CARS; i++) 
    	{
    		automobile[i].interrupt(); // Let's stop the auto threads.
    		try { automobile[i].join(); } catch (InterruptedException e) {}  // wait for termination
    	}
    	ambulance.interrupt(); // Stop the ambulance thread.
		try { ambulance.join(); } catch (InterruptedException e) {}  // wait for termination
    	
    	fer.cleanup();  // to remove semaphores
    }
}