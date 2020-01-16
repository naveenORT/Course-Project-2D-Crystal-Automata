package edu.neu.csye6200.ca;
import java.util.ArrayList;
/**
 * Base class which defines state of the cell
 * @author Naveen Rajendran
 *
 */

public class CAcell {

	private int size;
	private int crystal_cell [][];
	private int mid_point = size/2;
/*	
	Base cell of the crystal, used for creating generations
*/	
	public CAcell(int size)
	{	
		this.size = size;
		this.mid_point = size/2;
		crystal_cell = new int [size][size]; // constructing 2d grid to store 0's & 1's
		initial_cellvalue();
	}
	public CAcell()
	{
	}
	
	public void initial_cellvalue()        // fixing initial central lattice
	{		
		for(int x = 0; x < size; x++)
		{	
			for (int y = 0; y < size; y++ )
			{
				crystal_cell[x][y] = 0;
				if((x == mid_point) && (y == mid_point))
				{
					crystal_cell[x][y] = 1;
				}
			}	
	}}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int[][] getCrystal_cell() {
		return crystal_cell;
	}

	public void setCrystal_cell(int[][] crystal_cell) {
		this.crystal_cell = crystal_cell;
	}

	public int getMid_point() {
		return mid_point;
	}

	public void setMid_point(int mid_point) {
		this.mid_point = mid_point;
	}		
}
		

