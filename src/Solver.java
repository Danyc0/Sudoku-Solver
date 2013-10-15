import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Solver {
	private DataStructure puzzleGrid;
	Solver(String filePath){
		importFile(filePath);
	}
	
	public void importFile(String filePath){
		int[][] arrayPuzzleGrid = new int[9][9];
		Scanner file = null;
		try {
			file = new Scanner(new InputStreamReader (new FileInputStream(filePath)));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		}
		String line;
		for(int i = 0; i < 9; i++){
			if(!file.hasNextLine()){
				line = "         ";
			}
			else{
				line = file.nextLine();
			}
			line = line.replaceAll(" ", "0");
			for(int j = 0; j < 9; j++){
				line = normaliseLength(line);
				char character = line.charAt(j);
				arrayPuzzleGrid[i][j] = Character.getNumericValue(character);
			}
		}
		file.close();
		puzzleGrid = new DataStructure(arrayPuzzleGrid);
	}
	
	public String normaliseLength (String line){
		int lineLength = line.length();
		if(lineLength < 9){
			for(int k = 0; k < (9 - lineLength); k++){
				line = line + " ";
			}
		}
		return line;
	}
	
	public void start(){
		outputMenu();
		int teqnique = readInt("What teqnique do you want to use? ");
		runTeqnique(teqnique);
	}
	
	public void outputMenu(){
		System.out.println("Menu");
		System.out.println("1. The way my brain does it");
		System.out.println("2. ");
	}
	
	public int readInt(String prompt){
		System.out.print(prompt);
		Scanner input = new Scanner(System.in);
		int value = input.nextInt();
		input.close();
		return value;
	}

	public void runTeqnique(int teqnique){
		switch(teqnique){
			case 1: myWay(); break;
			case 2: break;
		}
	}
	
	public void outputRows(){
		for(int i = 0; i < 9; i++){
			Cell[] row = puzzleGrid.getRow(i);
			for(int j = 0; j < 9; j++){
				System.out.print(row[j].getValue());
			}
			System.out.println();
		}
	}
	
	public void outputCollumns(){
		int[][] puzzleArray = new int[9][9];
		for(int i = 0; i < 9; i++){
			Cell[] collumn = puzzleGrid.getCollumn(i);
			for(int j = 0; j < 9; j++){
				puzzleArray[i][j] = (collumn[j].getValue());
			}
			System.out.println();
		}
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				System.out.print(puzzleArray[j][i]);
			}
			System.out.println();
		}
	}
	
	public void outputSquareAsLine(int squareNum){
		Cell[] square = puzzleGrid.getSquare(squareNum);
		for(int i = 0; i<9; i++){
			System.out.print(square[i].getValue());
		}
	}
	
	public void myWay(){
		while(!isComplete()){
			int targetNumber = puzzleGrid.findMostCommonValue();
			Cell[] targets = findTargetCells(targetNumber);
			if(targets.length == 1){
				puzzleGrid.setValue(targets[0], targetNumber);
			}
			else{
				for(Cell targetCell : targets){ //MAYBE USE SOMETHING LIKE THIS INSTEAD(to improve efficiency)? int focusCell = getMostBlockingNumbers(targetNumber);
					//build my square(in a 1 dimensional bool array) = true
					boolean[] thisSquare = makeTrueArray(9);
					//look at other rows in my square
					if(targetCell.getRowNumber() % 3 == 0){ //NOT SURE IF CORRECT
						if(checkForTargetNumber((targetCell.getRowNumber()-2), targetNumber)){
							thisSquare[0] = false;
							thisSquare[3] = false;
							thisSquare[6] = false;
						}
						if(checkForTargetNumber((targetCell.getRowNumber()-1), targetNumber)){
							thisSquare[1] = false;
							thisSquare[4] = false;
							thisSquare[7] = false;
						}
					}
					//look at other collumns in my square
					if(targetCell.getCollumnNumber() % 3 == 0){ //NOT SURE IF CORRECT
						if(checkForTargetNumber((targetCell.getCollumnNumber()-2), targetNumber)){
							thisSquare[6] = false;
							thisSquare[7] = false;
							thisSquare[8] = false;
						}
						if(checkForTargetNumber((targetCell.getCollumnNumber()-1), targetNumber)){
							thisSquare[3] = false;
							thisSquare[4] = false;
							thisSquare[5] = false;
						}
					}
					thisSquare = addAlreadyFilledCells(thisSquare);
					int[] trues = getTrues(thisSquare);
					if(trues.length == 1){
						puzzleGrid.setValue(targetCell, targetNumber);
					}
					
					/*build my row(in a 1 dimensional bool array)
					look at other squares in my row
					look at other collumns in my row
					build my column(in a 1 dimensional bool array)
					look at other squares in my column
					look at other rows in my column*/
					//int possibilities[] = get possibilities
				}
				
			}
		}
		outputRows();
	}

	private boolean[] makeTrueArray(int size) {
		boolean[] trueArray = new boolean[size];
		for(boolean bool : trueArray){
			bool = true;
		}
		return null;
	}

	public int[] findTargetSquares(int target){
		int[] block;
		int[] 3By3 = get3By3();
		int[] collumn = getCollumnNumber();
		int[] row = getRowNumber();
		return block;
	}
	
}