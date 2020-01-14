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
	void WHEN_loadTemplateIsCalledAndTemplateNameIsNotPresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("", result);
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
	void WHEN_loadTemplateIsCalledAndContextIsNotPresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer", null);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "");
		context.put("amount", "");
		context.put("$template", "");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndContextIsNotFullyPresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Jimmy");
		context.put("amount", "");
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear Jimmy, We are happy to offer you a bonus of £.", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndContextIsPresentButNot$Template() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Jimmy");
		context.put("amount", "");
		context.put("$template", "");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndContextHasNullValue() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", null);
		context.put("amount", null);
		context.put("$template", null);
		
		String result = processor.expandTemplate(context);
		
		assertEquals("", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndContextHasSurplusEntries() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Jimmy");
		context.put("amount", 20);
		context.put("occupation", "Software Engineer");
		context.put("age", 27);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear Jimmy, We are happy to offer you a bonus of £20.", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndMultipleTemplatesArePresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer1", "Dear ${name1}, We are happy to offer you a bonus of £${amount1}.");
		processor.loadTemplate("offer2", "Dear ${name2}, We are happy to offer you a bonus of £${amount2}.");
		processor.loadTemplate("offer3", "Dear ${name3}, We are happy to offer you a bonus of £${amount3}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name1", "Jimmy");
		context.put("amount1", 20);
		context.put("$template", "offer1");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear Jimmy, We are happy to offer you a bonus of £20.", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndMultipleContextArePresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer3", "Dear ${name3}, We are happy to offer you a bonus of £${amount3}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name1", "Jimmy");
		context.put("amount1", 20);
		context.put("$template", "offer1");
		context.put("name2", "Tim");
		context.put("amount2", 30);
		context.put("$template", "offer2");
		context.put("name3", "Jon");
		context.put("amount3", 15);
		context.put("$template", "offer3");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear Jon, We are happy to offer you a bonus of £15.", result);
	}
	
	@Test
	void WHEN_loadTemplateIsCalledAndTemplateKeyIsEmpty() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Jimmy");
		context.put("amount", 20);
		context.put("$template", "");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear Jimmy, We are happy to offer you a bonus of £20.", result);
	}	
	
	@Test
	void WHEN_loadTemplateIsCalledAndSpecialCharactersArePresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		processor.loadTemplate("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "~!@#%^&*()-_=+[]\\{}|;':\",./<>?");
		context.put("amount", 20);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear ~!@#%^&*()-_=+[]{}|;':\",./<>?, We are happy to offer you a bonus of £20.", result);
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
	void WHEN_loadTemplatesAreCalledAndSpecialCharactersArePresent() {		
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "~!@#%^&*()-_=+[]\\{}|;':\",./<>?");
		context.put("amount", 20);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("Dear ~!@#%^&*()-_=+[]{}|;':\",./<>?, We are happy to offer you a bonus of £20.", result);
	}
	
	@Test
	void WHEN_loadTemplatesAreCalledAndContextIsBlank() {		
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "");
		context.put("amount", "");
		context.put("$template", "");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("", result);
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
	void WHEN_loadTemplatesAreCalledAndTemplateNameIsNotPresent() {
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("name", "Arthur");
		context.put("amount", 20);
		context.put("$template", "offer");
		
		String result = processor.expandTemplate(context);
		
		assertEquals("", result);
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
	void WHEN_loadTemplatesIsCalledAndMultipleTemplatesArePresent() {
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
	
	@Test
	void WHEN_loadTemplatesIsCalledAndMultipleTemplatesArePresent_BUT_ContextMismatch() {
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> templates = new HashMap<>();
		templates.put("offer", "Dear ${name}, We are happy to offer you a bonus of £${amount}.");
		templates.put("weather", "Hi ${firstName}, The weather today is ${weather}.");
		templates.put("reminder", "Dear Mr. ${lastName}, This is a reminder that the amount of £${overdue} is overdue.");
		processor.loadTemplates(templates);
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put("tool", "Arthur");
		context.put("money", 20);
		context.put("$templates", "offer");
		String result = processor.expandTemplate(context);
		assertEquals("", result);
		
		context.clear();
		
		context.put("firstname", "Jimmy");
		context.put("weatherType", "cold");
		context.put("$templates", "weather");
		String result2 = processor.expandTemplate(context);
		assertEquals("", result2);
		
		context.clear();
		
		context.put("lastname", "Abbot");
		context.put("overdueMoney", 149.99);
		context.put("$templates", "reminder");
		String result3 = processor.expandTemplate(context);
		assertEquals("", result3);
		
		context.clear();
	}
}



