
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
		//Start the program in batch mode
		if(args.length != 0){
			for(String argument : args){
				if(argument.endsWith(".sud")){
					Solver solver = new Solver(argument, true);
					solver.start();
				}
			}
		}
		else{
			//Start the program in GUI mode
			GUI gui = new GUI();
		}
		System.out.println();
	}
}
