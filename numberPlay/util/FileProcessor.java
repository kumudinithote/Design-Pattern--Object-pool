package numberPlay.util;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import numberPlay.driver.MyLogger;
import numberPlay.driver.MyLogger.DebugLevel;

import java.nio.file.InvalidPathException;


public final class FileProcessor {
	private BufferedReader reader;
	private String line;

	public FileProcessor(String inputFilePath) 
		throws InvalidPathException, SecurityException, FileNotFoundException, IOException {
		if (MyLogger.debugLevel == DebugLevel.CONSTRUCTOR) MyLogger.writeMessage(this.getClass().getName());
		
		if (!Files.exists(Paths.get(inputFilePath))) {
			System.out.println("invalid input file or input file in incorrect location");
			System.exit(0);
		}
		
		File newFile = new File(inputFilePath);
		if (newFile.length() == 0) {
			System.out.println("Given input file is empty");
			System.exit(0);
		}
		
		reader = new BufferedReader(new FileReader(new File(inputFilePath)));
		line = reader.readLine();
	}

	public Integer poll() throws  IOException {
		if (null == line) return null;

		try{
			Integer newValue = Integer.parseInt(line.trim());
			line = reader.readLine();
			return newValue;
		}catch(NumberFormatException e){
			System.out.println("Entry is input file is not proper integer");
			return 1;
		}	
	}
	
	public boolean isPrime(int n) 
    { 
        // Corner case 
        if (n <= 1) 
            return false; 
  
        // Check from 2 to n-1 
        for (int i = 2; i < n; i++) 
            if (n % i == 0) 
                return false; 
  
        return true; 
    }

	public void close() throws IOException {
		try {
			reader.close();
			line = null;
		} catch (IOException e) {
			throw new IOException("failed to close file", e);
		}
	}
}
