package edu.neu.csye6200.ca;

public class CArule 
	{
		private String Rule_Number; 		
		private int layer_no = 1;
		private int sum_of_neighbours;
		private int neighbour_count;
		private boolean done = false;
		
		public String getRule_Number() {
				return Rule_Number;
		}
		
		public void setRule_Number(String rule_Number) {
			Rule_Number = rule_Number;
		}
		
		public CArule(String Rule_Number)
		{
			this.Rule_Number = Rule_Number;		
		}
			
		/**
		Based on the rule given by user, crystal pattern is created
		Rule 1 - Diagonal growth of crystal
		Rule 2 - Based on total sum of neighbours
		Rule 3 - Based on no of neighboring cells 
		
		* Rules are given on the base of growth from mid point 
		*
		*/
		public void rule_selector (CAcrystal crystal)
		{
		int x;
		int y=0;
		
		switch(crystal.getRule().getRule_Number())
		
		{
		case "1":	
	
	        for(int i= crystal.getBase_Cell().getMid_point()-layer_no;i<=crystal.getBase_Cell().getMid_point()+layer_no;i=i+layer_no) {
				for(int j = crystal.getBase_Cell().getMid_point()-layer_no;j<=crystal.getBase_Cell().getMid_point()+layer_no;j=j+layer_no) {								
						if(crystal.getBase_Cell().getCrystal_cell()[i][j] == 0)
						{
							crystal.getBase_Cell().getCrystal_cell()[i][j] = 1;
						}
						
						}}
					 	
				layer_no++;
				y++;
				if(layer_no>5)
				{
					done = true;
				}	
				break;

		case "2":
	
				for(int i= crystal.getBase_Cell().getMid_point()-layer_no;
						i<=crystal.getBase_Cell().getMid_point()+layer_no;i++) {
					for(int j = crystal.getBase_Cell().getMid_point()-layer_no;
							j<=crystal.getBase_Cell().getMid_point()+layer_no;j++) {
						
							x=sum_of_neighbours(crystal.getBase_Cell(),i,j)%3;
							
								switch(x)
								{							
									case 0:
										crystal.getBase_Cell().getCrystal_cell()[i][j] = 1; break;					
									case 1:
										crystal.getBase_Cell().getCrystal_cell()[i][j] = 0; break;
									case 2:
										crystal.getBase_Cell().getCrystal_cell()[i][j] = 1; break;
										
									default : break;
								}
						
						}}
					System.out.println("\n");
					layer_no++; //Checking layer by layer from the center of the grid
					y++;
					if(layer_no>=crystal.getBase_Cell().getMid_point())
					{done=true; }
					break;				
		
		case "3":
					for(int i= crystal.getBase_Cell().getMid_point()-layer_no;
							i<=crystal.getBase_Cell().getMid_point()+layer_no;i++) {
					
						for(int j = crystal.getBase_Cell().getMid_point()-layer_no;
								j<=crystal.getBase_Cell().getMid_point()+layer_no;j++) { 
					
							x=check_neighbour(crystal.getBase_Cell(),i,j);
							//	System.out.print(+ i+","+j+","+ x);
							switch(x)
							{							
								case 0:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 1; break;					
								case 1:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 1; break;
								case 2:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 1; break;
								case 3:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 1; break;	
								case 4:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 0; break;	
								case 5:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 0; break;	
								case 6:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 0; break;	
								case 7:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 1; break;	
								case 8:
									crystal.getBase_Cell().getCrystal_cell()[i][j] = 1; break;	

								default : break;
								
							}	
						neighbour_count=0;
						}}
						System.out.println("\n");
						layer_no++;     //Checking layer by layer from the center of the grid
						y++;
					
						if(layer_no>=crystal.getBase_Cell().getMid_point())
						{done=true; }
						break;				
		}}
			
		
		public void print_grid(CAcrystal crystal)
		{
			for(int i = 0 ; i < crystal.getBase_Cell().getSize() ;i++) {	
				for(int j = 0 ; j < crystal.getBase_Cell().getSize() ;j++) {	
					System.out.print(crystal.getBase_Cell().getCrystal_cell()[i][j]);
				}
				System.out.println("\n");	
				}		
			System.out.println("\n");	
		}

		
		public int sum_of_neighbours(CAcell Base_Cell,int i, int j)  //calculating total neighbours values
		{
			sum_of_neighbours = Base_Cell.getCrystal_cell()[i-1][j] +  Base_Cell.getCrystal_cell()[i+1][j] 
								+ Base_Cell.getCrystal_cell()[i][j-1]+ Base_Cell.getCrystal_cell()[i][j+1] + 
								Base_Cell.getCrystal_cell()[i+1][j+1] + Base_Cell.getCrystal_cell()[i-1][j-1] + 
								Base_Cell.getCrystal_cell()[i-1][j+1] + Base_Cell.getCrystal_cell()[i+1][j-1] ; 
 
			return sum_of_neighbours;
		}
		public int check_neighbour(CAcell Base_Cell,int i,int j) //calculating total neighbours count
		{
	
				if(Base_Cell.getCrystal_cell()[i][j+1]==1) neighbour_count++;
				if(Base_Cell.getCrystal_cell()[i][j-1]==1) neighbour_count++;
				if(Base_Cell.getCrystal_cell()[i+1][j]==1) neighbour_count++;
				if(Base_Cell.getCrystal_cell()[i-1][j]==1) neighbour_count++;	
				if(Base_Cell.getCrystal_cell()[i-1][j-1]==1) neighbour_count++;
				if(Base_Cell.getCrystal_cell()[i+1][j+1]==1) neighbour_count++;
				if(Base_Cell.getCrystal_cell()[i+1][j-1]==1) neighbour_count++;
				if(Base_Cell.getCrystal_cell()[i-1][j+1]==1) neighbour_count++;	
				return neighbour_count;	
		}
	
		public int getLayer_no() 
		{
			return layer_no;
		}

		public void setLayer_no(int layer_no) 
		{
			this.layer_no = layer_no;
		}

		public int getSum_of_neighbours()
		{
			return sum_of_neighbours;
		}

		public void setSum_of_neighbours(int sum_of_neighbours) 
		{
			this.sum_of_neighbours = sum_of_neighbours;
		}

		public int getNeighbour_count()
		{
			return neighbour_count;
		}

		public void setNeighbour_count(int neighbour_count) 
		{
			this.neighbour_count = neighbour_count;
		}

		public boolean isDone() 
		{
			return done;
		}

		public void setDone(boolean done) 
		{
			this.done = done;
		}
	}