package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.uos.i2p.assignment.ReplaceTemplateText;
import uk.ac.uos.i2p.assignment.TemplateProcessor;

public class TemplateExampleTest {
	
	// TEMPLATE
	// TEMPLATE
	// TEMPLATE
	
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
	void WHEN_loadTemplateIsCalledAndTemplateIsDifferent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("template", "Hi ${firstName}, The weather today is ${weather}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "template");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Hi ${firstName}, The weather today is ${weather}.", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndTemplateIsDifferent_BUT_ContextMatches() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("template", "Hi ${name}, The weather today is ${weather}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Jimmy");
		context.put("weather", "cold");
		context.put("$template", "template");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Hi Jimmy, The weather today is cold.", result);
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
	
	// TEMPLATES
	// TEMPLATES
	// TEMPLATES
	
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
	void WHEN_loadTemplatesAreCalledAndTemplatesAreDifferent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("template", "Hi ${firstName}, The weather today is ${weather}.");
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "template");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Hi ${firstName}, The weather today is ${weather}.", result);
	}
	
	@Test
	void WHEN_loadTemplatesAreCalledAndTemplatesAreDifferent_BUT_ContextMatches() {
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("template", "Hi ${firstName}, The weather today is ${weather}.");
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("firstName", "Jimmy");
		context.put("weather", "cold");
		context.put("$template", "template");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Hi Jimmy, The weather today is cold.", result);
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
	
	@Test
	void WHEN_loadTemplatesAreCalledAndMultipleTemplatesArePresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		templates.put("weather", "Hi ${firstName}, The weather today is ${weather}.");
		templates.put("reminder", "Dear Mr. ${lastName}, This is a reminder that the amount of £${overdue} is overdue.");
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "offer");
		String result = processor.expandTemplate(context);
		assertEquals("Dear Arthur, We are happy to offer you a bonus of £20.", result);
		
		context.clear();
		
		context.put("firstName", "Jimmy");
		context.put("weather", "cold");
		context.put("$template", "weather");
		String result2 = processor.expandTemplate(context);
		assertEquals("Hi Jimmy, The weather today is cold.", result2);
		
		context.clear();
		
		context.put("lastName", "Abbot");
		context.put("overdue", 149.99);
		context.put("$template", "reminder");
		String result3 = processor.expandTemplate(context);
		assertEquals("Dear Mr. Abbot, This is a reminder that the amount of £149.99 is overdue.", result3);
		
		context.clear();
	}
}



