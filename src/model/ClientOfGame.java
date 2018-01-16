package model;

public class ClientOfGame {
	private int id;
	private String name;
	public ClientOfGame(String login, int id2) {
		this.name=login;
		this.id = id2;
	}
	public ClientOfGame() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	


	
}
