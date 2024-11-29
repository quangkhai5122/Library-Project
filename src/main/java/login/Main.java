package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginManager.fxml"));

        Image icon = new Image("/bookicon.png");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/design/style.css").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("L.S.M");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}