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
import java.io.FileNotFoundException;
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
		
		Map<String, String> storedTemplates = webgen.processFiles(destination, source, templates);
		processor.loadTemplates(storedTemplates);
		
		for(Map.Entry<String, String> template : storedTemplates.entrySet()) {
			String regex = "\\.\\w+";
			String tempKey = template.getKey().toString().split(regex)[0];
			System.out.println(processor.expandTemplate(webgen.processContext(source, tempKey)));
		}
		//REGEX REMOVE TEXT
		
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

	public Map<String, Object> processContext(File source, String templateName) {
		if (!source.isDirectory()) {
			System.out.println("Not a directory!");
			System.exit(2);
		}
		
		
		
		File[] files = source.listFiles();
		
		System.out.println(source.getPath());
		
		Map<String, Object> newContext = new HashMap<>();
		
		//https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
		
		for (File child : files) {
			if (!child.isDirectory()) {
					//initialize gson
					try {
						Gson gson = new Gson();
						JsonElement response = gson.fromJson(new JsonReader(new FileReader(child.getAbsolutePath())), JsonElement.class);
						System.out.println(response);
						for (JsonElement jsonContext : response.getAsJsonArray()) {
							//System.out.println(jsonContext.getAsJsonObject().get("template").getAsString());
							//System.out.println(templateName);
							if (jsonContext.getAsJsonObject().get("template").getAsString().equals(templateName)) {
								//map object from json to string $template in hashmap newContext
								newContext.put("$template", jsonContext.getAsJsonObject().get("template").getAsString());
								//System.out.println(jsonContext.getAsJsonObject().get("template").getAsString());
								for (JsonElement jsonTemplate : jsonContext.getAsJsonObject().get("context").getAsJsonArray()) {
									for(String templateContext : jsonTemplate.getAsJsonObject().keySet()) {
										newContext.put(templateContext, jsonTemplate.getAsJsonObject().get(templateContext).getAsString());
										System.out.println(templateContext);
										System.out.println(jsonTemplate.getAsJsonObject().get(templateContext).getAsString());
									}
								}
							}
						}
					} catch (JsonIOException e) {
						//e.printStackTrace();
					} catch (JsonSyntaxException e) {
						//e.printStackTrace();
					} catch (FileNotFoundException e) {
						//e.printStackTrace();
					}
				}
			}
		
		return newContext;
		}
	}