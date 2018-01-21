package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ModelClientGame;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("secondScene.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        CardGameController controller = fxmlLoader.getController();
                        try {
                            controller.End();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Application Closed by click to Close Button(X)");
                        System.exit(0);

                    }
                });
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
