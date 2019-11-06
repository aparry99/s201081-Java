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
			if (temp != null && temp != templateMap) {
				templateMap.put(temp.getKey(), temp.getValue());
				System.out.println(temp.getKey());
			}			
		}
	}

	@Override
	public String expandTemplate(Map<String, Object> context) {
		for (String key : context.keySet()) {
			String keyValue = context.get(key).toString();
			
//			System.out.println(keyValue);
		}
		
		
		
		return null;
	}

}