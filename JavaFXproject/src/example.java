import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.FloatMap;
import javafx.scene.text.Text;


public class example {
	
	 int width = 220;
	 int height = 100;
	 
	 FloatMap floatMap = new FloatMap();
	 floatMap.setWidth(220);
	 floatMap.setHeight(height);

	 for (int i = 0; i < width; i++) {
	     double v = (Math.sin(i / 20.0 * Math.PI) - 0.5) / 40.0;
	     for (int j = 0; j < height; j++) {
	         floatMap.setSamples(i, j, 0.0f, (float) v);
	     }
	 }

	 DisplacementMap displacementMap = new DisplacementMap();
	 displacementMap.setMapData(floatMap);

	 Text text = new Text();
	 text.setX(40.0);
	 text.setY(80.0);
	 text.setText("Wavy Text");
	 text.setFill(Color.web("0x3b596d"));
	 text.setFont(Font.font(null, FontWeight.BOLD, 50));
	 text.setEffect(displacementMap);


}
