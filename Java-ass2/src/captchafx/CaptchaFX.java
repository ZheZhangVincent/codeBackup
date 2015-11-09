package captchafx;


/**
 * Created with IntelliJ IDEA.
 * User: abx
 * Date: 25/04/2014
 * Time: 6:37 PM
 * Created for Assignment Two, COMP6700.2014, ANU, RSCS
 * @version 1.0
 * @author abx
 * @author (Sai Ma u5224340)
 * @see svgfontreader.SvgFontReader
 * @see captchariser.Transformer
 */

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Application.Parameters;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.xml.sax.SAXException;

import captchariser.Rendered;
import svgfontreader.CharNotRepresented;
import svgfontreader.SvgFontReader;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class CaptchaFX extends Application {

    private List<Group> letters = new ArrayList<>();
    private SVGPath currentCharPath;
    private Group letterEnclosure;
    private Group root;
    private Scale scale;
    private Translate translate;
    private SvgFontReader model;


    static final int scaleFactor = 40;
    static final int kerning = 3;
    private int flyToX = 75;
    private int flyToY = 100;
    private int flyFromX = 350;
    private int flyFromY = 500;
    private int currentCharX = flyToX;

    public static void main(String[] args)
            throws IOException, SAXException {


        Application.launch(args);
    }

    
    /** override start method which creates the scene
     *  and all nodes and shapes in it (main window only),
     *  and redefines how the nodes react to user inputs
     *  and other events;
     *  @param primaryStage Stage (the top level container)
     *  */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CAPTCHA with SVG fonts and JavaFX");
        //Parameters parameters = getParameters();
        //String fontFileName = parameters.getRaw().get(0);
        
        Rendered rend = new Rendered();
        String fontFileName = rend.get_random_fontfile();

        try {
            model = new SvgFontReader(fontFileName);
        } catch (SAXException e) {
            System.err.printf("cannot parse the file %s%n", fontFileName);
            System.exit(1);
        } catch (IOException e) {
            System.err.printf("cannot open the file %s%n", fontFileName);
            System.exit(2);
        }

        /* the main window */
        root = new Group();

        final Scene scene =
                SceneBuilder.create()
                        .root(root)
                        .width(300)
                        .height(200)
                        .fill(Color.WHITESMOKE)
                        .build();

        /* define the key pressed callback to generate flying glyphs */
        scene.onKeyPressedProperty().set(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {

                /* to allow erase everything and start anew by
                 * pressing escape-key */
                if (ke.getCode() == KeyCode.ESCAPE) {
                    root.getChildren().clear();
                    currentCharX = flyToX;
                    return;
                }
                /* what is entered on the keyboard */
                char c;
                
                
                //this method get the random element from list
                
                
                
                
                
                
                //##########

                // first constrain the key board events to those which _can_
                // represent text (including a single character
                try {
                    c = ke.getText().charAt(0);
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    System.out.printf("Callback for %s is not defined %n",
                            ke.getCode());
                    return;
                }

                // now get the SVG path for the character represented by the pressed key
                // OR detect absence of the character in the set and throw exception
                try {
                    String charCode = model.get(c);
//                System.out.printf("original glyph path: %s%n", charCode);
                    System.out.printf("%c pressed, it's in the set%n", c);

                    Bounds bb;
                    currentCharPath = new SVGPath();
                    currentCharPath.setContent(charCode);
//                    letterEnclosure = new Group();
                    letterEnclosure = new Group(currentCharPath);
//                    letterEnclosure.getChildren().add(currentCharPath);

                    // calculate glyph's size (dimensions of its bounding box
                    // for scaling it down to fixed (convenient) size
                    bb = currentCharPath.getLayoutBounds();
                    double xSize = bb.getWidth();
                    double ySize = bb.getHeight();
                    double aspectRatio = xSize / ySize;
                    double size = (min(xSize, ySize) + max(xSize, ySize)) / 2;
//                    double size = sqrt(min(xSize, ySize) * max(xSize, ySize));
                    currentCharPath.setFill(Color.DARKRED);
                    currentCharPath.setStrokeWidth(0);
                    root.getChildren().add(letterEnclosure);

                    // scale to the fixed size
                    scale = new Scale(-scaleFactor / size,
                            scaleFactor / size);
                    currentCharPath.getTransforms().add(scale);

                    // move to the same initial position
                    translate = new Translate();
                    double glyphCentreX = getNodePosX(currentCharPath);
                    double glyphCentreY = getNodePosY(currentCharPath);
//                    System.out.printf("the glyph centred at (%f,%f)%n", glyphCentreX, glyphCentreY);
//                    System.out.printf("must be moved to (%d,%d)%n", flyFromX, flyFromY);
                    translate.setX(flyFromX - glyphCentreX);
                    translate.setY(flyFromY - glyphCentreY);
                    currentCharPath.getTransforms().add(translate);
//                    System.out.printf("the glyph centred at (%f,%f)%n", glyphCentreX, glyphCentreY);

                    // define translation transition
                    TranslateTransition transTr = new
                            TranslateTransition(Duration.millis(1000),
                            letterEnclosure);
                    transTr.setFromX(flyFromX);
                    transTr.setFromY(flyFromY);
                    transTr.setToX(currentCharX);
                    transTr.setToY(flyToY);
                    transTr.setCycleCount(1);

                    // set path transition (to try instead of translate)
                    final Path transPath = new Path();
                    transPath.getElements().add(new MoveTo(flyFromX, flyFromY));
                    transPath.getElements().add(new LineTo(currentCharX, flyToY));
                    PathTransition pathTr = new PathTransition();
                    pathTr.setDuration(Duration.millis(1000));
                    pathTr.setPath(transPath);
                    pathTr.setNode(letterEnclosure);
                    pathTr.setOrientation(PathTransition.OrientationType.NONE);
                    pathTr.setCycleCount(1);
                    pathTr.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            transPath.getElements().clear();
                        }
                    });

                    // define rotate transition
                    RotateTransition rotTr = new
                            RotateTransition(Duration.millis(1000),
                            letterEnclosure);
                    rotTr.setByAngle(180f);
                    rotTr.setCycleCount(1);

                    // define parallel transition with translate
                    // (or path) and rotation (somersault)
                    ParallelTransition parTr = new
                            ParallelTransition();
                    parTr.getChildren().addAll(pathTr, rotTr);
                    parTr.play();

                    // crudely advance the horizontal location for the next glyph
                    // by glyph average size plus small space between ("kerning")
                    currentCharX += kerning + scaleFactor;

                    // add glyph to letters list (to take snapshot later)
                    letters.add(letterEnclosure);
                } catch (CharNotRepresented ce) {
                    System.err.println(ce);
                }

            }
        });


        /* define the mouse-click callback to take a snapshot
        * of the displayed glyphs and save it to a file */
        scene.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HBox letterBox = new HBox(letters.size());
                letterBox.getChildren().addAll(letters);
                WritableImage image = letterBox.snapshot(new
                        SnapshotParameters(), null);
                System.out.println("took snapshot of captcha" + image);
                BufferedImage bi = SwingFXUtils.fromFXImage(image, null);

                try {
                    File captchaFile = new File("captcha.png");
                    ImageIO.write(bi, "png", captchaFile);
                    root.getChildren().clear();
                    currentCharX = flyToX;

                } catch (IOException e) {
                    System.out.println("can't write to a file");
                }

            }
        });
        
        /**
        
        Group button_group = new Group();
        
        //create a textField to get the user input information
        final TextField answer = new TextField();
        answer.setLayoutX(10);
        answer.setLayoutY(110);
        answer.setPromptText("Please type the captcha in the picture");
        answer.setMaxWidth(280);
        //answer.clear();
        
        button_group.getChildren().add(answer);
        
        
      //exit button finish testing
        Button btn1 = new Button();
        btn1.setText("new captcha");
        //btn1.setMaxWidth(80);
        //btn1.setMaxHeight(20);
        btn1.setStyle("-fx-font-size: 12pt;");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //perform creating method
            }
        });

        button_group.getChildren().add(btn1);
        
        Button btn2 = new Button();
        btn2.setText("solve");
        btn2.setMaxWidth(80);
        btn2.setMaxHeight(20);
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	final String user_answer = answer.getText();
                

                System.out.println(user_answer);
            }
        });

        button_group.getChildren().add(btn2);
        
        Button btn3 = new Button();
        btn3.setText("quit");
        btn3.setMaxWidth(80);
        btn3.setMaxHeight(20);
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        
        button_group.getChildren().add(btn3);
        
        root.getChildren().add(button_group);
        
        
        //#################################################
        //add one image into scene to represent the graph?
        //#################################################
        
        */
        
        
        
        // finalise and display the whole scene graph
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** an auxiliary method to calculate the x-coordinate of
     * centre of a node
     * @param node which x-coordinate we calculate
     * @return the x-coordinate of node's centre
     * */
    private double getNodePosX(Node node) {
        Bounds bounds = node.getLayoutBounds();
        return 0.5 * (bounds.getMaxX() - bounds.getMinX());
//        return bounds.getMinX();
    }

    /** an auxiliary method to calculate the y-coordinate of
     * centre of a node
     * @param node which y-coordinate we calculate
     * @return the y-coordinate of node's centre
     * */
    private double getNodePosY(Node node) {
        Bounds bounds = node.getLayoutBounds();
        return 0.5 * (bounds.getMaxY() - bounds.getMinY());
//        return bounds.getMinY();
    }
}
