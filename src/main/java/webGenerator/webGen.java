package webGenerator;

import uk.ac.uos.i2p.assignment.ReplaceTemplateText;
import uk.ac.uos.i2p.assignment.TemplateProcessor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class webGen {
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("usage webGen destination source templates");
			System.exit(2);
		}
		File destination = new File(args[0]);
		File source = new File(args[1]);
		File templates = new File(args[2]);
		webGen webgen = new webGen();
		webgen.process(destination, source, templates);
		
		
		
		//source, destination, templates
		
		//source = context
		//a) .properties file
		//b) .json file
		
		//destination = folder to spit out completed templates
		
		//templates = where templates are stored
		// folder with any text format
	}
	
	public void process(File destination, File source, File templates) {
		if (!destination.isDirectory() || !source.isDirectory() || !templates.isDirectory()) {
			System.out.println("Not a directory!");
			//do exit
		}
		
		File[] files = templates.listFiles();
		
		for (File child : files) {
			System.out.println(child.getName());
		}
	}
	
	//templates, loop through getting templates
}
