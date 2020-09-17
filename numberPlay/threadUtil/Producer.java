package numberPlay.threadUtil;

import java.io.IOException;
import java.util.List;

import numberPlay.driver.MyLogger;
import numberPlay.driver.MyLogger.DebugLevel;
import numberPlay.util.FileProcessor;

class Producer implements IThreadImp
{
   private final List<Integer> taskQueue;
   private final int  MAX_CAPACITY;
   private ObjectPool<ExportingProcess> pool;  
   private int threadNo;
   private FileProcessor fileProcessor;
   private static int sumOfPrimeNum = 0;
 
   public Producer(List<Integer> sharedQueue, int size, ObjectPool<ExportingProcess> pool, int threadNo, 
		   FileProcessor fileProcessor)
   {
	  if (MyLogger.debugLevel == DebugLevel.CONSTRUCTOR) MyLogger.writeMessage(this.getClass().getName());
      this.taskQueue = sharedQueue;
      this.MAX_CAPACITY = size;
      this.pool = pool;  
      this.threadNo = threadNo;  
      this.fileProcessor = fileProcessor;
   }
   
   @Override
   public void run()
   {
	  if (MyLogger.debugLevel == DebugLevel.THREADRUN) MyLogger.writeMessage(Thread.currentThread().getName());
      while (true)
      {
         try
         {
        	Thread.sleep(1000);
        	runThread();
         } 
         catch (InterruptedException ex)
         {
            ex.printStackTrace();
         }
      }
   }
 
   public void runThread() throws InterruptedException
   {
	   ExportingProcess exportingProcess = pool.borrowObject();
       //System.out.println("Thread " + threadNo + ": Object with process no. "+ exportingProcess.getProcessNo() + " was borrowed"); 
       
      synchronized (fileProcessor)
      {
    	  synchronized (taskQueue){
    		  Thread.sleep(1000);
	  			Integer numData;
				try {
					numData = fileProcessor.poll();
					if(numData == null){
		  				if (MyLogger.debugLevel == DebugLevel.SUM) MyLogger.writeMessage(sumOfPrimeNum+""); 
		  				System.out.println("Closing client session");
		  				System.exit(1);
		  			}
		  			//System.out.println(Thread.currentThread().getName() +" : "+ numData);

		  			
	       		if(fileProcessor.isPrime(numData)){
	       			sumOfPrimeNum =+numData;
	       			addToSharedQueue(numData);
	       			if (MyLogger.debugLevel == DebugLevel.DATASTRUCTURE) MyLogger.writeMessage(taskQueue+"");
	       			if (MyLogger.debugLevel == DebugLevel.RESULTENTRY) MyLogger.writeMessage(numData+"");
	       		}
				} catch (IOException e) {
					System.out.println("Error while polling data from inputfile");
				}
	  			
	  			
    	  }
  		
      }
      
      pool.returnObject(exportingProcess);  

      //System.out.println("Thread " + threadNo +": Object with process no. "+ exportingProcess.getProcessNo() + " was returned");
   }
   
   private void addToSharedQueue(int data) throws InterruptedException{
	   
	   while (taskQueue.size() == MAX_CAPACITY)
       {
          //System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
          taskQueue.wait();
       }
	   
	   taskQueue.add(data);
	   taskQueue.notifyAll();
   }
}