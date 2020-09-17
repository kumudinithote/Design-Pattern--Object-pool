package numberPlay.driver;

public class MyLogger {
	// FIXME: Add more enum values as needed for the assignment
    public static enum DebugLevel { SUM, DATASTRUCTURE, RESULTENTRY, THREADRUN, CONSTRUCTOR, NONE};

    public static DebugLevel debugLevel;


    // FIXME: Add switch cases for all the levels
    public static void setDebugValue (int levelIn) {
		switch (levelIn) {
			case 0: debugLevel = DebugLevel.SUM; break;
			case 1: debugLevel = DebugLevel.DATASTRUCTURE; break;
			case 2: debugLevel = DebugLevel.RESULTENTRY; break;
			case 3: debugLevel = DebugLevel.THREADRUN; break;
			case 4: debugLevel = DebugLevel.CONSTRUCTOR; break;
			default: debugLevel = DebugLevel.NONE; break;
		}
    }

    public static void setDebugValue (DebugLevel levelIn) {
    	debugLevel = levelIn;
    }

    public static void writeMessage (String message) {
    	if(debugLevel ==  DebugLevel.SUM)
			System.out.println("Sum of all prime numbers is : "+message);
    	if(debugLevel ==  DebugLevel.DATASTRUCTURE)
			System.out.println("State of data structure is : "+message);
    	if(debugLevel ==  DebugLevel.RESULTENTRY)
			System.out.println("new found prime number is : "+message);
    	if(debugLevel ==  DebugLevel.THREADRUN)
			System.out.println("Run of thread : "+message);
    	if(debugLevel ==  DebugLevel.CONSTRUCTOR)
			System.out.println("Constructor of class is called : "+message); 
		
    }

    public String toString() {
	return "The debug level has been set to the following " + debugLevel;
    }
}
