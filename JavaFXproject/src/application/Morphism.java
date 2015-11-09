package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Morphism extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Scene scene  = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(400);
        primaryStage.setHeight(300);


        final Rectangle rect = new Rectangle(100, 50, 50, 50);
        rect.setFill(Color.RED);
        //rect.setArcHeight(100);
        //rect.setArcWidth(100);
        final ClosePath cp = new ClosePath();

        final Circle circ = new Circle(125,75,25);
        circ.setFill(Color.BLUE);
        circ.setOpacity(0.0);

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue kv1 = new KeyValue(rect.xProperty(),300);
        final KeyValue kv2 = new KeyValue(rect.fillProperty(),Color.BLUE);
        //final KeyValue kv2 = new KeyValue(rect.opacityProperty(),0.0);
        final KeyValue kv3 = new KeyValue(rect.arcHeightProperty(),100);
        final KeyValue kv4 = new KeyValue(rect.arcWidthProperty(),100);
        /*final KeyValue kv1 = new KeyValue(rect.xProperty(), 300);
        final KeyValue kv2 = new KeyValue(rect.opacityProperty(), 0.0);
        final KeyValue kv3 = new KeyValue(circ.centerXProperty(), 300);
        final KeyValue kv4 = new KeyValue(circ.opacityProperty(), 1.0);*/
        final KeyFrame kf1 = new KeyFrame(Duration.millis(5000),kv1,kv2,kv3,kv4);
        //final KeyValue kv3 = new KeyValue(circ.centerXProperty(), 300);
        //final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        timeline.getKeyFrames().add(kf1);
        //timeline.getKeyFrames().addAll(kf1,kf2);
        timeline.play();
        //timeline.pause();

        root.getChildren().addAll(rect, circ);
        primaryStage.show();
    }
}
