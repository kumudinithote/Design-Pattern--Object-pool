package numberPlay.threadUtil;

import numberPlay.driver.MyLogger;
import numberPlay.driver.MyLogger.DebugLevel;
import numberPlay.util.FileProcessor;

public class ExportingProcess {  
	private long processNo;
	private FileProcessor fileProcessor;
  
    
    public ExportingProcess(long processNo, FileProcessor fileProcessor)  { 
    	if (MyLogger.debugLevel == DebugLevel.CONSTRUCTOR) MyLogger.writeMessage(this.getClass().getName());
        this.processNo = processNo;  
        this.fileProcessor = fileProcessor;
       
     //System.out.println("Object with process no. " + processNo + " was created");  
    }
     
    public FileProcessor getChannel() {
		return fileProcessor;
	}

	public void setChannel(FileProcessor fileProcessor) {
		this.fileProcessor = fileProcessor;
	}

	public long getProcessNo() {  
        return processNo;  
    }  
}// End of the ExportingProcess class.  