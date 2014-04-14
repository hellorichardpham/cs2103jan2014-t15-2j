import static org.junit.Assert.*;

import org.junit.Test;


public class ProcessTest {
	//@author A0085107J
	@Test
	public void testProcessTime() {
		//the following methods will test different possibilities that user will input
		ProcessCommand pc = new ProcessCommand();

		//user enter range with "hrs"
		String timeDetails = "1400hrs1559hrs";
		pc.processTime(timeDetails);
		Command cAfter = pc.getCommand();	
		assertTrue(cAfter.getStartHours().equals("14"));
		assertTrue(cAfter.getStartMins().equals("00"));
		assertTrue(cAfter.getEndHours().equals("15"));
		assertTrue(cAfter.getEndMins().equals("59"));

		//user enter range with "hr"
		String timeDetails2 = "1400hr1559hr";
		pc.processTime(timeDetails2);
		Command cAfter2 = pc.getCommand();	
		assertTrue(cAfter2.getStartHours().equals("14"));
		assertTrue(cAfter2.getStartMins().equals("00"));
		assertTrue(cAfter2.getEndHours().equals("15"));
		assertTrue(cAfter2.getEndMins().equals("59"));

		//user enter only end time with "hr"
		String timeDetails3 = "1400hr";
		pc.processTime(timeDetails3);
		Command cAfter3 = pc.getCommand();	
		assertTrue(cAfter3.getStartHours() == null);
		assertTrue(cAfter3.getStartMins() == null);
		assertTrue(cAfter3.getEndHours().equals("14"));
		assertTrue(cAfter3.getEndMins().equals("00"));

		//user enter only end time with "hrs"
		String timeDetails4 = "1400hrs";
		pc.processTime(timeDetails4);
		Command cAfter4 = pc.getCommand();	
		assertTrue(cAfter4.getStartHours() == null);
		assertTrue(cAfter4.getStartMins() == null);
		assertTrue(cAfter4.getEndHours().equals("14"));
		assertTrue(cAfter4.getEndMins().equals("00"));

	}
	
	//@author A0083093E
	@Test
	public void testExtractTime(){
		String[] splitInput = {"add", "lunch", "1400hrs","to", "1500hrs"};
		ProcessCommand pc = new ProcessCommand();
		String result = pc.extractTime(splitInput);
		assert(result.equals("1400hrs1500hrs"));
		
		String[] splitInput2 = {"add", "lunch", "1400hrs","-", "1500hrs"};
		String result2 = pc.extractTime(splitInput2);
		assert(result2.equals("1400hrs1500hrs"));

		String[] splitInput3 = {"add", "lunch", "1400hrs", "1500hrs"};
		String result3 = pc.extractTime(splitInput3);
		assert(result3.equals("1400hrs1500hrs"));
	}
}//end class