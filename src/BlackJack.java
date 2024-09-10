import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {

    private class Card {
    
        String value;
        String type;

        Card(String value,String type){
            this.value = value;
            this.type = type;
        }
        public String toString(){
            return value + "-" + type;
        }

        public int getValue(){
            if("AJQK".contains(value)){
                if(value == "A"){
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value);
        }
        public boolean isAce(){
            return value == "A";
        }

    }

    ArrayList<Card> deck;

    Random random = new Random();

    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;


    BlackJack(){
        startGame();
    }

    public void startGame() {

        buildDeck();
        shuffledeck();

        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size()-1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);
    }

    public void buildDeck() {

        deck = new ArrayList<Card>();
        
        String[] values ={"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        String[] types = {"C","D","H","S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }
        System.out.println("BUILD DECK:");
        System.out.println(deck);
    }

    public void shuffledeck(){
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j); 

            deck.set(i, randomCard);
            deck.set(j, currCard);

        }
        System.out.println("after shifle");
        System.out.println(deck);
    }
}