package webGenerator;

import uk.ac.uos.i2p.assignment.ReplaceTemplateText;
import uk.ac.uos.i2p.assignment.TemplateProcessor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class webGen {
	public static void main(String[] args) {
		Map<String, String> template = new HashMap<>();
		
		File destination = new File(args[0]);
		File source = new File(args[1]);
		File templates = new File(args[2]);
		
		if (!destination.isDirectory() || !source.isDirectory() || !templates.isDirectory()) {
			System.out.println("Not a directory!");
			//do exit
		}
		
		File[] files = templates.listFiles();
		
		for (File child : files) {
			System.out.println(child.getName());
		}
		
		//source, destination, templates
		
		//source = context
		//a) .properties file
		//b) .json file
		
		//destination = folder to spit out completed templates
		
		//templates = where templates are stored
		// folder with any text format
	}
	
	//templates, loop through getting templates
}
