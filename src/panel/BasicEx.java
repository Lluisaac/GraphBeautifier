package panel;

import javax.swing.JFrame;

import graph.Graph;

public class BasicEx extends JFrame
{
	private static final long serialVersionUID = 710008495441621312L;
	
	private Surface surface;
	
	public BasicEx(Graph graph) {

        initUI(graph);
    }

    private void initUI(Graph graph) {

        this.surface = new Surface(graph);
		this.add(this.getSurface());

        setTitle("Graph beautifier");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	public Surface getSurface()
	{
		return surface;
	}
}
