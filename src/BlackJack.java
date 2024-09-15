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

        public String getImagePath(){
            return "./cards/" + toString() + ".png";
        }

    }

    ArrayList<Card> deck;
    Random random = new Random();

    //dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;


    //player

    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    //window

    int boardW = 660;
    int boardH = 660;

    int cardW = 110;
    int cardH = 154;

        JFrame frame = new JFrame("Black Jack");
        JPanel gamPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                
                try{
                {
                    Image hiddenCardImg = new ImageIcon("C:\\Users\\vando\\Desktop\\Black-Jack\\src\\cards\\BACK.png").getImage();
                    if(!stayButton.isEnabled()){
                        hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                    }
                    g.drawImage(hiddenCardImg, 30, 30 , cardW, cardH,null);
                }

                for (int i = 0; i < dealerHand.size(); i++) {
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardW + 40 + (cardW + 10)*i,30 ,cardW, cardH, null);
                }

                for (int i = 0; i < playerHand.size(); i++) {
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 30 + (cardW + 10)*i, 400, cardW, cardH,null);
                }

                if(!stayButton.isEnabled()){
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerSum);
                    System.out.println(playerSum);
                    
                    String message = "";


                    if(playerSum > 21){
                        message = "You Lose!";
                    }
                    else if (dealerSum > 21){
                        message = "You Win!";
                    }
                    else if (playerSum == dealerSum){
                        message = "Tie!";
                    }
                    else if (playerSum > dealerSum){
                        message = "You win!";
                    }
                    else if (playerSum < dealerSum){
                        message = "You Lose!";
                    }

                    g.setFont(new Font("Arial",Font.PLAIN, 30));
                    g.setColor(Color.orange);
                    g.drawString(message, 220, 250);

                }
            }
             catch (Exception e) {
                e.printStackTrace();
             }
           }
        };
        ImageIcon icon = new ImageIcon("C:/Users/vando/Desktop/Black-Jack/src/cards/BJ.png");  
        Image image = icon.getImage();
        JPanel buttonPanel = new JPanel();
        JButton hitButton = new JButton("Hit");
        JButton stayButton = new JButton("Stay");

    BlackJack(){
        startGame();

        frame.setVisible(true);
        frame.setSize(boardW,boardH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        gamPanel.setLayout(new BorderLayout());
        gamPanel.setBackground(new Color(85,118,68));
        frame.add(gamPanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel,BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Card card = deck.remove(deck.size()-1);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                playerHand.add(card);
                if(reducePlayerAce() > 21){
                    hitButton.setEnabled(false);
                }
                gamPanel.repaint();
            }
            
        });

        stayButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e){
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);

            while (dealerSum < 17) {
                Card card = deck.remove(deck.size()-1);
                dealerSum += card.getValue();
                dealerAceCount += card.isAce() ? 1 : 0;
                dealerHand.add(card);
            }
            gamPanel.repaint();
           } 
        });

        gamPanel.repaint();
    }

    public void startGame() {

        //deck
        buildDeck();
        shuffledeck();

        //dealer
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

        //player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("PLAYER: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
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
    public int reducePlayerAce(){
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce(){
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }
}