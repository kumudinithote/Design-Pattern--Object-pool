package numberPlay.threadUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.TimeUnit;  
import java.util.concurrent.atomic.AtomicLong;
import numberPlay.util.FileProcessor;


public class ObjectPoolDemo{  
	
	static final int minNumOfObjects = 3;
	static final int maxNumOfObjects = 5;
	static final int timeInSec = 5;
	private int numOfThreads;
	private int  MAX_CAPACITY;
	private List<Integer> taskQueue;
    private ObjectPool<ExportingProcess> pool; 
    private AtomicLong processNo=new AtomicLong(0); 
    private FileProcessor fileProcessor;
    
    public void setUp(String inputFile, int numOfThreads, List<Integer> sharedQueue, int size, 
    		FileProcessor fileProcessor) {  
  	  this.numOfThreads = numOfThreads;
  	  this.taskQueue = sharedQueue;
  	  this.MAX_CAPACITY = size;
  	  this.fileProcessor = fileProcessor;
  	  
	      pool = new ObjectPool<ExportingProcess>(minNumOfObjects, maxNumOfObjects, timeInSec)  
	        {              
				protected ExportingProcess createObject()  
	            {   
	            	return new ExportingProcess( processNo.incrementAndGet(), fileProcessor);
	    
	            }  
	        };  
    }  
    public void tearDown() {  
      pool.shutdown();  
    }  
    public void testObjectPool() {  
      ExecutorService executor = Executors.newFixedThreadPool(100);  

      for(int threadNum = 0; threadNum < numOfThreads; threadNum++){   	 
      	executor.execute(new Producer(taskQueue, MAX_CAPACITY, pool, threadNum, fileProcessor));  
      }
      
      
      executor.shutdown();  
      try {  
          executor.awaitTermination(30, TimeUnit.SECONDS);  
          } catch (InterruptedException e)  
            
            {  
             e.printStackTrace();  
            }  
    }  
    public void StartThreads(String inputFile, int numOfThreads, List<Integer> sharedQueue, int size,
    		FileProcessor fileProcessor)  {   
        
    	setUp(inputFile, numOfThreads, sharedQueue, size, fileProcessor);
    	tearDown();
    	testObjectPool(); 
    }   
}