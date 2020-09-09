//package FlowSkeleton;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class Flow{
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static FlowPanel fp;
	static BufferedImage waterLayer;

	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX,int frameY,Terrain landdata) {
		
		Dimension fsize = new Dimension(800, 800);
    	JFrame frame = new JFrame("Waterflow"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().setLayout(new BorderLayout());
    	
      	JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
   
		fp = new FlowPanel(landdata);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		fp.addMouseListener(new MouseActions(fp,frame));

		// to do: add Listeners and buttons
		g.add(fp);
		JPanel b = new JPanel();
	    b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
		JButton endB = new JButton("End");

		// add the listener to the jbutton to handle the "pressed" event

		endB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// to do ask threads to stop
				frame.dispose();
			}
		});
		
		b.add(endB);
		g.add(b);
    	
		frame.setSize(frameX, frameY+50);	// a little extra space at the bottom for buttons
      	frame.setLocationRelativeTo(null);  // center window on screen
      	frame.add(g); //add contents to window
        frame.setContentPane(g);
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
	
		
	public static void main(String[] args) {
		Terrain landdata = new Terrain();
		
		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java -jar flow.java intputfilename");
			System.exit(0);
		}
				
		// landscape information from file supplied as argument
		// 
		landdata.readData(args[0]);
		
		frameX = landdata.getDimX();
		frameY = landdata.getDimY();
		SwingUtilities.invokeLater(()->setupGUI(frameX, frameY, landdata));
		
		// to do: initialise and start simulation
	}
}

class MouseActions implements MouseListener{

	FlowPanel fp;
	

	public MouseActions(FlowPanel fp, JFrame frame){
		this.fp = fp;	
	
	}
	
	@Override
	public void mouseClicked(MouseEvent click){
		int x = click.getX();
		int y = click.getY();

		fp.addWater(x,y);
		fp.waterLayer.setRGB(x,y,new Color(0,0,255).getRGB());
	}

	@Override
	public void mouseReleased(MouseEvent released){

	}
			
	@Override
	public void mousePressed(MouseEvent pressed){

	}
	
	@Override
	public void mouseEntered(MouseEvent entered){
				
	}

	@Override
	public void mouseExited(MouseEvent exited){

	}
		
}