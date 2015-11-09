package application;

/**
 * Created with IntelliJ IDEA.
 * User: abx
 * Date: 7/05/13
 * Time: 12:19 PM
 */

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ComboTransitions extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Scene scene  = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(700);
        primaryStage.setHeight(400);


        final Rectangle rect = new Rectangle(100, 50, 50, 50);
        rect.setFill(Color.RED);

        TranslateTransition translate = new
                TranslateTransition(Duration.millis(1500),rect);
        translate.setFromX(100);
        translate.setToX(300);
        translate.setFromY(50);
        translate.setToY(250);
        translate.setCycleCount(1);
        translate.setAutoReverse(true);

        RotateTransition rotate =
                new RotateTransition(Duration.millis(1500), rect);
        rotate.setByAngle(360f);
        //rotate.setAxis(new Point3D(0,0,1.0));
        System.out.println(rotate.getNode().rotationAxisProperty());
        rotate.setCycleCount(1);
        rotate.setAutoReverse(true);


//        SequentialTransition seqTran = new
//                SequentialTransition();
//        seqTran.getChildren().addAll(translate,rotate);
//        seqTran.setCycleCount(Timeline.INDEFINITE);
//        seqTran.setAutoReverse(true);
//        seqTran.play();


        ParallelTransition parTran = new
                ParallelTransition();
        parTran.getChildren().addAll(translate,rotate);
        parTran.setCycleCount(Timeline.INDEFINITE);
        parTran.setAutoReverse(true);
        parTran.play();

        root.getChildren().addAll(rect);
        primaryStage.show();
    }


}
