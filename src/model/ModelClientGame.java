package model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ModelClientGame {
    private int idPlayer;
    private int[] cardToChange;
    private int[] cards = new int[7];
    private Socket clientSocket = null;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;
    public void changeCardBeforeBid(int[] number, ArrayList<Integer> change){
        for(int i=0;i<3;i++) {
            switch (change.get(i)) {
                case 1:
                    cards[0]=number[i];
                    break;
                case 2:
                    cards[1]=number[i];
                    break;
                case 3:
                    cards[2]=number[i];
                    break;
                case 4:
                    cards[3]=number[i];
                    break;
                case 5:
                    cards[4]=number[i];
                    break;
                case 6:
                    cards[5]=number[i];
                    break;
                case 7:
                    cards[6]=number[i];
                    break;
            }
        }
    }

    private Boolean[] cardCanBePut={false,false,false,false,false,false,false};
    public Boolean getCardCanBePut(int i){
        return cardCanBePut[i];
    }
    public void setCardCanToBePut(int firstCard,int colorChoice){
        int count=0;
        if(firstCard>0 && firstCard<7){
            for(int i=0;i<7;i++){
                if(cards[i]>0 && cards[i]<7){
                    cardCanBePut[i]=true;
                    count++;
                }
            }
        }else  if(firstCard>6 && firstCard<13){
            for(int i=0;i<7;i++){
                if(cards[i]>6 && cards[i]<13){
                    cardCanBePut[i]=true;
                    count++;
                }
            }
        }else  if(firstCard>12 && firstCard<19){
            for(int i=0;i<7;i++){
                if(cards[i]>12 && cards[i]<19){
                    cardCanBePut[i]=true;
                    count++;
                }
            }

        }else  if(firstCard>18 && firstCard<25){
            for(int i=0;i<7;i++){
                if(cards[i]>18 && cards[i]<25){
                    cardCanBePut[i]=true;
                    count++;
                }
            }
        }else if(colorChoice!=0){
            if(colorChoice==1){
                for(int i=0;i<7;i++){
                    if(cards[i]>0 && cards[i]<7){
                        cardCanBePut[i]=true;
                        count++;
                    }
                }
            }else  if(colorChoice==2){
                for(int i=0;i<7;i++){
                    if(cards[i]>6 && cards[i]<13){
                        cardCanBePut[i]=true;
                        count++;
                    }
                }
            }else  if(colorChoice==3){
                for(int i=0;i<7;i++){
                    if(cards[i]>12 && cards[i]<19){
                        cardCanBePut[i]=true;
                        count++;
                    }
                }
            }else  if(colorChoice==4){
                for(int i=0;i<7;i++){
                    if(cards[i]>18 && cards[i]<24){
                        cardCanBePut[i]=true;
                        count++;
                    }
                }
            }
        }
        if(count==0){
            for(int i=0;i<7;i++) {
                cardCanBePut[i] = true;
            }

        }
    }
    public void setSockets(Socket clientSocket,ObjectOutputStream output,ObjectInputStream input){
        this.clientSocket=clientSocket;
        this.output=output;
        this.input=input;
    }

    public void closeScoket() throws IOException {
        this.clientSocket.close();
        this.output.close();
        this.input.close();
    }


    public int getCard(int i){
        return cards[i];
    }
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

    public int[] getCards() {
        return cards;
    }

    public void setCards(int[] cards) {
        this.cards = cards;
    }
}
