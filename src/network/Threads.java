package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
                        input.close();
                        output.close();
                        socket.close();
                        System.exit(1);
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


		if(response.isThreeCardPuts()){
            response.setPlayerTurn(changePlayer(response.getPlayerTurn()));
            game.sentToRoom(gson.toJson(response), output.toString());
            //Tutaj będzie sprawdzane kto wygrał

            response.setEndOfTurn(true);
            response.setPlayerTurn(whoWin(response));
            System.out.println(Integer.toString(whoWin(response)));
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
        if(response.getColorChoice()==0) {
            ///Poprawić bo jest chujowe xD

            int[] tabTemp = response.getCardsPuts();
            if (tabTemp[0] > tabTemp[1]) {
                if (tabTemp[0] > tabTemp[2]) {
                    return 0;
                } else {
                    return 2;
                }
            } else if (tabTemp[1] > tabTemp[2]) {
                return 1;
            } else {
                return 2;
            }
        }else{
            return 0;
        }

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
