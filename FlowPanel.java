//package FlowSkeleton;

import javax.swing.JPanel;
import java.awt.image.*;
import java.awt.Graphics;

public class FlowPanel extends JPanel implements Runnable{
	Terrain land;
	BufferedImage waterLayer; //overlay

	FlowPanel(Terrain terrain) {
		land=terrain;
		waterLayer = new BufferedImage(land.dimx, land.dimy, BufferedImage.TYPE_INT_ARGB);
	}


	// responsible for painting the terrain and water
	// as images
	@Override
    protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		  
		super.paintComponent(g);
		
		// draw the landscape in greyscale as an image
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
		}

		if (waterLayer != null){
			g.drawImage(waterLayer, 0, 0, null);
		}
	}

	public void addWater(int x, int y){
		land.surface[x][y].addWater();
		run();
	}
	
	public void run() {	
		// display loop here
		// to do: this should be controlled by the GUI
		// to allow stopping and starting
	    repaint();
	}

	
}

