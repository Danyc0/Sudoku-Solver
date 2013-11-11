import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests for the DataStructure class
 * @author Daniel Clark (dac46@aber.ac.uk)
 *
 */
public class TestDataStructure {
	/**
	 * Tests that the default constructor constructs everything properly
	 */
	@Test
	public void testDefaultConstructor(){
		DataStructure testDataStructure = new DataStructure();
		if(testDataStructure.getRow(0)[0].getValue() != 0 || testDataStructure.getColumn(0)[0].getValue() != 0 || testDataStructure.getSquare(0)[0].getValue() != 0){
			fail();
		}
	}
	
	/**
	 * Tests that the filePath constructor constructs everything properly and that the importFile method imports properly
	 */
	@Test
	public void testFilePathConstructor(){
		DataStructure testDataStructure = new DataStructure("data/web.sud");
		if(testDataStructure.getRow(0)[1].getValue() != 6 || testDataStructure.getRow(8)[7].getValue() != 7){
			fail();
		}
	}
	
	/**
	 * Tests the equals method 
	 */
	@Test
	public void testEquals(){
		DataStructure firstDataStructure = new DataStructure("data/web.sud");
		DataStructure secondDataStructure = new DataStructure("data/web.sud");
		DataStructure thirdDataStructure = new DataStructure("data/guardian.sud");
		assertTrue(firstDataStructure.equals(secondDataStructure));
		assertFalse(firstDataStructure.equals(thirdDataStructure));
	}
	
	/**
	 * Tests the findMostCommonValue method
	 */
	@Test
	public void testFindMostCommonValue(){
		DataStructure testDataStructure = new DataStructure();
		assertTrue(testDataStructure.findMostCommonValue() == 0);
	}
	
	/**
	 * Tests the checkForTargetNumber method
	 */
	@Test
	public void testCheckForTargetNumber(){
		DataStructure testDataStructure = new DataStructure("data/web.sud");
		assertTrue(testDataStructure.checkForTargetNumber(0, 'r', 6));
	}
}

