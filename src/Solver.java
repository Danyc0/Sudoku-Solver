
/**
 * @author Daniel Clark (dac46@aber.ac.uk)
 * The class which will solve the given sudoku puzzle
 */

public class Solver {
	private DataStructure puzzleGrid;
	private boolean commandLine;
	
	/**
	 * A Constructor for when loading with command line arguments, to enable batch processing
	 * @param filePath
	 * @param commandLine
	 */
	Solver(String filePath, boolean commandLine){
		importFile(filePath);
		this.commandLine = commandLine;
	}
	
	/**
	 * A Constructor for when loading normally after the GUI had been started
	 */
	Solver() {
		//Create an empty DataStructure to be populated from a load via the GUI
		puzzleGrid = new DataStructure();
	}
	
	public Cell[] getRow(int rowNum){
		return puzzleGrid.getRow(rowNum);
	}
	
	public Cell[][] getRows(){
		return puzzleGrid.getRows();
	}

	/**
	 * A method which creates a DataStructure with the data found at the filePath
	 * @param filePath
	 */
	public void importFile(String filePath){
		puzzleGrid = new DataStructure(filePath);
		updateAllPencilMarks();
	}
	
	/**
	 * Starts the solver running and outputs the result to the command line if in batch mode
	 */
	public void start(){
		completePairs();
		if(commandLine){
			//Change this to outputRows(); to output the rows in a more human readable output
			puzzleGrid.outputRowsAsLine();
		}
	}

	/**
	 * A method to Complete the puzzle using the Naked Pairs & Pointing Pairs algorithms
	 */
	private void completePairs() {
		int count = 0;
		//Solving loop runs 10 times, should be enough to solve anything in the likely scope
		//See documentation for why this is so basic and how i originally planned to implement it
		while(count < 10){
			updateAllPencilMarks();
			insertPairs();
			completeSingles();
			count++;
		}
	}

	/**
	 * Method to run the Pointing Pairs algorithm for both rows and columns, then run the Naked Pairs algorithm for rows, columns and squares
	 */
	private void insertPairs() {
		insertPointingPairs('r');
		updateAllPencilMarks();
		insertPointingPairs('c');
		updateAllPencilMarks();
		insertNakedMultiples(2, 'r');
		updateAllPencilMarks();
		insertNakedMultiples(2, 'c');
		updateAllPencilMarks();
		insertNakedMultiples(2, 's');
		updateAllPencilMarks();
	}

	/**
	 * Run the Naked Pairs Algorithm (possibility to expand to triples/quads)
	 * @param multiple
	 * @param blockChar
	 */
	
	private void insertNakedMultiples(int multiple, char blockChar) {
	//Will solve for Naked Pairs only,
	//but was designed to be expandable to naked triples/quads, although that never quite worked
		for(int i = 0; i < 9; i++){
			Cell[] block = null;
			//Get the appropriate row, column or square
			switch(blockChar){
				case 'r' : block = puzzleGrid.getRow(i); break;
				case 'c' : block = puzzleGrid.getColumn(i); break;
				case 's' : block = puzzleGrid.getSquare(i); break;
			}
			//Gets all the cells that have the appropriate number of pencil marks
			Cell[] multipleCells = getCellsWithMultiplePencilMarks(block, multiple);
			
			insertMultiples(multiple, multipleCells, blockChar, i);
		}
	}

	/**
	 * Returns all the cells in the block that have the multiple number of pencil marks
	 * @param block
	 * @param multiple
	 * @return
	 */
	private Cell[] getCellsWithMultiplePencilMarks(Cell[] block, int multiple) {
		//Create an array to store the cells that contain the appropriate number of pencil marks
		Cell[] multipleCells = new Cell[9];
		int multipleCount = 0;
		for(int j = 0; j < 9; j++){
			Cell currentCell = block[j];
			//If cell doesn't already have a firm value
			if(currentCell.getValue() == 0){
				int[] pencilMarks = currentCell.getPencilMarks();
				int numPencilMarks = countNonZeros(pencilMarks);
				if(numPencilMarks == multiple){
					multipleCells[multipleCount] = currentCell;
					multipleCount++;
				}
			}
		}
		return multipleCells;
	}

	/**
	 * Method to remove the pencil marks for any multiples which aren't part of the Naked Pair/Triple/Quad
	 * @param multiple The value that is the focus of this run of the algorithm
	 * @param multipleCells The cells that contain the correct amount of the values
	 * @param blockChar The type of the block that the run of the Algorithm is focusing on (Rows/Columns/Squares)
	 * @param i The number of the block that the run of the Algorithm is focusing on
	 */
	private void insertMultiples(int multiple, Cell[] multipleCells, char blockChar, int i) {
		//For all cells in multipleCells
		for(int j = 0; j < multipleCells.length; j++){
			if(multipleCells[j] != null){
				//For all cells in multipleCells which haven't already been covered
				for(int k = (j+1); k < multipleCells.length; k++){
					if(multipleCells[k] != null){
						int[] jPencilMarks = multipleCells[j].getPencilMarks();
						int[] kPencilMarks = multipleCells[k].getPencilMarks();
						boolean areEqual = true;
						//If the 2 cells don't have the right amount of the same pencil marks, return false
						for(int l = 0; l < multiple; l++){
							if(jPencilMarks[l] != kPencilMarks[l]){
								areEqual = false;
							}
						}
						if(areEqual){
							Cell[] cellArray = {multipleCells[j], multipleCells[k]};
							for(int n = 0; n < multiple; n++){
								//Remove all pencil mark occurrences of jPencilMarks[n] in blockChar (Row/Column/Square)
								//number i, EXCEPT ones in cells that exist in cellArray
								removeOccurencesExcept(blockChar, i, jPencilMarks[n], cellArray);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Count the number of non zero values in an array
	 * @param values
	 * @return
	 */
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

	/**
	 * Run the Pointing Pairs algorithm
	 * @param blockChar
	 */
	private void insertPointingPairs(char blockChar) {
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				Cell[] occurences = getOccurences((i+1), 's', j);
				if(occurences != null && occurences[0] != null){
					//Do the cells in occurrences share a block, if so, return it's number, if not, return -1
					int commonBlockNumber = areOccurencesInSameBlock(occurences, blockChar);
					if(commonBlockNumber != -1){
						//Remove all occurrences of (i+1) in blockChar (Row/Column/Square) number commonBlockNumber,
						//EXCEPT in cells which also appear in occurrences
						removeOccurencesExcept(blockChar, commonBlockNumber, (i+1), occurences);
					}
				}
			}
		}
	}

	/**
	 * Return if the cells in occurrences share a block, if so, return it's number, if not, return -1
	 * @param occurances
	 * @param blockChar
	 * @return
	 */
	private int areOccurencesInSameBlock(Cell[] occurances, char blockChar) {
		boolean sameBlock = true;
		int blockNumber = 0;
		//If i had more time this if-statement would be removed,
		//and getRowNumber() would be replaced with getBlockNumber(blockChar)
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

	/**
	 * Remove all occurrences of value in blockChar (Row/Column/Square) number commonBlockNumber, except if the cell appears in occurrences
	 * @param blockChar
	 * @param commonBlockNumber
	 * @param value
	 * @param occurences
	 */
	private void removeOccurencesExcept(char blockChar, int commonBlockNumber, int value, Cell[] occurences) {
		Cell[] block = getOccurences(value, blockChar, commonBlockNumber);
		for(int i = 0; i < 9; i++){
			if(block[i] != null){
				boolean found = false;
				for(Cell currentOccurence : occurences){
					if(block[i] == currentOccurence){
						found = true;
					}
				}
				if(!found && block[i].getValue() == 0){
					//System.out.println("Removing " + value + " from " + rowCollumnSquareChar + commonBlockNumber + " cell " + i);
					block[i].removePencilMark(value);
					updateAllPencilMarks();
				}
			}
		}
	}

	/**
	 * Run the Hidden Singles and Naked Singles algorithms
	 */
	private void completeSingles() {
		int count = 0;
		while(count < 10){
			updateAllPencilMarks();
			insertHiddenSingles();
			insertNakedSingles();
			count ++;
		}
	}
	
	/**
	 * Update the pencil marks for each cell
	 */
	public void updateAllPencilMarks(){
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				updatePencilMarkInCell(i, j, 'r');
				updatePencilMarkInCell(i, j, 'c');
				updatePencilMarkInCell(i, j, 's');
			}
		}
	}

	/**
	 * Run Hidden Singles for each block type (Row/Column/Square)
	 */
	private void insertHiddenSingles() {
		hiddenSinglesBlock('r');
		hiddenSinglesBlock('c');
		hiddenSinglesBlock('s');
	}

	/**
	 * Run the Hidden Singles algorithm for a particular block type
	 * @param blockChar
	 */
	private void hiddenSinglesBlock(char blockChar) {
		updateAllPencilMarks();
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				Cell[] occurences = getOccurences((i+1), blockChar, j);
				if(occurences[1] == null && occurences[0] != null){
					occurences[0].setValue((i+1));
				}
			}
		}
	}
	
	/**
	 * Get all Occurrences of value in blockChar (Row/Column/Square) number blockNumber
	 * @param value
	 * @param blockChar
	 * @param blockNumber
	 * @return
	 */
	private Cell[] getOccurences(int value, char blockChar, int blockNumber){
		int count = 0;
		Cell[] occuranceCells = new Cell[9];
		Cell[] block = null;
		switch(blockChar){
			case 'r' : block = puzzleGrid.getRow(blockNumber); break;
			case 'c' : block = puzzleGrid.getColumn(blockNumber); break;
			case 's' : block = puzzleGrid.getSquare(blockNumber); break;
		}
		for(Cell currentCell : block){
			if(currentCell.getValue() == 0){
				int[] possibleValues = currentCell.getPencilMarks();
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
	
	/**
	 * Run the Naked Singles Algorithm
	 */
	private void insertNakedSingles() {
		Cell cell;
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				cell = puzzleGrid.getRow(i)[j];
				int[] possibleValues = cell.getPencilMarks();
				if(possibleValues[0] != 0 && possibleValues[1] == 0){
					cell.setValue(possibleValues[0]);
				}
			}
		}
	}

	/**
	 * Update the pencil marks in a particular cell
	 * @param i
	 * @param j
	 * @param rowCollumnSquare
	 */
	private void updatePencilMarkInCell(int i, int j, char rowCollumnSquare) {
		Cell[] block = null;
		switch(rowCollumnSquare){
		case 'r' : block = puzzleGrid.getRow(i); break;
		case 'c' : block = puzzleGrid.getColumn(i); break;
		case 's' : block = puzzleGrid.getSquare(i); break;
		}
		Cell currentCell = block[j];
		if(currentCell.getValue() == 0){
			for(Cell cell : block){
				int value = cell.getValue();
				if(value != 0){
					currentCell.removePencilMark(value);
				}
			}
		}
	}

	/**
	 * Save the current state of the puzzle in the filePath
	 * @param filePath
	 */
	public void saveCurrentState(String filePath) {
		puzzleGrid.saveCurrentState(filePath);
	}
}
