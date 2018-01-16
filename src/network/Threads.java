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
				System.out.println("id gracza"+id);
				StageGame stageGame = new StageGame();
				stageGame.setIdplayer(idClientInRoom);
				stageGame.setId(true);
                game.sentToPlayer(gson.toJson(stageGame),output.toString());
                System.out.println("Czeka na login");
				ClientOfGame resultObject = gson.fromJson(input.readObject().toString(), ClientOfGame.class);
				System.out.println("Login z gson" +resultObject.getName());
				login=resultObject.getName();
				stageGame.setId(false);
				while(true){

					StageGame response = gson.fromJson(input.readObject().toString(), StageGame.class);

					while(!response.getEndOfTheGame()){
						if(idPlayers==0){
							System.out.println("id");
							response.setGameState(1);
						}
						switch (response.getGameState()){
							case 1:
								cards(response);
								break;
							case 2:
								auction(response);
								break;
							case 3:
								turns(response);
								break;

						}
					}

					if(response.getEndOfTheGame()==true) {
                        break;
                    }
				}
			    input.close();
		        output.close();
		        socket.close();
		
			} catch (IOException e) {
			    System.out.println(e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

	}

	private void cards(StageGame response) throws IOException {
        response.setCards(randCardToGame());
        Gson gson = new Gson();
		response.setGameState(2);
		response.setIdplayer(idClientInRoom);
        //output.writeObject(gson.toJson(response));
		System.out.println("Losuje karty");
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
        tab[3][0]=obj.get(23);

        return tab;

    }
	private void turns(StageGame response) {
	}

	private void auction(StageGame response) {
	}

/*

	private int getMessege(String login) throws IOException, ClassNotFoundException {
		try{
				output.writeObject("1 " + login + "?");
			} catch (IOException e) {
				e.printStackTrace();
			}
		return Integer.parseInt((input.readObject().toString().trim()));
	}
*/

}
