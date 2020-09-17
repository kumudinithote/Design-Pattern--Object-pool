package numberPlay.driver;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import numberPlay.threadUtil.Consumer;
import numberPlay.threadUtil.ObjectPoolDemo;
import numberPlay.util.FileProcessor;

/**
 * @author Kumudini Thote
 *
 */
public class Driver {
	

	public static void main(String[] args) {
		
		if ((args.length != 6) || (args[0].equals("${inputFile}")) || (args[1].equals("${numThreads}")) || (args[2].equals("${capacity}"))
				|| (args[3].equals("${persisterServiceIp}")) || (args[4].equals("${persisterServicePort}")) || (args[5].equals("${debugValue}"))) {
			System.err.println("Error: Incorrect number of arguments. Program accepts 6 arguments.");
			System.exit(0);
		}
		
		List<Integer> taskQueue = new ArrayList<Integer>();
		
	    int MAX_CAPACITY = Integer.parseInt(args[2]);
	    if(MAX_CAPACITY <= 0){
	    	System.err.println("Error: data structure capacity can't be 0 or less than that");
			System.exit(0);
	    }
	    
	    int port = Integer.parseInt(args[4]);
	    if(port < 32768 && port > 50000){
	    	System.err.println("Error: port given is invalid");
			System.exit(0);
	    }
		
		ObjectPoolDemo demo = new ObjectPoolDemo();
		
		Thread tConsumer = new Thread(new Consumer(taskQueue, args[3], Integer.parseInt(args[4])), "Consumer");
		tConsumer.start();
		
		MyLogger.setDebugValue(Integer.parseInt(args[5]));
		
		FileProcessor fileProcessor;
		try {
			fileProcessor = new FileProcessor(args[0]);
			demo.StartThreads(args[0], Integer.parseInt(args[1]), taskQueue, MAX_CAPACITY, fileProcessor);
		} catch (InvalidPathException | SecurityException | IOException e) {
			System.out.println("Error while processing inputfile");
		}
		
		
		
	}
}
