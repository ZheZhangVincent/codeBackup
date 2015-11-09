package assignment1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPproperties {
	


	public static void main(String[] args) {
		 
		ReadPproperties obj = new ReadPproperties();
		obj.atuorun();
	 
	  }
	
	public static String[] atuorun() {
		String[] value = null;
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("names.properties"));
		  
		  for(String key : properties.stringPropertyNames()) {
			  value = properties.getProperty(key).split(",");
			  
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(value[1]);
		return value;
		
	}
	
}
