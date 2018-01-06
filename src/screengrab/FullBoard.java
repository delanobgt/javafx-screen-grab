package screengrab;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class FullBoard {
    private ScreenGrab parentCaller;
    private final double width;
    private final double height;
    private Canvas canvas;
    private GraphicsContext gc;
    private Stage stage;
    private BufferedImage img;
    private Image imgFX;
    
    public FullBoard(ScreenGrab parentCaller) {
        this.parentCaller = parentCaller;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        stage = new Stage();
        img = Tool.getFullScreenshot();
        imgFX = SwingFXUtils.toFXImage(img, null);
        init(stage);
    }

    public void showUp(){
        stage.show();
        File saveFile = Tool.showSaveDialog(stage);
        if (saveFile != null) {
            Tool.saveImageToFile(img, saveFile);
        }
        parentCaller.showStage();
        stage.close();
    }
    
    private void init(Stage stage) {
        gc.drawImage(imgFX, 0, 0);
        StackPane pane = new StackPane(canvas);
        Scene scene = new Scene(pane, width, height);
        stage.setX(0);
        stage.setY(0);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.sizeToScene();
    }
}
