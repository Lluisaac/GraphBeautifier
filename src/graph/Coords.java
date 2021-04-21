package graph;

import java.util.List;

public class Coords
{
	public double x;
	public double y;
	
	public Coords(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Coords(Coords other)
	{
		this(other.x, other.y);
	}
	
	public Coords()
	{
		this(0, 0);
	}

	public void add(Coords other)
	{
		this.x += other.x;
		this.y += other.y;
	}

	public void add(List<Coords> moves)
	{
		for (Coords c : moves)
		{
			this.add(c);
		}
	}
	
	public double distance(Coords other)
	{
		return Math.sqrt(((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)));
	}
	
	public void moveByAwayFrom(double dt, Coords other)
	{
		double d = this.distance(other);
		
		double t = dt / d;
		
		this.x = (((1.0 - t) * this.x) + (t * other.x));
		this.y = (((1.0 - t) * this.y) + (t * other.y));
	}
	
	public Coords getDifference(Coords other)
	{
		return new Coords(this.x - other.x, this.y - other.y);
	}
	
	@Override
	public String toString()
	{
		String str = "";
		
		str += "(" + this.x + ", " + this.y + ")";
		
		return str;
	}
}
