package screengrab;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CropBoard {
    private ScreenGrab parentCaller;
    private final double width;
    private final double height;
    private Stage stage;
    private Canvas canvas;
    private GraphicsContext gc;
    private double startX = -1, startY = -1, endX = -1, endY = -1;
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
        pane.setCursor(Cursor.CROSSHAIR);
        Scene scene = new Scene(pane, width, height);
        stage.setX(0);
        stage.setY(0);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (e) -> {
        if (e.getCode() == KeyCode.ESCAPE) {
            stage.close();
            parentCaller.showStage();
        }
    });
    }
    
    private void initCanvas(Canvas canvas) {
        prepareCleanCanvas();
        canvas.setOnMousePressed((e) -> {
            startX = e.getX();
            startY = e.getY();
            gc.setStroke(Color.rgb(0, 120, 215));
            gc.setFill(Color.rgb(0, 120, 215, 0.15));
            gc.setLineWidth(1);
        });
        canvas.setOnMouseDragged((e) -> {
            endX = e.getX();
            endY = e.getY();
            gc.drawImage(imgFX, 0, 0);
            Rectangle r = getValidRegion(startX, startY, endX, endY);
            gc.fillRect(r.x, r.y, r.width, r.height);
            gc.strokeRect(r.x, r.y, r.width, r.height);
        });
        canvas.setOnMouseReleased((e) -> {
            if(SaveCancelDialog.showUp()) {
                File saveFile = Tool.showSaveDialog(stage);
                if (saveFile != null) {
                    Rectangle r = getValidRegion(startX, startY, endX, endY);
                    BufferedImage subImg = img.getSubimage(r.x, r.y, r.width, r.height);
                    Tool.saveImageToFile(subImg, saveFile);
                }
                stage.close();
                parentCaller.showStage();
            } else {
                prepareCleanCanvas();
            }
        });
    }
    
    private void prepareCleanCanvas() {
        gc.drawImage(imgFX, 0, 0);
        int boxW = 425, boxH = 150;
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRoundRect((width-boxW)/2, (height-boxH)/2, boxW, boxH, 5, 5);
        gc.setFont(new Font(24));
        gc.setFill(Color.rgb(255, 255, 255));
        gc.fillText("Drag your mouse to select a region", 505, 370);
        gc.fillText("Press ESC to cancel cropping", 525, 405);
    }
    
    private static Rectangle getValidRegion(double a, double b, double x, double y) {
        return new Rectangle(
                (int)Math.min(a, x),
                (int)Math.min(b, y),
                (int)Math.abs(a-x),
                (int)Math.abs(b-y)
        );
    }
}
