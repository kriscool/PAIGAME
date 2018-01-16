package model;

import java.util.ArrayList;
import java.util.List;

public class StageGame {

    public StageGame(){
        this.endOfTheGame=false;
        this.endOfTurn=false;
        this.playerTurn=1;
        this.numberOfPlayers=0;
        this.gameState=0;
    }
    public void StageGame(int playerTurn, Boolean endOfTheGame, Boolean endOfTurn, int colorChoice, int[] cardsPuts, int[] pointInTurn, List<Auction> auctions, int numberOfPlayers,int gameState) {
        this.playerTurn = playerTurn;
        this.endOfTheGame = endOfTheGame;
        this.endOfTurn = endOfTurn;
        this.colorChoice = colorChoice;
        this.cardsPuts = cardsPuts;
        this.pointInTurn = pointInTurn;
        this.auctions = auctions;
        this.numberOfPlayers = numberOfPlayers;
        this.gameState=gameState;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Boolean getEndOfTheGame() {
        return endOfTheGame;
    }

    public void setEndOfTheGame(Boolean endOfTheGame) {
        this.endOfTheGame = endOfTheGame;
    }

    public Boolean getEndOfTurn() {
        return endOfTurn;
    }

    public void setEndOfTurn(Boolean endOfTurn) {
        this.endOfTurn = endOfTurn;
    }

    public int getColorChoice() {
        return colorChoice;
    }

    public void setColorChoice(int colorChoice) {
        this.colorChoice = colorChoice;
    }

    public int[] getCardsPuts() {
        return cardsPuts;
    }

    public void setCardsPuts(int[] cardsPuts) {
        this.cardsPuts = cardsPuts;
    }

    public int[] getPointInTurn() {
        return pointInTurn;
    }

    public void setPointInTurn(int[] pointInTurn) {
        this.pointInTurn = pointInTurn;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    private static StageGame stageGame = null;
    private int playerTurn;
    private Boolean endOfTheGame=false;
    private Boolean endOfTurn=false;
    private int colorChoice;
    private int[] cardsPuts = new int[3];
    private int[] pointInTurn = new int [3];
    private int[][] cards = new int[4][7];
    private List<Auction> auctions= new ArrayList<Auction>();
    private int numberOfPlayers=0;
    private int whichPairReport;
    private int gameState;
    private int idplayer;
    private Boolean isId=false;

    public static StageGame getInstance(){
        if(stageGame == null){
            stageGame = new StageGame();
        }
        return stageGame;
    }

    public int getWhichPairReport() {
        return whichPairReport;
    }

    public void setWhichPairReport(int whichPairReport) {
        this.whichPairReport = whichPairReport;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int[][] getCards() {
        return cards;
    }

    public void setCards(int[][] cards) {
        this.cards = cards;
    }

    public int getIdplayer() {
        return idplayer;
    }

    public void setIdplayer(int idplayer) {
        this.idplayer = idplayer;
    }

    public Boolean getId() {
        return isId;
    }

    public void setId(Boolean id) {
        isId = id;
    }
}