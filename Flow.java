import javax.swing.*;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.image.*;
//import java.awt.Color;


public class Flow{
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static FlowPanel fp;
	static BufferedImage waterLayer;
	static boolean on;
	static int count = 0; //random variable to increment
	static Thread threads[] = new Thread[4];
	static boolean canStart = false;
	static int timeStepCounter = 0;

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
		fp.addMouseListener(new MouseActions(fp));

		g.add(fp);

		on = false;

		// Create Button Panel b
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
		
		// End Button
		JButton endB = new JButton("End");

		endB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
				System.exit(0);

			}
		}); 
		//End Button

		// Play Button
		JButton Start = new JButton("Play");

		Start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on = true;
				
			}
		}); 
		//Play Button

		//Pause Button
		JButton Pause = new JButton("Pause");

		Pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on = false;

			}
		}); 
		//Pause Button

		//Reset Button
		JButton Reset = new JButton("Reset");

		Reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Reset overlay and counter
				on = false;
				for(int i = 0; i < fp.land.dimx; i++){
					for (int j = 0; j < fp.land.dimy; j++){
						if (fp.land.surface[i][j].hasWater()){
							fp.land.surface[i][j].setNoWater();
						}
					}
				}
				timeStepCounter = 0;
			}
		}); 
		//Reset Button

		JPanel Counter = new JPanel();
		JTextField counterField = new JTextField(11);
	
		counterField.setText("Sim Step: " + Integer.toString(timeStepCounter));
		Counter.add(counterField);


		//Add Buttons to button panel
		b.add(Start);
		b.add(Pause);
		b.add(Reset);
		b.add(endB);
		b.add(Counter);

		//Add buttons to g the main panel
		g.add(b);
			
		frame.setSize(frameX, frameY+50);	// a little extra space at the bottom for buttons
      	frame.setLocationRelativeTo(null);  // center window on screen
      	frame.add(g); //add contents to window
        frame.setContentPane(g);
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
		fpt.start();

		canStart = true;

		//Will infinitely loop with a delay of 1ms between each loop to allow the GUI to update
		Timer t = new Timer(1,new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (canStart){
					timeStepCounter = loop();
					counterField.setText("Sim Step: " + Integer.toString(timeStepCounter));

				}
			}
		});
		
		t.start();
	}


	public static int loop(){

		if(on){	
			timeStep();			
			timeStepCounter++;
		}

		fp.run();

		return timeStepCounter;
	}


	public static void timeStep(){

		int size = (fp.land.dim()-1)/4;
		Thread[] threads = new Thread[4];

				
		for (int i = 0; i < 4; i++){					
			threads[i] = new Thread(new sim(i*size,(i+1)*size, fp.land));
		}
		
		for (int i = 0; i < 4; i++){
			threads[i].start();
		}
		try{
			for (int i = 0; i < 4; i++){
				threads[i].join();
			}
		}

		catch(InterruptedException e){
			System.out.println("Interrupted");
		}

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
		landdata.readData(args[0]);
		
		frameX = landdata.getDimX();
		frameY = landdata.getDimY();
		SwingUtilities.invokeLater(()->setupGUI(frameX, frameY, landdata));

	}
}

/** Handles Mouse Events 
 * @author Jet Hendricks
*/
class MouseActions implements MouseListener{

	private FlowPanel fp;

	/**Stores a FlowPanel object to be altered on click
	 * @param fp FlowPanel object to be changed
	 */
	public MouseActions(FlowPanel fp){
		this.fp = fp;	
		
	}

	/** On Mouse click will add 3 units of water to each Water item outwards 3 units in each direction from the click */
	@Override
	public void mouseClicked(MouseEvent click){
		int x = click.getX();
		int y = click.getY();

		for (int i = -3; i < 4; i++){
			for (int j = -3; j < 4; j++){
				if (!Util.isOut(x + i, y + j, fp.land.dimx, fp.land.dimy)){
					fp.addWater(x+i,y+j);
				}
			}
		}
		
		fp.run();
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