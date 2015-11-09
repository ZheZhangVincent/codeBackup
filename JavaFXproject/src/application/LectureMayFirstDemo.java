package application;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LectureMayFirstDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        final Circle c1 = new Circle(300, 200, 50);

        root.getChildren().add(c1);
        /*RadialGradient rg = new RadialGradient(0, 1,
                c1.getRadius(),
                c1.getCenterX(),
                c1.getCenterY(),
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED),
                new Stop(1, Color.BLUE));
        */
        RadialGradient rg = RadialGradientBuilder.create()
                .centerX(c1.getCenterX())
                .centerY(c1.getCenterY())
                .radius(c1.getRadius())
                .proportional(false)
                .stops(new Stop(0, Color.RED),
                new Stop(0.5, Color.BLUE),
                new Stop(1, Color.GREEN))
                .build();

        c1.setFill(rg);

        final FadeTransition ft = new FadeTransition(Duration.millis(2000), c1);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                c1.setFill(Color.RED);
            }
        });

        c1.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ft.stop();
                ft.playFromStart();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
