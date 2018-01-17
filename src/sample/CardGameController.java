package sample;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import network.Messeges;
import xml.ParseXml;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class CardGameController {
    private Boolean isSelected=false;
    private int whichCardIsSelected = 0;
    private Configuration configuration;
    private StageGame stageGame = null;
    private int[] cards=new int[3];
    private int idPlayer;
    private Gson gson;
    private ModelClientGame modelClientGame= new ModelClientGame();
    //private int[] cardsToChange=new int[3];
    private ArrayList<Integer> cardsToChange= new ArrayList<Integer>();
    int cardChooseToChang=0;
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

    @FXML
    ImageView firstOponent;
    @FXML
    ImageView secondOponent;
    @FXML
    TextField points;
    @FXML
    ImageView choose;
    @FXML
    Button btnSetPoints;
    @FXML
    Button accept;
    @FXML
    Button playButton;
    @FXML
    TextField loginText;
    @FXML
    Text text;
    @FXML
    Text auction;
    @FXML
    Text text3;
    @FXML
    Text score1;
    @FXML
    Text nameScore;
    @FXML
    Text nameScore1;
    @FXML
    Text nameScore2;
    @FXML
    Text score2;
    @FXML
    Text score3;
    @FXML
    Button pass;
    @FXML
    Button start;
    @FXML
    public void setAcceptCard() throws IOException {
        if(stageGame.getGameState()==3 && cardChooseToChang==3){
            setCards(modelClientGame.getCardToChange(),cardsToChange);
            stageGame.setGameState(4);
            output.writeObject(gson.toJson(stageGame));
        }
    }

    public void setCards(int[] number,ArrayList<Integer> numberToChange){
        Image im2;
        for(int i=0;i<3;i++) {
            switch (numberToChange.get(i)) {
                case 1:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    first.setImage(im2);
                    break;
                case 2:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    second.setImage(im2);
                    break;
                case 3:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    thirdth.setImage(im2);
                    break;
                case 4:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    fourth.setImage(im2);
                    break;
                case 5:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    fifth.setImage(im2);
                    break;
                case 6:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    sixth.setImage(im2);
                    break;
                case 7:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    seventh.setImage(im2);
                    break;
            }
        }
    }

    @FXML
    public void startGame() throws IOException {

        int temp[][]=stageGame.getCards();
        cards[stageGame.getIdplayer()]=temp[stageGame.getIdplayer()][whichCardIsSelected];
        stageGame.setCardsPuts(cards);
        stageGame.setIdplayer(stageGame.getIdplayer());
        output.writeObject(gson.toJson(stageGame));
        btnSetPoints.setVisible(true);
        points.setVisible(true);
        choose.setVisible(true);
        accept.setVisible(true);
        text3.setVisible(true);
        nameScore.setVisible(true);
        nameScore2.setVisible(true);
        nameScore1.setVisible(true);
        score1.setVisible(true);
        score2.setVisible(true);
        score3.setVisible(true);
        start.setVisible(false);
        pass.setVisible(true);
        auction.setVisible(true);
    }


    private int whichCard;
    public void setChoosenImage(int choice){
        switch(choice){
            case 1:
                Image im = new Image("Card/czerwo.png");
                choose.setImage(im);
                break;
            case 2:
                Image im2 = new Image("Card/dzwonek.png");
                choose.setImage(im2);
                break;
            case 3:
                Image im3 = new Image("Card/Wino.png");
                choose.setImage(im3);
                break;
            case 4:
                Image im4 = new Image("Card/żołądź.png");
                choose.setImage(im4);

                break;
        }
    }

    @FXML
    private void choseKozer(ActionEvent event)
    {
        setChoosenImage(whichCard);
    }

    @FXML
    private void chooseFirst(ActionEvent event)
    {
        whichCard=1;
    }

    @FXML
    private void chooseSecond(ActionEvent event)
    {
        whichCard=2;
    }

    @FXML
    private void chooseThird(ActionEvent event)
    {
        whichCard=3;
    }

    @FXML
    private void chooseFourth(ActionEvent event)
    {
        whichCard=4;
    }

    public void initialize(){
        stageGame=StageGame.getInstance();
        configuration=Configuration.getInstance();
        cardsToChange.add(0);
        cardsToChange.add(0);
        cardsToChange.add(0);


    }

    public void onSelectFirst() {
        Boolean isSelected=false;
        if(cardChooseToChang<3 && stageGame.getGameState()==3 && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {
            for(int i=0;i<3;i++){
                if((cardsToChange.get(i)==1)){
                    isSelected=true;
                }
            }
            if(!isSelected){
                first.setLayoutX(configuration.getFirstCardPostion());
                first.setLayoutY(configuration.getYposition()-30);
                cardsToChange.set(cardChooseToChang,1);
                cardChooseToChang++;
            }else{
                cardsToChange.set(cardChooseToChang,0);
                thirdth.setLayoutY(configuration.getYposition() + 30);
                cardChooseToChang--;
                isSelected=false;

            }

        }else {
            if (isSelected && whichCardIsSelected == 1 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
                first.setLayoutX(configuration.getFirstCardPostion());
                first.setLayoutY(configuration.getYposition());
                isSelected = false;
                whichCardIsSelected = 0;
            } else if (whichCardIsSelected == 0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
                first.setLayoutX(300);
                first.setLayoutY(300);
                isSelected = true;
                whichCardIsSelected = 1;
            }
        }
    }

    public void onSelectSecond(){

        if(cardChooseToChang<3 && stageGame.getGameState()==3  && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {
            Boolean isSelected=false;
            for(int i=0;i<3;i++){
                if((cardsToChange.get(i)==2)){
                    isSelected=true;
                }
            }
            if(!isSelected){
                second.setLayoutX(configuration.getSecondCardPostion());
                second.setLayoutY(configuration.getYposition()-30);
                cardsToChange.set(cardChooseToChang,2);
                cardChooseToChang++;
            }else{
                cardsToChange.set(cardChooseToChang,0);
                thirdth.setLayoutY(configuration.getYposition() + 30);
                cardChooseToChang--;
                isSelected=false;

            }
        }else {
        if (isSelected && whichCardIsSelected==2 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
            second.setLayoutX(configuration.getSecondCardPostion());
            second.setLayoutY(configuration.getYposition());
            isSelected=false;
            whichCardIsSelected=0;
        }else if( whichCardIsSelected==0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())){
            second.setLayoutX(300);
            second.setLayoutY(300);
            isSelected=true;
            whichCardIsSelected=2;
        }}
    }

    public void onSelectThirdth(){
        if(cardChooseToChang<3 && stageGame.getGameState()==3  && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {
            Boolean isSelected=false;
            for(int i=0;i<3;i++){
                if((cardsToChange.get(i)==3)){
                    isSelected=true;
                }
            }
            if(!isSelected) {
                thirdth.setLayoutX(configuration.getThirdCardPostion());
                thirdth.setLayoutY(configuration.getYposition() - 30);
                cardsToChange.set(cardChooseToChang,3);
                cardChooseToChang++;
            }else{
                cardsToChange.set(cardChooseToChang,0);
                thirdth.setLayoutY(configuration.getYposition() + 30);
                cardChooseToChang--;
                isSelected=false;

            }
        }else {
            if (isSelected && whichCardIsSelected == 3 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
                thirdth.setLayoutX(configuration.getThirdCardPostion());
                thirdth.setLayoutY(configuration.getYposition());
                isSelected = false;
                whichCardIsSelected = 0;
            } else if (whichCardIsSelected == 0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
                thirdth.setLayoutX(300);
                thirdth.setLayoutY(300);
                isSelected = true;
                whichCardIsSelected = 3;
            }
        }
    }

    public void onSelectFourth(){
        if(cardChooseToChang<3 && stageGame.getGameState()==3  &&  (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {
            Boolean isSelected=false;
            for(int i=0;i<3;i++){
                if((cardsToChange.get(i)==4)){
                    isSelected=true;
                }
            }
            if(!isSelected) {
                fourth.setLayoutX(configuration.getFourthCardPostion());
                fourth.setLayoutY(configuration.getYposition() - 30);
                cardsToChange.set(cardChooseToChang,4);
                cardChooseToChang++;
            }else{
                cardsToChange.set(cardChooseToChang,0);
                thirdth.setLayoutY(configuration.getYposition() + 30);
                cardChooseToChang--;
                isSelected=false;

            }
        }else {
            if (isSelected && whichCardIsSelected == 4 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
                fourth.setLayoutX(configuration.getFourthCardPostion());
                fourth.setLayoutY(configuration.getYposition());
                isSelected = false;
                whichCardIsSelected = 0;
            } else if (whichCardIsSelected == 0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
                fourth.setLayoutX(300);
                fourth.setLayoutY(300);
                isSelected = true;
                whichCardIsSelected = 4;
            }
        }
    }

    public void onSelectfive(){
        if(cardChooseToChang<3 && stageGame.getGameState()==3  &&  (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {
            Boolean isSelected=false;
            for(int i=0;i<3;i++){
                if((cardsToChange.get(i)==5)){
                    isSelected=true;
                }
            }
            if(!isSelected) {
                fifth.setLayoutX(configuration.getFifthCardPostion());
                fifth.setLayoutY(configuration.getYposition() - 30);
                cardsToChange.set(cardChooseToChang,5);
                cardChooseToChang++;
            }else{
                cardsToChange.set(cardChooseToChang,0);
                thirdth.setLayoutY(configuration.getYposition() + 30);
                cardChooseToChang--;
                isSelected=false;

            }
        }else {
        if (isSelected && whichCardIsSelected==5 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
            fifth.setLayoutX(configuration.getFifthCardPostion());
            fifth.setLayoutY(configuration.getYposition());
            isSelected=false;
            whichCardIsSelected=0;
        }else if( whichCardIsSelected==0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {

            fifth.setLayoutX(300);
            fifth.setLayoutY(300);
            isSelected = true;
            whichCardIsSelected = 5;
        }
        }
    }

    public void onSelectSixth(){
        if(cardChooseToChang<3 && stageGame.getGameState()==3  && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {
            Boolean isSelected=false;
            for(int i=0;i<3;i++){
                if((cardsToChange.get(i)==6)){
                    isSelected=true;
                }
            }
            if(!isSelected) {
                sixth.setLayoutX(configuration.getSixthCardPostion());
                sixth.setLayoutY(configuration.getYposition() - 30);

                cardsToChange.set(cardChooseToChang,6);
                cardChooseToChang++;
            }else{
                cardsToChange.set(cardChooseToChang,0);
                thirdth.setLayoutY(configuration.getYposition() + 30);
                cardChooseToChang--;
                isSelected=false;

            }
        }else {
        if (isSelected && whichCardIsSelected==6 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
            sixth.setLayoutX(configuration.getSixthCardPostion());
            sixth.setLayoutY(configuration.getYposition());
            isSelected=false;
            whichCardIsSelected=0;
        }else if( whichCardIsSelected==0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
            sixth.setLayoutX(300);
            sixth.setLayoutY(300);
            isSelected = true;
            whichCardIsSelected = 6;
        }
        }
    }

    public void onSelectSeven(){
        if(cardChooseToChang<3 && stageGame.getGameState()==3  && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {
            Boolean isSelected=false;
            for(int i=0;i<3;i++){
                if((cardsToChange.get(i)==7)){
                    isSelected=true;
                }
            }
            if(!isSelected) {
                seventh.setLayoutX(configuration.getSeventhCardPosition());
                seventh.setLayoutY(configuration.getYposition() - 30);
                cardsToChange.set(cardChooseToChang,7);
                cardChooseToChang++;
            }else{
                cardsToChange.set(cardChooseToChang,0);
                thirdth.setLayoutY(configuration.getYposition() + 30);
                cardChooseToChang--;
                isSelected=false;

            }
        }else {
        if (isSelected && whichCardIsSelected==7 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
            seventh.setLayoutX(configuration.getSeventhCardPosition());
            seventh.setLayoutY(configuration.getYposition());
            isSelected=false;
            whichCardIsSelected=0;
        }else if( whichCardIsSelected==0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())) {
            seventh.setLayoutX(300);
            seventh.setLayoutY(300);
            isSelected = true;
            whichCardIsSelected = 7;
        }
        }
    }

    Socket clientSocket = null;

    ObjectOutputStream output = null;
    ObjectInputStream input = null;


    @FXML
    private void bump(ActionEvent event) throws IOException {
        System.out.println(Integer.toString(stageGame.getGameState()));
        if(stageGame.getGameState()==2){
            if(stageGame.getPlayerTurn()==stageGame.getIdplayer()){
                if(points.getText()!=null){
                    List<Auction> tempList = stageGame.getAuctions();
                    tempList.set(stageGame.getIdplayer(), new Auction(Integer.parseInt(points.getText()), false));
                    stageGame.setAuctions(tempList);
                    stageGame.setEndOfTurn(false);
                    output.writeObject(gson.toJson(stageGame));
                }
            }
        }
    }

    @FXML
    private void pass(ActionEvent event) throws IOException {
        if(stageGame.getGameState()==2) {
            if (stageGame.getPlayerTurn() == stageGame.getIdplayer()) {
                List<Auction> tempList = stageGame.getAuctions();
                tempList.set(stageGame.getIdplayer(), new Auction(0, true));

                stageGame.setAuctions(tempList);
                output.writeObject(gson.toJson(stageGame));
            }
        }
    }

    @FXML
    private void confirm(ActionEvent event) throws IOException {

    }



    @FXML
    private void test(ActionEvent event) throws IOException {
        start.setVisible(true);
        text.setText("Poczekaj na przeciwnikow");
        playButton.setVisible(false);
        loginText.setVisible(false);
        ParseXml p = new ParseXml();

        Configuration configuration = Configuration.getInstance();

        try {
            clientSocket = new Socket(configuration.getNameHost(), configuration.getPort());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
           input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
        gson = new Gson();


        ClientOfGame client = new ClientOfGame();
        client.setName(loginText.getText());
        try {
            output.writeObject(gson.toJson(client));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView[] imageViews = new ImageView[7];

        imageViews[0]=first;
        imageViews[1]=second;
        imageViews[2]=thirdth;
        imageViews[3]=fourth;
        imageViews[4]=fifth;
        imageViews[5]=sixth;
        imageViews[6]=seventh;

        Messeges inputClass = new Messeges(stageGame.getIdplayer(),input,text,firstOponent,secondOponent,score1,score2,score3,stageGame,imageViews,idPlayer,auction,modelClientGame);
        Thread t1 = new Thread(inputClass);
        t1.start();

    }

}
