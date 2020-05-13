package edu.neu.csye6200.ui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 * A  abstract application class
 * @author MMUNSON
 *
 */
public abstract class CAApp implements ActionListener, WindowListener {
	protected JFrame frame = null;
 
	
	public CAApp() {
		initGUI();
	}
    public void initGUI() {
    	frame = new JFrame();
		frame.setTitle("JUIApp2");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //JFrame.DISPOSE_ON_CLOSE)
		
		frame.addWindowListener(this); // used for updating window's position 
		frame.setLayout(new BorderLayout());
		frame.add(getMainPanel(), BorderLayout.WEST);
	
    }
    

    public abstract JPanel getMainPanel() ;  //abstract function for data security
    
    
    public void showUI() {
    	
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	frame.setVisible(true); // The UI is built, so display it;
     
            }
        });
    	
    }

    /**
     * Shut down the application
     */
    public void exit() {
    	frame.dispose();
    	System.exit(0);
    }   
}
