package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Room {
	public ArrayList<ObjectOutputStream> outputStreams;
	private StageGame StageGameRoom = new StageGame();
	public Room(){
		outputStreams = new ArrayList<>();
	}
	
	public void addOutputStream(ObjectOutputStream e){
		outputStreams.add(e);
	}

	public int getNumberOfPlayer(){
		int i=0;
		for(ObjectOutputStream e:outputStreams) {
			i++;
		}
		return i;
	}

	public void removePlayerFromRoom(String s){
		for(ObjectOutputStream e : outputStreams){
			if(s.equals(e.toString())){
				outputStreams.remove(e);
			}
		}

	}
	public void writeToUsers(String s) throws IOException{
		Gson gson = new GsonBuilder().create();
		StageGame resultObject = gson.fromJson(s, StageGame.class);
		for(ObjectOutputStream e : outputStreams){
			e.writeObject(s);
		}
	}

	public StageGame getStageGameRoom() {
		return StageGameRoom;
	}

	public void setStageGameRoom(StageGame stageGameRoom) {
		StageGameRoom = stageGameRoom;
	}
}
