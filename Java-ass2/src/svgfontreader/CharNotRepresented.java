package svgfontreader;

/**
 * Created with IntelliJ IDEA.
 * User: abx
 * Date: 3/05/2014
 * Time: 10:53 PM
 * Created for Assignment Two, COMP6700.2014, ANU, RSCS
 * @version 1.0
 * @author abx
 * @author (Sai Ma u5224340)
 * @see SvgFontReader
 */
public class CharNotRepresented extends RuntimeException {

    private final char c;

    public CharNotRepresented(char c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return String.format("The character %c is not in this font", c);
    }
}
