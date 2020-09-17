package numberPlay.serverUtil;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import numberPlay.driver.MyLogger;
import numberPlay.driver.MyLogger.DebugLevel;
import numberPlay.util.Results;

public class MyServer implements IServer{

	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream input = null;
	private Results result;
	
	/**
	 * This method will start the sever and waits for the connection.
	 * @param portNumber
	 * @param outputFileName
	 * 
	 */
	public MyServer(int portNumber, String outputFileName){
		if (MyLogger.debugLevel == DebugLevel.CONSTRUCTOR) MyLogger.writeMessage(this.getClass().getName());
		try {
			server = new ServerSocket(portNumber);
			result = new Results(outputFileName);
			System.out.println("Server Started");
		} catch (IOException e) {
			System.out.println("Error while starting server");
		}  
        
	}
	
	/**
	 * This method will block until the clients connects to server, reads the number until "over" is sent and closes connection.
	 */
	
	@Override
	public void startAccepting() {
		try {	
			System.out.println("Start Accepting");
			socket = server.accept();
			System.out.println("Client accepted");
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				    
			String line = "";
	        while (!line.equals("Over")) 
	        { 
	            try
	            { 
	                line = input.readUTF(); 
	                result.writeToFile(line);   
	            } 
	            catch(IOException i) 
	            { 
	            	System.out.println("Closing server session");
	            	System.exit(1);
	            }
	        }
	        
	        System.out.println("Closing connection"); 

	        // close connection 
	        try {
				socket.close();
				input.close();
			} catch (IOException e) {
				System.out.println("Getting error while closing connection."); 
			} 
		} catch (IOException e) {
			
			System.out.println("Error while accepting request from client");
		}
		
		
	}

}
