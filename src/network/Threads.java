package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.CardPoints;
import model.ClientOfGame;
import model.Game;
import model.StageGame;
import serwer.Serwer;

public class Threads  implements Runnable{
	ObjectInputStream input;
	ObjectOutputStream output; 
    Socket socket;
    int id;
    Serwer serwer;
    Game game;
	int idPlayers;
	int idClientInRoom;
    Boolean gameStarted=false;
    Gson gson = new Gson();
    public Threads(Socket sock,int id, Serwer serwer,Game game){
    	this.socket = sock;
    	this.id = id;
    	this.serwer = serwer;
    	this.game = game;
    	System.out.println( id + " Zalogowano! (" + sock +")" );

    	try {
    		output = new ObjectOutputStream(sock.getOutputStream());
    		input = new ObjectInputStream(sock.getInputStream());

    	} catch (IOException e) {
    	    System.out.println(e);
    	}
    }
    
	@Override
	public void run() {
		  	String login;
	        
			try {

                Gson gson = new GsonBuilder().create();
                idClientInRoom =game.addClientToRoom(output);
				idPlayers=idClientInRoom;
				StageGame stageGame = new StageGame();
				stageGame.setIdplayer(idClientInRoom);
				stageGame.setId(true);
                game.sentToPlayer(gson.toJson(stageGame),output.toString());
				ClientOfGame resultObject = gson.fromJson(input.readObject().toString(), ClientOfGame.class);

				login=resultObject.getName();
				stageGame.setId(false);
				while(true){

                    StageGame response = gson.fromJson(input.readObject().toString(), StageGame.class);
                    if(response.getEndOfTheGame()==true) {
                        System.out.println("Disconect" + Integer.toString(idClientInRoom));
                        game.sentToRoom(gson.toJson(response),output.toString());
                        input.close();
                        output.close();
                        socket.close();
                    }
					if(!response.getEndOfTheGame()){
						if(idPlayers==0){
							response.setGameState(1);
							gameStarted=true;
						}

							switch (response.getGameState()) {
								case 1:
									cards(response);
									break;
								case 2:
									auction(response);
									break;
								case 3:
									changeCard(response);
									break;
								case 4:
									turns(response);
									break;

							}

					}



				}


			} catch (IOException e) {

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

	}

	private void changeCard(StageGame response) {

	}

	private void cards(StageGame response) throws IOException {
        System.out.println("Wybieranie kart");
        System.out.println(response.getFirstPlayer().size());
        if(response.getFirstPlayer().size()!=0 || response.getSecondPlayer().size()!=0 || response.getThirdPlayer().size()!=0){
            System.out.println("Obliczanie pkt");
                CardPoints cardPoints = new CardPoints();
                int fi= cardPoints.GetPointsFromCard(response.getFirstPlayer());
                int se =cardPoints.GetPointsFromCard(response.getSecondPlayer());
                int thi =cardPoints.GetPointsFromCard(response.getThirdPlayer());
                int[] tabPointsOfPla={fi,se,thi};
                response.setPointsOfPlayer(tabPointsOfPla);
        }
        response.setCards(randCardToGame());
        Gson gson = new Gson();
		response.setGameState(2);
		response.setIdplayer(idClientInRoom);
		response.setCardDeal(true);
		idPlayers=4;
        game.sentToRoom(gson.toJson(response),output.toString());
	}

    public int[][] randCardToGame(){
        ArrayList<Integer> obj = new ArrayList<Integer>();
        int[][] tab = new int[4][7];
        for(int i =1;i<25;i++){
            obj.add(i);
        }
        Collections.shuffle(obj);
        for(int i =0;i<3;i++){
            for(int j=0;j<7;j++){
                tab[i][j]=obj.get(i*7+j);
            }
        }
        tab[3][0]=obj.get(21);
        tab[3][1]=obj.get(22);
        tab[3][2]=obj.get(23);

        return tab;

    }
	private void turns(StageGame response) throws IOException {
        int[] tab = response.getCardsPuts();

        //System.out.print(gson.toJson(response));
		if(response.isThreeCardPuts()){
            response.setPlayerTurn(changePlayer(response.getPlayerTurn()));
            game.sentToRoom(gson.toJson(response), output.toString());
            //Tutaj będzie sprawdzane kto wygrał
            int whoWIn=whoWin(response);
            response.setEndOfTurn(true);
            response.setPlayerTurn(whoWin(response));
            int[] tabTemp = response.getCardsPuts();
            for(int i=0;i<3;i++){
                if(whoWIn==1){
                    response.getFirstPlayer().add(tabTemp[i]);
                }else if(whoWIn==2){
                    response.getSecondPlayer().add(tabTemp[i]);
                }else if(whoWIn==0){
                    response.getThirdPlayer().add(tabTemp[i]);
                }
            }

            response.setFirstCardPut(0);
            System.out.println("Kto wygral"+Integer.toString(whoWin(response)));
           // System.out.println("Kozer " +Integer.toString(response.getColorChoice()));
            game.sentToRoom(gson.toJson(response), output.toString());
        }else if(response.isThreeCardNull()){
            game.sentToRoom(gson.toJson(response), output.toString());
        }else{
            if(isFirstCard(tab)) {
                for (int i = 0; i < 3; i++) {
                    if (tab[i] != 0) {
                        response.setFirstCardPut(tab[i]);
                    }
                }
            }
            response.setPlayerTurn(changePlayer(response.getPlayerTurn()));
            game.sentToRoom(gson.toJson(response), output.toString());
        }
	}

	public int whoWin(StageGame response){
        int[] tabTemp = response.getCardsPuts();
        int firstCard = response.getFirstCardPut();
        int[] tempValue = new int[3];
        int indexOfCardSelected=0;
        if(response.getColorChoice()==0) {
            return getWiningCardWithoutKozer(response.getFirstCardPut(),response.getCardsPuts());
           }else{
            int[] tabCardWithKozer= new int[3];
            for(int i=0;i<3;i++) {
                int color = response.getColorChoice();
                    switch (response.getColorChoice()) {
                        case 1:
                            for (int index = 0; index < 3; index++) {
                                if (tabTemp[index] > 0 && tabTemp[index] < 7) {
                                    tabCardWithKozer[index] = getValueOfCard(tabTemp[index]);
                                }else {
                                    tabCardWithKozer[index]=0;
                                }
                            }
                            if(tabCardWithKozer[0]==0 && tabCardWithKozer[1]==0  && tabCardWithKozer[2]==0 ){
                                return getWiningCardWithoutKozer(response.getFirstCardPut(),response.getCardsPuts());
                            }else {
                                return highestCard(tabCardWithKozer);
                            }
                        case 2:
                            for (int index = 0; index < 3; index++) {
                                if (tabTemp[index] > 6 && tabTemp[index] <13) {
                                    tabCardWithKozer[index] = getValueOfCard(tabTemp[index]);
                                }else {
                                    tabCardWithKozer[index]=0;
                                }
                            }
                            if(tabCardWithKozer[0]==0 && tabCardWithKozer[1]==0  && tabCardWithKozer[2]==0 ){
                                return getWiningCardWithoutKozer(response.getFirstCardPut(),response.getCardsPuts());
                            }else {
                                return highestCard(tabCardWithKozer);
                            }
                        case 3:
                            for (int index = 0; index < 3; index++) {
                                if (tabTemp[index] > 12 && tabTemp[index] < 19) {
                                    tabCardWithKozer[index] = getValueOfCard(tabTemp[index]);
                                }else {
                                    tabCardWithKozer[index]=0;
                                }
                            }
                            if(tabCardWithKozer[0]==0 && tabCardWithKozer[1]==0  && tabCardWithKozer[2]==0 ){
                                return getWiningCardWithoutKozer(response.getFirstCardPut(),response.getCardsPuts());
                            }else {
                                return highestCard(tabCardWithKozer);
                            }
                        case 4:
                            for (int index = 0; index < 3; index++) {
                                if (tabTemp[index] > 18 && tabTemp[index] < 25) {
                                    tabCardWithKozer[index] = getValueOfCard(tabTemp[index]);
                                }else {
                                    tabCardWithKozer[index]=0;
                                }
                            }
                            if(tabCardWithKozer[0]==0 && tabCardWithKozer[1]==0  && tabCardWithKozer[2]==0 ){
                                return getWiningCardWithoutKozer(response.getFirstCardPut(),response.getCardsPuts());
                            }else {
                                return highestCard(tabCardWithKozer);
                            }
                    }
            }
        }

        return 4;



    }

    public int getWiningCardWithoutKozer(int firstCard,int[] tabTemp) {
        int indexOfCardSelected=0;
        int[] tempValue = new int[3];
        if (firstCard > 0 && firstCard < 7) {
            for(int i=0;i<3;i++) {
                if (tabTemp[i] > 0 && tabTemp[i] < 7) {
                    tempValue[i] = getValueOfCard(tabTemp[i]);
                } else {
                    tabTemp[i] = 0;
                }
            }
                return highestCard(tabTemp);
        } else if (firstCard > 6 && firstCard < 13) {
                for(int i=0;i<3;i++) {
                    if (tabTemp[i] > 6 && tabTemp[i] < 13) {
                        tempValue[i] = getValueOfCard(tabTemp[i]);
                    } else {
                        tabTemp[i] = 0;
                    }
                }  tabTemp[indexOfCardSelected] = 0;
                return highestCard(tabTemp);
        } else if (firstCard > 12 && firstCard < 19) {
            for(int i=0;i<3;i++) {
                if (tabTemp[i] > 12 && tabTemp[i] < 19) {
                    tempValue[i] = getValueOfCard(tabTemp[i]);
                } else {
                    tabTemp[i] = 0;
                }
            }  tabTemp[indexOfCardSelected] = 0;
            return highestCard(tabTemp);
        } else if (firstCard > 18 && firstCard < 25) {
            for(int i=0;i<3;i++) {
                if (tabTemp[i] > 18 && tabTemp[i] < 25) {
                    tempValue[i] = getValueOfCard(tabTemp[i]);
                } else {
                    tabTemp[i] = 0;
                }
            }  tabTemp[indexOfCardSelected] = 0;
            return highestCard(tabTemp);
        }
        return 4;
    }

    public int highestCard(int[] tab){
        if (tab[0] > tab[1]) {
            if (tab[0] > tab[2]) {
                return 0;
            } else {
                return 2;
            }
        } else if (tab[1] > tab[2]) {
            return 1;
        } else {
            return 2;
        }
    }
    public int getValueOfCard(int card){
        switch (card){
            case 1:
                return 0;
            case 2:
                return 5;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 6;
        }
        return 0;
    }
    private Boolean isFirstCard(int[] tab){
        int count =0;
        for(int i=0;i<3;i++){
            if(tab[i]==0){
                count++;
            }
        }
        if(count==2){
            return true;
        }
        return false;
    }
	private void auction(StageGame response) throws IOException {
        Boolean isOnePlayerInAuction=false;
        Gson gson = new Gson();
        int falseAuction=0;
        for(int i=0;i<3;i++){
            if(response.getAuctions().get(i).getOVer()==true){
                falseAuction++;
            }
        }
        if(response.getEndOfTurn()==false) {
            if (falseAuction != 2) {
                int tempPlayer = response.getIdplayer();
                response.setPlayerTurn(changePlayer(tempPlayer));
                game.sentToRoom(gson.toJson(response), output.toString());
            }else{
				for(int i=0;i<3;i++){
					if(!(response.getAuctions().get(i).getPrice()==0)){
					    response.setWhichPlayerWinAuction(i);
                        response.setPlayerTurn(i);
					}
				}
				response.setGameState(3);
				game.sentToRoom(gson.toJson(response), output.toString());
			}

        }
	}

	private int changePlayer(int a){
        if(a==0){
            return 1;
        }else if(a==1){
            return  2;
        }else{
            return 0;
        }
    }


}
