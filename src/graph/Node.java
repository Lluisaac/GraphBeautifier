package graph;

import java.util.ArrayList;
import java.util.List;

public class Node
{
	private static final int ACCELERATION = 50;

	private static final int ACCELERATION_FACTOR = 100 / ACCELERATION;

	private static final double OFFSET_FLEXIBILITY = 0.05d;

	private List<Node> adjacent;
	
	private Coords coords;
	
	private List<Coords> moves;

	public Node()
	{
		this.adjacent = new ArrayList<>();
		this.setCoords(new Coords());
		this.moves = new ArrayList<>();
	}
	
	public Node(List<Node> nodes)
	{
		this();
		for(Node node : nodes)
		{
			this.add(node);
		}
	}
	
	public Coords getCoords()
	{
		return coords;
	}

	public void setCoords(Coords coords)
	{
		this.coords = coords;
	}

	public void add(Node node)
	{
		this.adjacent.add(node);
		
		if (!node.isAdjacent(this))
		{
			node.add(this);
		}
	}
	
	public boolean remove(Node node)
	{
		return this.adjacent.remove(node);
	}

	public boolean isAdjacent(Node node)
	{
		return this.adjacent.contains(node);
	}

	public List<Node> getAdjacent()
	{
		return this.adjacent;
	}

	public void repellFromOther(Node second, int strength)
	{
		double distance = this.coords.distance(second.coords);
		double ajuster = -this.ajuster(distance, strength);
		
		if (distance < strength)
		{
			Coords other = new Coords(this.coords);
			
			other.moveByAwayFrom(ajuster * this.petitPas(ajuster, strength), second.coords);
			
			this.moves.add(other.getDifference(this.coords));			
		}
	}

	public void attractToAdjacent(int strength)
	{
		for (Node node : this.adjacent)
		{
			double distance = this.coords.distance(node.coords);
			double ajuster = -this.ajuster(distance, strength);
			
			if (distance > strength)
			{				
				Coords other = new Coords(this.coords);
				
				other.moveByAwayFrom(ajuster * this.petitPas(ajuster, strength), node.coords);
				
				this.moves.add(other.getDifference(this.coords));
			}
		}
	}
	
	private double petitPas(double ajuster, int strength)
	{
		double ratio = ajuster / strength;
		return Math.abs(ratio) < OFFSET_FLEXIBILITY ? 0 : (ratio * ratio)/ACCELERATION_FACTOR;
	}

	public double ajuster(double base, double voulue)
	{
		return -((base / voulue) - 1) * voulue;
	}

	public double getDistanceFrom(Node second)
	{
		return this.getCoords().distance(second.getCoords());
	}

	public void applyMovement()
	{
		this.coords.add(this.moves);
		//System.out.println(Arrays.toString(this.moves.toArray()));
		this.moves.clear();
	}
}
