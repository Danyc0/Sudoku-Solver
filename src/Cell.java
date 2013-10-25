
/**
 * @author Daniel Clark (dac46@aber.ac.uk)
 * The class that stores all the data about any particular cell in the grid
 */

public class Cell{
	int value;
	int row, collumn;
	boolean[] pensilMarks;
	
	/**
	 * Constructs a new cell with it's position and Svalue
	 * @param x
	 * @param y
	 * @param value
	 */
	public Cell(int x, int y, int value){
		this.row = x;
		this.collumn = y;
		this.value = value;
		//Lists all possible pensil marks to be possible
		this.pensilMarks = makeTrueArray(9);
	}
	
	/**
	 * Checks if 2 cells are equal
	 * @param other
	 * @return
	 */
	public boolean equals(Cell other){
	//This was implemented for the purposes of checking if the current state of the board had changed,
	//but that feature was never implemented
		if(this.getValue() == other.getValue()){
			return true;
		}
		return false;
	}
	
	/**
	 * Make an array full of Trues rather than the default which is Falses
	 * @param size
	 * @return
	 */
	private boolean[] makeTrueArray(int size) {
		boolean[] trueArray = new boolean[size];
		for(int i = 0; i<size; i++){
			trueArray[i] = true;
		}
		return trueArray;
	}
	
	/**
	 * Remove a pencil mark from this cell
	 * @param value
	 */
	public void removePencilMark(int value){
		pensilMarks[(value-1)] = false;
	}
	
	/**
	 * Return an array of all the pencil marks for this cell
	 * @return
	 */
	public int[] getPencilMarks(){
		int[] values = new int[9];
		int count = 0;
		for(int i = 0; i<pensilMarks.length; i++){
			if(pensilMarks[i] == true){
				values[count] = (i+1);
				count++;
			}
		}
		return values;
	}

	/**
	 * Return the value of this cell
	 * @return
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * Set the value of this cell
	 * @param value
	 */
	public void setValue(int value){
		this.value = value;
	}

	/**
	 * Return which row this cell resides in
	 * @return
	 */
	public int getRowNumber(){
		return row;
	}
	
	/**
	 * Return which column this cell resides in 
	 * @return
	 */
	public int getCollumnNumber(){
		return collumn;
	}

}
