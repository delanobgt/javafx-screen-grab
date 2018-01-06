package screengrab;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Tool {
    
    public static int getRandomIntegerInRange(int a, int b) {
        return ( (int)(Math.random()*(b-a+1)) ) + a;
    }
    
    public static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static File showSaveDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp")
        );
        File file = fileChooser.showSaveDialog(stage);
        return file;
    }
    
    public static Image getFullScreenshotFX() {
        Image img = null;
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            img = SwingFXUtils.toFXImage(screenFullImage, null);            
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return img;
    }
    
    public static BufferedImage getFullScreenshot() {
        BufferedImage img = null;
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            img = robot.createScreenCapture(screenRect);
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return img;
    }
    
    public static void saveImageToFile(BufferedImage img, File saveFile) {
        try {
            ImageIO.write(img, "bmp", saveFile);
        } catch (IOException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
