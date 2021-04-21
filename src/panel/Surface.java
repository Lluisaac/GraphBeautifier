package panel;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import graph.Coords;
import graph.Graph;
import graph.Node;

public class Surface extends JPanel
{
	private static final long serialVersionUID = 6328200030462566259L;
	
	private static final int NODE_SIZE = 35;
	
	private Graphics2D drawing;
	private Graph graph;
	
	public Surface(Graph graph)
	{
		this.setGraph(graph);
	}

	public Graph getGraph()
	{
		return this.graph;
	}

	public void setGraph(Graph graph)
	{
		this.graph = graph;
	}

	private void doDrawing(Graphics g) {

        this.drawing = (Graphics2D) g;
        this.paintGraph();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
    
    public int getOffset()
    {
    	return (NODE_SIZE / 2);
    }
    
    public void paintGraph()
    {
    	int i = 0;
    	for (Node node : this.getGraph().getNodes())
    	{
    		Coords coord = node.getCoords();
    		this.drawing.fillOval((int) coord.x, (int) coord.y, NODE_SIZE, NODE_SIZE);
    		
    		this.drawing.drawString("" + i, (int) coord.x, (int) coord.y);
    		
    		for (Node other : node.getAdjacent())
    		{
    			Coords otherCoord = other.getCoords();
        		this.drawing.drawLine((int) coord.x + this.getOffset(), (int) coord.y + this.getOffset(), (int) otherCoord.x + this.getOffset(), (int) otherCoord.y + this.getOffset());
    		}
    		i++;
    	}
    	
    	this.getGraph().recenterAll(this.getWidth()/2, this.getHeight() / 2);
    }
}
