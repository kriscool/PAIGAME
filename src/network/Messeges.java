package network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.ClientOfGame;
import model.StageGame;

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.IOException;
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

    public Messeges(int idlayer,ObjectInputStream in, Text javafxControol,ImageView imageViewPlayerOne,ImageView imageViewPlayerTwo,Text score,Text score1,Text score2,StageGame stageGame,ImageView[] imageViews,int idPlayer){
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
                }
               stageGame.StageGame(resultObject.getPlayerTurn(),false,false,0,resultObject.getCardsPuts(),resultObject.getPointInTurn(),null,3,resultObject.getGameState());
				//stageGame=resultObject;
                if(resultObject.getGameState()==2) {
					int[][] temp = resultObject.getCards();

					for (int i = 0; i < 7; i++) {
						System.out.println(Integer.toString(temp[player][i]));
						Image im = new javafx.scene.image.Image("Card/" + Integer.toString(temp[player][i]) + ".png");
						imageViews[i].setImage(im);
					}
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
