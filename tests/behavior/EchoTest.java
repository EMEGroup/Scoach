/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author mario
 */
public class EchoTest {
	
	private Echo target;
	
	public EchoTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		target = new Echo();
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testEchoResponseText0(){
		String textToTest = "Hola Mundo !!";
		
		String expectedText = textToTest;
		
		Map<String, List<String>> arguments;
		arguments = new HashMap<String, List<String>>();
		arguments.put("text", Arrays.asList( new String[]{textToTest} ) );
		Map<String, String> returned = target.Run(arguments);
		
		String receivedTest = returned.get("text");
		
		assertEquals("Echo responded a text different from the one passed.", 
			expectedText, receivedTest);
	}
	
	@Test
	public void testEchoResponseText1(){
		String textToTest = "AAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSASASDSDSDASCASADWDASDACASCACASDASDASCASACS";
		
		String expectedText = textToTest;
		
		Map<String, List<String>> arguments;
		arguments = new HashMap<String, List<String>>();
		arguments.put("text", Arrays.asList( new String[]{textToTest} ) );
		Map<String, String> returned = target.Run(arguments);
		
		String receivedTest = returned.get("text");
		
		assertEquals("Echo responded a text different from the one passed.", 
			expectedText, receivedTest);
	}
	
	@Test
	public void testEchoResponseText2(){
		String textToTest = "A\nB\nC\nD\nE\nF\n";
		
		String expectedText = textToTest;
		
		Map<String, List<String>> arguments;
		arguments = new HashMap<String, List<String>>();
		arguments.put("text", Arrays.asList( new String[]{textToTest} ) );
		Map<String, String> returned = target.Run(arguments);
		
		String receivedTest = returned.get("text");
		
		assertEquals("Echo responded a text different from the one passed.", 
			expectedText, receivedTest);
	}
	
	@Test
	public void testEchoResponseText3(){
		String textToTest = 
			"Escritura en español para probar el manejo de caracteres "
			+ "no propios del idioma inglés.";
		
		String expectedText = textToTest;
		
		Map<String, List<String>> arguments;
		arguments = new HashMap<String, List<String>>();
		arguments.put("text", Arrays.asList( new String[]{textToTest} ) );
		Map<String, String> returned = target.Run(arguments);
		
		String receivedTest = returned.get("text");
		
		assertEquals("Echo responded a text different from the one passed.", 
			expectedText, receivedTest);
	}
	
	@Test
	public void testEchoResponseText4(){
		String textToTest = "このテキストは日本語で書きました。";
		
		String expectedText = textToTest;
		
		Map<String, List<String>> arguments;
		arguments = new HashMap<String, List<String>>();
		arguments.put("text", Arrays.asList( new String[]{textToTest} ) );
		Map<String, String> returned = target.Run(arguments);
		
		String receivedTest = returned.get("text");
		
		assertEquals("Echo responded a text different from the one passed.", 
			expectedText, receivedTest);
	}
}
