package screengrab;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ScreenGrab extends Application {
    
    private Stage stage;
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Platform.setImplicitExit(false);
        init(stage);
        stage.show();
    }

    public void hideStage() {
        Platform.runLater(() -> {
            stage.hide();
        });
    }
    
    public void showStage() {
        Platform.runLater(() -> {
            stage.show();
        });
    }
    
    private void init(Stage stage) {
        stage.setOnCloseRequest((e) -> {
            Platform.exit();
        });
        
        Button fullScreenshotBtn = new Button("Full Grab");
        fullScreenshotBtn.setWrapText(true);
        fullScreenshotBtn.setPrefSize(60, 60); 
        fullScreenshotBtn.setOnAction((e) -> {
            initiateFullScreenGrabSequence();
        });
        
        Button regionScreenshotBtn = new Button("Region Grab");
        regionScreenshotBtn.setWrapText(true);
        regionScreenshotBtn.setPrefSize(60, 60); 
        regionScreenshotBtn.setOnAction((e) -> {
            initiateRegionScreenGrabSequence();
        });
        
        Button closeBtn = new Button("Close");
        closeBtn.setWrapText(true);
        closeBtn.setPrefSize(60, 60); 
        closeBtn.setOnAction((e) -> {
            Platform.exit();
        });
        
        HBox root = new HBox(fullScreenshotBtn, regionScreenshotBtn, closeBtn);
        root.setSpacing(10.0);
        root.setPadding(new Insets(10.0));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Screen Grab");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
    
    private void initiateFullScreenGrabSequence() {
        hideStage();
        new Thread(() -> {
            Tool.sleep(500);
            Platform.runLater(() -> {
                FullBoard fullBoard = new FullBoard(this);
                fullBoard.showUp();
            });
        }).start();
    }
    
    private void initiateRegionScreenGrabSequence() {
        hideStage();
        new Thread(() -> {
            Tool.sleep(500);
            Platform.runLater(() -> {
                CropBoard cropBoard = new CropBoard(this);
                cropBoard.showUp();
            });
        }).start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
