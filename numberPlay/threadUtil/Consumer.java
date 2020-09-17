package numberPlay.threadUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import numberPlay.driver.MyLogger;
import numberPlay.driver.MyLogger.DebugLevel;

public class Consumer implements IThreadImp {
	private final List<Integer> taskQueue;
	private Socket socket = null; 
    private DataOutputStream output = null;
    
    /**
     * This method establish the connection.
     * @param sharedQueue
     * @param address
     * @param port
     */
	 
	   public Consumer(List<Integer> sharedQueue, String address, int port)
	   {
		  if (MyLogger.debugLevel == DebugLevel.CONSTRUCTOR) MyLogger.writeMessage(this.getClass().getName());
	      this.taskQueue = sharedQueue;
	      try
	        { 
	            socket = new Socket(address, port); 
	            System.out.println("Connected"); 
	        } 
	        catch(UnknownHostException u) 
	        { 
	            System.out.println(u); 
	        } 
	        catch(IOException i) 
	        { 
	            System.out.println(i); 
	        } 
	   }
	   
	   /**
	    * This method is calling runThread(). 
	    */
	   
	   public void run()
	   {
		  if (MyLogger.debugLevel == DebugLevel.THREADRUN) MyLogger.writeMessage(Thread.currentThread().getName());
	      while (true)
	      {
	         try
	         {
	        	 runThread();
	         } catch (InterruptedException ex)
	         {
	            System.out.println("Error while running thread");
	         }
	      }
	   }
	   
	   
	   /**
	    * This method check if queue is empty and waits the thread. Writes the output and notify to all the threads.
	    * 
	    */
	   public void runThread() throws InterruptedException
	   {
	      synchronized (taskQueue)
	      {
	         while (taskQueue.isEmpty())
	         {
	            //System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
	            taskQueue.wait();
	         }
	         try {
				output = new DataOutputStream(socket.getOutputStream());
				int num = taskQueue.remove(0);
				output.writeUTF(num+"");
		        taskQueue.notifyAll();
			} catch (IOException e) {
				System.out.println("Error while sending data");
			}
	         
	      }
	   }
}
