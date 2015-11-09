package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
public class ColouredShapes extends Application{
	
	final ListView<String> console = new ListView<String>();
    //create a observableArrayList of logged events that will be listed in console
    final ObservableList<String> consoleObservableList = FXCollections.observableArrayList();
    {
        //set up the console
        console.setItems(consoleObservableList);
        console.setLayoutY(375);
        console.setPrefSize(400, 25);
    }
    
	public static void main(String[] args) {
        launch(args);
    }
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("ColouredShapes");
		 Group root = new Group();
		 Scene scene = new Scene(root, 400, 400, Color.WHITE);
		 Group group = new Group();
		 primaryStage.setScene(scene);
		 
		 final Rectangle rect = new Rectangle(50, 50, 100, 80);
	     rect.setFill(Color.RED);
	     group.getChildren().add(rect);
	     
	     final Circle circ = CircleBuilder.create()
	    	      .radius(50)
	    	      .centerX(260)
	    	      .centerY(80)
	    	      .fill(Color.BLUE)
	    	      .strokeWidth(2)
	    	      .build();
	     group.getChildren().add(circ);
		 
	     final Polygon polygon = new Polygon();
	     polygon.getPoints().addAll(new Double[]{
	         200.0, 200.0,
	         150.0, 300.0,
	         250.0, 300.0 });
	     polygon.setFill(Color.YELLOW);
	     group.getChildren().add(polygon);
	     root.getChildren().add(console);
	     root.getChildren().add(group);
		 
	     group.setOnMouseMoved(new EventHandler<MouseEvent>() {
	    	    public void handle(MouseEvent me) {
	    	    	showOnConsole("Mouse located, x: " + me.getX() + ", y: " + me.getY() ); 
	    	    }
	     });
	     
	     circ.setOnMouseEntered(new EventHandler<MouseEvent>() {
	    	    public void handle(MouseEvent me) {
	    	    	circ.setFill(Color.GREEN);
	    	    }
	    	});
		 
	     circ.setOnMouseExited(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	            	circ.setFill(Color.BLUE);
	            }
	        });
	     
	     polygon.setOnMouseEntered(new EventHandler<MouseEvent>() {
	    	    public void handle(MouseEvent me) {
	    	    	polygon.setFill(Color.GREEN);
	    	    }
	    	});
		 
	     polygon.setOnMouseExited(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	            	polygon.setFill(Color.YELLOW);
	            }
	        });
	     
	     rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
	    	    public void handle(MouseEvent me) {
	    	    	rect.setFill(Color.GREEN);
	    	    }
	    	});
		 
	     rect.setOnMouseExited(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	            	rect.setFill(Color.RED);
	            }
	        });
	     
		 primaryStage.show();
	}
	
	private void showOnConsole(String text) {
        //if there is 8 items in list, delete first log message, shift other logs and  add a new one to end position
        if (consoleObservableList.size()==1) {
           consoleObservableList.remove(0);
        }
        consoleObservableList.add(text);
   }

}
