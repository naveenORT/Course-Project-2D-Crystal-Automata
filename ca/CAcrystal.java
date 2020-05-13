package edu.neu.csye6200.ca;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;
import edu.neu.csye6200.ui.CACanvas;

/**
	* CAcrystal class helps to create crystal object & stores multiple generations of crystal in a thread-safe arraylist.
	* CAcrystal class is observed by CAcanvas class in the UI section
	* Any change in the state of crystal is simulated by CAcanvas class
*/
public class CAcrystal extends Observable implements Runnable
{
	private int Crystal_No;
	private CAcell Base_Cell;
	private CArule Rule;
	ArrayList<CAcrystal> Crystal_Set = new ArrayList<CAcrystal>();
	private static List<CAcrystal> Sync_Set = null;
	private CAcrystal crystal;
	private  int gen_limit=5;
	private static CACanvas canvasObserver = null;
	private static Logger log = Logger.getLogger(CAcrystal.class.getName());
	private CAcell first_gen = new CAcell();

	synchronized public CAcell getFirst_gen() {
		return first_gen;
	}

	synchronized public void setFirst_gen(CAcell first_gen) {
		this.first_gen = first_gen;
	}

	public CAcrystal(CAcell Base_Cell, CArule Rule, int gen_limit)
	{	
		this.Base_Cell = Base_Cell;
		this.Rule = Rule; 
		this.gen_limit = gen_limit;		 
		canvasObserver = CACanvas.instance(); //getting the unique instance
		this.addObserver(canvasObserver); // making subscription				
	}
	
	public void add_generations(CAcrystal crystal)
	{
	Sync_Set.add(crystal);
	log.info("Added generation, notifying observers");
	setChanged(); 
	notifyObservers(crystal); // Indicate that a generation has been added
	}

	public void remove_generations()
	{
	Sync_Set = null;
	Base_Cell = null;
	Rule = null;
	crystal = new CAcrystal(Base_Cell,Rule, gen_limit);
	setChanged();
	notifyObservers(0); // Notifies the observers upon reset event by sending integer 0.
	}	
	
	public void setCrystal(CAcrystal crystal) {
		this.crystal = crystal;
	}

	public List<CAcrystal> getSync_Set() {
		return Sync_Set;
	}

	public void setSync_Set(List<CAcrystal> sync_Set) {
		Sync_Set = sync_Set;
	}

	@Override
	public void run()  //implementing run method for concurrent operation
	{
		if(Crystal_Set.isEmpty())
		{
			Sync_Set = Collections.synchronizedList(Crystal_Set);
			Base_Cell = new CAcell(21);
			crystal = new CAcrystal(Base_Cell,Rule, gen_limit);
			crystal.setFirst_gen(Base_Cell);
			log.info("created base cell");
		
			for(int i = 1; i < crystal.getGen_limit()+1 ; i++) {
		
				Rule.rule_selector(crystal);
				add_generations(crystal);	
				log.info("Created generation number - " + i );
				try {
					Thread.sleep(4000L); // Delay growth by 4 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
		}		
		}
	
	public List<CAcrystal> copy_set() {
		return Crystal_Set;
	}

	public int getGen_limit() {
		return gen_limit;
	}

	public void setGen_limit(int gen_limit) {
		this.gen_limit = gen_limit;
	}

	public int getCrystal_No() {
		return Crystal_No;
	}

	public void setCrystal_No(int crystal_No) {
		Crystal_No = crystal_No;
	}

	public  CAcell getBase_Cell() {
		return Base_Cell;
	}

	public void setBase_Cell(CAcell base_Cell) {
		Base_Cell = base_Cell;
	}

	public CArule getRule() {
		return Rule;
	}

	public void setRule(CArule rule) {
		Rule = rule;
	}

	public CACanvas getCanvasObserver() {
		return canvasObserver;
	}

	public void setCanvasObserver(CACanvas canvasObserver) {
		this.canvasObserver = canvasObserver;
	}

	public CAcrystal getCrystal() {
		return crystal;
	}

	public void setX(CAcrystal crystal) {
		this.crystal = crystal;
	}
}



