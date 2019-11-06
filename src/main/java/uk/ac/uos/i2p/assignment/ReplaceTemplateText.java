package uk.ac.uos.i2p.assignment;

import java.util.*;
import java.util.regex.*;

public class ReplaceTemplateText implements TemplateProcessor {
	Map<String, String> templateMap = new HashMap<>();
	
	@Override
	public void loadTemplate(String name, String template) {
		templateMap.put(name, template);
//		System.out.println(templateMap.get("offer"));
	}
	
	@Override
	public void loadTemplates(Map<String, String> templates) {
		for (Map.Entry<String, String> temp : templates.entrySet()) {
			if (temp.getKey() != null && !templates.containsKey(temp.getKey())) {
				templateMap.put(temp.getKey(), temp.getValue());
				System.out.println(temp.getKey());
			}			
		}
	}

	@Override
	public String expandTemplate(Map<String, Object> context) {
		String replacedTemplate = templateMap.get(context.get("$template"));
		for (Map.Entry<String, Object> expand : context.entrySet()) {
			String keyValue = expand.getKey();
			// System.out.println(keyValue);
			String regex = "\\$\\{" + expand.getKey() + "\\}";
			replacedTemplate = replacedTemplate.replaceAll(regex, expand.getValue().toString());
		}
		return replacedTemplate;
	}
}