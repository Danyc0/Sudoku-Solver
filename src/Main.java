
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Solver solver = new Solver(args[0]);
			solver.start();
		}
		catch(Exception ArrayIndexOutOfBoundsException){
			GUI gui = new GUI();
		}
	}
}
