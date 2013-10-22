import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
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
		outputRows();
	}
	
	public String normaliseLength (String line){
		int lineLength = line.length();
		if(lineLength < 9){
			for(int k = 0; k < (9 - lineLength); k++){
				line = line + "0";
			}
		}
		return line;
	}
	
	public void start(){
		while(true){
			outputMenu();
			int technique = readInt("What technique do you want to use? ");
			runTechnique(technique);
			outputRows();
		}
	}
	
	public void outputMenu(){
		System.out.println("Menu");
		System.out.println("1. Basic Complete");
		System.out.println("2. Complete Singles");
		System.out.println("3. Complete Pairs");
		System.out.println("4. Exit");
	}
	
	public void runTechnique(int technique){
		switch(technique){
			case 1: basicComplete(); break;
			case 2: completeSingles(); break;
			case 3: completePairs(); break;
			case 5: System.exit(0);
		}
	}

	private void completePairs() {
		int count = 0;
		while(count < 10){
			updateAllPensilMarks();
			insertPairs();
			completeSingles();
			count++;
		}
	}

	private void insertPairs() {
		insertPointingPairs('r');
		updateAllPensilMarks();
		insertPointingPairs('c');
		updateAllPensilMarks();
		insertNakedMultiples(2, 'r');
		updateAllPensilMarks();
		insertNakedMultiples(2, 'c');
		updateAllPensilMarks();
		insertNakedMultiples(2, 's');
		updateAllPensilMarks();
	}

	private void insertNakedMultiples(int multiple, char blockChar) {
		for(int i = 0; i < 9; i++){
			Cell[] block = null;
			switch(blockChar){
			case 'r' : block = puzzleGrid.getRow(i); break;
			case 'c' : block = puzzleGrid.getCollumn(i); break;
			case 's' : block = puzzleGrid.getSquare(i); break;
			}
			Cell[] multipleCells = new Cell[9];
			int multipleCount = 0;
			for(int j = 0; j < 9; j++){
				Cell currentCell = block[j];
				if(currentCell.getValue() == 0){
					int[] pensilMarks = currentCell.getPensilMarks();
					int numPensilMarks = countNonZeros(pensilMarks);
					if(numPensilMarks == multiple){
						multipleCells[multipleCount] = currentCell;
						multipleCount++;
					}
				}
			}
			for(int j = 0; j < multipleCells.length; j++){
				if(multipleCells[j] != null){
					for(int k = (j+1); k < multipleCells.length; k++){
						if(multipleCells[k] != null){
							int[] jPensilMarks = multipleCells[j].getPensilMarks();
							int[] kPensilMarks = multipleCells[k].getPensilMarks();
							boolean areEqual = true;
							for(int l = 0; l < multiple; l++){
								if(jPensilMarks[l] != kPensilMarks[l]){
									areEqual = false;
								}
							}
							if(areEqual){
								Cell[] cellArray = {multipleCells[j], multipleCells[k]};
								for(int n = 0; n < multiple; n++){
									removeOccurancesExcept(blockChar, i, jPensilMarks[n], cellArray);
								}
							}
						}
					}
				}
			}
		}
	}

	private int countNonZeros(int[] values) {
		int count = 0;
		for(int currentValue : values){
			if(currentValue == 0){
				return count;
			}
			else{
				count++;
			}
		}
		return count;
	}

	private void insertPointingPairs(char blockChar) {
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				Cell[] occurances = getOccurances((i+1), 's', j);
				if(occurances != null && occurances[0] != null){
					int commonBlockNumber = areOccurancesInSameBlock(occurances, blockChar);
					if(commonBlockNumber != -1){
						removeOccurancesExcept(blockChar, commonBlockNumber, (i+1), occurances);
					}
				}
			}
		}
	}

	private int areOccurancesInSameBlock(Cell[] occurances, char blockChar) {
		boolean sameBlock = true;
		int blockNumber = 0;
		if(blockChar == 'r'){
			blockNumber = occurances[0].getRowNumber();
			for(Cell currentCell : occurances){
				if(currentCell != null){
					if(blockNumber != currentCell.getRowNumber()){
						sameBlock = false;
					}
				}
			}
		}
		else if(blockChar == 'c'){
			blockNumber = occurances[0].getCollumnNumber();
			for(Cell currentCell : occurances){
				if(currentCell != null){
					if(blockNumber != currentCell.getCollumnNumber()){
						sameBlock = false;
					}
				}
			}
		}
		if(sameBlock){
			return blockNumber;
		}
		else{
			return -1;
		}
	}

	private void removeOccurancesExcept(char rowCollumnSquareChar, int commonBlockNumber, int value, Cell[] occurances) {
		Cell[] block = getOccurances(value, rowCollumnSquareChar, commonBlockNumber);
		for(int i = 0; i < 9; i++){
			if(block[i] != null){
				boolean found = false;
				for(Cell currentOccurance : occurances){
					if(block[i] == currentOccurance){
						found = true;
					}
				}
				if(!found && 
						block[i].getValue() == 0){
					System.out.println("Removing " + value + " from " + rowCollumnSquareChar + commonBlockNumber + " cell " + i);
					block[i].removePensilMark(value);
					updateAllPensilMarks();
				}
			}
		}
	}

	private void completeSingles() {
		int count = 0;
		while(count < 10){
			updateAllPensilMarks();
			insertSingles();
			basicComplete();
			count ++;
		}
	}
	
	public void updateAllPensilMarks(){
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				updatePensilMarkInCell(i, j, 'r');
				updatePensilMarkInCell(i, j, 'c');
				updatePensilMarkInCell(i, j, 's');
			}
		}
	}

	private void insertSingles() {
		insertHiddenSingles();
		insertNakedSingles();
	}

	private void insertHiddenSingles() {
		hiddenSinglesBlock('r');
		hiddenSinglesBlock('c');
		hiddenSinglesBlock('s');
	}

	private void hiddenSinglesBlock(char blockChar) {
		updateAllPensilMarks();
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				Cell[] occurances = getOccurances((i+1), blockChar, j);
				if(occurances[1] == null && occurances[0] != null){
					occurances[0].setValue((i+1));
				}
			}
		}
	}

	private Cell[] getOccurances(int value, char rowCollumnSquare, int blockNumber){
		int count = 0;
		Cell[] occuranceCells = new Cell[9];
		Cell[] block = null;
		switch(rowCollumnSquare){
		case 'r' : block = puzzleGrid.getRow(blockNumber); break;
		case 'c' : block = puzzleGrid.getCollumn(blockNumber); break;
		case 's' : block = puzzleGrid.getSquare(blockNumber); break;
		}
		for(Cell currentCell : block){
			if(currentCell.getValue() == 0){
				int[] possibleValues = currentCell.getPensilMarks();
				for(int i = 0; i<possibleValues.length; i++){
					if(possibleValues[i] == value){
						occuranceCells[count] = currentCell;
						count++;
					}
				}
			}
		}
		//System.out.println((occuranceCells[count].getRowNumber()+1) + "," + (occuranceCells[count].getCollumnNumber()+1) + " is the only one in the " + rowCollumnSquare + " that has a " + value);
		return occuranceCells;
	}
	
	private void insertNakedSingles() {
		Cell cell;
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				cell = puzzleGrid.getRow(i)[j];
				int[] possibleValues = cell.getPensilMarks();
				if(possibleValues[0] != 0 && possibleValues[1] == 0){
					cell.setValue(possibleValues[0]);
				}
			}
		}
	}

	private void updatePensilMarkInCell(int i, int j, char rowCollumnSquare) {
		Cell[] block = null;
		switch(rowCollumnSquare){
		case 'r' : block = puzzleGrid.getRow(i); break;
		case 'c' : block = puzzleGrid.getCollumn(i); break;
		case 's' : block = puzzleGrid.getSquare(i); break;
		}
		Cell currentCell = block[j];
		if(currentCell.getValue() == 0){
			for(Cell cell : block){
				int value = cell.getValue();
				if(value != 0){
					currentCell.removePensilMark(value);
				}
			}
		}
	}

	public int readInt(String prompt){
		System.out.print(prompt);
		int value = 0;
		Scanner input = new Scanner(System.in);
		try{
			value = input.nextInt();
		}
		catch(InputMismatchException e){
			System.out.println("You entered something that's not a number");
			e.printStackTrace();
		}
		//input.close();
		return value;
	}
	
	private void basicComplete() {
		int count = 0;
		while(count < 10){
			completeBlock('r');
			completeBlock('c');
			completeBlock('s');
			count++;
		}
	}

	private void completeBlock(char rowCollumnSquare){
		for(int i = 0; i<9; i++){
			int count = 0;
			Cell emptyCell = null;
			int sum = 0;
			for(Cell currentCell : puzzleGrid.getRowCollumnSquare(i, rowCollumnSquare)){
				if(currentCell.getValue() == 0){
					count++;
					emptyCell = currentCell;
				}
				sum = sum + currentCell.getValue();
			}
			if(count == 1){
				emptyCell.setValue((45 - sum));
			}
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
	
	/*public void myWay(){
		Cell[][] oldData;
		while(oldData != puzzleGrid.getRows()){
			oldData = puzzleGrid.getRows();
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
					int targetSquare = puzzleGrid.getSquareContaining(targetCell);
					if(targetCell.getRowNumber() % 3 == 0){ //NOT SURE IF CORRECT
						if(puzzleGrid.checkForTargetNumber((targetCell.getRowNumber()-2), targetNumber, 'r')){
							thisSquare[0] = false;
							thisSquare[3] = false;
							thisSquare[6] = false;
						}
						if(puzzleGrid.checkForTargetNumber((targetCell.getRowNumber()-1), targetNumber, 'r')){
							thisSquare[1] = false;
							thisSquare[4] = false;
							thisSquare[7] = false;
						}
					}
					//look at other collumns in my square
					if(targetCell.getCollumnNumber() % 3 == 0){ //NOT SURE IF CORRECT
						if(puzzleGrid.checkForTargetNumber((targetCell.getCollumnNumber()-2), targetNumber, 'c')){
							thisSquare[6] = false;
							thisSquare[7] = false;
							thisSquare[8] = false;
						}
						if(puzzleGrid.checkForTargetNumber((targetCell.getCollumnNumber()-1), targetNumber, 'c')){
							thisSquare[3] = false;
							thisSquare[4] = false;
							thisSquare[5] = false;
						}
					}
					thisSquare = puzzleGrid.addAlreadyFilledCells(thisSquare, targetSquare, 's');
					int trues = getTrues(thisSquare);
					if(trues == 1){
						puzzleGrid.setValue(targetCell, targetNumber);
					}
					
					/*build my row(in a 1 dimensional bool array)
					look at other squares in my row
					look at other collumns in my row
					build my column(in a 1 dimensional bool array)
					look at other squares in my column
					look at other rows in my column
					//int possibilities[] = get possibilities
				}
				
			}
		}
		outputRows();
	}

	private int getTrues(boolean[] thisSquare) {
		int trues = 0;
		for(boolean currentBool : thisSquare){
			if(currentBool == true){
				trues++;
			}
		}
		return trues;
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
	}*/
	
}