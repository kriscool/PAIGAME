package serwer;
import java.io.*;
import java.net.*;

import model.Configuration;
import model.Game;
import network.Threads;
import xml.ParseXml;



public class Serwer {
	
    ServerSocket socketServer = null;
    Socket clientSocket = null;
    int con = 0;
    int port;
    Game game;
	
    public Serwer(int port,int numberOfPlayer) {
    	this.port = port;
    	this.game = Game.getInstance();
    	this.game.setNumberOfPlayerInRoom(numberOfPlayer);
    }

    public void startServer() {
    	
        try {
        	socketServer = new ServerSocket(port);
        } catch (IOException e) {
	    System.out.println(e);
        }   
	
		System.out.println("Serwer Start");
		while (true) {
		    try {
				clientSocket = socketServer.accept();
				System.out.println(clientSocket.getOutputStream().toString());
				Threads th = new Threads(clientSocket, ++con, this, game);
				new Thread(th).start();
		    } catch (IOException e) {
			System.out.println(e);
		    }
		    
		}
    }
    
    
    public static void main(String args[]) {
    	Game game = Game.getInstance();
    	Configuration configuration = Configuration.getInstance();
    	ParseXml p = new ParseXml();
 		Serwer server = new Serwer(configuration.getPort(),configuration.getNumberOfPlayer());
 		server.startServer();
 	
     }
    
}