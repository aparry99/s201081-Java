package uk.ac.uos.i2p.webGenerator;

import uk.ac.uos.i2p.assignment.ReplaceTemplateText;
import uk.ac.uos.i2p.assignment.TemplateProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class webGen {
	public static void main(String[] args) {
		
		String userSource = null;
		String userTemplate = null;
		String userDestination = null;
		Boolean exists = false;
		
		if (args.length != 3) {
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
					
					Map<String, String> storedTemplates = webgen.processFiles(destination, templates, templates);
					processor.loadTemplates(storedTemplates);
					
					for(Map.Entry<String, String> template : storedTemplates.entrySet()) {
						String tempKey = new File(template.getKey()).getName();
						
						Map<String, Object> context = webgen.processContext(source, tempKey);
						processor.expandTemplate(context);
					}
					
					for (Map.Entry<String, String> expandedTemplates : storedTemplates.entrySet()) {
						String tempKey = new File(expandedTemplates.getKey()).getName();
						
						Map<String, Object> context = webgen.processContext(source, tempKey);
						
						Path path = Paths.get(destination.getAbsolutePath() + "/" + expandedTemplates.getKey() + ".html");
						try {
							Files.write(path, Arrays.asList(processor.expandTemplate(context)));
						}
						catch(Exception e) {
						}
					}
				}
				else {
					System.out.println("User declined process... \n Terminating program");
			    	System.exit(2);
				}
				inputScanner.close();
			}
			else {
				System.out.println("Output:");
				webGen webgen = new webGen();
				File destination = new File(".\\destination");
				File source = new File(".\\source");
				File templates = new File(".\\templates");
				
				TemplateProcessor processor = new ReplaceTemplateText();
				
				Map<String, String> storedTemplates = webgen.processFiles(destination, templates, templates);
				processor.loadTemplates(storedTemplates);
				
				for(Map.Entry<String, String> template : storedTemplates.entrySet()) {
					String tempKey = new File(template.getKey()).getName();
					
					Map<String, Object> context = webgen.processContext(source, tempKey);
					processor.expandTemplate(context);
				}
				
				for (Map.Entry<String, String> expandedTemplates : storedTemplates.entrySet()) {
					String tempKey = new File(expandedTemplates.getKey()).getName();
					
					Map<String, Object> context = webgen.processContext(source, tempKey);
					
					Path path = Paths.get(destination.getAbsolutePath() + "/" + expandedTemplates.getKey() + ".html");
					try {
						Files.write(path, Arrays.asList(processor.expandTemplate(context)));
					}
					catch(Exception e) {
					}
				}
			}
			System.exit(2);
		}
		
		webGen webgen = new webGen();
		File destination = new File(args[0]);
		File source = new File(args[1]);
		File templates = new File(args[2]);
		
		//System.out.println(source.getAbsolutePath());
		
		TemplateProcessor processor = new ReplaceTemplateText();
		
		Map<String, String> storedTemplates = webgen.processFiles(destination, templates, templates);
		//System.out.println(storedTemplates);
		processor.loadTemplates(storedTemplates);
		
		for(Map.Entry<String, String> template : storedTemplates.entrySet()) {
//			String regexFindDot = "\\.\\w+";
			String tempKey = new File(template.getKey()).getName();
			
			Map<String, Object> context = webgen.processContext(source, tempKey);
//			System.out.println(context);
			processor.expandTemplate(context);
		}
		
		for (Map.Entry<String, String> expandedTemplates : storedTemplates.entrySet()) {
			//System.out.println(expandedTemplates.getKey());
			String tempKey = new File(expandedTemplates.getKey()).getName();
			//System.out.println(tempKey);
//			Pattern pattern = Pattern.compile("[^\\\\]+\\.");
//			
//			Matcher m = pattern.matcher(expandedTemplates.getKey());
//			String tempKey = "";
//			if(m.find()) {
//			    tempKey = m.group(0).replace(".", "");
//			}
			//System.out.println(tempKey + "          " + expandedTemplates.getKey());
			
			Map<String, Object> context = webgen.processContext(source, tempKey);
			
			
//			expandedTemplates.setValue(processor.expandTemplate(context));
			//System.out.println(Arrays.asList(processor.expandTemplate(context)));
			//System.out.println(destination.getAbsolutePath());
			Path path = Paths.get(destination.getAbsolutePath() + "/" + expandedTemplates.getKey() + ".html");
			try {
				Files.write(path, Arrays.asList(processor.expandTemplate(context)));
			}
			catch(Exception e) {
				
			}
		}
		//source, destination, templates
		
		//source = context
		//a) .properties file
		//b) .json file
		
		//destination = folder to spit out completed templates
		
		//templates = where templates are stored
		// folder with any text format
	}
	
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
					
					String regex = "\\.\\w+";
//					String childName = child.getName().split(regex)[0];
					
					String utfReplace = everything.replaceAll(new String("�".getBytes("UTF-8"), "UTF-8"), "");
					
//					newTemplates.put(childName, utfReplace);
//					File childName = new File(child.getAbsolutePath().toString().replace(source.getAbsolutePath(), "").substring(1).split(regex)[0]);
					
//					newTemplates.put(new File(child.getAbsolutePath().toString().replace(source.getAbsolutePath(), "")).getName().split(regex)[0], utfReplace);
					newTemplates.put(child.getAbsolutePath().toString().replace(source.getAbsolutePath(), "").substring(1).split(regex)[0], utfReplace);
//					System.out.println(new File(child.getAbsolutePath().toString().replace(source.getAbsolutePath(), "")).getName().split(regex)[0]);
//					System.out.println(child.getAbsolutePath().toString().replace(source.getAbsolutePath(), "").substring(1).split(regex)[0]);
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else {
				//Below line makes new folder at child location, instead need to get pat of destination and put it in there
				//System.out.println(child.getAbsolutePath());
				//System.out.println(source.getAbsolutePath());
				//System.out.println(destination.getAbsolutePath());
				
				newTemplates.putAll(processFiles(destination, source, new File(child.getAbsolutePath())));
				new File(child.getAbsolutePath().replace(source.getAbsolutePath(), destination.getAbsolutePath())).mkdirs(); //Makes new folder.
				//Line below to call this function again, but for this nested folder.
				
//				System.out.println("Not a file");
//				System.exit(2);
			}
		}
		//System.out.println(newTemplates);
		return newTemplates;

	}

	public Map<String, Object> processContext(File source, String templateName) {
		if (!source.isDirectory()) {
			System.out.println("Not a directory!");
			System.exit(2);
		}
		
		File[] files = source.listFiles();
		Map<String, Object> newContext = new HashMap<>();
		
		for (File child : files) {
			if (!child.isDirectory()) {
					//initialize gson
					try {
						Gson gson = new Gson();
						JsonElement json = gson.fromJson(new JsonReader(new FileReader(child.getAbsolutePath())), JsonElement.class);
						//System.out.println(response);
						for (JsonElement jsonContext : json.getAsJsonArray()) {
							//System.out.println(jsonContext.getAsJsonObject().get("template").getAsString());
							//System.out.println(templateName);
							if (jsonContext.getAsJsonObject().get("template").getAsString().equals(templateName)) {
								//map object from json to string $template in hashmap newContext
								newContext.put("$template", jsonContext.getAsJsonObject().get("template").getAsString());
								//System.out.println(jsonContext.getAsJsonObject().get("template").getAsString());
								for (JsonElement jsonTemplate : jsonContext.getAsJsonObject().get("context").getAsJsonArray()) {
									for(String templateContext : jsonTemplate.getAsJsonObject().keySet()) {
										newContext.put(templateContext, jsonTemplate.getAsJsonObject().get(templateContext).getAsString());
//										System.out.println("getKey \n " + templateContext);
//										System.out.println("getValue \n " + jsonTemplate.getAsJsonObject().get(templateContext).getAsString());
									}
								}
							}
						}
					} catch (JsonIOException e) {
						e.printStackTrace();
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		return newContext;
		}
	}