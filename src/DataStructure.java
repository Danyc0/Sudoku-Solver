
public class DataStructure {
	private Cell[][] rows;
	private Cell[][] collumns;
	private Cell[][] squares;
	DataStructure(int[][]grid){
		rows = new Cell[9][9]; //THINK ABOUT SIZE SUBSIZE WHERE 9 IS SIZE AND 3 IS SUBSIZE IN THIS EXAMPLE
		collumns = new Cell[9][9];
		squares = new Cell[9][9];
		populate(grid);
	}
	
	public boolean equals(DataStructure other){
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
	
	public Cell[] getCollumn(int collumnNum){
		return this.collumns[collumnNum];
	}

	public Cell[] getSquare(int squareNum) {
		return this.squares[squareNum];
	}

	public void setValue(Cell cell, int targetNumber) {
		rows[cell.getRowNumber()][cell.getCollumnNumber()].setValue(targetNumber);
	}
	
	public int findMostCommonValue(){
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

	public boolean checkForTargetNumber(int i, int targetNumber, char rowCollumnSquare) {
		if(rowCollumnSquare == 'r'){
			return searchForNumber(rows[i], targetNumber);
		}
		if(rowCollumnSquare == 'c'){
			return searchForNumber(collumns[i], targetNumber);
		}
		if(rowCollumnSquare == 's'){
			return searchForNumber(squares[i], targetNumber);
		}
		return false;
	}

	private boolean searchForNumber(Cell[] cells, int targetNumber) {
		for(Cell currentCell : cells){
			if(currentCell.getValue() == targetNumber){
				return true;
			}
		}
		return false;
	}
	
	public boolean[] addAlreadyFilledCells(boolean[] thisSquare, int targetSquare, char rowCollumnSquare) {
		Cell[] rowCollumnSquareArray = new Cell[9];
		if(rowCollumnSquare == 'r'){
			rowCollumnSquareArray = rows[targetSquare];
		}
		if(rowCollumnSquare == 'c'){
			rowCollumnSquareArray = collumns[targetSquare];
		}
		if(rowCollumnSquare == 's'){
			rowCollumnSquareArray = squares[targetSquare];
		}
		for(int i = 0; i < rowCollumnSquareArray.length; i++){
			if(rowCollumnSquareArray[i].getValue() != 0){
				thisSquare[i] = false;
			}
		}
		return thisSquare;
	}

	public Cell[] getRowCollumnSquare(int value, char rowCollumnSquare) {
		switch(rowCollumnSquare){
		case 'r' : return getRow(value);
		case 'c' : return getCollumn(value);
		case 's' : return getSquare(value);
		}
		return null;
	}

	public void outputRows(){
		for(int i = 0; i < 9; i++){
			Cell[] row = getRow(i);
			for(int j = 0; j < 9; j++){
				System.out.print(row[j].getValue());
			}
			System.out.println();
		}
	}
	
	public void outputCollumns(){
		int[][] puzzleArray = new int[9][9];
		for(int i = 0; i < 9; i++){
			Cell[] collumn = getCollumn(i);
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
		Cell[] square = getSquare(squareNum);
		for(int i = 0; i<9; i++){
			System.out.print(square[i].getValue());
		}
	}
	
}
