import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Keefer on 2/13/2017.
 */
public class Board implements Serializable{

    int[][] redStartingLocations = {
    		{6, 0},
    		{5, 1},
    		{7, 1},
    		{6, 2},
    		{5, 3},
    		{7, 3},
    		{6, 4},
    		{5, 5},
    		{7, 5},
    		{6, 6},
    		{5, 7},
    		{7, 7}
    };

    int[][] blackStartingLocations = {
    		{0, 0},
    		{2, 0},
    		{1, 1},
    		{0, 2},
    		{2, 2},
    		{1, 3},
    		{0, 4},
    		{2, 4},
    		{1, 5},
    		{0, 6},
    		{2, 6},
    		{1, 7}
    };

    public boolean redTurn;
    public boolean gameOver;
    public boolean redWin;
    public boolean blackWin;
    public ArrayList<Piece> redPieces;
    public ArrayList<Piece> blackPieces;
    private Boolean resign = false;

    public Board() {
        redPieces = new ArrayList<Piece>();
        blackPieces = new ArrayList<Piece>();
        redWin = false;
        blackWin = false;
        redTurn = true;
        gameOver = false;

        for (int i = 0; i < redStartingLocations.length; i++) {
            Piece redPiece = new Piece(redStartingLocations[i][0], redStartingLocations[i][1]);
            redPiece.setRed();
            redPieces.add(redPiece);
        }

        for (int i = 0; i < blackStartingLocations.length; i++) {
            Piece blackPiece = new Piece(blackStartingLocations[i][0], blackStartingLocations[i][1]);
            blackPiece.setBlack();
            blackPieces.add(blackPiece);
        }
    }

    public void setGameOver()
    {
        this.gameOver = true;
    }

    public boolean getGameOver()
    {
        return gameOver;
    }

    public void setBlackWin()
    {
        this.blackWin = true;
    }

    public void setRedWin()
    {
        this.redWin = true;
    }

    public boolean getRedTurn()
    {
        return redTurn;
    }
    
    public String getTurn()
    {
    	if(redTurn)
    	{
    		return "Red Player";
    	}
    	else
    	{
    		return "Black Player";
    	}
    }

    public void nextTurn() {
        this.redTurn = !this.redTurn;
    }

    public void checkWinCondition() {
        if (checkBlackWin())
        {
            setBlackWin();
            setGameOver();
        }
        if (checkRedWin())
        {
            setRedWin();
            setGameOver();
        }
    }

    private boolean checkBlackWin() {
        if (this.redPieces.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkRedWin() {
        if (this.blackPieces.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getRedCount() {
        return this.redPieces.size();
    }

    public int getBlackCount() {
        return this.blackPieces.size();
    }

    public ArrayList<int[]> getValidMovesForPiece(Piece piece) {
        ArrayList<int[]> validMoves = new ArrayList<>();

        if (piece.getRed()) {
            for (int[] location : piece.getPossibleMoves()) {
                if (location[0] < piece.getLocation()[0]) {
                    validMoves.add(location);
                }
            }

            if (piece.getKing()) {
                for (int[] location : piece.getPossibleMoves()) {
                    if (location[0] > piece.getLocation()[0]) {
                        validMoves.add(location);
                    }
                }

            }
        } else {
            for (int[] location : piece.getPossibleMoves()) {
                if (location[0] > piece.getLocation()[0]) {
                    validMoves.add(location);
                }
            }

            if (piece.getKing()) {
                for (int[] location : piece.getPossibleMoves()) {
                    if (location[0] < piece.getLocation()[0]) {
                        validMoves.add(location);
                    }
                }

            }
        }

        ArrayList<int[]> valuesToRemove = new ArrayList<>();
        for (int[] location : validMoves) {
            // If null then no
            if (getPieceAtLocation(location) != null) {
                valuesToRemove.add(location);
            }
        }

        validMoves.removeAll(valuesToRemove);
        return validMoves;
    }

    public ArrayList<int[]> getValidTakesForPiece(Piece piece) {
        ArrayList<int[]> validTakes = new ArrayList<>();

        if (piece.getRed()) {
            for (int[] location : piece.getPossibleTakes()) {
                if (location[0] < piece.getLocation()[0]) {
                    if (getPieceAtLocation(location) == null) {
                        validTakes.add(location);
                    }
                }
            }
            if (piece.getKing()) {
                for (int[] location : piece.getPossibleTakes()) {
                    if (location[0] > piece.getLocation()[0]) {
                        if (getPieceAtLocation(location) == null) {
                            validTakes.add(location);
                        }
                    }
                }
            }
        }
        else
        {
            for (int[] location : piece.getPossibleTakes()) {
                if (location[0] > piece.getLocation()[0]) {
                    if (getPieceAtLocation(location) == null) {
                        validTakes.add(location);
                    }
                }
            }
            if (piece.getKing()) {
                for (int[] location : piece.getPossibleTakes()) {
                    if (location[0] < piece.getLocation()[0]) {
                        if (getPieceAtLocation(location) == null) {
                            validTakes.add(location);
                        }
                    }
                }
            }
        }

        ArrayList<int[]> valuesToRemove = new ArrayList<>();
        for(int[] location : validTakes)
        {
            // Check if piece to skip exists
            int[] skipLocation = getSkipLocation(piece.getLocation(), location);
            if(getPieceAtLocation(skipLocation) == null)
            {
                valuesToRemove.add(location);
            }
        }

        validTakes.removeAll(valuesToRemove);
        return validTakes;

    }

    public Piece getPieceAtLocation(int[] location) {

        for (Piece redPiece : redPieces) {
            if (Arrays.equals(redPiece.getLocation(), location)) {
                return redPiece;
            }
        }

        for (Piece blackPiece : blackPieces) {
            if (Arrays.equals(blackPiece.getLocation(), location)) {
                return blackPiece;
            }
        }

        // If no piece is found, returns null
        return null;
    }
    public void checkCanMove()
    {
    	ArrayList<int[]> validOptions = new ArrayList<>();
    	if(getRedTurn())
    	{
	    	for(Piece piece : redPieces)
	    	{
	    		validOptions.addAll(piece.getPossibleMoves());
	    		validOptions.addAll(piece.getPossibleTakes());
	    	}
    	}
    	else
    	{
	    	for(Piece piece : blackPieces)
	    	{
	    		validOptions.addAll(piece.getPossibleMoves());
	    		validOptions.addAll(piece.getPossibleTakes());
	    	}
    	}
    	
    	if(validOptions.isEmpty())
    	{
    		setGameOver();
    	}
    }
    public boolean clientMove(Coordinate start, Coordinate end)
    {
    	//Check if possible moves are available if not game over
    	checkCanMove();
    	if(getGameOver())
    	{
    		return true;
    	}
    	int[] pieceStart = {start.getX(), start.getY()};
    	int[] pieceEnd =  {end.getX(), end.getY()};
    	ArrayList<int[]> takes =new ArrayList<int[]>();
        Piece pieceToMove = getPieceAtLocation(pieceStart);
        if(pieceToMove!=null)
        {
        	takes = getValidTakesForPiece(pieceToMove);
        
	        if(pieceToMove.getRed() != getRedTurn())
	        {
	        	return false;
	        }
        }
        if(!takes.isEmpty())
        {
            for(int[] takeLocation : takes)
            {
                if(Arrays.equals(pieceEnd, takeLocation))
                {
                    
                    checkKing(pieceToMove);
                    int[] skipLoc = getSkipLocation(pieceStart, pieceEnd);
                    if(getPieceAtLocation(skipLoc).getRed() == getPieceAtLocation(pieceStart).getRed())
                    {
                    	return false;
                    }
                    takePieceAtLocation(skipLoc);
                    pieceToMove.setLocation(pieceEnd);
                    checkKing(pieceToMove);

                    // check if there is another take that can be made
                    if(pieceCanTakeAgain(pieceToMove))
                    {
                    	return true;
                    }
                    
                    checkWinCondition();
                    if(getGameOver())
                    {
                        return true;
                    }
                    nextTurn();
                    return true;
                }
            }
        }
        else
        {
	        ArrayList<int[]> moves = new ArrayList<int[]>();
	        if(pieceToMove!=null)
	        	moves = getValidMovesForPiece(pieceToMove);
	        if(!moves.isEmpty())
	        {
	            for(int[] moveLocation : moves)
	            {
	                if(Arrays.equals(pieceEnd, moveLocation))
	                {
	                    pieceToMove.setLocation(pieceEnd);
	                    checkWinCondition();
	                    checkKing(pieceToMove);
	                    if(getGameOver())
	                    {
	                        return true;
	                    }
	                    nextTurn();
	                    return true;
	                }
	            }
	        }
        }

        return false;
    }

    public void checkKing(Piece piece)
    {
        if(piece.getRed())
        {
            if(piece.getLocation()[0] == 0)
            {
                piece.setKing();
            }
        }

        if(!piece.getRed())
        {
            if(piece.getLocation()[0] == 7)
            {
                piece.setKing();
            }
        }
    }

    public void takePieceAtLocation(int[] skipLocation)
    {
        Piece pieceToTake = getPieceAtLocation(skipLocation);
        takePiece(pieceToTake);
    }

    private void takePiece(Piece piece)
    {
        if(this.redPieces.contains(piece))
        {
            redPieces.remove(piece);
        }

        if(this.blackPieces.contains(piece))
        {
            blackPieces.remove(piece);
        }
    }

    private int[] getSkipLocation(int[] startLoc, int[] endLoc)
    {
        int xLoc = (startLoc[0] + endLoc[0]) / 2;
        int yLoc = (startLoc[1] + endLoc[1]) / 2;

        return new int[] {xLoc, yLoc};
    }
    public void setResign()
    {
    	resign = true;
    }
    public boolean getResign()
    {
    	return resign;
    }
    public int[][] getBoardForDisplay()
    {
        int[][] board = new int[8][8];

        for(Piece piece : redPieces)
        {
            int[] pieceLoc = piece.getLocation().clone();
            if(piece.getKing())
            {
                board[pieceLoc[0]][pieceLoc[1]] = 5;
            }
            else
            {
                board[pieceLoc[0]][pieceLoc[1]] = 2;
            }
        }

        for(Piece piece : blackPieces)
        {
            int[] pieceLoc = piece.getLocation().clone();
            if(piece.getKing())
            {
                board[pieceLoc[0]][pieceLoc[1]] = 4;
            }
            else
            {
                board[pieceLoc[0]][pieceLoc[1]] = 1;
            }
        }

        return board;
    }
    

    public void clone (Board b)
    {
    	b.redTurn = this.redTurn;
    	b.gameOver = this.gameOver;
    	b.redPieces = (ArrayList<Piece>) this.redPieces.clone();
    	b.blackPieces = (ArrayList<Piece>) this.blackPieces.clone();
    	b.redWin = this.redWin;
    	b.blackWin = this.blackWin;
    	b.resign = this.resign;
    }
    
    public boolean pieceCanTakeAgain(Piece piece)
    {
    	piece.updateValidMoveLocations();
    	if(!getValidTakesForPiece(piece).isEmpty())
    	{
    		return true;
    	}
    	
    	return false;

    }

}
