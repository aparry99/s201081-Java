package webGenerator;

import uk.ac.uos.i2p.assignment.ReplaceTemplateText;
import uk.ac.uos.i2p.assignment.TemplateProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

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
		
		TemplateProcessor processor = new ReplaceTemplateText();
		
//		webgen.processContext(source);
		
		Map<String, String> storedTemplates = webgen.processFiles(destination, source, templates);
		processor.loadTemplates(storedTemplates);
		
		for(Map<String, String> template : storedTemplates)
		{
			
		}
		
		processor.expandTemplate(webgen.processContext(source));
		//source, destination, templates
		
		//source = context
		//a) .properties file
		//b) .json file
		
		//destination = folder to spit out completed templates
		
		//templates = where templates are stored
		// folder with any text format
	}
	
	public Map<String, String> processFiles(File destination, File source, File templates) {
		if (!templates.isDirectory()) {
			System.out.println(templates + "is not a valid file path");
			System.exit(2);
		}
		
		File[] files = templates.listFiles();
		
		Map<String, String> newTemplates = new HashMap<>();
		
		//https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
		
		for (File child : files) {
			if (!child.isDirectory()) {
				//find out why buffered reader
				//read file line by line, puts into newTemplates via string
				try (BufferedReader buffread = new BufferedReader (new FileReader(child.getPath()))) {
					StringBuilder stringbuild = new StringBuilder();
					String line = buffread.readLine();
					
					//loop through each line
					while (line != null) {
						stringbuild.append(line);
						stringbuild.append(System.lineSeparator());
						line = buffread.readLine();
						
					}
					String everything = stringbuild.toString();
					newTemplates.put(child.getName(), everything);
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else {
				System.out.println("Not a file");
			}
//			System.out.println(newTemplates);
		}
//		System.out.println(newTemplates);
		return newTemplates;
		
	}

	public Map<String, Object> processContext(File source) {
		if (!source.isDirectory()) {
			System.out.println("Not a directory!");
			System.exit(2);
		}
		
		File[] files = source.listFiles();
		
		Map<String, Object> newContext = new HashMap<>();
		
		//https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
		
		for (File child : files) {
			if (!child.isDirectory()) {
				//find out why buffered reader
				//read file line by line, puts into newTemplates via string
				try (BufferedReader buffread = new BufferedReader (new FileReader(child.getPath()))) {
					StringBuilder stringbuild = new StringBuilder();
					String line = buffread.readLine(); 
					
					//loop through each line
					while (line != null) {
						stringbuild.append(line);
						stringbuild.append(System.lineSeparator());
						line = buffread.readLine();
					}
					String everything = stringbuild.toString();
					newContext.put(child.getName(), everything);
					
					Gson gson = new Gson();
					JsonObject response = gson.fromJson(new JsonReader(new FileReader(child.getAbsolutePath())), JsonElement.class);
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else {
				System.out.println("Not a file");
			}
//			System.out.println(newContext);
		}
		return newContext;
		
	}

	
	
	//templates, loop through getting templates
}
