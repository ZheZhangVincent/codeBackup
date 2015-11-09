package captchariser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: abx
 * Date: 25/04/2014
 * Time: 6:38 PM
 * Created for Assignment Two, COMP6700.2014, ANU, RSCS
 * @version 1.0
 * @author abx
 * @author (Sai Ma u5224340)
 * @see captchafx.CaptchaFX
 */

public class Rendered {

    // TODO implement this class if you want to render glyphs
    // TODO separately from the main class captchafx.CaptchaFX
	
	public String get_random_fontfile() {
		//finish testing
		String file_name = null;
		
		List<String> file_list = new ArrayList<String>();
		
		Properties properties = new Properties();
		
		try {
			  properties.load(new FileInputStream("svgfonts.properties"));
			  
			  for(String key : properties.stringPropertyNames()) {
				  file_list.add(properties.getProperty(key));
			  }
			  
			  Random rand = new Random(); 
			  int index = rand.nextInt(file_list.size());
			  file_name = file_list.get(index);
			  
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return file_name;
		
	}
	
	//return a random elements in the 26+10 = 36 list
	public int get_random_element() {
		//finish testing
		Random rand = new Random();
		int index = rand.nextInt(36);
		return index;
	}
	
	
	//return the random numbers of line
	public int get_line_number() {
		Random rand = new Random();
		int index = rand.nextInt(100);
		return index;
	}
	
	//return the line's X coordinate number (from 0 to 300)
	public int get_X_coordinate() {
		Random rand = new Random();
		int index = rand.nextInt(300);
		return index;
	}
	
	//return the line's Y coordinate number (from 0 to 200)
	public int get_Y_coordinate() {
		Random rand = new Random();
		int index = rand.nextInt(200);
		return index;
	}
	
	//return the random scaling and tilting elements
	
	public static void main(String args[]) {
		Rendered ren = new Rendered();
		String a = ren.get_random_fontfile();
		System.out.println(a);
		int b = ren.get_random_element();
		System.out.println(b);
	}

}
