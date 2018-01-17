package model;

public class ModelClientGame {
    private int idPlayer;
    private int[] cardToChange;

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int[] getCardToChange() {
        return cardToChange;
    }

    public void setCardToChange(int[] cardToChange) {
        this.cardToChange = cardToChange;
    }
}
