package assignment1;

import java.io.File;
import java.util.Properties;

public class DirectoryLister {

	public static void main(String[] args){
		final String suffix = args.length < 1 ? "" : args[0];
		
		String cwdName = System.getProperty("names.properties");
		File cwd = new File(cwdName);
		
		FilenameFileter ffilter = new FilenameFilter() {
			
			public boolean accept(File f, String n) {
				
				return (n.endsWith(suffix)) ? true : false;
				
			}
		};
		
		if(cwd.isDirectory()) {
			
			System.out.println("This directory contains: ");
			for (File file: cwd.listFiles(ffilter))
				System.out.printf("%s: %d\n", file.getName(), file.length());
		}
		
	}
	
}
