import javafx.application.Application;  
import javafx.geometry.Insets;  
import javafx.geometry.Pos;  
import javafx.geometry.VPos;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.scene.control.Hyperlink;  
import javafx.scene.image.ImageView;  
import javafx.scene.layout.AnchorPane;  
import javafx.scene.layout.BorderPane;  
import javafx.scene.layout.FlowPane;  
import javafx.scene.layout.GridPane;  
import javafx.scene.layout.HBox;  
import javafx.scene.layout.Priority;  
import javafx.scene.layout.StackPane;  
import javafx.scene.layout.VBox;  
import javafx.scene.paint.Color;  
import javafx.scene.paint.CycleMethod;  
import javafx.scene.paint.LinearGradient;  
import javafx.scene.paint.Stop;  
import javafx.scene.shape.Rectangle;  
import javafx.scene.text.Font;  
import javafx.scene.text.FontWeight;  
import javafx.scene.text.Text;  
import javafx.stage.Stage;  
  
public class Layouts extends Application {  
  
    public static void main(String[] args) {  
        launch(args);  
    }  
  
    @Override  
    public void start(Stage primaryStage) throws Exception {  
        // ��ʼ��  
        primaryStage.setTitle("Layouts in JavaFX");  
        BorderPane root = new BorderPane();  
        Scene scene = new Scene(root, 700, 500); 
        primaryStage.setScene(scene);  
  
        // ���嶥����2��button  
        Button button1 = new Button("Current");  
        button1.setStyle("-fx-base: red;");  
        Button button2 = new Button("Projected");  
        button1.setPrefWidth(100);  
        button2.setPrefWidth(100);  
        button1.setPrefHeight(20);  
        button2.setPrefHeight(20);  
  
        // ���������ť  
        StackPane stackPane = new StackPane();  
        Rectangle helpIcon = new Rectangle();  
        helpIcon.setWidth(30.0);  
        helpIcon.setHeight(25.0);  
        helpIcon.setArcWidth(3.5);  
        helpIcon.setArcHeight(3.5);  
        helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true,  
                CycleMethod.NO_CYCLE, new Stop(0, Color.web("#4977A3")),  
                new Stop(0.5, Color.web("#B0C6DA")), new Stop(1, Color  
                        .web("#9CB6CF"))));  
        helpIcon.setStroke(Color.web("#D0E6FA"));  
        Text text = new Text();  
        text.setText("?");  
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 18));  
        text.setFill(Color.WHITE);  
        text.setStroke(Color.web("#7080A0"));  
        stackPane.getChildren().addAll(helpIcon, text);  
        stackPane.setAlignment(Pos.CENTER_RIGHT);// ���нڵ����Ҷ���  
        StackPane.setMargin(text, new Insets(0, 10, 0, 0));// Center "?"  
  
        // ʹ��HBox������BorderPane�Ķ���λ��  
        HBox hBox = new HBox();  
        hBox.getChildren().addAll(button1, button2, stackPane);  
        // give the stack pane any extra space  
        HBox.setHgrow(stackPane, Priority.ALWAYS);  
        hBox.getStyleClass().add("h-box");  
        root.setTop(hBox);  
  
        // ʹ��VBox������BorderPane�����λ��  
        VBox vBox = new VBox();  
        vBox.getStyleClass().add("v-box");  
        Text title = new Text("Data");  
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));  
        vBox.getChildren().add(title);  
        Hyperlink[] options = new Hyperlink[] { new Hyperlink("Sales"),  
                new Hyperlink("Marketing"), new Hyperlink("Distribution"),  
                new Hyperlink("Costs") };  
        for (Hyperlink e : options) {  
            VBox.setMargin(e, new Insets(0, 0, 0, 8));  
            vBox.getChildren().add(e);  
        }  
        root.setLeft(vBox);  
  
        // ʹ��AnchorPane������BorderPane���м�λ��  
        // AnchorPane�а���GridPane  
        GridPane gridPane = new GridPane();  
        gridPane.getStyleClass().add("grid-pane");  
        gridPane.setGridLinesVisible(true);  
        // Category in column 2, row 1  
        Text category = new Text("Sales:");  
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));  
        gridPane.add(category, 1, 0);  
        // Title in column 3, row 1  
        Text chartTitle = new Text("Current year");  
        chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));  
        gridPane.add(chartTitle, 2, 0);  
        // Subtitle in column 2-3, row 2  
        Text chartSubtitle = new Text("Goods and Services");  
        gridPane.add(chartSubtitle, 1, 1, 2, 1);  
        // House icon in column 1, row 1-2  
        ImageView houseIcon = new ImageView(getClass().getResource(  
                "Graphics/house.png").toString());  
        gridPane.add(houseIcon, 0, 0, 1, 2);  
        // Left label in column 1 (bottom), row 3  
        Text goodsPercent = new Text("Goods\n80%");  
        GridPane.setValignment(goodsPercent, VPos.BOTTOM);  
        gridPane.add(goodsPercent, 0, 2);  
        // Chart in column 2-3, row 3  
        ImageView chartImage = new ImageView(getClass().getResource(  
                "Graphics/piechart.png").toExternalForm());  
        gridPane.add(chartImage, 1, 2, 2, 1);  
        // Right label in column 4 (top), row 3  
        Text servicesPercent = new Text("Services\n20%");  
        GridPane.setValignment(servicesPercent, VPos.TOP);  
        gridPane.add(servicesPercent, 3, 2);  
  
        Button buttonSave = new Button("Save");  
        Button buttonCancel = new Button("Cancel");  
        HBox hBox2 = new HBox();  
        hBox2.setSpacing(10);  
        hBox2.getChildren().addAll(buttonSave, buttonCancel);  
        AnchorPane anchorPane = new AnchorPane();  
        anchorPane.getChildren().addAll(gridPane, hBox2);  
        AnchorPane.setBottomAnchor(hBox2, 10.0);  
        AnchorPane.setRightAnchor(hBox2, 10.0);  
        AnchorPane.setTopAnchor(gridPane, 10.0);  
        // Add the anchorPane to the center of border pane  
        root.setCenter(anchorPane);  
  
        // ʹ��FlowPane������BorderPane���ұ�λ��  
        // (ע��TilePane��FlowPane���ƣ�ֻ������FlowPane��ͬ��TilePaneÿ��tile�Ĵ�С����ͬ��)  
        FlowPane flowPane = new FlowPane();// Define a horizontal flow pane  
        flowPane.getStyleClass().add("flow-pane");  
        for (int i = 0; i < 8; i++) {  
            flowPane.getChildren().add(  
                    new ImageView(getClass().getResource(  
                            "Graphics/chart_" + (i + 1) + ".png")  
                            .toExternalForm()));  
        }  
        root.setRight(flowPane);  
  
        // ���ó�ʼ���������ߣ�after the component has been realized, but just before the  
        // frame is displayed��  
        button2.requestFocus();  
        // ��ʾ�������  
        primaryStage.show();  
    }  
  
}  