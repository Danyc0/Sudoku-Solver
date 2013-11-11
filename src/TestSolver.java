import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests for the Solver class
 * @author Daniel Clark (dac46@aber.ac.uk)
 *
 */
public class TestSolver {
	/**
	 * Tests that the default constructor constructs everything properly
	 */
	@Test
	public void testDefaultConstructor(){
		Solver testSolver = new Solver();
		assertTrue(testSolver.getRow(0)[0].getValue() == 0 && testSolver.getRow(8)[8].getValue() == 0);
	}
	
	/**
	 * Tests that the filePath constructor constructs everything properly
	 */
	@Test
	public void testFilePathConstructor(){
		Solver testSolver = new Solver("data/web.sud", true);
		if(testSolver.getRow(0)[1].getValue() != 6 || testSolver.getRow(8)[7].getValue() != 7){
			fail();
		}
	}
	
	/**
	 * Tests the importFile method
	 */
	@Test
	public void testImportFile(){
		Solver testSolver = new Solver();
		testSolver.importFile("data/web.sud");
		int[] webPencilMarks = {3, 9};
		for(int i = 0; i < webPencilMarks.length; i++){
			if(webPencilMarks[i] != testSolver.getRow(0)[0].getPencilMarks()[i]){
				fail();
			}
		}
	}
	/**
	 * Tests that the program can correctly solve a basic Sudoku
	 */
	@Test
	public void testBasicStart(){
		Solver testSolver = new Solver("data/web.sud", false);
		testSolver.start();
		for(int i = 0; i < 9; i++){
			Cell[] row = testSolver.getRow(i);
			int count = 0;
			for(Cell cell : row){
				count = count + cell.getValue();
			}
			if(count != 45){
				fail();
			}
		}
	}
	
	/**
	 * Tests that the program can correctly solve a more complex Sudoku
	 */
	@Test
	public void testComplexStart(){
		Solver testSolver = new Solver("data/book70.sud", false);
		testSolver.start();
		for(int i = 0; i < 9; i++){
			Cell[] row = testSolver.getRow(i);
			int count = 0;
			for(Cell cell : row){
				count = count + cell.getValue();
			}
			if(count != 45){
				fail();
			}
		}
	}
}