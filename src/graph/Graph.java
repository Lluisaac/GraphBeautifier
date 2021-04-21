package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Graph
{
	private List<Node> nodes;
	
	private Random rand;

	public Graph()
	{
		this.nodes = new ArrayList<>();
		this.rand = new Random();
	}

	public List<Node> getNodes()
	{
		return this.nodes;
	}

	public Node getNode(int i)
	{
		return this.nodes.get(i);
	}

	public void addNodes(int number)
	{
		for(int i = 0; i < number; i++)
		{
			this.addNode();
		}
	}

	public Node addNode()
	{
		Node node = new Node();

		this.nodes.add(node);

		return node;
	}

	private void buildEdges(float p)
	{
		for (int i = 0; i < this.nodes.size() - 1; i++)
		{
			for( int j = i + 1; j < this.nodes.size(); j++)
			{
				Node first = this.nodes.get(i);
				Node second = this.nodes.get(j);

				if(this.rand.nextFloat() <= p && !first.isAdjacent(second))
				{
					first.add(second);
				}
			}
		}
	}

	private void assignCoordinates(int spreadX, int spreadY)
	{
		for (Node node : this.nodes)
		{
			Coords c = node.getCoords();
			c.x = this.rand.nextInt(spreadX * 2) - spreadX;
			c.y = this.rand.nextInt(spreadY * 2) - spreadY;
		}
	}

	public static Graph generateRandom(int i, float p, int spreadX, int spreadY)
	{
		Graph graph = new Graph();

		graph.addNodes(i);
		graph.buildEdges(p);
		graph.assignCoordinates(spreadX, spreadY);

		return graph;
	}
	
	public void simulate(int strength)
	{
		attractToAdjacent(strength);
		
		repellFromOthers(strength);
		
		applyMovements();
		
		System.out.println();
	}

	private void applyMovements()
	{
		for (Node node : this.nodes)
		{
			node.applyMovement();
		}
	}

	private void attractToAdjacent(int strength)
	{
		for (Node node : this.nodes)
		{
			node.attractToAdjacent(strength / 2);
		}
	}

	private void repellFromOthers(int strength)
	{
		for (int i = 0; i < this.nodes.size(); i++)
		{
			for( int j = 0; j < this.nodes.size(); j++)
			{
				if (i != j)
				{					
					Node first = this.nodes.get(i);
					Node second = this.nodes.get(j);
					
					first.repellFromOther(second, strength / 2);
				}
			}
		}
	}

	public void recenterAll(int offsetX, int offsetY)
	{
		Coords center = getCenterCoords();
		Coords ideal = new Coords(offsetX, offsetY);
		
		Coords toMove = new Coords(-(center.x - ideal.x), -(center.y - ideal.y));
		
		for (Node node : this.nodes)
		{
			Coords c = node.getCoords();
			
			c.add(toMove);
		}
	}

	private Coords getCenterCoords()
	{
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		
		for (Node node : this.nodes)
		{
			Coords c = node.getCoords();
			
			if (c.x > maxX)
			{
				maxX = c.x;
			}
			
			if (c.y > maxY)
			{
				maxY = c.y;
			}
			
			if (c.x < minX)
			{
				minX = c.x;
			}
			
			if (c.y < minY)
			{
				minY = c.y;
			}
		}

		double centerX = (maxX + minX) / 2;
		double centerY = (maxY + minY) / 2;
		
		return new Coords(centerX, centerY);
	}
}
