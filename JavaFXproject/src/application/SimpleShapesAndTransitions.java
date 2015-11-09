package application;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SimpleShapesAndTransitions extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Group root = new Group();
        final Scene scene = new Scene(root, 600, 450, Color.WHITE);
        primaryStage.setTitle("Hello World");

        final Circle c1 = new Circle(200, 200, 50);
        RadialGradient grad = RadialGradientBuilder.create()
                .centerX(c1.getCenterX())
                .centerY(c1.getCenterY())
                .radius(50)
                .proportional(false)
                .stops(new Stop(0, Color.RED),
                        new Stop(0.5, Color.BLUE),
                        new Stop(1, Color.GREEN))
                .build();

        c1.setFill(Color.RED);
//        c1.setOpacity(1.0);


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
//        ft.setAutoReverse(true);
//        ft.play();

        /*c1.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.printf("mouse out at (%.2f,%.2f)%n",
                        e.getX(), e.getY());
                ft.play();
            }
        });*/

        c1.onMouseEnteredProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ft.stop();
                ft.playFromStart();
            }
        });

        c1.onMouseExitedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.printf("mouse out at (%.2f,%.2f)%n",
                        e.getX(), e.getY());
                ft.stop();
//                ft.playFrom(Duration.millis(1000));
                c1.setOpacity(1.0);
            }
        });

        final Rectangle rect = RectangleBuilder.create()
                .width(20)
                .height(200)
                .fill(Color.BLUE)
                .x(400)
                .y(100)
                .build();

        rect.getTransforms().add(new Rotate(45, 410, 200));
        root.getChildren().addAll(c1, rect);

        scene.onKeyPressedProperty().set(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.R)
                    rect.getTransforms().add(new Rotate(22.5, 410, 200));
            }
        });


        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
