package model;


import xml.ParseXml;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Game {
	private ArrayList<ClientOfGame> users;
	public ArrayList<Room> rooms;
	private static Game game = null;
	private int numberOfClient = 0;
	private int indexOfRoom = -1;
	private int numberOfPlayerInRoom;
	
	public void setNumberOfPlayerInRoom(int number){
		numberOfPlayerInRoom=number;
	}
	protected Game(){
		Configuration configuration = Configuration.getInstance();
		ParseXml p = new ParseXml();
		numberOfPlayerInRoom=configuration.getNumberOfPlayer();
		rooms = new ArrayList<>();
		users = new ArrayList<>();
	}

	public int howMuchPlayerInRoom(String outputStream){
		for (Room room: rooms){
			for(ObjectOutputStream e: room.outputStreams){
				if(e.toString().equals(outputStream)){
					return room.getNumberOfPlayer();
				}
			}
		}
		return 0;
	}

	public void removePlayerInRoom(String outputStream){
		for (Room room: rooms){
			for(ObjectOutputStream e: room.outputStreams){
				if(e.toString().equals(outputStream)){
					 room.removePlayerFromRoom(outputStream);
				}
			}
		}
	}
	public int addClientToRoom(ObjectOutputStream client){
		if(numberOfClient % 3 == 0){
			Room r = new Room();
			rooms.add(r);
			numberOfClient++; 
			indexOfRoom++;
			rooms.get(indexOfRoom).addOutputStream(client);
			System.out.println("Liczba uzytkownikow " +Integer.toString(numberOfClient));
		}else{
			rooms.get(indexOfRoom).addOutputStream(client);
			numberOfClient++;
			System.out.println("else"+client.toString());
		}

		return numberOfClient%3;
	}
	
	public Boolean createClientOfBank(String login){
			ClientOfGame client = new ClientOfGame(login, users.size() + 1);
			users.add(client);
			return true;
	}
	
	public void sentToRoom(String s,String outputStream) throws IOException{
		for (Room room: rooms){
			for(ObjectOutputStream e: room.outputStreams){
				if(e.toString().equals(outputStream)){
					room.writeToUsers(s);
				}
			}
		}
	}

	public void sentToPlayer(String s,String outputstream) throws IOException {
		for (Room room: rooms){
			for(ObjectOutputStream e: room.outputStreams){
				if(e.toString().equals(outputstream)){
					e.writeObject(s);
				}
			}
		}
	}

	
	public static Game getInstance(){
		if(game == null){
			game = new Game();
		}
		return game;
	}
}
