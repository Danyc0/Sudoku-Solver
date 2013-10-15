
public class Cell{
	int value;
	int row, collumn;
	public Cell(int x, int y, int value){
		this.row = x;      //CHECK THESE ARE CORRECT WAY ROUND
		this.collumn = y;  //
		this.value = value;
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
