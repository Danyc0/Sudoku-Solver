import static org.junit.Assert.*;
import org.junit.Test;
/**
 * Tests for the Cell class
 * @author Daniel Clark (dac46@aber.ac.uk)
 *
 */
public class TestCell {
	/**
	 * Tests that the constructor constructs everything properly
	 */
	@Test
	public void testDefaultConstructor(){
		Cell testCell = new Cell(1, 2, 3);
		if(testCell.getRowNumber() != 1 || testCell.getCollumnNumber() != 2 || testCell.getValue() != 3){
			fail();
		}
		assertNotNull(testCell.getPencilMarks());
	}
	
	/**
	 * Tests the equals method 
	 */
	@Test
	public void testEquals(){
		Cell firstCell = new Cell(1, 2, 3);
		Cell secondCell = new Cell(1, 2, 3);
		Cell thirdCell = new Cell(3, 2, 1);
		assertTrue(firstCell.equals(secondCell));
		assertFalse(firstCell.equals(thirdCell));
	}
	
	/**
	 * Tests the removePencilMark method 
	 */
	@Test
	public void testRemovePencilMark(){
		Cell testCell = new Cell(1, 2, 3);
		testCell.removePencilMark(1);
		assertTrue(testCell.getPencilMarks()[0] != 1);
	}
}