

public class Coordinate{
	
	public int X,Y;
	
	Coordinate(){
		X=-1;
		Y=-1;
	}
	
	Coordinate(int in_x, int in_y){
		X = in_x;
		Y = in_y;
	}
	
	public void setX(int in_x){
		X = in_x;
	}
	
	public void setY(int in_y){
		Y = in_y;
	}
	
	public boolean isEmpty(){
		return (X == -1 || Y == -1);
	}
	
	public String toString(){
		return "(" + X + "," + Y + ")";
	}
	
	public void reset(){
		X = -1;
		Y = -1;
	}

	public int getX(){
		return this.X;
	}

	public int getY(){
		return this.Y;
	}
}
