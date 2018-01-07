package screengrab;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SaveCancelDialog {
    
    private Stage stage;
    private Scene scene;
    private Button saveBtn;
    private Button cancelBtn;
    private boolean response;
    
    public SaveCancelDialog() {
        saveBtn = new Button("Save");
        saveBtn.setOnAction((e) -> {
            response = true;
            stage.close();
        });
        cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction((e) -> {
            response = false;
            stage.close();
        });
        
        HBox root = new HBox(saveBtn, cancelBtn);
        root.setSpacing(10.0);
        root.setPadding(new Insets(10.0));
        scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
    }
    
    public static boolean showUp() {
        SaveCancelDialog saveCancelDialog = new SaveCancelDialog();
        saveCancelDialog.stage.showAndWait();
        return saveCancelDialog.response;
    }
}
