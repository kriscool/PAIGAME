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
    Button setBid;
    @FXML
    public void setAcceptCard() throws IOException {
        int count=0;
       if(first.getImage()==null && second.getImage()==null && thirdth.getImage()==null && fourth.getImage()==null && fifth.getImage()==null && sixth.getImage()==null && seventh.getImage()==null){
           System.out.println("Wszystkie karty wyjęte");
           stageGame.setEndOfTurnToCalculatePoints(1);
           stageGame.setGameState(1);
           stageGame.setBid(0);
           cardChooseToChang=0;
           List<Auction> a = stageGame.getAuctions();
           a.get(0).setOVer(false);
           a.get(1).setOVer(false);
           a.get(2).setOVer(false);
           a.get(0).setPrice(0);
           a.get(1).setPrice(0);
           a.get(2).setPrice(0);
           stageGame.setAuctions(a);
           output.writeObject(gson.toJson(stageGame));
           System.out.println("kilk");
           first.setLayoutY(configuration.getYposition());
           first.setLayoutX(configuration.getFirstCardPostion());
           second.setLayoutX(configuration.getSecondCardPostion());
           second.setLayoutY(configuration.getYposition());
           thirdth.setLayoutX(configuration.getThirdCardPostion());
           thirdth.setLayoutY(configuration.getYposition());
           fourth.setLayoutX(configuration.getFourthCardPostion());
           fourth.setLayoutY(configuration.getYposition());
           fifth.setLayoutX(configuration.getFifthCardPostion());
           fifth.setLayoutY(configuration.getYposition());
           sixth.setLayoutX(configuration.getSixthCardPostion());
           sixth.setLayoutY(configuration.getYposition());
           seventh.setLayoutX(configuration.getSeventhCardPosition());
           seventh.setLayoutY(configuration.getYposition());
           cardsToChange.clear();

       }else
        if(stageGame.getEndOfTheGame()){
            text.setText("Gracz się wylogowal prosze wyjsc z gry");
            System.out.println("Zakonczona gra");
        }else
        if(!stageGame.getEndOfTurn()) {
            if (stageGame.getGameState() == 3 && cardChooseToChang == 3) {
                setCards(modelClientGame.getCardToChange(), cardsToChange);
                stageGame.setGameState(4);
                setBid.setVisible(true);
                pass.setVisible(false);
                btnSetPoints.setVisible(false);
                System.out.print("Wymiana kart?");
            } else if (stageGame.getGameState() == 4 && (stageGame.getPlayerTurn() == modelClientGame.getIdPlayer() && whichCardIsSelected != 0)
                    ) {
                System.out.print("Wymiana kart?");
                int card = whichCardIsSelected;
                int returnA=isPairToKozer(modelClientGame.getCard(--card),card);

                if(stageGame.isThreeCardNull()) {
                    if (returnA != 0) {
                        stageGame.setColorChoice(returnA);
                        System.out.println("Kozer" + Integer.toString(returnA));
                    }
                }
                card = whichCardIsSelected;
                stageGame.setCardPutPlayer(modelClientGame.getIdPlayer(), modelClientGame.getCard(--card));
                stageGame.setPlayerTurn(modelClientGame.getIdPlayer());
                output.writeObject(gson.toJson(stageGame));
                isSelected=false;
                whichCardIsSelected=0;

            }
        }else if(stageGame.isThreeCardNull()){

            System.out.print("Wymiana kart?");
            int card = whichCardIsSelected;
            int returnA=isPairToKozer(modelClientGame.getCard(--card),card);

            if(stageGame.isThreeCardNull()) {
                if (returnA != 0) {
                    stageGame.setColorChoice(returnA);
                    System.out.println("Kozer" + Integer.toString(returnA));
                }
            }
            card = whichCardIsSelected;
            stageGame.setCardPutPlayer(modelClientGame.getIdPlayer(), modelClientGame.getCard(--card));
            stageGame.setPlayerTurn(modelClientGame.getIdPlayer());
            stageGame.setEndOfTurn(false);
            output.writeObject(gson.toJson(stageGame));

            isSelected=false;
            whichCardIsSelected=0;
        }

    }

    public int isPairToKozer(int idCard,int card){
        int choose=0;
        if(idCard==4 || idCard==5){
            for(int i=0;i<7;i++){

               // System.out.println(Integer.toString(i));
                if(card!=i){
                    if(modelClientGame.getCard(i)==4 || modelClientGame.getCard(i)==5){
                   //     System.out.println("Pierwsza para");
                        choose= 1;
                    }
                }
            }
        }else  if(idCard==10 || idCard==11){
            for(int i=0;i<7;i++){
             //   System.out.println(Integer.toString(i));
                if(card!=i){
                    if(modelClientGame.getCard(i)==10 || modelClientGame.getCard(i)==11){
                //        System.out.println("Druga para");
                        choose=2;
                    }
                }
            }
        }else if(idCard==16 || idCard==17){
            for(int i=0;i<7;i++){

               // System.out.println(Integer.toString(i));
                if(card!=i){
                    if(modelClientGame.getCard(i)==16 || modelClientGame.getCard(i)==17){
                //        System.out.println("Trzecia para");
                        choose=3;
                    }
                }
            }
        }else if(idCard==22 || idCard==23){
            for(int i=0;i<7;i++){

              //  System.out.println(Integer.toString(i));
                if(card!=i){
                    if(modelClientGame.getCard(i)==22 || modelClientGame.getCard(i)==23){
              //          System.out.println("Czwarta para");
                        choose=4;
                    }
                }
            }
        }
        return choose;
    }
    @FXML
    public void setBid() throws IOException {
        if(!points.getText().isEmpty()) {
            try {
                stageGame.setBid(Integer.parseInt(points.getText()));
            }catch (NumberFormatException e){
                auction.setText("Podaj liczby");
            }
            output.writeObject(gson.toJson(stageGame));

        }else {
            text.setText("Podaj o ile grasz");
        }

    }


    public void setCards(int[] number,ArrayList<Integer> numberToChange){
        Image im2;
        for(int i=0;i<3;i++) {
            switch (numberToChange.get(i)) {
                case 1:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    first.setImage(im2);
                    first.setLayoutY(configuration.getYposition());
                    break;
                case 2:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    second.setImage(im2);
                    second.setLayoutY(configuration.getYposition());
                    break;
                case 3:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    thirdth.setImage(im2);
                    thirdth.setLayoutY(configuration.getYposition());
                    break;
                case 4:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    fourth.setImage(im2);
                    fourth.setLayoutY(configuration.getYposition());
                    break;
                case 5:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    fifth.setImage(im2);
                    fifth.setLayoutY(configuration.getYposition());
                    break;
                case 6:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    sixth.setImage(im2);
                    sixth.setLayoutY(configuration.getYposition());
                    break;
                case 7:
                    im2 = new Image("Card/"+Integer.toString(number[i])+".png");
                    seventh.setImage(im2);
                    seventh.setLayoutY(configuration.getYposition());
                    break;
            }
        }
        modelClientGame.changeCardBeforeBid(number,numberToChange);
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

    }

    public void onSelectFirst() {
        Boolean isSelected=false;
        if(cardChooseToChang<3 && stageGame.getGameState()==3 && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {

                first.setLayoutY(configuration.getYposition()-30);
                cardChooseToChang++;
                cardsToChange.add(1);


        }else {
            if (isSelected && whichCardIsSelected == 1 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) ) {
                first.setLayoutX(configuration.getFirstCardPostion());
                first.setLayoutY(configuration.getYposition());
                isSelected = false;
                whichCardIsSelected = 0;
            } else if (stageGame.getGameState()==4 && whichCardIsSelected == 0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer())&& (modelClientGame.getCardCanBePut(0)==true || stageGame.getFirstCardPut()==0)) {
                first.setLayoutX(300);
                first.setLayoutY(300);
                isSelected = true;
                whichCardIsSelected = 1;
            }
        }
    }

    public void onSelectSecond(){

        if(cardChooseToChang<3 && stageGame.getGameState()==3  && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer()) ) {

                second.setLayoutY(configuration.getYposition()-30);
                cardChooseToChang++;
                cardsToChange.add(2);

        }else {
        if (isSelected && whichCardIsSelected==2 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(1)==true)) {
            second.setLayoutX(configuration.getSecondCardPostion());
            second.setLayoutY(configuration.getYposition());
            isSelected=false;
            whichCardIsSelected=0;
        }else if(stageGame.getGameState()==4 &&  whichCardIsSelected==0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(1)==true || stageGame.getFirstCardPut()==0)){
            second.setLayoutX(300);
            second.setLayoutY(300);
            isSelected=true;
            whichCardIsSelected=2;
        }}
    }

    public void onSelectThirdth(){
        if(cardChooseToChang<3 && stageGame.getGameState()==3  && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {

                thirdth.setLayoutY(configuration.getYposition() - 30);
                cardChooseToChang++;
                cardsToChange.add(3);

        }else {
            if (isSelected && whichCardIsSelected == 3 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(2)==true)) {
                thirdth.setLayoutX(configuration.getThirdCardPostion());
                thirdth.setLayoutY(configuration.getYposition());
                isSelected = false;
                whichCardIsSelected = 0;
            } else if (stageGame.getGameState()==4 && whichCardIsSelected == 0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(2)==true || stageGame.getFirstCardPut()==0)) {
                thirdth.setLayoutX(300);
                thirdth.setLayoutY(300);
                isSelected = true;
                whichCardIsSelected = 3;
            }
        }
    }

    public void onSelectFourth(){
        System.out.print(isSelected);
        System.out.println(whichCardIsSelected);
        System.out.println(stageGame.getPlayerTurn());
        for(int i=0;i<7;i++){
            System.out.println(modelClientGame.getCardCanBePut(i));
        }
        if(cardChooseToChang<3 && stageGame.getGameState()==3  &&  (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {

                fourth.setLayoutY(configuration.getYposition() - 30);
                cardChooseToChang++;
                cardsToChange.add(4);

        }else {
            System.out.print(isSelected);
            System.out.println(whichCardIsSelected);
            System.out.println(stageGame.getPlayerTurn());
            for(int i=0;i<7;i++){
                System.out.println(modelClientGame.getCardCanBePut(i));
            }
            if (isSelected && whichCardIsSelected == 4 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(3)==true)) {
                fourth.setLayoutX(configuration.getFourthCardPostion());
                fourth.setLayoutY(configuration.getYposition());
                isSelected = false;
                whichCardIsSelected = 0;
            } else if (stageGame.getGameState()==4 && whichCardIsSelected == 0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(3)==true || stageGame.getFirstCardPut()==0)) {
                fourth.setLayoutX(300);
                fourth.setLayoutY(300);
                isSelected = true;
                whichCardIsSelected = 4;
            }
        }
    }

    public void onSelectfive(){
        System.out.print(isSelected);
        System.out.println(whichCardIsSelected);
        System.out.println(stageGame.getPlayerTurn());
        for(int i=0;i<7;i++){
            System.out.println(modelClientGame.getCardCanBePut(i));
        }
        if(cardChooseToChang<3 && stageGame.getGameState()==3  &&  (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {

                fifth.setLayoutY(configuration.getYposition() - 30);
                cardChooseToChang++;
                cardsToChange.add(5);

        }else {
        if (isSelected && whichCardIsSelected==5 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(4)==true)) {
            fifth.setLayoutX(configuration.getFifthCardPostion());
            fifth.setLayoutY(configuration.getYposition());
            isSelected=false;
            whichCardIsSelected=0;
        }else if(stageGame.getGameState()==4 &&  whichCardIsSelected==0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(4)==true || stageGame.getFirstCardPut()==0)) {

            fifth.setLayoutX(300);
            fifth.setLayoutY(300);
            isSelected = true;
            whichCardIsSelected = 5;
        }
        }
    }

    public void onSelectSixth(){
        System.out.print(isSelected);
        System.out.println(whichCardIsSelected);
        System.out.println(stageGame.getPlayerTurn());
        for(int i=0;i<7;i++){
            System.out.println(modelClientGame.getCardCanBePut(i));
        }
        if(cardChooseToChang<3 && stageGame.getGameState()==3  && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {

                sixth.setLayoutY(configuration.getYposition() - 30);
                cardChooseToChang++;
                cardsToChange.add(6);

        }else {
        if (isSelected && whichCardIsSelected==6 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(5)==true)) {
            sixth.setLayoutX(configuration.getSixthCardPostion());
            sixth.setLayoutY(configuration.getYposition());
            isSelected=false;
            whichCardIsSelected=0;
        }else if(stageGame.getGameState()==4 &&  whichCardIsSelected==0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(5)==true || stageGame.getFirstCardPut()==0)) {
            sixth.setLayoutX(300);
            sixth.setLayoutY(300);
            isSelected = true;
            whichCardIsSelected = 6;
        }
        }
    }

    public void onSelectSeven(){
        System.out.print(isSelected);
        System.out.println(whichCardIsSelected);
        System.out.println(stageGame.getPlayerTurn());
        for(int i=0;i<7;i++){
            System.out.println(modelClientGame.getCardCanBePut(i));
        }
        if(cardChooseToChang<3 && stageGame.getGameState()==3  && (stageGame.getWhichPlayerWinAuction()==modelClientGame.getIdPlayer())) {

                seventh.setLayoutY(configuration.getYposition() - 30);
                cardChooseToChang++;
                cardsToChange.add(7);

        }else {
        if (isSelected && whichCardIsSelected==7 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(6)==true)) {

            seventh.setLayoutX(configuration.getSeventhCardPosition());
            seventh.setLayoutY(configuration.getYposition());
            isSelected=false;
            whichCardIsSelected=0;
        }else if( stageGame.getGameState()==4 && whichCardIsSelected==0 && (stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()) && (modelClientGame.getCardCanBePut(6)==true || stageGame.getFirstCardPut()==0)) {
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
    private void bump(ActionEvent event)  {

        if(stageGame.getGameState()==2){
            if(stageGame.getPlayerTurn()==stageGame.getIdplayer()){
               try{
               if(points.getText()!=null && (Integer.parseInt(points.getText())>Integer.parseInt(auction.getText())) && (Integer.parseInt(points.getText())>100 && Integer.parseInt(points.getText())<300)){
                    List<Auction> tempList = stageGame.getAuctions();
                    tempList.set(stageGame.getIdplayer(), new Auction(Integer.parseInt(points.getText()), false));
                    stageGame.setAuctions(tempList);
                    stageGame.setEndOfTurn(false);
                    try {
                        output.writeObject(gson.toJson(stageGame));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
               }
               catch(NumberFormatException e)
               {text.setText("Podaj liczby nie litery");}
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
        if (!loginText.getText().isEmpty()) {
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
            modelClientGame.setSockets(clientSocket,output,input);

            ClientOfGame client = new ClientOfGame();
            client.setName(loginText.getText());
            try {
                output.writeObject(gson.toJson(client));
            }catch (NullPointerException e){
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView[] imageViews = new ImageView[7];

            imageViews[0] = first;
            imageViews[1] = second;
            imageViews[2] = thirdth;
            imageViews[3] = fourth;
            imageViews[4] = fifth;
            imageViews[5] = sixth;
            imageViews[6] = seventh;

            Messeges inputClass = new Messeges(pass,btnSetPoints,setBid,isSelected,whichCardIsSelected,choose,stageGame.getIdplayer(), input, text, firstOponent, secondOponent, score1, score2, score3, stageGame, imageViews, idPlayer, auction, modelClientGame);
            Thread t1 = new Thread(inputClass);
            t1.start();

        }else {
            text.setText("Nie podałeś loginu");
        }
    }
    private Boolean[] cardCandPuts={false,false,false,false,false,false,false};

    public void whichCardCanPut(int firstCard){
        if(firstCard>0 && firstCard<7){
            for(int i=0;i<7;i++){
                if(modelClientGame.getCard(i)>0 && modelClientGame.getCard(i)<7){
                    cardCandPuts[i]=true;
                }
            }
        }else  if(firstCard>6 && firstCard<13){
            for(int i=0;i<7;i++){
                if(modelClientGame.getCard(i)>6 && modelClientGame.getCard(i)<13){
                    cardCandPuts[i]=true;
                }
            }
        }else  if(firstCard>12 && firstCard<19){
            for(int i=0;i<7;i++){
                if(modelClientGame.getCard(i)>12 && modelClientGame.getCard(i)<19){
                    cardCandPuts[i]=true;
                }
            }

        }else  if(firstCard>18 && firstCard<24){
            for(int i=0;i<7;i++){
                if(modelClientGame.getCard(i)>18 && modelClientGame.getCard(i)<24){
                    cardCandPuts[i]=true;
                }
            }
        }else if(stageGame.getColorChoice()!=0){
            if(stageGame.getColorChoice()==1){
                for(int i=0;i<7;i++){
                    if(modelClientGame.getCard(i)>0 && modelClientGame.getCard(i)<7){
                        cardCandPuts[i]=true;
                    }
                }
            }else  if(stageGame.getColorChoice()==2){
                for(int i=0;i<7;i++){
                    if(modelClientGame.getCard(i)>6 && modelClientGame.getCard(i)<13){
                        cardCandPuts[i]=true;
                    }
                }
            }else  if(stageGame.getColorChoice()==3){
                for(int i=0;i<7;i++){
                    if(modelClientGame.getCard(i)>12 && modelClientGame.getCard(i)<19){
                        cardCandPuts[i]=true;
                    }
                }
            }else  if(stageGame.getColorChoice()==4){
                for(int i=0;i<7;i++){
                    if(modelClientGame.getCard(i)>18 && modelClientGame.getCard(i)<24){
                        cardCandPuts[i]=true;
                    }
                }
            }
        }else{
            for(int i=0;i<7;i++){
                    cardCandPuts[i]=true;
            }
        }
    }


    public void End() throws IOException {
        stageGame.setEndOfTheGame(true);
        output.writeObject(gson.toJson(stageGame));
        modelClientGame.closeScoket();
    }
}
