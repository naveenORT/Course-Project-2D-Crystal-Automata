package edu.neu.csye6200.ui;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.awt.Color;	
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import javax.swing.JPanel;
import edu.neu.csye6200.ca.CAcell;
import edu.neu.csye6200.ca.CAcrystal;

public class CACanvas extends JPanel implements ActionListener,Observer {

	private Logger log = Logger.getLogger(CACanvas.class.getName());
    private Color col = null;
    private  static CACanvas instance = null;
    private static CAcell c2 = null;
	private boolean reset = false;
	int counter = 0;

	public CACanvas()
	{
		col = Color.white;
    }
	
    public void paint(Graphics g)
    {
       if(c2 == null && !reset)
		{   System.out.println("BackGround Draw");
			drawBG(g); // Our Added-on drawing		
		}
       else
		{
			System.out.println("Automata Drawing");
			drawCA(g);
		}    
    }
	
	public void drawBG(Graphics g)  // paint the background only if crystal is not created
	{
		int cellSize =21;
		log.info("Drawing cell automation " + counter++);
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, size.width, size.height);
		
		g2d.setColor(Color.RED);
		g2d.drawString("Cellular Automaton 2D", 10, 15);
		
		int maxRows = size.height / cellSize;
		int maxCols = size.width / cellSize;
		for (int j = 0; j < maxRows; j++) {
		   for (int i = 0; i < maxCols; i++) {
			   int redVal = validColor(i*5);
			   int greenVal = validColor(255-j*5);
			   int blueVal = validColor((j*5)-(i*2));
			   col = new Color(redVal, greenVal, blueVal);
			   // Draw box, one pixel less to create a black outline
			   paintRect( g2d, i*cellSize, j*cellSize + 20, cellSize-1, col); 
		   }
		}
		
	}
	private int validColor(int colorVal) 
	{
		if (colorVal > 255)
			colorVal = 255;
		if (colorVal < 0)
			colorVal = 0;
		return colorVal;
	}
	public void drawCA(Graphics g)   //when crystal is created draw the pattern
	{
		log.info("c2 created");
		int cellSize=2;
		log.info("Drawing cell automation " + counter++);
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, size.width, size.height);
		
		for(int i =0 ;i<c2.getSize();i++) {
			for(int j=0; j<c2.getSize();j++) {
				if(c2.getCrystal_cell()[i][j]==1)
					g2d.setColor(col.red);
				if(c2.getCrystal_cell()[i][j]==0)
					g2d.setColor(col.WHITE);
			g2d.fillRect(50 +(i*size.height/20)+20,50+((j*size.width)/40), 15, 15);
		}}	
	}	
	
	public void paintRect(Graphics2D g2d, int x, int y, int size, Color color) 
	{
			g2d.setColor(color);
			g2d.fillRect(x, y, size, size);
	}

		
	public static CACanvas instance() 
	{
		if (instance == null)
			instance = new CACanvas(); // Build if needed
		return instance; // Return the single copy
	}
	
	@Override
	public void update(Observable arg0, Object crystal)  // updating the pattern 
	{  

		CAcrystal cy;
		if (crystal instanceof Integer) { //The object passes will be integer 0 upon button click event of Reset
			reset = false;
			c2 = null;
			this.repaint();
			log.info("redrawing bg");
		} else {
			cy = (CAcrystal) crystal; 
			c2 = cy.getFirst_gen();
		//	layer_no++;
			this.revalidate();
			this.repaint();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

