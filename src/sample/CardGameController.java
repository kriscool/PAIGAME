package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardGameController {
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
        Image im = new Image("Card/1.png");
        first.setImage(im);
        Image im1 = new Image("Card/2.png");
        second.setImage(im1);
        Image im2 = new Image("Card/3.png");
        thirdth.setImage(im2);
        Image im3 = new Image("Card/4.png");
        fourth.setImage(im3);
        Image im4 = new Image("Card/5.png");
        fifth.setImage(im4);
        Image im5 = new Image("Card/6.png");
        sixth.setImage(im5);
        Image im6 = new Image("Card/7.png");
        seventh.setImage(im6);

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


}
