
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.*;

public class UserInterface {
	private final int portNumber = 9876;
	private Menu menu_screen;
	private CheckersBoard checkers_screen;
	private ErrorScreen error;
	private JFrame f;
	private int playerNo;
	private Socket server;
	UserInterface() {
			
	}
	
	public void initialize()
	{
		f = new JFrame();
		menu_screen = new Menu(this);
		int menu_return = menu_screen.initialize();
		System.out.println(menu_return);
	}

	public int getPlayer()
	{
		return playerNo;
	}



	public void begin(String host) throws IOException, ConnectException {
		InetAddress addr = InetAddress.getByName(host);
		System.out.println("Creating socket to '" + addr + "' on port " + portNumber);
		try{
		server = new Socket(addr,portNumber);
		System.out.println("socket created");
		checkers_screen = new CheckersBoard(f, server);
		f.add(checkers_screen.getGui());
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.pack();
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
		} catch(ConnectException e) {
			error = new ErrorScreen("No Game is currently running!");
			throw e;
		}
        
	}

}