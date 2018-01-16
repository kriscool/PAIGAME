package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {
    @FXML
    Button playButton;
    @FXML
    TextField loginText;

    private Boolean isSelected=false;
    @FXML
    ImageView first;
    @FXML
    ImageView second;
    @FXML
    ImageView thirdth;
    @FXML
    ImageView fourth;
    @FXML
    ImageView fifth;
    @FXML
    ImageView sixth;
    @FXML
    ImageView seventh;

    public void initialize(){

    }

    public void onSelectFirst() {
        if (isSelected) {
            first.setLayoutX(200);
            first.setLayoutY(500);
            isSelected=false;
        }else{
            first.setLayoutX(300);
            first.setLayoutY(300);
            isSelected=true;
        }
    }
    public void onSelectSecond(){

        if (isSelected) {
            second.setLayoutX(250);
            second.setLayoutY(500);
            isSelected=false;
        }else{
            second.setLayoutX(300);
            second.setLayoutY(300);
            isSelected=true;
        }
    }

    public void onSelectThirdth(){

        if (isSelected) {
            thirdth.setLayoutX(300);
            thirdth.setLayoutY(500);
            isSelected=false;
        }else{
            second.setLayoutX(300);
            second.setLayoutY(300);
            isSelected=true;
        }
    }

    public void onSelectFourth(){

        if (isSelected) {
            fourth.setLayoutX(350);
            fourth.setLayoutY(500);
            isSelected=false;
        }else{
            fourth.setLayoutX(300);
            fourth.setLayoutY(300);
            isSelected=true;
        }
    }

    public void onSelectfive(){
        if (isSelected) {
            fifth.setLayoutX(400);
            fifth.setLayoutY(500);
            isSelected=false;
        }else{

            fifth.setLayoutX(300);
            fifth.setLayoutY(300);
            isSelected=true;
        }
    }

    public void onSelectSixth(){
        if (isSelected) {
            sixth.setLayoutX(450);
            sixth.setLayoutY(500);
            isSelected=false;
        }else{
            sixth.setLayoutX(300);
            sixth.setLayoutY(300);
            isSelected=true;
        }
    }

    public void onSelectSeven(){
        if (isSelected) {
            seventh.setLayoutX(500);
            seventh.setLayoutY(500);
            isSelected=false;
        }else{
            sixth.setLayoutX(300);
            sixth.setLayoutY(300);
            isSelected=true;
        }
    }
    @FXML
    private void test(ActionEvent event)
    {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("secondScene.fxml"));
        Parent root = null;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);

        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 800, 600));
        stage.showAndWait();


    }

}
