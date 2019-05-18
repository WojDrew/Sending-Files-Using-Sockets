package pkg171838lab_2;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button button;
    private ExecutorService executor;
    private FileChooser fileChooser;
    Stage primaryStage;

    public Controller(){
        executor = Executors.newSingleThreadExecutor();
    }
    @FXML
    public void initialize() {
    }

    public void chooseFile(){
        fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        Task<Void> sendFileTask = new SendFileTask(file); //klasa zadania

        executor.submit(sendFileTask); //uruchomienie zadania w tle

        statusLabel.textProperty().bind(sendFileTask.messageProperty());
        progressBar.progressProperty().bind(sendFileTask.progressProperty());
    }

    public void setPS(Stage pS){
        this.primaryStage = pS;
    }

}
