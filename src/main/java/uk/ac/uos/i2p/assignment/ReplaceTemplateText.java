package uk.ac.uos.i2p.assignment;

import java.io.File;
import java.util.*;

//import exceptions.ContextValidationException;
//import exceptions.TemplateDoesNotExistException;

public class ReplaceTemplateText implements TemplateProcessor {
	Map<String, String> templateMap;
	private static final String TEMPLATE_KEY = "$template"; //set final key - $template used in multiple places and never changes
	
	public ReplaceTemplateText() {
		templateMap = new HashMap<String, String>();
	}
	
	@Override
	public void loadTemplate(String name, String template) {
			templateMap.put(name, template);
	}
	
	@Override
	public void loadTemplates(Map<String, String> templates) {
		for (Map.Entry<String, String> temp : templates.entrySet()) {
			//Changed this line by adding new file.getname to remove folder names so context can find it 
			this.loadTemplate(new File(temp.getKey()).getName(), temp.getValue()); 
		}
	}

	@Override
	public String expandTemplate(Map<String, Object> context) {
		// get value of $template from templateMap
		String replacedTemplate = templateMap.get(context.get(TEMPLATE_KEY));
		// set variable to null value to return in case of null $template
		String nullTemplate = "";
		
		if (replacedTemplate != null) {
			for (Map.Entry<String, Object> expand : context.entrySet()) {
				String keyValue = expand.getKey();
				String regex = "\\$\\{" + keyValue + "\\}";
				replacedTemplate = replacedTemplate.replaceAll(regex, expand.getValue().toString());
			}
			return replacedTemplate;
		}
		return nullTemplate;
	}
}