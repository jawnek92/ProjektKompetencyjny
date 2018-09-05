package App;

import App.WebSerching.OlxElements;
import App.data.OlxData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        OlxElements olxElements = new OlxElements("https://www.olx.pl/motoryzacja/samochody/q-Honda-crx/");
//        System.out.println(olxElements.getTitlesFromOlx().get(0));


        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Szperacz xD");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
