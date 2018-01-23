package network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Auction;
import model.ClientOfGame;
import model.ModelClientGame;
import model.StageGame;

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

public class Messeges implements Runnable {
  	ObjectInputStream mes;
	public boolean flag;
	private Text text;
	private Text score;
    private Text score1;
    private Text score2;
	private StageGame stageGame;
    private ImageView imageViewPlayerOne;
    private ImageView imageViewPlayerTwo,colorChoice;
    private ImageView[] imageViews;
    private int player;
    private Text auction;
    private int[] tabCardToChange=new int [3];
    private ModelClientGame modelClientGame;
    private int whichCardIsSelected;
    private Boolean isSelected;
    private Button start,pass,gram;

    public Messeges(Button pass,Button start,Button gram,Boolean isSelected,int whichCard,ImageView colorChoice,int idlayer, ObjectInputStream in, Text javafxControol, ImageView imageViewPlayerOne, ImageView imageViewPlayerTwo, Text score, Text score1, Text score2, StageGame stageGame, ImageView[] imageViews, int idPlayer, Text auction, ModelClientGame modelClientGame){
		this.pass=pass;
		this.gram=gram;
		this.start=start;
        this.isSelected=isSelected;
        this.mes = in;
		this.whichCardIsSelected=whichCard;
		this.stageGame=stageGame;
		flag = true;
		text=javafxControol;
        this.imageViewPlayerOne=imageViewPlayerOne;
        this.imageViewPlayerTwo=imageViewPlayerTwo;
        this.score=score;
        this.score1=score1;
        this.score2=score2;
        this.imageViews=imageViews;
        this.player=idlayer;
        this.player=idPlayer;
        this.auction=auction;
        this.modelClientGame=modelClientGame;
        this.colorChoice=colorChoice;
	}
	
	public void run(){
		while(true){

			Object obj = null;
			if(stageGame.getEndOfTheGame()==true){
                System.exit(1);
            }
			try {
				obj = mes.readObject();
			} catch (SocketException sock) {
			    stageGame.setEndOfTheGame(true);
			    text.setText("Gra została przerwana przepraszamy");
			    System.exit(1);
            } catch (ClassNotFoundException | IOException  e ) {
				e.printStackTrace();
			}
            if(obj != null){
				String message = obj.toString();
                Gson gson = new GsonBuilder().create();

                StageGame resultObject = gson.fromJson(message, StageGame.class);
                if(resultObject.getId()==true){
                    player=resultObject.getIdplayer();
                    stageGame.setIdplayer(player);
                    modelClientGame.setIdPlayer(player);
                }
                System.out.println(message);
               stageGame.StageGame(resultObject.getPointsOfPlayer() ,resultObject.getFirstPlayer(),resultObject.getSecondPlayer(),resultObject.getThirdPlayer(),resultObject.getPlayerTurn(),resultObject.getEndOfTheGame(),resultObject.getEndOfTurn(),resultObject.getColorChoice(),resultObject.getCardsPuts(),resultObject.getPointInTurn(),resultObject.getAuctions(),3,resultObject.getGameState(),resultObject.getWhichPlayerWinAuction(),resultObject.getFirstCardPut());
               int[] points = stageGame.getPointsOfPlayer();
               score.setText(Integer.toString(points[0]));
                score1.setText(Integer.toString(points[1]));
                score2.setText(Integer.toString(points[2]));
                if(resultObject.getGameState()==2 && resultObject.getCardDeal()==true) {
                    int x=50;
                    for(int i=0;i<7;i++){
                        imageViews[i].setLayoutY(500);
                        imageViews[i].setLayoutX(x+(i*100));
                    }
                    start.setVisible(true);
                    pass.setVisible(true);
                    gram.setVisible(false);
                    String a = "Licytacja";
                    if(stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()){
                        a+=" Twój ruch";
                    }
                    text.setText(a);
					int[][] temp = resultObject.getCards();

					for (int i = 0; i < 7; i++) {
						Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[player][i]) + ".png");
						imageViews[i].setImage(im);
					}
					tabCardToChange[0]=temp[3][0];
                    tabCardToChange[1]=temp[3][1];
                    tabCardToChange[2]=temp[3][2];
                    modelClientGame.setCards(temp[player]);
                    modelClientGame.setCardToChange(tabCardToChange);
                    //text.setText(Integer.toString(tabCardToChange[0]) + " " +Integer.toString(tabCardToChange[1]) + " "+Integer.toString(tabCardToChange[2]) + " ");
				}
				if(resultObject.getGameState()==2){
                    String a = "Licytacja";
                    if(stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()){
                        a+=" Twój ruch";
                    }
                    text.setText(a);
                	List<Auction> temp = resultObject.getAuctions();
					int wynik = temp.get(0).getPrice();
					for (int i=1; i<3; i++) {
						if (wynik < temp.get(i).getPrice()) {
							wynik =temp.get(i).getPrice();
						}
					}
					auction.setText(Integer.toString(wynik));
				}

				if(resultObject.getGameState()==3){
                    String a = "";
                    if(stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()){
                        a+="Wymien karty i nastepnie Wybierz o ile grasz";
                    }
                    text.setText(a);
                    stageGame.setCardsToChange(tabCardToChange);
                }
                if(stageGame.getFirstCardPut()!=0){
                    System.out.println("Zmien karty mozlwie do rzucenia");
                    modelClientGame.setCardCanToBePut(stageGame.getFirstCardPut(),stageGame.getColorChoice(),imageViews);
                }
                if(resultObject.getGameState()==4){
                    String a = "Gramy o"+ Integer.toString(stageGame.getBid());
                    if(stageGame.getPlayerTurn()==modelClientGame.getIdPlayer()){
                        a+=" Twój ruch";
                    }
                    text.setText(a);
                    if(stageGame.getColorChoice()!=0){
                        Image im ;
                        switch (stageGame.getColorChoice()){
                            case 1:
                                im = new javafx.scene.image.Image("Card/żołądź.png");
                                colorChoice.setImage(im);
                                break;
                            case 2:
                                im = new javafx.scene.image.Image("Card/wino.png");
                                colorChoice.setImage(im);
                                break;
                            case 3:
                                im = new javafx.scene.image.Image("Card/czerwo.png");
                                colorChoice.setImage(im);
                                break;
                            case 4:
                                im = new javafx.scene.image.Image("Card/dzwonek.png");
                                colorChoice.setImage(im);
                                break;
                        }
                    }
                    setCardOnView(stageGame.getCardsPuts());
                    if(stageGame.getEndOfTurn()==true){

                        imageViewPlayerTwo.setImage(null);
                        imageViewPlayerOne.setImage(null);
                        for(int i=0;i<7;i++){
                            if(imageViews[i].getLayoutY()==300){
                                imageViews[i].setImage(null);
                            }
                        }

                        isSelected=false;
                        whichCardIsSelected=0;
                        int[] tab = {0,0,0};
                        stageGame.setCardsPuts(tab);
                        modelClientGame.getCardCanBePut(0);
                        //stageGame.setGameState(1);
                        System.out.println(whichCardIsSelected);
                    }
				}

				if(stageGame.getEndOfTheGame()){
					flag = false;
                    try {
                        modelClientGame.closeScoket();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(1);
					break;
				}
			}
		}
		
	}

	public void setCardOnView(int[] temp) {
        if (modelClientGame.getIdPlayer() == 0) {
            if (temp[1] != 0 && temp[2] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[1]) + ".png");
                imageViewPlayerOne.setImage(im);
                Image im2 = new javafx.scene.image.Image("Card/" + Integer.toString(temp[2]) + ".png");
                imageViewPlayerTwo.setImage(im2);
            } else if (temp[2] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[2]) + ".png");
                imageViewPlayerTwo.setImage(im);
            } else if (temp[1] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[1]) + ".png");
                imageViewPlayerOne.setImage(im);
            }
        } else if (modelClientGame.getIdPlayer() == 1) {
            if (temp[2] != 0 && temp[0] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[2]) + ".png");
                imageViewPlayerOne.setImage(im);
                Image im2 = new javafx.scene.image.Image("Card/" + Integer.toString(temp[0]) + ".png");
                imageViewPlayerTwo.setImage(im2);
            } else if (temp[0] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[0]) + ".png");
                imageViewPlayerTwo.setImage(im);
            } else if (temp[2] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[2]) + ".png");
                imageViewPlayerOne.setImage(im);
            }
        } else if (modelClientGame.getIdPlayer() == 2) {
            if (temp[0] != 0 && temp[1] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[0]) + ".png");
                imageViewPlayerOne.setImage(im);
                Image im2 = new javafx.scene.image.Image("Card/" + Integer.toString(temp[1]) + ".png");
                imageViewPlayerTwo.setImage(im2);
            } else if (temp[1] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[1]) + ".png");
                imageViewPlayerTwo.setImage(im);
            } else if (temp[0] != 0) {
                Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[0]) + ".png");
                imageViewPlayerOne.setImage(im);
            }
        }
    }


}
