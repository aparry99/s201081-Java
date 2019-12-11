package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.uos.i2p.assignment.ReplaceTemplateText;
import uk.ac.uos.i2p.assignment.TemplateProcessor;

public class TemplateExampleTest {
	
	@Test
	void WHEN_loadTemplateIsCalledAndTemplateIsPresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear Arthur, We are happy to offer you a bonus of £20.", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndTemplateIsNotPresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer", null);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("", result);
	}
	
	@Test
	void WHEN_loadTemplatesAreCalledAndTemplatesArePresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear Arthur, We are happy to offer you a bonus of £20.", result);
	}
	
	@Test
	void WHEN_loadTemplatesAreCalledAndTemplatesAreNotPresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("offer", null);
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("", result);
	}
	
	
}


