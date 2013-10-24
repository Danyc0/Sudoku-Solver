/**
 * @author Daniel Clark (dac46@aber.ac.uk)
 * A Program To Solve Sudoku Puzzles for CS21120
 */

public class Main {

	/**
	 * Starts the program by trying to start the Solver with the command line arguments, to enable batching of the program, no GUI is started
	 * If this fails (due to no command line arguments being sent), it will start the GUI
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//Start the program in batch mode
			Solver solver = new Solver(args[0], true);
			solver.start();
		}
		catch(Exception ArrayIndexOutOfBoundsException){
			//Start the program in GUI mode
			GUI gui = new GUI();
		}
	}
}
