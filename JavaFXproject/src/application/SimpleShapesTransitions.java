package application;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SimpleShapesTransitions extends Application {
	
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);
	
	public static void main(String[] args) {
        launch(args);
    }
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Shape and Transition");
		Group root = new Group();
		Scene scene = new Scene(root, 400, 400, Color.WHITE);
		primaryStage.setScene(scene);
		Group group = new Group();
		root.getChildren().add(group);

		Rectangle rect = new Rectangle(50, 50, 100, 150);
		rect.setFill(Color.RED);
		group.getChildren().add(rect);
		
		final TranslateTransition transition = createTranslateTransition(rect);
		
		moveRectOnMousePress(scene, rect, transition);
		rotateRectOnKeyPress(scene, rect);
		
		
		
		
		
		
		primaryStage.show();
	}
	
	private TranslateTransition createTranslateTransition(final Rectangle rect) {
	    final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, rect);
	    transition.setOnFinished(new EventHandler<ActionEvent>() {
	      public void handle(ActionEvent t) {
	    	  rect.setX(rect.getTranslateX() + rect.getX());
	    	  rect.setY(rect.getTranslateY() + rect.getY());
	    	  rect.setTranslateX(0);
	    	  rect.setTranslateY(0);
	      }
	    });
	    return transition;
	  }
	
	private void moveRectOnMousePress(Scene scene, final Rectangle rect, final TranslateTransition transition) {
	    scene.setOnMousePressed(new EventHandler<MouseEvent>() {
	    	
	    	public void handle(MouseEvent event) {
	    		transition.setToX(event.getSceneX() - rect.getX());
	    		transition.setToY(event.getSceneY() - rect.getY());
	    		transition.playFromStart();

	      }
	    });
	  }
	
	private void rotateRectOnKeyPress(Scene scene, final Rectangle rect) {
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    	
			public void handle(KeyEvent keyEvent) {
	    		
				if (keyEvent.getCode().toString() == "UP") {
					rect.setRotate(rect.getRotate() + 45);

				}
				if (keyEvent.getCode().toString() == "DOWN") {
					rect.setRotate(rect.getRotate() - 45);

				}
				
	      }
	    });
	  }

}
