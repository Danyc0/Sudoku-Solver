
public class Main {

	/**
	 * @param args
	 */
	private final static String defaultPath = "/home/daniel/SudokuData/simple351.sud";
	public static void main(String[] args) {
		Solver solver;
		try{
			solver = new Solver(args[0]);
		}
		catch(Exception ArrayIndexOutOfBoundsException){
			solver = new Solver(defaultPath);
		}
		solver.start();
	}
}
