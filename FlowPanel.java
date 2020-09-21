//package FlowSkeleton;

import javax.swing.JPanel;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.Color;

public class FlowPanel extends JPanel implements Runnable{
	Terrain land;
	BufferedImage waterLayer; //overlay to show water on screen
	boolean state;
	

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

	/** Adds the 3 units of water to the Terrain at the 2D row and colum index
	 * @param x row index
	 * @param y column index
	 */
	public void addWater(int x, int y){
		land.surface[x][y].addWaterClick();
	}

	
	/** Checks the terrain for water and repaints the waterLayer overlay accordingly
	 * Also removes water if it reaches the edges
	 */
	public void run() {	
		waterLayer = new BufferedImage(land.dimx, land.dimy, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < land.dimx; i++){
			for (int j = 0; j < land.dimy; j++){

				Water current = land.surface[i][j];

				if (current.hasWater()){
					if(!Util.atBorder(i, j, land.dimx, land.dimy)){
						waterLayer.setRGB(i,j,new Color(0,0,255).getRGB());
					}
					
					else{
						land.surface[i][j].setNoWater();
					}
				}
			}
		}	
	    repaint();
	}

}
