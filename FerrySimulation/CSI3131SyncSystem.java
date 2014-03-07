import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Class used to emulate a OS synchronization system for creating and managing semaphores
 * Used synchronized methods to ensure atomic executions of the methods.
 *
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
		int id = 0;
		return (id);		
	}
	
	// Removing a semapahore
	public void removeSem(int semid)
	{
	}
	
	// A signal to the semaphore identified by semid
	public void signalsem(int semid) 
	{
	}
	
	// A wait on the semaphore identified by semid
	public void waitsem(int semid)
	{
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
