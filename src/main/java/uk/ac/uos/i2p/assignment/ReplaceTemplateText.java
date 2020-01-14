package uk.ac.uos.i2p.assignment;

import java.io.File;
import java.util.*;

//import exceptions.ContextValidationException;
//import exceptions.TemplateDoesNotExistException;

public class ReplaceTemplateText implements TemplateProcessor {
	Map<String, String> templateMap = new HashMap<>();
	private static final String TEMPLATE_KEY = "$template"; //set final key - $template used in multiple places and never changes
	
	@Override
	public void loadTemplate(String name, String template) {
			templateMap.put(name, template);
			//System.out.println(name);
	}
	
	@Override
	public void loadTemplates(Map<String, String> templates) {
		for (Map.Entry<String, String> temp : templates.entrySet()) {
				this.loadTemplate(new File(temp.getKey()).getName(), temp.getValue()); //Changed this line by adding new file.getname to remove folder names :) so context can find it :)
//				System.out.println(new File(temp.getKey()).getName());
		}
	}

	@Override
	public String expandTemplate(Map<String, Object> context) {
		// get value of $template from templateMap
		String replacedTemplate = templateMap.get(context.get(TEMPLATE_KEY));
//		System.out.println(templateMap.get(context.get(TEMPLATE_KEY)));
		// set variable to null value to return in case of null $template
		String nullTemplate = "";
		
		if (replacedTemplate != null) {
			for (Map.Entry<String, Object> expand : context.entrySet()) {
				String keyValue = expand.getKey();
				//System.out.println("getKey \n " + expand.getKey());
				//System.out.println("getValue \n " + expand.getValue());
				String regex = "\\$\\{" + keyValue + "\\}";
				replacedTemplate = replacedTemplate.replaceAll(regex, expand.getValue().toString());
			}
			//System.out.println(replacedTemplate);
			return replacedTemplate;
		}
		return nullTemplate;
	}
}