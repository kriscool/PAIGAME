package model;

import java.util.ArrayList;
import java.util.List;

public class StageGame {
    private int EndOfTurnToCalculatePoints=0;
    public StageGame(){
        this.endOfTheGame=false;
        this.endOfTurn=false;
        this.playerTurn=1;
        this.numberOfPlayers=0;
        this.gameState=0;
        List<Auction> temp =new ArrayList<Auction>();
        Auction a = new Auction();
        temp.add(a);
        temp.add(a);
        temp.add(a);
        this.auctions= temp;
        cardDeal=false;

    }
    public void StageGame(ArrayList<Integer> firstPlayer,ArrayList<Integer> secondPlayer,ArrayList<Integer> thirdPlayer,int playerTurn, Boolean endOfTheGame, Boolean endOfTurn, int colorChoice, int[] cardsPuts, int[] pointInTurn, List<Auction> auctions, int numberOfPlayers,int gameState,int whichPlayerWinAuction,int firstCardPut) {
        this.firstPlayer=firstPlayer;
        this.secondPlayer=secondPlayer;
        this.thirdPlayer=thirdPlayer;
        this.playerTurn = playerTurn;
        this.endOfTheGame = endOfTheGame;
        this.endOfTurn = endOfTurn;
        this.colorChoice = colorChoice;
        this.cardsPuts = cardsPuts;
        this.pointInTurn = pointInTurn;
        this.auctions = auctions;
        this.numberOfPlayers = numberOfPlayers;
        this.gameState=gameState;
        this.whichPlayerWinAuction=whichPlayerWinAuction;
        this.firstCardPut=firstCardPut;
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
    private int[] cardsPuts = {0,0,0};
    private int[] pointInTurn = new int [3];
    private int[] cardsToChange = new int[3];
    private int[][] cards = new int[4][7];
    private int[][] cardPickUpPlayer = new int[3][];
    private ArrayList<Integer> firstPlayer = new ArrayList<Integer>();
    private int[] pointsOfPlayer = new int[3];

    private ArrayList<Integer> secondPlayer = new ArrayList<Integer>();

    private ArrayList<Integer> thirdPlayer = new ArrayList<Integer>();
    private List<Auction> auctions;
    private int numberOfPlayers=0;
    private int whichPairReport;
    private int gameState;
    private int idplayer;
    private Boolean isId=false;
    private Boolean cardDeal;
    private int whichPlayerWinAuction=3;
    private int bid;
    private int firstCardPut;
    public Boolean isThreeCardPuts(){
        if(cardsPuts[0]!=0 && cardsPuts[1]!=0 && cardsPuts[2]!=0)
            return true;
        return false;
    }

    public Boolean isThreeCardNull(){
        if(cardsPuts[0]==0 && cardsPuts[1]==0 && cardsPuts[2]==0)
            return true;
        return false;
    }
    public void setCardPutPlayer(int idplayer,int card){
        cardsPuts[idplayer]=card;
    }
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

    public Boolean getCardDeal() {
        return cardDeal;
    }

    public void setCardDeal(Boolean cardDeal) {
        this.cardDeal = cardDeal;
    }

    public int getWhichPlayerWinAuction() {
        return whichPlayerWinAuction;
    }

    public void setWhichPlayerWinAuction(int whichPlayerWinAuction) {
        this.whichPlayerWinAuction = whichPlayerWinAuction;
    }

    public int[] getCardsToChange() {
        return cardsToChange;
    }

    public void setCardsToChange(int[] cardsToChange) {
        this.cardsToChange = cardsToChange;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getFirstCardPut() {
        return firstCardPut;
    }

    public void setFirstCardPut(int firstCardPut) {
        this.firstCardPut = firstCardPut;
    }

    public int[][] getCardPickUpPlayer() {
        return cardPickUpPlayer;
    }

    public void setCardPickUpPlayer(int[][] cardPickUpPlayer) {
        this.cardPickUpPlayer = cardPickUpPlayer;
    }

    public ArrayList<Integer> getThirdPlayer() {
        return thirdPlayer;
    }

    public void setThirdPlayer(ArrayList<Integer> thirdPlayer) {
        this.thirdPlayer = thirdPlayer;
    }

    public ArrayList<Integer> getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(ArrayList<Integer> secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public ArrayList<Integer> getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(ArrayList<Integer> firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public int getEndOfTurnToCalculatePoints() {
        return EndOfTurnToCalculatePoints;
    }

    public void setEndOfTurnToCalculatePoints(int endOfTurnToCalculatePoints) {
        EndOfTurnToCalculatePoints = endOfTurnToCalculatePoints;
    }

    public int[] getPointsOfPlayer() {
        return pointsOfPlayer;
    }

    public void setPointsOfPlayer(int[] pointsOfPlayer) {
        this.pointsOfPlayer = pointsOfPlayer;
    }
}
