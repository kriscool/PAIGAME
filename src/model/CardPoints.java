package model;

import java.util.ArrayList;

public class CardPoints {
	public CardPoints(){
	}
	
	public int GetPointsFromCard(ArrayList<Integer> tabOfCard){
		int sum=0;
		for(int card:tabOfCard){
			if(card%6==1){}
			else if(card%6==2){sum+=10;}
			else if(card%6==3){sum+=2;}
			else if(card%6==4){sum+=3;}
			else if(card%6==5){sum+=4;}
			else if(card%6==0){sum+=11;}
		}
		return sum;
	}
	
	public int GetPointFromKozer(int choice){
		switch(choice){
			case 1:
				return 60;
			case 2:
				return 40;
			case 3:
				return 100;
			case 4:
				return 80;
		}
		return choice;
	}
}
