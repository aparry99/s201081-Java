package uk.ac.uos.i2p.webGenerator;

import uk.ac.uos.i2p.assignment.ReplaceTemplateText;
import uk.ac.uos.i2p.assignment.TemplateProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class webGen {
	public static void main(String[] args) {
		
		String userSource = null;
		String userTemplate = null;
		String userDestination = null;
		Boolean exists = false;
		
		if (args.length != 3) {
			@SuppressWarnings("resource")
			Scanner inputScanner = new Scanner(System.in);
			System.out.println("Would you like to specify source, template and destination folder? Y/N");
			System.out.println("Choosing \"N\" defaults to standard directories");
			System.out.println("----------------------------------------------------------------------");
			String userSpecifyString = inputScanner.nextLine().trim().toLowerCase();
			Boolean userSpecify = false;
			if (userSpecifyString.equals("y")) {
				userSpecify = true;
			}
			else if (userSpecifyString.equals("n")) {
				userSpecify = false;
			}
			else {
				System.out.println("Incorrect usage... \n Terminating program");
		    	System.exit(2);
			}
			
			if (userSpecify) {
				System.out.println("Please choose source, template and destination directory as shown below, or choose custom directory:");
				System.out.println("Usage: \"C:\\SomeFolder\\source\" OR \".\\source\" OR \"\\source\" OR \"source\"");
				System.out.println("----------------------------------------------------------------------------------------------------");
				
				File file = new File(".");
				File[] files = file.listFiles();
				for (File f : files) {
					if (!f.getName().contains(".")) {
						System.out.println(f.getPath());
					}
				}
				//source
				System.out.println("----------------------------------------------------------------------------------------------------");
				System.out.println("Enter source directory:");
				userSource = inputScanner.nextLine();
				File s = new File(userSource);
			    exists = s.exists();
			    if (!exists) {
			    	System.out.println("Invalid file path... \n Terminating program");
			    	System.exit(2);
			    }
				webGen.clearConsole();
				
				for (File f : files) {
					if (!f.getName().contains(".")) {
						System.out.println(f.getPath());
					}
				}
				//template
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("Enter template directory:");
				userTemplate = inputScanner.nextLine();
				File t = new File(userTemplate);
				exists = t.exists();
			    if (!exists) {
			    	System.out.println("Invalid file path... \n Terminating program");
			    	System.exit(2);
			    }
				webGen.clearConsole();
				
				for (File f : files) {
					if (!f.getName().contains(".")) {
						System.out.println(f.getPath());
					}
				}
				//destination
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("Enter destination directory:");
				userDestination = inputScanner.nextLine();
				File d = new File(userDestination);
				exists = d.exists();
//			    if (!exists) {
//			    	System.out.println("Invalid file path... \n Terminating program");
//			    	System.exit(2);
//			    }
				webGen.clearConsole();
				
				System.out.println("Would you like to process your template with directories listed below? Y/N \n");
				System.out.println("Source directory: " + userSource);
				System.out.println("Template directory: " + userTemplate);
				System.out.println("Destination directory: " + userDestination);
				System.out.println("--------------------------------------------------------------------------");
				String launchProcessor = inputScanner.nextLine().trim().toLowerCase();
				Boolean userLaunchBool;
				if (launchProcessor.equals("y")) {
					userLaunchBool = true;
				}
				else {
					userLaunchBool = false;
				}
				
				if (userLaunchBool) {
					webGen webgen = new webGen();
					File destination = new File(userDestination);
					File source = new File(userSource);
					File templates = new File(userTemplate);
					
					TemplateProcessor processor = new ReplaceTemplateText();
					
					Map<String, String> storedTemplates = webgen.processFiles(destination, source, templates);
					processor.loadTemplates(storedTemplates);
					
					for(Map.Entry<String, String> template : storedTemplates.entrySet()) {
						String regex = "\\.\\w+";
						String tempKey = template.getKey().toString().split(regex)[0];
						System.out.println(processor.expandTemplate(webgen.processContext(source, tempKey)));
					}
				}
			}
			
			else {
				System.out.println("Output:");
				webGen webgen = new webGen();
				File destination = new File(".\\destination");
				File source = new File(".\\source");
				File templates = new File(".\\templates");
				
				TemplateProcessor processor = new ReplaceTemplateText();
				
				Map<String, String> storedTemplates = webgen.processFiles(destination, source, templates);
				processor.loadTemplates(storedTemplates);
				
				for(Map.Entry<String, String> template : storedTemplates.entrySet()) {
					String regex = "\\.\\w+";
					String tempKey = template.getKey().toString().split(regex)[0];
					System.out.println(processor.expandTemplate(webgen.processContext(source, tempKey)));
				}
			}
			System.exit(2);
		}
		
		webGen webgen = new webGen();
		File destination = new File(args[0]);
		File source = new File(args[1]);
		File templates = new File(args[2]);
		
//		webGen webgen = new webGen();
//		File destination = new File(args[0]);
//		File source = new File(args[1]);
//		File templates = new File(args[2]);
		
		//System.out.println(source.getAbsolutePath());
		
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> storedTemplates = webgen.processFiles(destination, source, templates);
		processor.loadTemplates(storedTemplates);
		
		for(Map.Entry<String, String> template : storedTemplates.entrySet()) {
			String regex = "\\.\\w+";
			String tempKey = template.getKey().toString().split(regex)[0];
			System.out.println(processor.expandTemplate(webgen.processContext(source, tempKey)));
		}
		
		//source, destination, templates
		
		//source = context
		//a) .properties file
		//b) .json file
		
		//destination = folder to spit out completed templates
		
		//templates = where templates are stored
		// folder with any text format
	}
	
//	public static void isPathValid(String path) {
//	    File f = new File(path);
//	    try {
//	    	f.getCanonicalPath();
//	    }
//	    catch (Exception e) {
//	    	System.out.println("Not a valid path");
//	    }
//	}
	
	public static void clearConsole() {
		System.out.println(new String(new char[50]).replace("\0", "\r\n"));
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