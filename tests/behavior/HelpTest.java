package behavior;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HelpTest {
	
	private Help target;
	
	public HelpTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		target = new Help();
	}
	
	@After
	public void tearDown() {
		target = null;
	}
	
	@Test
	public void testDefaultText(){
		String expectedText = Help.HELPTEXT;
		
		Map<String, String> returned = target.Run(null);
		String receivedText = returned.get("text");
		
		assertEquals("The default help text returned was not the expected.", 
			expectedText, receivedText);	
	}
	
	@Test
	public void testEchoHelpText(){
		String expectedText = Echo.HELPTEXT;
		
		Map<String, List<String>> arguments = new HashMap<String, List<String>>();
		arguments.put("text", Arrays.asList( new String[]{"echo"} ) );
		
		Map<String, String> returned = target.Run(arguments);
		String receivedText = returned.get("text");
		
		assertEquals("Command Echo help text was not the expected.", 
			expectedText, receivedText);
	}
	
	@Test
	public void testSubmissionsHelpText(){
		String expectedText = Submissions.HELPTEXT;
		
		Map<String, List<String>> arguments = new HashMap<String, List<String>>();
		arguments.put("text", Arrays.asList( new String[]{"submissions"} ) );
		
		Map<String, String> returned = target.Run(arguments);
		String receivedText = returned.get("text");
		
		assertEquals("Command Submissions help text was not the expected.", 
			expectedText, receivedText);
	}
	
	@Test
	public void testStudentInfoHelpText(){
		String expectedText = StudentInfo.HELPTEXT;
		
		Map<String, List<String>> arguments = new HashMap<String, List<String>>();
		arguments.put("text", Arrays.asList( new String[]{"student"} ) );
		
		Map<String, String> returned = target.Run(arguments);
		String receivedText = returned.get("text");
		
		assertEquals("Command Submissions help text was not the expected.", 
			expectedText, receivedText);
	}
}
