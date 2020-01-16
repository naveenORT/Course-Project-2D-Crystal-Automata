package edu.neu.csye6200.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.neu.csye6200.ca.CAcell;
import edu.neu.csye6200.ca.CArule;
import edu.neu.csye6200.ca.CAcrystal;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * A Test application for the Wolfram Celular Automaton application
 * WolfApp extends abstract class CAApp
 * @author MMUNSON
 *
 */
	public class WolfApp extends CAApp {

		//App variables
		private CAcell first_gen;	
		private HashMap<Integer, CArule> rules;
		private ArrayList<String> Rule_Number;
		private static Logger log = Logger.getLogger(WolfApp.class.getName());	
		private Thread growThread;
		private CAcrystal crystal;
		private int ruleCount;
		private static int MAX_GENERATIONS = 10;
		private boolean crystal_created = false;
		
		//Panel items
		protected JPanel mainPanel = null;
		protected JPanel NorthPanel = null;
		protected JButton startBtn = null;
		protected JButton stopBtn = null;
		protected JButton pauseBtn = null;
		private CACanvas caPanel = null;
		private String[] genNumbers; //numbers in generation combo box
		private String[] rNames;
		protected JComboBox<String> ruleList;
		protected JComboBox<String> generationList;
		protected JLabel title = null;
		protected JButton resetBtn = null;
		protected JLabel ruleLabel = null;
		protected JLabel genLabel = null;
		protected JButton viewRulesBtn = null;
		
		
    /*
     * WoldApp constructor
     */
    public WolfApp() 
    	{
    	frame.setSize(500,500);     //setting size of pixels
		frame.setTitle("WolfApp");		
    	showUI();
    	}
    	
	@Override
	public JPanel getMainPanel() {   // mainpanel holding options panel & canvas panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		caPanel = CACanvas.instance();		
		mainPanel.add(BorderLayout.CENTER, caPanel);
		mainPanel.add(BorderLayout.WEST, getNorthPanel());
		
		return mainPanel;
	}


    public JPanel getNorthPanel()  //options panel design & layout
    { 
		
   	   caPanel = CACanvas.instance();
    	createDefaultRule();
	
		NorthPanel = new JPanel();
		NorthPanel.setBackground(Color.gray);
	
		title = new JLabel("CRYSTAL GROWTH APP"); //title
		title.setForeground(Color.WHITE);

     	frame.add(caPanel, BorderLayout.CENTER); // Add to the center of our frame
    	frame.setVisible(true); // The UI is built, so display it
			
		viewRulesBtn = new JButton("View Rules"); //view rules button
		ruleLabel = new JLabel("Rule"); // label indicating rule selection
		ruleLabel.setForeground(Color.WHITE);

		genLabel = new JLabel("Number of Generations"); //label indicating #generation selection
		genLabel.setForeground(Color.WHITE);

		rNames = new String[rules.size()];
		int j = 0;
		for (String name : Rule_Number) {
			rNames[j] = name;
			j++;
		} 
		ruleList = new JComboBox<String>(rNames); //Combo box to select rule

		genNumbers = new String[MAX_GENERATIONS];
		for (int i = 0; i < MAX_GENERATIONS; i++) {
			genNumbers[i] = Integer.toString(i + 1);
		}
		generationList = new JComboBox<String>(genNumbers); // combo box to select generation limit

		final JButton startBtn = new JButton("Start"); // start button declared final to control from action events
		startBtn.setEnabled(true);
				startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (growThread != null && growThread.isAlive()) {
					growThread.resume();
				} else {
					crystal = new CAcrystal(first_gen,rules.get(ruleList.getSelectedIndex()), Integer.parseInt((String) generationList.getSelectedItem()));
					growThread = new Thread(crystal); // creating thread for concurrency
					growThread.start();
				}
				JButton source = (JButton) arg0.getSource();
				source.setText("Start");
				source.setEnabled(false);
			}
		});

		pauseBtn = new JButton("Pause"); // Pause button
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (growThread != null) {
					growThread.suspend();  //resuming thread execution
					startBtn.setText("Resume"); // change start button text to "Resume"
					startBtn.setEnabled(true);
				}
			}
		});

		resetBtn = new JButton("Reset"); // reset button
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (growThread != null) {
					growThread.stop();   // stopping thread execution
					growThread = null;
					crystal.remove_generations();
					startBtn.setEnabled(true);
				}
			}
		});
		DesignGridLayout Layout = new DesignGridLayout(NorthPanel); // gridlayout for alignment

		Layout.row().center().add(title);
		for (int i = 0; i < 5; i++) {
			Layout.row().center().add(new JLabel("")); // Aligning the panel using grid layout
		}

		Layout.row().left().add(ruleLabel, genLabel); // Aligning the panel using grid layout
		Layout.emptyRow();
		Layout.row().left().add(ruleList, generationList); // Aligning the panel using grid layout
		for (int i = 0; i < 25; i++) {
			Layout.row().left().add(new JLabel(""));
		}

		Layout.row().left().add(startBtn);
		Layout.emptyRow();
		Layout.row().left().add(pauseBtn);
		Layout.emptyRow();
		Layout.row().left().add(resetBtn); 
	
		return NorthPanel;
	}

    public boolean isCrystal_created() {
		return crystal_created;
	}
	public void setCrystal_created(boolean crystal_created) {
		this.crystal_created = crystal_created;
	}
      
	private void createDefaultRule()  // creating rule definitions inside rule combo box 
	{
		rules = new HashMap<Integer, CArule>();
		Rule_Number = new ArrayList<String>();	
	
		CArule rule1 = new CArule("1");
		rules.put(ruleCount, rule1);
		Rule_Number.add(rule1.getRule_Number());
		ruleCount++;
		
		CArule rule2 = new CArule("2");
		rules.put(ruleCount, rule2);
		Rule_Number.add(rule2.getRule_Number());
		ruleCount++;
	
		CArule rule3 = new CArule("3");
		rules.put(ruleCount, rule3);
		Rule_Number.add(rule3.getRule_Number());
		
	}
    
    @Override
	public void actionPerformed(ActionEvent ae) //status update loggers 
    {
		log.info("We received an ActionEvent " + ae);
		if (ae.getSource() == startBtn)
			System.out.println("Start pressed");
		else if (ae.getSource() == stopBtn)
			System.out.println("Stop pressed");
	}

	@Override
	public void windowOpened(WindowEvent e) {
		log.info("Window opened");
	}

	@Override
	public void windowClosing(WindowEvent e) {	
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
		//log.info("Window iconified");
	}

	@Override
	public void windowDeiconified(WindowEvent e) {	
		//log.info("Window deiconified");
	}

	@Override
	public void windowActivated(WindowEvent e) {
		log.info("Window activated");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {	
		log.info("Window deactivated");
	}
	
	public static void main(String[] args) {
		WolfApp wapp = new WolfApp();
		log.info("WolfApp started");
	}
}
