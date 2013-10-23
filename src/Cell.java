
public class Cell{
	int value;
	int row, collumn;
	boolean[] pensilMarks;
	public Cell(int x, int y, int value){
		this.row = x;
		this.collumn = y;
		this.value = value;
		this.pensilMarks = makeTrueArray(9);
	}
	
	public boolean equals(Cell other){
		if(this.getValue() == other.getValue()){
			return true;
		}
		return false;
	}
	
	private boolean[] makeTrueArray(int size) {
		boolean[] trueArray = new boolean[size];
		for(int i = 0; i<size; i++){
			trueArray[i] = true;
		}
		return trueArray;
	}
	
	public void removePensilMark(int value){
		pensilMarks[(value-1)] = false;
	}
	
	public int[] getPensilMarks(){
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

	public int getValue(){
		return value;
	}
	
	public void setValue(int value){
		this.value = value;
	}

	public int getRowNumber(){
		return row;
	}
	
	public int getCollumnNumber(){
		return collumn;
	}

}
