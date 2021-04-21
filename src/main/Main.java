package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import graph.Graph;
import panel.BasicEx;

public class Main
{
	private static Graph graph;
	private static BasicEx ex;
	private static boolean doAnimation = true;
	private static Thread th;
	
	public static void main(String[] args)
	{
		Main.graph = Graph.generateRandom(20, 0.12f, 300, 300);
    	Main.ex = new BasicEx(Main.graph);
    	Main.ex.setVisible(true);
    	
        Main.th = new Thread() {
            public void run() {
                draw();
            }
        };
        
        Main.th.start();
        
        
        Main.ex.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mouseEntered(MouseEvent e){}
			public void mouseClicked(MouseEvent e){}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (Main.doAnimation)
				{
					Main.th.suspend();
				}
				else
				{
			        Main.th.resume();
				}
				
				Main.doAnimation = !Main.doAnimation;
			}
		});
	}
	
	private static void draw() {
				
	    while(true) {
	    	Main.ex.getSurface().repaint();
	    	Main.graph.simulate(450);
	        try {
	            Thread.sleep(25);
	        } 
	        catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
