package uk.ac.uos.i2p.assignment;

import java.util.Map;

public interface TemplateProcessor {
	void loadTemplate(String name, String template);
	void loadTemplates(Map<String, String> templates);
	String expandTemplate(Map<String, Object> context);
}
