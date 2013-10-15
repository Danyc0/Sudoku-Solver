
public class DataStructure {
	private Cell[][] rows;
	private Cell[][] collumn;
	private Cell[][] square;
	private int gridSize;
	private int gridSubSize;
	DataStructure(int[][]grid, int size, int subsize) throws subsizeNotFactorException{
		if((size % subsize) != 0){
			throw new subsizeNotFactorException();
		}
		else{
			rows = new Cell[9][9]; //THINK ABOUT SIZE SUBSIZE WHERE 9 IS SIZE AND 3 IS SUBSIZE IN THIS EXAMPLE
			collumn = new Cell[9][9];
			square = new Cell[9][9];
			populate(grid);
		}
	}
	
	public void populate(int[][]grid){
		int count[] = new int[9];
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				Cell myCell = new Cell(i, j, grid[i][j]);
				rows[i][j] = myCell;
				collumn[j][i] = myCell;
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
				square[countNum][count[countNum]] = myCell;
				count[countNum]++;
			}
		}
	}
	
	public Cell[] getRow(int rowNum){
		return this.rows[rowNum];
	}
	
	public Cell[] getCollumn(int collumnNum){
		return this.collumn[collumnNum];
	}

	public Cell[] getSquare(int squareNum) {
		return this.square[squareNum];
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
}
