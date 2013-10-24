import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Daniel Clark (dac46@aber.ac.uk)
 * The class that contains all the cells and manipulates them
 */

public class DataStructure {
	private Cell[][] rows;
	private Cell[][] collumns;
	private Cell[][] squares;
	
	/**
	 * When constructed this way it will populate the structure with the data read from the file
	 * @param filePath
	 */
	DataStructure(String filePath){
		initializeArrays();
		populate(importFile(filePath));
	}

	/**
	 * When constructed this way it will populate with an empty array before a file is loaded
	 */
	DataStructure(){
		initializeArrays();
		populate(new int[9][9]);
	}	
	
	/**
	 * Creates the data structure for storing all the data, it is 3 2D arrays,
	 * but they all contain the same data, just stored in a different order, making them very easy to search through
	 */
	private void initializeArrays() {
		rows = new Cell[9][9];
		collumns = new Cell[9][9];
		squares = new Cell[9][9];
	}
	
	/**
	 * Compares two DataStructures
	 * @param other
	 * @return
	 */
	public boolean equals(DataStructure other){
	//Implemented to be used when checking if the state of the grid had changed, but didn't have time to implement that
		if(other == null){
			return false;
		}
		for(int i = 0; i<this.getRows().length; i++){
			for(int j = 0; j<this.getRows()[i].length; j++){
				if(!(this.getRow(i)[j].equals(other.getRow(i)[j]))){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Imports the file at filePath into the program
	 * @param filePath
	 * @return
	 */
	public int[][] importFile(String filePath){
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
		return arrayPuzzleGrid;
	}
	
	/**
	 * Normalises the length of a string to the 9 characters needed for the program
	 * @param line
	 * @return
	 */
	public String normaliseLength (String line){
		int lineLength = line.length();
		if(lineLength < 9){
			for(int k = 0; k < (9 - lineLength); k++){
				line = line + "0";
			}
		}
		return line;
	}
	
	/**
	 * Populates the DataStructure with data from a 2D array of integer values
	 * @param grid
	 */
	public void populate(int[][]grid){
		int count[] = new int[9];
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				Cell myCell = new Cell(i, j, grid[i][j]);
				rows[i][j] = myCell;
				collumns[j][i] = myCell;
				int countNum;
				if(i<3){
					if(j<3){
						countNum = 0;
					}
					else if(j<6){
						countNum = 1;
					}
					else{
						countNum = 2;
					}
				}
				else if(i<6){
					if(j<3){
						countNum = 3;
					}
					else if(j<6){
						countNum = 4;
					}
					else{
						countNum = 5;
					}
				}
				else{
					if(j<3){
						countNum = 6;
					}
					else if(j<6){
						countNum = 7;
					}
					else{
						countNum = 8;
					}
				}
				squares[countNum][count[countNum]] = myCell;
				count[countNum]++;
			}
		}
	}
	
	public Cell[] getRow(int rowNum){
		return this.rows[rowNum];
	}
	
	public Cell[][] getRows(){
		return this.rows;
	}
	
	public Cell[] getColumn(int collumnNum){
		return this.collumns[collumnNum];
	}

	public Cell[] getSquare(int squareNum) {
		return this.squares[squareNum];
	}

	public void setValue(Cell cell, int targetNumber) {
		rows[cell.getRowNumber()][cell.getCollumnNumber()].setValue(targetNumber);
	}
	
	/**
	 * Returns the most common value in the array
	 * @return
	 */
	public int findMostCommonValue(){
	//Was implemented to allow prioritising the order in which the algorithms solve the puzzle,
	//getting harder each time, as the most common value is probably the easiest to solve,
	//however this feature was never implemented due to lack of time
		int[] valueArray = new int[9];
		for(Cell[] row  : rows){
			for(Cell cell : row){
				if(cell.getValue() != 0){
					valueArray[cell.getValue()]++;
				}
			}
		}
		int mostCommon = 0;
		int highestOccurrences = 0;
		for(int i = 0; i < 9; i++){
			if(valueArray[i] > highestOccurrences){
				mostCommon = i+1;
				highestOccurrences = valueArray[i];
			}
		}
		return mostCommon;
	}

	/**
	 * Checks if a particular number, targetNumber, exists in blockChar (Row/Column/Square) number blockNumber
	 * @param blockNumber
	 * @param blockChar
	 * @param targetNumber
	 * @return
	 */
	public boolean checkForTargetNumber(int blockNumber, char blockChar, int targetNumber) {
		if(blockChar == 'r'){
			return searchForNumber(rows[blockNumber], targetNumber);
		}
		if(blockChar == 'c'){
			return searchForNumber(collumns[blockNumber], targetNumber);
		}
		if(blockChar == 's'){
			return searchForNumber(squares[blockNumber], targetNumber);
		}
		return false;
	}

	/**
	 * Searches for a value in an array of Cells
	 * @param cells
	 * @param targetNumber
	 * @return
	 */
	private boolean searchForNumber(Cell[] cells, int targetNumber) {
		for(Cell currentCell : cells){
			if(currentCell.getValue() == targetNumber){
				return true;
			}
		}
		return false;
	}

	/**
	 * Outputs the rows in the right order to the command line
	 */
	public void outputRows(){
		//Not used anywhere but useful for debugging
		for(int i = 0; i < 9; i++){
			Cell[] row = getRow(i);
			for(int j = 0; j < 9; j++){
				System.out.print(row[j].getValue());
			}
			System.out.println();
		}
	}
	
	/**
	 * Outputs the columns in the right order to the command line
	 */
	public void outputCollumns(){
		//Not used anywhere but useful for debugging
		int[][] puzzleArray = new int[9][9];
		for(int i = 0; i < 9; i++){
			Cell[] collumn = getColumn(i);
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
	
	/**
	 * Outputs a particular square to the command line in a single line
	 */
	public void outputSquareAsLine(int squareNum){
		//Not used anywhere but useful for debugging
		Cell[] square = getSquare(squareNum);
		for(int i = 0; i<9; i++){
			System.out.print(square[i].getValue());
		}
	}
	
	/**
	 * Saves the current state of the puzzle to the file at filePath
	 * @param filePath
	 */
	public void saveCurrentState(String filePath) {
		PrintWriter outFile = null;
		//Start the printWriter
		try {
			outFile = new PrintWriter (new OutputStreamWriter (new FileOutputStream (filePath))); 
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		}
		for(int i = 0; i < 9; i++){
			Cell[] row = getRow(i);
			for(int j = 0; j < 9; j++){
				outFile.print(row[j].getValue());
			}
			outFile.println();
		}
	}

	/**
	 * Outputs the rows all in one line, used only for when in batch mode
	 */
	public void outputRowsAsLine() {
		for(int i = 0; i < 9; i++){
			Cell[] row = getRow(i);
			for(int j = 0; j < 9; j++){
				System.out.print(row[j].getValue());
			}
		}
	}
	
}