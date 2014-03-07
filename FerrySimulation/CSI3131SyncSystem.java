import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Author: Collin Glass
 * Student Number: 6006546
 */

public class CSI3131SyncSystem 
{
	private ArrayList<CSI3131Semaphore> semList = new ArrayList<CSI3131Semaphore>(0);  // used to manage an array of semaphores
	public FileWriter syslog;   // The system log

        // The Constructor
	public CSI3131SyncSystem()
	{
		// Setup the log file
		try {
			syslog = new FileWriter("CSI3131SyncSystemLog.txt");
			syslog.write("Log file, CSI3131SyncSystemLog.txt created and opened\n");
		} catch (IOException e) {
			System.out.println("Cannot open system log file");
			e.printStackTrace();
		}
	}
        
	// Creating a new semaphore
	public int createSem(String name, int initValue)
	{
		// Create new semaphore
		CSI3131Semaphore semaphore = new CSI3131Semaphore(name, initValue, this);
		// Add it to list and get index
		int id = addSem(semaphore);
		try {
			// Try logging to ensure all information is working
			syslog.write("Semaphore " + name + " with id " + id + ", semval " + initValue + " created and added to list. Size of list is now " + semList.size() + "\n");
		} catch (IOException e) {
			// Throw error if failure
            System.out.println("createSem failed when trying to log"); 
			e.printStackTrace();
		}
		return (id);		
	}

	// Adds semaphore to a list
	private int addSem(CSI3131Semaphore sem)
	{
		// Initialize returd id to -1 for error if not declared
		int id = -1;
		// Iterate over list to find empty slot
		for(int ix = 0 ; ix < semList.size() && id == -1 ; ix++)
		{
			// if empty slot set semaphore and return id
			if(semList.get(ix) == null) 
			{
				 semList.set(ix,sem);
				 id = ix;
			}
		}
		// if no empty slots found increase list size and add semaphore
		if(id == -1) 
		{
			// Increase size and return index of new slot
			id = semList.size();
			// Add semaphore to edn of list
			semList.add(sem);
		}
		// return index of semaphore
		return(id);
	}
	
	// Removing a semapahore
	public void removeSem(int semid)
	{
		try {
			// Try writing to log file
			syslog.write("Semaphore with id " + semid + ", semval " + semList.get(semid).getSemval() + ", size of list is now " + semList.size() + "\n");
		} catch (IOException e) {
			// Throw exception if loggin failed
            System.out.println("removeSem could not write to system log file"); 
			e.printStackTrace();
		}
		// Remove semaphore
		semList.set(semid, null);
	}
	
	// A signal to the semaphore identified by semid
	public void signalsem(int semid) 
	{
		// Signal semaphore in list
		semList.get(semid).signalsem();
	}
	
	// A wait on the semaphore identified by semid
	public void waitsem(int semid)
	{
		// Wait semaphore in list
		semList.get(semid).waitsem();
	}

	// A method for logging to the log file, including output of the thread names 
	// in ArrayList referenced by arr.
	public void logSemMsg(String msg, ArrayList<String> arr)
	{
		int ix;
		try {
			syslog.write(msg+": ");
			if(arr.size() == 0) syslog.write("empty\n");
			else
			{
				syslog.write(arr.get(0));
				for(ix=1 ; ix < arr.size(); ix++) syslog.write(", "+arr.get(ix));	
				syslog.write("\n");
			}			
		} catch (IOException e) {
            System.out.println("logSemMsg could not write to system log file"); 
			e.printStackTrace();
		}
	}

}
