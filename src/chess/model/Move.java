package chess.model;

public class Move
{
	public int downward;
	public int rightward;
	
	//Move Types:
	// 0 - normal move
	// 1 - only move if enemy piece is at destination
	public int moveType;
	// if limitDistance only move the amount specified by the up, down, left, and right
	public boolean limitDistance;
	// change movement direction based on team
	public boolean mirrorWithTeam;
	// can only make move if the piece hasn't been moved before
	public boolean onlyOnFirstMove;
	
	public Move(int up, int down, int left, int right, boolean limitDistance)
	{
		this.downward -= up;
		this.downward += down;
		this.rightward -= left;
		this.rightward += right;
		this.limitDistance = limitDistance;
		
		this.moveType = 0;
		this.mirrorWithTeam = false;
		this.onlyOnFirstMove = false;
	}
	
	public Move(int up, int down, int left, int right, boolean limitDistance, int moveType, boolean mirrorWithTeam, boolean onlyOnFirstMove)
	{
		this.downward -= up;
		this.downward += down;
		this.rightward -= left;
		this.rightward += right;
		this.limitDistance = limitDistance;
		
		this.moveType = moveType;
		this.mirrorWithTeam = mirrorWithTeam;			
		this.onlyOnFirstMove = onlyOnFirstMove;
	}
	
	public String toString()
	{
		String built = "";
		built += downward + " down, " + rightward + " right ";
		built += limitDistance ? "limited" : "unlimited";
		built += " ";
		built += mirrorWithTeam ? "mirrored" : "not mirrored";
		built += " ";
		built += onlyOnFirstMove ? "only first" : "";
		built += " ";
		built += moveType == 1 ? "only on capture" : "";
		return built;
		
	}
}
