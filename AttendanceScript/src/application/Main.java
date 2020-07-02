package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }	

    
    @Override
    public void start(Stage primaryStage) throws Exception{
    	Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Attendance Script");
        Scene scene = new Scene(root, 825,535);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




}
