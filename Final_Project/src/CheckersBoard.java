
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.*;

public class CheckersBoard {

	private JFrame frame;
	private JPanel gui;
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private int[][] currentBoard;
    private Coordinate first_click;
    private Coordinate second_click;
    private Coordinate third_click;
    private String player; //1= red, 2= black;
    private final JTextField txtGameStatus = new JTextField();
    private JTextField turn;
    private ObjectInputStream inFromServer;
    private ObjectOutputStream outToServer;
    private Socket server;
    private Board board;
    private Board temp;
    private JButton submit;
    private ErrorScreen error;
    private boolean clickFlag = false;
    CheckersBoard(JFrame in_f, Socket s) throws IOException {
    	frame = in_f;
    	txtGameStatus.setText("Game Status");
    	txtGameStatus.setColumns(7);
    	txtGameStatus.setEditable(false);
        first_click = new Coordinate();
        second_click = new Coordinate();
        third_click = new Coordinate();
        server = s;
        board = new Board();	
     	outToServer = new ObjectOutputStream(server.getOutputStream());
     	outToServer.flush();
		inFromServer = new ObjectInputStream(server.getInputStream());	
     	initialize();
    }
    
    
    /**
     * @wbp.parser.entryPoint
     */
    private void initialize(){
        // set up the main GUI
        
        gui = new JPanel();
        frame.getContentPane().add(gui); 
        
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
       	gui.setLayout(new BorderLayout());
        gui.add(tools, BorderLayout.NORTH);
       
        JButton resign_button = new JButton("Resign");
        resign_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					resign();
				}
			});
        submit = new JButton("Submit");
        submit.setVisible(true);
        
        submit.addActionListener(new  ActionListener() {
        
			@Override
			public void actionPerformed(ActionEvent e) {
				resetClick();
				clickFlag = false;
				turn.setText("Opponents Turn");
				sendBoard();
			}
				
			});
        turn = new JTextField();
        turn.setColumns(7);;
        turn.setEditable(false);
        tools.add(txtGameStatus);
        tools.add(submit);
        tools.add(resign_button);
        tools.add(turn);

        chessBoard = new JPanel(new GridLayout(0, 8));
        gui.add(chessBoard, BorderLayout.CENTER);
        currentBoard = board.getBoardForDisplay().clone();
        buildButtons();
        fillBoard();
        addGui();    
        try {       	
        	player = (String) inFromServer.readUTF();
        	updateStatus(player);
			temp = (Board) inFromServer.readObject();
			turn.setText("Your Turn");
			board = temp;
			currentBoard = board.getBoardForDisplay().clone();
			fillBoard();
			addGui();	
			if(board.getGameOver())
			{
				error = new ErrorScreen(player + " won the game", true);
				frame.setVisible(false);
				frame.dispose();
				close();
				return;
			}
				} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }


    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }

  	private void click(int i, int j){
  		if(board.getTurn().equalsIgnoreCase(player)){
	  		if (clickFlag == false) {
		  		if(first_click.isEmpty()){
		  			first_click = new Coordinate(i, j);
		  		}
		  		else 
		  		{
		  			second_click = new Coordinate(i,j);
		  			if((second_click.X ==  first_click.X) && (second_click.Y ==  first_click.Y))
		  				{
		  	  			second_click.reset();
		  	  			return;
		  				}
		  			if(!board.clientMove(first_click, second_click)){	
		  				first_click.reset();
		  	  			second_click.reset();
						return;
					}
		  			first_click.reset();
		  			clickFlag = true;
		  			if(board.getTurn().equalsIgnoreCase(player)){
		  				updateBoardBeforeTurn();
		  			}
		  			else updateBoardAfterTurn();
		  			
		  		}
	  		}
	  		else {
	  			if(first_click.isEmpty())
	  			{
	  				first_click = new Coordinate(i,j);
	  				if(!((first_click.X == second_click.X) && (first_click.Y == second_click.Y))) 
	  				{
	  	  				first_click.reset();
	  				}
	  			}
	  			else {
	  				third_click = new Coordinate(i,j);
		  			if((third_click.X ==  first_click.X) && (third_click.Y ==  third_click.Y))
		  				{
		  	  			third_click.reset();
		  	  			return;
		  				}
		  			if(!board.clientMove(first_click, third_click)){	
		  				first_click.reset();
		  	  			third_click.reset();
						return;
					}
		  			second_click.X = third_click.X;
		  			second_click.Y = third_click.Y;
		  			third_click.reset();
		  			updateBoardAfterTurn();
	  			}
	  			
	  		}
  		}
	}
  	
  	private void buildButtons(){
  		for(int i=0; i<8; i++){
  			for(int j=0; j<8; j++){
  				JButton b = new JButton();
  				int row = i;
  				int col = j;
  				b.addActionListener(new ActionListener() {
  					@Override
  					public void actionPerformed(ActionEvent e) {
  						click(row,col);
  					}
  				});
  				chessBoardSquares[j][i] = b;
  			}
  		}	
  	}
    boolean fillBoard(){

    	Insets buttonMargin = new Insets(0,0,0,0);
    	for (int ii = 0; ii < currentBoard.length; ii++) {
    		for (int jj = 0; jj < currentBoard[ii].length; jj++) {
    			JButton b = chessBoardSquares[jj][ii];
    
    			if ((jj%2 != ii%2)){
    				ImageIcon black = new ImageIcon(getClass().getResource("black_blank.png"));
    				b.setIcon(black);
    			}
    			else if(currentBoard[ii][jj] == 1){

    				ImageIcon black = new ImageIcon(getClass().getResource("black.png"));
    				b.setIcon(black);
    			}
    			else if(currentBoard[ii][jj] == 2){
    				ImageIcon red = new ImageIcon(getClass().getResource("red.png"));
    				b.setIcon(red);
    			}
    			else if(currentBoard[ii][jj] == 4){
    				ImageIcon green = new ImageIcon(getClass().getResource("black_king.png"));
    				b.setIcon(green);
    			}
    			else if(currentBoard[ii][jj] == 5){
    				ImageIcon green = new ImageIcon(getClass().getResource("red_king.png"));
    				b.setIcon(green);
    			}
    			else{
    				ImageIcon white = new ImageIcon(getClass().getResource("white_blank.png"));
    				b.setIcon(white);
    			}
    			b.setMargin(buttonMargin);

    			chessBoardSquares[jj][ii] = b;
    		}
    	}
    	return true;
    }
    
    public boolean addGui(){
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                       chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        
        gui.add(chessBoard);
        return true;
    }
    
    public void updateStatus(String update){
    	txtGameStatus.setText(update);
    }
    private void updateBoardAfterTurn()
    {	
    	currentBoard = board.getBoardForDisplay().clone();
    	fillBoard();
   		addGui();
		turn.setText("Opponents Turn");
   		if(board.getGameOver())
   			doGameOverProcedure();
    }
    private void doGameOverProcedure() {
    	error = new ErrorScreen(player + " won the game", true);
    	frame.setVisible(false);
    	frame.dispose();
    	try {
			outToServer.writeObject(board);		
			outToServer.flush();
			outToServer.reset();    	
		close();
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void updateBoardBeforeTurn()
    {
    	//subButton.setVisible(false);
    	currentBoard = board.getBoardForDisplay().clone();
		fillBoard();
		addGui();
		if(board.getGameOver())
   			doGameOverProcedure();
    }
    public void sendBoard()
	{
		try {
			
			outToServer.writeObject(board);
			outToServer.flush();
			outToServer.reset();
			frame.setEnabled(false);
			try {
				temp = (Board) inFromServer.readObject();
				temp.clone(board);;
				frame.setEnabled(true);
				updateBoardBeforeTurn();
				if(board.getGameOver() && !board.getResign())
				{
					if(player.equals("Red Player"))
						error = new ErrorScreen("Black Player won the game", true);
					else error = new ErrorScreen("Red Player won the game", true);
					frame.setEnabled(false);
					frame.dispose();
					return;
				}
				else if (board.getGameOver() && board.getResign())
				{
					error = new ErrorScreen(player + " won the game", true);
					frame.setEnabled(false);
					frame.dispose();
					//close();
				}
				turn.setText("Your Turn");
				
			} catch (ClassNotFoundException | IOException e) {
				error = new ErrorScreen(player + " won the game", true);
				frame.setEnabled(false);
				frame.dispose();
				//close();
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void open() {
		gui.setVisible(true);
	}
	
	public void resign() {
			board.setGameOver();
			board.setResign();
			if(player.equals("Red Player")) {
				board.setBlackWin();
				error = new ErrorScreen("Black Player won the game", true);
			}
			else {
				board.setRedWin();
			    error = new ErrorScreen("Red Player won the game", true);
			}
			try {		
				frame.setEnabled(false);
				frame.dispose();
				outToServer.writeObject(board);
				outToServer.flush();
				outToServer.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			close();
		 
	}
	public void close()
	{

		try {
			outToServer.close();
			inFromServer.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void resetClick()
	{
		first_click.reset();
		second_click.reset();
	}    
}