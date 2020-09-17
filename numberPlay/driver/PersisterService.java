package numberPlay.driver;

import numberPlay.serverUtil.MyServer;

public class PersisterService {

	public static void main(String[] args) {
		
		if ((args.length != 3) || (args[0].equals("${port}")) || (args[1].equals("${outputFile}")) || (args[2].equals("${debugValue}"))) {
			System.err.println("Error: Incorrect number of arguments. Program accepts 3 arguments.");
			System.exit(0);
		}
		
		int port = Integer.parseInt(args[0]);
	    if(port < 32768 && port > 50000){
	    	System.err.println("Error: port given is invalid");
			System.exit(0);
	    }
		
		MyLogger.setDebugValue(Integer.parseInt(args[2]));
		MyServer server = new MyServer(Integer.parseInt(args[0]), args[1]);
		server.startAccepting();
	}
	
}
