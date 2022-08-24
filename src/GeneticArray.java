import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticArray {
	private int numOfEmployees = 30;  //number of rows
	private int numOfDays = 14;  //number of columns
	private int numOfLists = 50;
	private int min = 0;
	private int max = 3;
	private int morning = 8;
	private int afternoon = 8;
	private int night = 10;
	private int[][] array ;
	
	ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<ArrayList<Integer>>();
	Random rand = new Random();
	
	GeneticArray() {
		this.array = new int[numOfEmployees][numOfDays];
	}

	//Getters Setters
	public int[][] getArray() {
		return array;
	}
	
	public void setArray(int[][] array) {
		this.array = array;
	}
	
	public int getNumOfEmployees() {
		return numOfEmployees;
	}

	public void setNumOfEmployees(int numOfEmployees) {
		this.numOfEmployees = numOfEmployees;
	}

	public int getNumOfDays() {
		return numOfDays;
	}

	public void setNumOfDays(int numOfDays) {
		this.numOfDays = numOfDays;
	}
	
	//Other Methods
	
	
	// List Generator
	public void listGenerator(){
		
    	   ArrayList<Integer> singleList1 = new ArrayList<Integer>();
    	   ArrayList<Integer> singleList2 = new ArrayList<Integer>();
    	   ArrayList<Integer> singleList3 = new ArrayList<Integer>();
			
			for(int j = 0; j< numOfEmployees; j++){ // List of Mondays or Tuesdays Index '0' of listOfLists
				if(j < 10) singleList1.add(1);
				else if(j < 20) singleList1.add(2);
				else if(j < 25) singleList1.add(3);
				else singleList1.add(rand.nextInt(4));
			}
			listOfLists.add(singleList1); 
			
			for(int j = 0; j< numOfEmployees; j++){ // List of Wednesday or Friday Index '1' of listOfLists
				if(j < 5) singleList2.add(1);
				else if(j < 15) singleList2.add(2);
				else if(j < 20) singleList2.add(3);
				else singleList2.add(rand.nextInt(4));
			}
			listOfLists.add(singleList2); 
			
			for(int j = 0; j< numOfEmployees; j++){ // List of every other day  Index '2' of listOfLists
				if(j < 5) singleList3.add(1);
				else if(j < 10) singleList3.add(2);
				else if(j < 15) singleList3.add(3);
				else singleList3.add(rand.nextInt(4));
			}
			listOfLists.add(singleList3); 

    	   ArrayList<Integer> singleList = new ArrayList<Integer>();  // Wrong lists  Index '3' of listOfLists
    	   for(int j = 0; j< numOfEmployees; j++){
    		   singleList.add(rand.nextInt(4));
    	   }
    	   listOfLists.add(singleList); 

	}
	
	// For printing a specific list
	public void printLists(int list){
		
		ArrayList<Integer> singleList = new ArrayList<Integer>();
		
		for(int i=0; i < listOfLists.size(); i++){
			System.out.print("\nList ID: "+i+" ");
			singleList = listOfLists.get(i);
			System.out.println("single list size: "+singleList.size());
			for(int j=0; j<singleList.size(); j++){
				 System.out.print(singleList.get(j)+"|");
			}
		}
		
/*		
		singleList = listOfLists.get(list);
		System.out.println("Display of list "+list);
		for(int i=0; i<singleList.size(); i++){
		 System.out.print(singleList.get(i)+"|");
		}
*/		
	}
	
	//INITIALIZE

	public void initialization() {
		Random rand = new Random();			
		int [][]a = this.getArray();  			//get the array of this object GeneticArray
		double prob;
		
		ArrayList<Integer> singleList = new ArrayList<Integer>();
		ArrayList<Integer> balader = new ArrayList<Integer>();
		
		for(int i=0; i<numOfDays; i++){
			
			prob = Math.random();  // Choose randomly a probability prob e[0,1]
		  
			if(i%7==0){  // Monday
				singleList = listOfLists.get(0);
			}
			else if((i-1)%7==0){  // Tuesday
				singleList = listOfLists.get(0);
			}
			else if((i-2)%7==0){ // Wednesday
				if(prob >= 0.3){singleList = listOfLists.get(1);}   // If prob >= 0.3 pick a feasible list
				else singleList = listOfLists.get(3);               // Else pick a random list
			}
			else if((i-3)%7==0){  // Thursday 
				if(prob >= 0.3){singleList = listOfLists.get(2);}   // If prob >= 0.3 pick a feasible list
				else singleList = listOfLists.get(3);			    // Else pick a random list
			}
			else if((i-4)%7==0){ // Friday
				if(prob >= 0.3){singleList = listOfLists.get(1);}   // If prob >= 0.3 pick a feasible list
				else singleList = listOfLists.get(3);			    // Else pick a random list
			}
			else if((i-5)%7==0){  // Saturday
				singleList = listOfLists.get(2);
			}
			else if((i-6)%7==0){  //Sunday
				singleList = listOfLists.get(2);
			}
			

			for(int l=0; l < singleList.size(); l++){balader.add(singleList.get(l));} // balader list helps to pick all the elements of
			 																		  // singleList without repetitions 
			for(int j=0; j < numOfEmployees; j++){
				int randomIndex = rand.nextInt(balader.size());
				int randomElement = balader.get(randomIndex);
				a[j][i] = randomElement;
				balader.remove(randomIndex);
			}
		}
		
	}
	
	//PRINT ARRAY
	public void printArray() {
		int[][] a = this.getArray();
	    
		System.out.println("\nSchedule");
		for(int i = 0; i<numOfEmployees; i++)
		{
			System.out.print("Employee: "+i+"\t|");
		    for(int j = 0; j<numOfDays; j++)
		    {
		        System.out.print(a[i][j]+"|");
		    }
		    System.out.println();
		}
	}
	
	public boolean newCheckArray(){
		
		int[][] a = this.getArray();
		int [][] dayDuties = new int[3][numOfDays];
		boolean feasibility = true;
		
		for(int i = 0; i < 3; i++){             // initialize the array of employees
			for(int j = 0; j < numOfDays; j++){
				dayDuties[i][j] = 0;
			}
		}
		
		for(int i = 0; i < numOfEmployees; i++){
			for(int j = 0; j < numOfDays; j++){
			   if(a[i][j] == 1){  // Morning duty
				   dayDuties[0][j]++;
			   }else if(a[i][j] == 2){ // Afternoon duty
				   dayDuties[1][j]++;
			   }else if(a[i][j] == 3){ // Night's watch
				   dayDuties[2][j]++;
			   }
			}
		}
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < numOfDays; j++){
				if(i == 0){ // Morning checks
					if(j == 0 || j == 1 || j == 7 || j == 8){ // if it's Monday or Tuesday 
						if(dayDuties[i][j] < 10) feasibility = false;
					}else{ // every other day
						if(dayDuties[i][j] < 5) feasibility = false;
					}
				}else if(i == 1){ // Afternoon checks
					 if(j == 0 || j == 1 || j == 2 || j == 4 || j == 7 || j == 8 || j == 9 || j == 11){ // if it's Monday or Tuesday or Wednesday
						 if(dayDuties[i][j] < 10) feasibility = false;                                  // or Friday
					 }else{ // every other day 
						 if(dayDuties[i][j] < 5) feasibility = false;
					 }
				}else{ // Night's check
					if(dayDuties[i][j] < 5) feasibility = false;
				}
			}
		}
        return feasibility;
	}
	
	public int scoreArray() {
		int[][] a = this.getArray();
		int sumOfHours = 0;			//constraint 1
		int workDays2 = 0;           //constraint 2
		int consNights = 0;         //constraint 3
		int score = 0;
		int brakeAfter4Nights = 0; 	//constraint 7
		int workDays = 0; 			//constraint 8
		
		for(int i=0;i<numOfEmployees;i++) {
			workDays2 = 0;
			sumOfHours = 0;
			for(int j=0;j<numOfDays;j++) {
				
				if(a[i][j]==0) {       //if he has a brake(VISMA DILADI FILE MANOLI)
					workDays2 = 0;		//reset workDays
					consNights = 0;
				}
				else if(a[i][j]==1) {  //if it is morning
					sumOfHours = sumOfHours + morning;
					workDays2 = workDays2 + 1;
					if(workDays2>7) {  //if constraint 2 is not OK
						score = score + 1000;
						workDays2 = 0;
					}
					consNights = 0;		//reset
				}
				else if(a[i][j] == 2) {  //if it is afternoon
					sumOfHours = sumOfHours + afternoon;
					workDays2 = workDays2 + 1;
					if(workDays2>7) {	//if constraint 2 is not OK
						score = score + 1000;
						workDays2 = 0;
					}
					consNights = 0;		//reset
				}
				else {				  //if it is night
					sumOfHours = sumOfHours + night;
					workDays2 = workDays2 + 1;
					if(workDays2>7) {	//if constraint 2 is not OK
						score = score + 1000;
						workDays2 = 0;
					}
					
					consNights = consNights + 1;
					if(consNights>4) {	//if constraint 3 is not OK
						score= score + 1000;
						consNights = 0;
					}
				}
				if(j!=numOfDays-1) {    //Without this line we will have ArrayOutOfBounds
					if(a[i][j]==3 && a[i][j+1]==1) {		//if constraint 4 is not OK
						score = score + 1000;
					}
				}
				
				
				//------CONSTRAINT 5---//
				if(j!=numOfDays-1) {    //Without this line we will have ArrayOutOfBounds
					if(a[i][j]==2 && a[i][j+1]==1) {		//if constraint 5 is not OK
						score = score +800;
					}
				}
			
				
				
				//------CONSTRAINT 6---//
				if(j!=numOfDays-1) {    //Without this line we will have ArrayOutOfBounds
					if(a[i][j]==3 && a[i][j+1]==2) {		//if constraint 6 is not OK
						score = score +600;
					}
				}
				
				
				//------CONSTRAINT 7---//
				if(a[i][j]==3) {
					brakeAfter4Nights = brakeAfter4Nights + 1;
					if(brakeAfter4Nights == 4) {
						if(j<numOfDays-2) {		//Without this line we will have ArrayOutOfBounds
							if(a[i][j+1]!=0 | a[i][j+2]!=0) {
								score=score+100;	//if constraint 7 is not OK
							}
						}
						brakeAfter4Nights=0; //RESET
					}
				}
				
				//------CONSTRAINT 8---//
				if(a[i][j]!=0) {
					workDays = workDays+1;
					if(workDays==7) {
						if(j<numOfDays-2) {		//Without this line we will have ArrayOutOfBounds
							if(a[i][j+1]!=0 | a[i][j+2]!=0) {
								score=score+1;	//if constraint 8 is not OK
							}
						}
						workDays=0; //RESET
					}
				}
				
				//------CONSTRAINT 9---//
				if(j<numOfDays-2) {		//Without this line we will have ArrayOutOfBounds
					if(a[i][j]!=0 && a[i][j+1]==0 && a[i][j+2]!=0) {
						score=score+1;	//if constraint 9 is not OK
					}
				}
				
				//------CONSTRAINT 10---//
				if(j<numOfDays-2) {		//Without this line we will have ArrayOutOfBounds
					if(a[i][j]==0 && a[i][j+1]!=0 && a[i][j+2]==0) {
						score=score+1;	//if constraint 10 is not OK
					}
				}
				
				//------CONSTRAINT 11---//
				//if a[i][0] is Monday then the following cells is SK
				if(a[i][5]!=0) {
					if(a[i][6]==0) {
						score=score+1;
					}
				}
				if(a[i][12]!=0) {
					if(a[i][13]==0) {
						score=score+1;
					}
				}
				
				//------CONSTRAINT 12---//
				//if a[i][0] is Monday then the following cells is SK
				if(a[i][6]!=0) {
					if(a[i][5]==0) {
						score=score+1;
					}
				}
				if(a[i][13]!=0) {
					if(a[i][12]==0) {
						score=score+1;
					}
				}
				
				
				//------CONSTRAINT 13---//
				//if a[i][0] is Monday then the following cells is SK
				if(a[i][5]!=0 && a[i][6]!=0 && a[i][12]!=0 && a[i][13]!=0) {
					score=score+1;
				}
				
			}
			
			if(sumOfHours>70) {  //if constraint 1 is not OK
				score = score + 1000;
			}
			
		}
		return score;
	}
	
	public GeneticArray[] onePointCrossover(GeneticArray sched_1, GeneticArray sched_2){
		
		double pcross = 1;  // Probability of crossover
		
		int[][] a = sched_1.getArray();  // First Parent
		int[][] b = sched_2.getArray();  // Second Parent
		
		int crossPoint = rand.nextInt(numOfDays);  // This is the random crossover point
		
		GeneticArray[] heirs = new GeneticArray[2];  // Two heirs
		
		GeneticArray gn1 = new GeneticArray();
		GeneticArray gn2 = new GeneticArray();
		
		int[][] n1 = gn1.getArray(); // First elements of this heir come from parent 1 and the rest from parent 2
		int[][] n2 = gn2.getArray(); // First elements of this heir come from parent 2 and the rest from parent 1
		
		for(int i = 0; i < numOfEmployees; i++){
			for(int j = 0; j < numOfDays; j++){
				if(j < crossPoint){
					n1[i][j] = a[i][j];
					n2[i][j] = b[i][j];
				}else{
					n1[i][j] = b[i][j];
					n2[i][j] = a[i][j];
				}
			}
		}
		
		heirs[0] = gn1; 
		heirs[1] = gn2;
		
		double prob = Math.random(); // prob e[0,1]
		
		if(prob > pcross){
			heirs[0] = sched_1;
			heirs[1] = sched_2;
		}
		
		return heirs;  // Return the two heirs
		
	}
	
	public void mutInversion(){
		int[][] a = this.getArray();
		
		ArrayList<Integer> singleList = new ArrayList<Integer>();
		
		for(int i=0; i<numOfEmployees; i++){  // Take the elements of the first column
			singleList.add(a[i][0]);
		}
		
		 Collections.reverse(singleList); // Reverse the first column. Top element goes to the button etc
		 
		 for(int i=0; i<numOfEmployees; i++){  // Replace the elements of the first column
				a[i][0] = singleList.get(i);
			}
		 
	}

}
