package network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private ImageView imageViewPlayerTwo;
    private ImageView[] imageViews;
    private int player;
    private Text auction;
    private int[] tabCardToChange=new int [3];
    private ModelClientGame modelClientGame;

    public Messeges(int idlayer, ObjectInputStream in, Text javafxControol, ImageView imageViewPlayerOne, ImageView imageViewPlayerTwo, Text score, Text score1, Text score2, StageGame stageGame, ImageView[] imageViews, int idPlayer, Text auction, ModelClientGame modelClientGame){
		this.mes = in;
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
	}
	
	public void run(){
		while(true){
			Object obj = null;
			try {
				obj = mes.readObject();
			} catch (ClassNotFoundException | IOException  e) {
				e.printStackTrace();
			}
			if(obj != null){
				String message = obj.toString();
				System.out.println(message);
                Gson gson = new GsonBuilder().create();

                StageGame resultObject = gson.fromJson(message, StageGame.class);
                System.out.println(message);
                if(resultObject.getId()==true){
                    player=resultObject.getIdplayer();
                    stageGame.setIdplayer(player);
                    modelClientGame.setIdPlayer(player);
                }
               stageGame.StageGame(resultObject.getPlayerTurn(),resultObject.getEndOfTheGame(),resultObject.getEndOfTurn(),resultObject.getColorChoice(),resultObject.getCardsPuts(),resultObject.getPointInTurn(),resultObject.getAuctions(),3,resultObject.getGameState(),resultObject.getWhichPlayerWinAuction());

                if(resultObject.getGameState()==2 && resultObject.getCardDeal()==true) {
                    text.setText("Licytacja");
					int[][] temp = resultObject.getCards();

					for (int i = 0; i < 7; i++) {

						Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[player][i]) + ".png");
						imageViews[i].setImage(im);
					}
					tabCardToChange[0]=temp[3][0];
                    tabCardToChange[1]=temp[3][1];
                    tabCardToChange[2]=temp[3][2];
                    modelClientGame.setCardToChange(tabCardToChange);
                    text.setText(Integer.toString(tabCardToChange[0]) + " " +Integer.toString(tabCardToChange[1]) + " "+Integer.toString(tabCardToChange[2]) + " ");
				}
				if(resultObject.getGameState()==2){
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
                    stageGame.setCardsToChange(tabCardToChange);
                }
				if(message.equalsIgnoreCase("Dowidzenia")){
					flag = false;
					System.exit(1);
					break;
				}
				else{
					System.out.println(message);
				}
			}
		}
		
	}
}
