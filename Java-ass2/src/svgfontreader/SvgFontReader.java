package svgfontreader;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: abx
 * Date: 24/04/2014
 * Time: 8:47 PM
 * Created for Assignment Two, COMP6700.2014, ANU, RSCS
 * @version 1.0
 * @author abx
 * @author (Sai Ma u5224340)
 * @see captchafx.CaptchaFX
 */

public class SvgFontReader extends DefaultHandler {

    /* path code for all glyphs in chosen font;
       characters belong to the chosen set charSet
       which is initialised by static block --
       one and for all possible fonts
     */
    private Map<Character, String> glyphs;

    private static Set<Character> charSet;
    
    //mark:create map to save number from 0 to 9, element from a to z

    static {
        charSet = new HashSet<Character>();
        for (char c = '0'; c <= '9'; c++) {
            charSet.add(c);
        }
        for (int i = (int) 'a'; i <= (int) 'z'; i++) {
            charSet.add((char) i);
        }
    }
    
    
    

    public Map<Character, String> getGlyphs() {
        return glyphs;
    }

    /**
     * @param c is the character from the charSet
     * @return path which represent c in the SVG font object
     * @throws CharNotRepresented exception if c in not in the charSet
     */
    public String get(char c) throws CharNotRepresented {
        String str = this.getGlyphs().get(c);
        if (str != null)
            return str;
        else
            throw new CharNotRepresented(c);
    }

    /**
     * SvgScanner constructor which creates a SAX scanner object,
     * open an svg file, scans and parses it; the document tree root
     * is referenced by <code>this.root</code> field
     *
     * @param fn is the name of an svg file
     * @throws SAXException if the scanning fails due to document being malformed
     * @throws IOException  if the file cannot be open for reading
     */
    public SvgFontReader(String fn) throws SAXException, IOException {
        super();
        glyphs = new HashMap<Character, String>();
        XMLReader xr = XMLReaderFactory.createXMLReader();
        /* keep the next two statements to avoid dtd validation:
         * it'll allow to run off-line, and 'll be lot quicker */
        xr.setFeature(
                "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                false);
        xr.setFeature("http://xml.org/sax/features/validation", false);

        xr.setContentHandler(this);
        xr.setErrorHandler(this);
        xr.setDTDHandler(this);
        FileReader fr = new FileReader(fn);
        xr.parse(new InputSource(fr));
    }

    // SAX event handlers overrides

    /**
     * event handler for the start document tag;
     * senses the document beginning and creates the root
     * of a tree element and its children (the root has
     * only one child --- the <svg> node which contains
     * all the graphics and group nodes as its children)
     */
    @Override
    public void startDocument() {
        //System.out.println("Start parsing document:");
        System.out.print("Find glyph elements and collect their " +
                "\"unicode\" and \"path\" attribute values...");
    }

    /**
     * that's it, the document is scanned, and the tree is built;
     * we only make crudest check that it was not malformed or empty
     */
    @Override
    public void endDocument() {
        System.out.println(" done");
    }

    /**
     * The start of a new element event handler:
     * first it checks that the corresponding element is nested inside
     * the (next to) top <svg> element; then it determines the kind of the
     * element -- group-like or basic graphics -- and created SvgContainerElement
     * or SvgGraphicsElement element correspondingly, sets its name ("unknown" if
     * the tag is not recognised) and type (container type or graphics type)
     *
     * @param uri   is The Namespace URI, not used since we do not process Namespace
     * @param name  is the opening (local) tag name ("svg", "g", "rect" etc)
     * @param qName is the qualified name (with prefix set for namespace), not used
     * @param atts  are the tag's attributes (we only look for "glyph", and in its
     *              children -- for "unicode" and "d")
     */
    @Override
    public void startElement(String uri, String name,
                             String qName, Attributes atts) {
        // System.out.println("The tag name is " + name);
        // value for the attribute "unicode" (which is glyph's unicode symbol)
        String unicodeValue = atts.getValue("unicode");
        // value for the attribute "d" (which is glyph's svg-path)
        String pathValue = atts.getValue("d");
        char c;

        if (name.equals("glyph") && unicodeValue != null) {
            c = unicodeValue.charAt(0);

            if (charSet.contains(c) && pathValue != null) {
                glyphs.put(c, pathValue);
            }
        }
    }
    
    
    //form a list to save characters information from CharSet
    public static ArrayList<Character> form_character_list() {
    	
    	ArrayList<Character> character_list = new ArrayList<Character>();
    	
    	for (Character item : charSet) {
    		character_list.add(item);
    	}
    	return character_list;
    }
    
    //get a random character from character_list
    public static Character get_Random_character() {
    	ArrayList<Character> character_list = SvgFontReader.form_character_list();
    	Random rand = new Random();
		int index = rand.nextInt(character_list.size());
		return character_list.get(index);
    }
    
    //form a list to save the selected characters information
    public static ArrayList<Character> selected_list() {
    	ArrayList<Character> selected_list = new ArrayList<Character>();
    	
    	//firstly, random to decide the length fixed to 7 or 8
    	Random rand = new Random();
    	int index = rand.nextInt(2);
    	int list_length = 0;
    	if (index == 0) {
    		list_length = 7;
    	}
    	else {
    		list_length = 8;
    	}
    	
    	//the length of selected_list fixed to 8 
    	while (selected_list.size() < list_length) {
    		selected_list.add(SvgFontReader.get_Random_character());
    	}
    	
    	return selected_list;
    }
    
    
    
    
    

    /* this is for self testing only */
    public static void main(String args[]) throws Exception {
    	
    	
    	
    	System.out.println(SvgFontReader.selected_list());
        System.out.println("The allowed character set --- " +
                "low-case ascii letters and decimal digits:");
        for (Character c : SvgFontReader.charSet) {
            System.out.printf("%c ", c);
        }
        System.out.println();

        SvgFontReader svgr = new SvgFontReader(args[0]);
        System.out.println("Found paths for characters from the set:");
        for (Character c : svgr.getGlyphs().keySet())
            System.out.printf("%c: %s%n", c, svgr.glyphs.get(c));
    }
}
