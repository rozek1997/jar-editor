import com.sun.javafx.robot.FXRobot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.xml.transform.Source;


public class FxStarter extends Application {


    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main_scene.fxml"));
        Scene scene = new Scene(root, 1920, 1080);
        stage.setTitle("Jar Explorer");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }






}
