import java.util.LinkedList;
import java.util.Scanner;

public class GeneticMain {

	static LinkedList<GeneticArray> listOfArrays = new LinkedList<GeneticArray>(); // linked list of arrays
	static LinkedList<GeneticArray> FeasibleArrays = new LinkedList<GeneticArray>();
	static LinkedList<GeneticArray> NextGeneration = new LinkedList<GeneticArray>();
	static LinkedList<Integer> score = new LinkedList<Integer>();
	static int[] scoreTable;
	static double previousArg, newArg=0;
	static int Min =9;
	static int Max =12;
	static int GeneretionLoop =30;
	static double propCross = 1; 
	static double pmut = 0.09;  // Probability of mutation
	static double percentage = 0.01;
	
	public static void main(String[] args) {
				
		int LOOP = 3000;	//number of loops
	
		for(int i=0; i<LOOP; i++) {	
			GeneticArray ga = new GeneticArray();	//create a new object GeneticArray
			ga.listGenerator();
			//ga.printLists(0);
			ga.initialization();
			listOfArrays.add(ga);		//Add it in the Linked List
			//System.out.println("Array No."+(listOfArrays.size()-1));
			//ga.printArray();
		}
		
		boolean[] appropriate = new boolean[listOfArrays.size()];   //if true=> OK(accept this array)  if false=>NOT OK(reject this array)
		for(int i=0; i<listOfArrays.size(); i++){
			appropriate[i] = listOfArrays.get(i).newCheckArray();
			if(appropriate[i] == true){ 
				FeasibleArrays.add(listOfArrays.get(i));
			}
		}		

		System.out.println("\nNum of feasibles: "+FeasibleArrays.size());
		
		int choice = printMenu();
		
		switch(choice){
		  case 1:
			  System.out.print("Average number of score before Genetic Algorithm: "); printAverageScore(1); // argument 1. Print for FeasibleArrays
			  int generation = 1;
			  while((double)(((previousArg-newArg)/previousArg)*100) > percentage){
		  	     System.out.println("\nGeneration: "+generation+"\n");
		         geneticAl(generation);
			     generation++;
			  }
			  break;
		  case 2:
			  geneticAlgorithm();
			  break;
		  default:
			  // Doesn't get here
			  break;
		}
		
		//bubbleSort();
		//FeasibleArrays.get(0).printArray();
		
	}
	
	static void geneticAl(int generation){
		
		GeneticArray g = new GeneticArray();
		SortFeasibles(); // Sort increasingly the feasible schedules by their score.

		int size = FeasibleArrays.size();
		if(size%2!=0) {
			size--;
		}
		if(size%2 == 0){
			for(int i=0; i<size/2; i=i+2){
				GeneticArray ga[] = new GeneticArray[2];
				ga = g.onePointCrossover(FeasibleArrays.get(i), FeasibleArrays.get(i+1));
				NextGeneration.add(ga[0]);  // Add the first result occurred by Crossover
				NextGeneration.add(ga[1]);  // Add the second result occurred by Crossover
			}
			
		}
		
		mutation();
		
		for(int i=0; i<size/2; i++){
			NextGeneration.add(FeasibleArrays.get(i));  // Rest of elements are the first half of FeasibleArrays without process 
		}
		
		for(int i=0; i<NextGeneration.size(); i++){
			if(!NextGeneration.get(i).newCheckArray()){
				NextGeneration.remove(i);
			}
		}
	
		if(generation>1){previousArg=newArg;}
		System.out.print("Average number of score in this Generation: "); printAverageScore(2);
		System.out.println("Best score in this Generation: "+FeasibleArrays.get(0).scoreArray()+"\n");
		FeasibleArrays.clear();

		for(int i=0; i<NextGeneration.size(); i++){      // Copy to FeasibleArrays the NextGeneration list
			FeasibleArrays.add(NextGeneration.get(i));
		}
		NextGeneration.clear();
	}
	
	static void geneticAlgorithm(){
		int count =0;
		while(count<=GeneretionLoop) {
		boolean[] appropriate;
		if(count==0) {
				appropriate = new boolean[listOfArrays.size()];   //if true=> OK(accept this array)  if false=>NOT OK(reject this array)
				FeasibleArrays.clear();
				for(int i=0; i<listOfArrays.size(); i++){
					appropriate[i] = listOfArrays.get(i).newCheckArray();
					if(appropriate[i] == true){ 
						FeasibleArrays.add(listOfArrays.get(i));
					}
				}
		}
		else {
				//clear list of arrays
				listOfArrays.clear();
				//add The previous next generation to list of arrays
				for(int i=0;i<NextGeneration.size();i++) {
					listOfArrays.add(NextGeneration.get(i));
				}
				/*if(listOfArrays.isEmpty()) {
					break;
				}*/
			
				//clear previous feasible arrays
				FeasibleArrays.clear();
			
				//check list of arrays(previous next generation)
				appropriate = new boolean[listOfArrays.size()];   //if true=> OK(accept this array)  if false=>NOT OK(reject this array)
				for(int i=0; i<listOfArrays.size(); i++){
					appropriate[i] = listOfArrays.get(i).newCheckArray();
					if(appropriate[i] == true){ 
						FeasibleArrays.add(listOfArrays.get(i));
					}
				}
			
			}	

		//clear previous next generation
		NextGeneration.clear();

		if(count==0){
			System.out.println();
			System.out.println("Num of feasibles: "+FeasibleArrays.size());
		}		
				
		int score2 = 0;
		for(int i=0;i<FeasibleArrays.size();i++) {
			score2 = score2 + FeasibleArrays.get(i).scoreArray();
		}
		        
		//System.out.printf("\nScore of the %d generation is:%d \n",count, score2/FeasibleArrays.size());
			
				
				
		scoreTable =new int[FeasibleArrays.size()] ;
				
				
		for(int i=0;i<FeasibleArrays.size();i++) {
			scoreTable[i] = FeasibleArrays.get(i).scoreArray();
		}
			

		bubbleSort(scoreTable);
		
		System.out.printf("\nScore of the %d generation is:%d \n",count, score2/FeasibleArrays.size());
		System.out.printf("\nBest of the %d generation is:%d \n",count,FeasibleArrays.get(0).scoreArray());
		
		System.out.println();
		int score1 = 0;
		for(int i=0;i<FeasibleArrays.size();i++) {
			score1 = score1 + FeasibleArrays.get(i).scoreArray();
		}
				
		//Choose only the first half of the feasible array
		int size = FeasibleArrays.size();
		if(size%2!=0) {
			size=size-1;
		}
		if(size%2 == 0) {
			//Take the first half(best parents)
			for(int i=0;i<size/2;i++) {
				NextGeneration.add(FeasibleArrays.get(i));
     		}
			//Take the rest
	    	for(int i=0;i<size/2;i++) {
				GeneticArray g1 = new GeneticArray();
				GeneticArray g2 = new GeneticArray();
				g1 = FeasibleArrays.get(i);
				g2 = FeasibleArrays.get(size/2-i-1);
				//g2 = FeasibleArrays.get(size-i-1);   //Bad crossover
				double num = Math.random();
				if(num<=propCross){
					NextGeneration.add(intersectionE(g1,g2));
				}
				else{
					NextGeneration.add(g1);
				}
				
					
			}					
		}
		else {
			System.out.println("\nSomething was wronrg\n");
		}
				
		//Transform
		GeneticArray transg;
		for(int j=NextGeneration.size()/2;j<NextGeneration.size();j++) {
			GeneticArray gn = NextGeneration.get(j);
		    double prob = Math.random(); // prob e[0,1]
				
			if(prob < pmut){
				transg=mutationHalf(gn);
				//transg=transformE(gn);
			}
			
		}
		count++;	
	}
}
	
	static void mutation()
	{
		for(int i=0; i<NextGeneration.size(); i++){
			double prob = Math.random(); // prob e[0,1]
			
			if(prob < pmut){
				NextGeneration.get(i).mutInversion();
			}
			
		}
	}
	
	static GeneticArray mutationHalf(GeneticArray g) {
    	int[][] a = g.getArray();
    	int temp;
    	int position = 7;
    	
    	for(int i=0;i<g.getNumOfEmployees()/2;i++) {
    		temp = a[i][position];
    		a[i][position] = a[i+g.getNumOfEmployees()/2][position];
    		a[i+g.getNumOfEmployees()/2][position] = temp;
    	}
    	
    	return g;
    }
	
	static void SortFeasibles() 
    {  
		score.clear();
		for(int i=0; i<FeasibleArrays.size(); i++){        // Calculate scores of all arrays in FeasibleArrays
        	score.add(FeasibleArrays.get(i).scoreArray());
        }
		
        int n = score.size(); 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (score.get(j) > score.get(j+1)) 
                { 
                    // swap score[j+1] and score[i] 
                    int temp = score.get(j); 
                    GeneticArray gA = FeasibleArrays.get(j);
                    score.set(j, score.get(j+1));
                    FeasibleArrays.set(j, FeasibleArrays.get(j+1));
                    score.set(j+1, temp);
                    FeasibleArrays.set(j+1, gA);
                }       
    }
	
	static void bubbleSort(int arr[]) 
    { 
        int n = arr.length; 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr[j] > arr[j+1]) 
                { 
                    // swap arr[j+1] and arr[i] 
                    int temp = arr[j]; 
                    GeneticArray gA = FeasibleArrays.get(j);
                    arr[j] = arr[j+1]; 
                    FeasibleArrays.set(j, FeasibleArrays.get(j+1));
                    arr[j+1] = temp; 
                    FeasibleArrays.set(j+1, gA);
                } 
    }
	
	static GeneticArray intersectionE(GeneticArray g1,GeneticArray g2) {
		int[][] ar1 = g1.getArray();
		int[][] ar2 = g2.getArray();
		GeneticArray gn = new GeneticArray();
		int[][] n = gn.getArray();
		for(int i=0;i<g1.getNumOfEmployees();i++) {
			for(int j=0;j<g1.getNumOfDays();j++) {
				if(j>=6 && j<=10) {
					n[i][j] = ar2[i][j];
				}
				else {
					n[i][j] = ar1[i][j];
				}
			}
		}
		return gn;
	}
    
    static GeneticArray transformE(GeneticArray g) {
    	int position ;
		int newNumber ;
		
		int[][] a =g.getArray();
		
		for(int i=0;i<g.getNumOfEmployees();i++) {
			//System.out.printf("\ni Am here for line:%d\n",i);
			position = Min + (int)(Math.random() * ((Max - Min) + 1)); //Find the position that will transform
            newNumber = 0 + (int)(Math.random() * ((1 - 0) + 1));      //Find random the number that we will put
            if(newNumber<0.1) {
    			a[i][position] = 3;
    		}
    		else if(newNumber<0.5) {
    			a[i][position] = 0;
    		}
    		else if(newNumber<0.7) {
    			a[i][position] = 2;
    		}
    		else {
    			a[i][position] = 1;
    		}
		}
		return g;
    }
	
	static void printAverageScore(int Case){
		
		int totalScore;
		
		switch(Case){
			case 1: 
				totalScore = 0;
				for(int i=0;i<FeasibleArrays.size();i++) {
					int scr = FeasibleArrays.get(i).scoreArray();
					totalScore = totalScore + scr;
				}
				totalScore = totalScore/FeasibleArrays.size();
				previousArg = totalScore;
				
				System.out.println(totalScore);
				break;
			case 2:
				totalScore = 0;
				for(int i=0;i<NextGeneration.size();i++) {
					int scr = NextGeneration.get(i).scoreArray();
					totalScore = totalScore + scr;
				}
				totalScore = totalScore/NextGeneration.size();
				newArg = totalScore;
				
				System.out.println(totalScore);
		}
	
	}
	
	public static int printMenu(){
		Scanner sir = new Scanner(System.in);
		
		System.out.println("---------------------------------");
		System.out.println("Press '1' for first way of Crossover & Mutation");
		System.out.println("Press '2' for second way of Crossover & Mutation");
		System.out.println("---------------------------------");
		
		int answer = sir.nextInt();

		while(answer!=1 && answer!=2){
			System.out.println("Invalid Choice.. Try again");
			answer = sir.nextInt();
		}
		
		return answer;
	}

}
