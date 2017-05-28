import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Reefer on 2/13/2017.
 */
public class Piece implements Serializable {

    public int[] location;
    public ArrayList<int[]> possibleMoves;
    public ArrayList<int[]> possibleTakes;
    private boolean isKing;
    private boolean isRed;

    public Piece(int xLoc, int yLoc)
    {
        possibleMoves = new ArrayList<int[]>();
        possibleTakes = new ArrayList<int[]>();
        isKing = false;
        isRed = false;
        int[] temp = {xLoc, yLoc};
        this.location = temp.clone();

        updateValidMoveLocations();
    }

    public void setKing()
    {
        this.isKing = true;
    }

    public void setRed()
    {
        this.isRed = true;
    }

    public void setBlack()
    {
        this.isRed = false;
    }

    public boolean getRed()
    {
        return this.isRed;
    }

    public boolean getKing()
    {
        return isKing;
    }

    public int[] getLocation()
    {
        return location;
    }

    public void setLocation(int[] newLocation)
    {
        this.location[0] = newLocation[0];
        this.location[1] = newLocation[1];
        updateValidMoveLocations();
        
    }

    public ArrayList<int[]> getPossibleMoves()
    {
        return possibleMoves;
    }

    public ArrayList<int[]> getPossibleTakes()
    {
        return possibleTakes;
    }

    public void updateValidMoveLocations()
    {
        // Moves
        int[] northWestMove = getMoveLocation(this.location, -1, 1);
        int[] northEastMove = getMoveLocation(this.location, 1, 1);
        int[] southWestMove = getMoveLocation(this.location, -1, -1);
        int[] southEastMove = getMoveLocation(this.location, 1, -1);

        if(validLocation(northWestMove))
        {
            possibleMoves.add(northWestMove);
        }

        if(validLocation(northEastMove))
        {
            possibleMoves.add(northEastMove);
        }

        if(validLocation(southWestMove))
        {
            possibleMoves.add(southWestMove);
        }

        if(validLocation(southEastMove))
        {
            possibleMoves.add(southEastMove);
        }

        // Takes
        int[] northWestTake = getMoveLocation(this.location, -2, 2);
        int[] northEastTake = getMoveLocation(this.location, 2, 2);
        int[] southWestTake = getMoveLocation(this.location, -2, -2);
        int[] southEastTake = getMoveLocation(this.location, 2, -2);

        if(validLocation(northWestTake))
        {
            possibleTakes.add(northWestTake);
        }

        if(validLocation(northEastTake))
        {
            possibleTakes.add(northEastTake);
        }

        if(validLocation(southWestTake))
        {
            possibleTakes.add(southWestTake);
        }

        if(validLocation(southEastTake))
        {
            possibleTakes.add(southEastTake);
        }

    }

    private int[] getMoveLocation(int[] currentLocation, int xDelta, int yDelta)
    {
        return new int[] {currentLocation[0] + xDelta, currentLocation[1] + yDelta};
    }

    public boolean validLocation(int[] location)
    {
        if(validateXLoc(location[0]) && validateYLoc(location[1]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validateXLoc(int x)
    {
        if(x >= 0 && x < 8)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validateYLoc(int y)
    {
        if(y >= 0 && y < 8)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
