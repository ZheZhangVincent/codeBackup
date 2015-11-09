import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
 
public class GridPaneExample extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Grid Pane Example!");
 
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setGridLinesVisible(true);
        Button button1 = new Button("OK");
        gridPane.add(button1,0,0,1,2);
 
        Button button2 = new Button("Button2");
        gridPane.add(button2,1,0,1,1);
 
        Button button3 = new Button("Button3");
        gridPane.add(button3,1,1,1,1);
 
        Button button4 = new Button("Button4");
        gridPane.add(button4,0,2,2,1);
        gridPane.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(gridPane, 400, 250));
        primaryStage.show();
    }
}