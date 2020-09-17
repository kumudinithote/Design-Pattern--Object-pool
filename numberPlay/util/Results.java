package numberPlay.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import numberPlay.driver.MyLogger;
import numberPlay.driver.MyLogger.DebugLevel;

public class Results implements FileDisplayInterface, StdoutDisplayInterface {
	private BufferedWriter writer;
	private String outputFileName;
	
	public Results(String outputFileName) {
		if (MyLogger.debugLevel == DebugLevel.CONSTRUCTOR) MyLogger.writeMessage(this.getClass().getName());
		this.outputFileName = outputFileName;  
	}

	/**
	 * This method write the prime number to  output file
	 * @param line data received from the client in queue
	 * @return write to the output file
	 */
	
	@Override
	public void writeToFile(String line) {
	    try {
	    	this.writer = new BufferedWriter(new FileWriter(outputFileName, true));
			writer.newLine();
			writer.write(line);
		    writer.close();
		} catch (IOException e) {
			System.out.println("Error while writing into file");
		}
	}
	
	/**
	 * This method prints the prime number on the console.
	 * @param data received from the client in queue
	 */
	@Override
	public void writeToStdout(String line) {
		System.out.println("Prime number is : "+ line);
	}
}
