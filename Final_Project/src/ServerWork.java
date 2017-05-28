import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerWork {
	private Socket socket;
	private int portNumber;
	private String playerName;
	private ObjectOutputStream outToClient;
	private ObjectInputStream inFromClient;
	private OutputStream os;

	public ServerWork(Socket socket, int port, String pn)
	{
		this.socket = socket;
		this.portNumber = port;
		playerName = pn;
		System.out.println("Creating server socket on port " + portNumber);
		try {
			os = socket.getOutputStream();
			outToClient = new ObjectOutputStream(os);
			outToClient.flush();
			inFromClient = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void initialize()
	{
		try {
			outToClient.writeUTF(playerName);
			outToClient.flush();
			outToClient.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Board play(Board board)
	{
		try {
				sendBoard(board);
				board = (Board) inFromClient.readObject();
				return board;
			 } catch (IOException e2) {
				 board.gameOver = true;
					if(playerName == "Red Player")
						{
						board.setBlackWin();
						board.setResign();
						board.redTurn = false;
						}
					else {
						board.setRedWin();
						board.setResign();
						board.redTurn = true;
					}
						//e2.printStackTrace();
						return board;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} 
		return null;
	}
	public void sendBoard(Board board) throws SocketException
	{
		try {
			outToClient.writeObject(board);
			outToClient.flush();
			outToClient.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close()
	{
		try {
			outToClient.close();
			inFromClient.close();
			socket.close();
		} catch (IOException e) {
			
		}
	}
}
