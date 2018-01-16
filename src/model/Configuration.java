package model;


public class Configuration {
	private int port;
	private String nameHost;
	private int numberOfPlayer;
	private int yposition;
	private int firstCardPostion;
	private int secondCardPostion;
	private int thirdCardPostion;
	private int fourthCardPostion;
	private int fifthCardPostion;
	private int sixthCardPostion;
	private int seventhCardPosition;
	
	private static Configuration con = null;
	
	protected Configuration(){
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getNameHost() {
		return nameHost;
	}

	public void setNameHost(String nameHost) {
		this.nameHost = nameHost;
	}

	public int getNumberOfPlayer() {
		return numberOfPlayer;
	}

	public void setNumberOfPlayer(int numberOfPlayer) {
		this.numberOfPlayer = numberOfPlayer;
	}

	public int getYposition() {
		return yposition;
	}

	public void setYposition(int yposition) {
		this.yposition = yposition;
	}

	public int getFirstCardPostion() {
		return firstCardPostion;
	}

	public void setFirstCardPostion(int firstCardPostion) {
		this.firstCardPostion = firstCardPostion;
	}

	public int getSecondCardPostion() {
		return secondCardPostion;
	}

	public void setSecondCardPostion(int secondCardPostion) {
		this.secondCardPostion = secondCardPostion;
	}

	public int getThirdCardPostion() {
		return thirdCardPostion;
	}

	public void setThirdCardPostion(int thirdCardPostion) {
		this.thirdCardPostion = thirdCardPostion;
	}

	public int getFourthCardPostion() {
		return fourthCardPostion;
	}

	public void setFourthCardPostion(int fourthCardPostion) {
		this.fourthCardPostion = fourthCardPostion;
	}

	public int getFifthCardPostion() {
		return fifthCardPostion;
	}

	public void setFifthCardPostion(int fifthCardPostion) {
		this.fifthCardPostion = fifthCardPostion;
	}

	public int getSixthCardPostion() {
		return sixthCardPostion;
	}

	public void setSixthCardPostion(int sixthCardPostion) {
		this.sixthCardPostion = sixthCardPostion;
	}

	public int getSeventhCardPosition() {
		return seventhCardPosition;
	}

	public void setSeventhCardPosition(int seventhCardPosition) {
		this.seventhCardPosition = seventhCardPosition;
	}

	public static Configuration getInstance(){
		if(con == null){
			con = new Configuration();
		}
		return con;
	}
	
}
