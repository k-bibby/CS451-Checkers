import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainServer implements Runnable{
	private static final Exception BindException = new Exception();
	int PORT;
	private InetAddress addr;
	private Thread t;
    public MainServer(int PORT) {
        this.PORT = PORT;
        try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
        t = new Thread(this);
        t.start();
    }
	@Override
	public void run() {
		ServerSocket serverSocket = null;
        Socket redSocket = null, blackSocket = null;
        ServerWork redPlayer, blackPlayer;
        
		try {
			System.out.println("Creating Server on '" + addr.getHostAddress()+ "' on port " + PORT);
			try {
			serverSocket = new ServerSocket(PORT);
			}catch(BindException e)
			{
				System.err.println("Already Connected!");
				return;
			}
        	
        	redSocket = serverSocket.accept();
        	
        	Board b = new Board();
        	//new thread for a client
        	redPlayer = new ServerWork(redSocket, PORT, "Red Player");
        	redPlayer.initialize();
        	System.out.println("Waiting for player 2 to join");
        	if(redSocket.isConnected())
        	{
        		blackSocket = serverSocket.accept();
        	
        		blackPlayer = new ServerWork(blackSocket, PORT, "Black Player");
        		blackPlayer.initialize();
        	//	blackPlayer.initialize(b);
        		while(!b.getGameOver()) {
        			if(redSocket.isConnected() && blackSocket.isConnected()){
        				if(b.getRedTurn())
        					b = redPlayer.play(b);
        				else
        					b = blackPlayer.play(b);	
        			}
        			else break;
        		}
	        	if(b.getGameOver() && !b.getResign())
	        	{
	        		if(b.blackWin)
	        			redPlayer.play(b);
	        		else blackPlayer.play(b);
	        	}
	        	else if(b.getGameOver() && b.getResign())
	        	{
	        		if(b.blackWin)
	        			blackPlayer.play(b);
	        		else redPlayer.play(b);
	        	}
	        		if(blackSocket.isConnected())
	        			blackPlayer.close();
	        	}
	        	else { }
	        	if(redSocket.isConnected())
	        		redPlayer.close(); 
	    		serverSocket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
			
			//System.exit(0);
	}
}
