package screengrab;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CropBoard {
    private ScreenGrab parentCaller;
    private final double width;
    private final double height;
    private Stage stage;
    private Canvas canvas;
    private GraphicsContext gc;
    private double startX = -1;
    private double startY = -1;
    private BufferedImage img;
    private Image imgFX;
    
    public CropBoard(ScreenGrab parentCaller) {
        this.parentCaller = parentCaller;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        stage = new Stage();
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        img = Tool.getFullScreenshot();
        imgFX = SwingFXUtils.toFXImage(img, null);
        init(stage);
    }

    public void showUp(){
        stage.show();
    }
    
    private void init(Stage stage) {
        initCanvas(canvas);
        StackPane pane = new StackPane(canvas);
        Scene scene = new Scene(pane, width, height);
        stage.setX(0);
        stage.setY(0);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.sizeToScene();
    }
    
    private void initCanvas(Canvas canvas) {
        gc.drawImage(imgFX, 0, 0);
        canvas.setOnMousePressed((e) -> {
            startX = e.getX();
            startY = e.getY();
            gc.setStroke(Color.rgb(0, 120, 215));
            gc.setFill(Color.rgb(0, 120, 215, 0.15));
            gc.setLineWidth(1);
        });
        canvas.setOnMouseDragged((e) -> {
            gc.clearRect(0, 0, width, height);
            gc.drawImage(imgFX, 0, 0);
            gc.fillRect(startX, startY, e.getX()-startX, e.getY()-startY);
            gc.strokeRect(startX, startY, e.getX()-startX, e.getY()-startY);
        });
        canvas.setOnMouseReleased((e) -> {
            startX = -1;
            startY = -1;
            gc.clearRect(0, 0, width, height);
            gc.drawImage(imgFX, 0, 0);
        });
    }
}
