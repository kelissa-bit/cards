import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;



public class Spit extends CardGame {
    ArrayList<Card> discardPile2 = new ArrayList<>();

    public void discardPiles() {
        discardPile.add(deck.remove(0));
        discardPile2.add(deck.remove(0));
    };

    private int validPlays(Card card){
        String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(card.value)) {
                return i;
            }
        }
        return -1;
    }

    public void winCondition() {

        boolean computerCanPlay = playerCanPlay(playerTwoHand);
        boolean personCanPlay = playerCanPlay(playerOneHand); 
        if (playerOneHand.getSize() == 0) {
            gameOver = true;
            playerWon = true;
        }
        
        if (playerTwoHand.getSize() == 0) {
            gameOver = true;
            playerWon = false;
        }

        if (deck.size() == 0 && !computerCanPlay && !personCanPlay) {
            gameOver = true;
            tie = true;
        }
    }

    protected boolean isValidPlay(Card card, ArrayList<Card> pile) {
        if (pile.isEmpty()) return false;

        Card lastPlayedCard = pile.get(pile.size() - 1);

        int playing = validPlays(card);
        int last = validPlays(lastPlayedCard);

        if (playing == 0 && last == 12) return true;
        if (playing == 12 && last == 0) return true;

        return Math.abs(playing - last) == 1;

    }

     public boolean playCard(Card card, Hand hand) {
        // Check if card is valid to play
        if (!isValidPlay(card, discardPile) && !isValidPlay(card, discardPile2)) {
            System.out.println("Invalid play: " + card.value + " of " + card.suit);
            return false;
        }

        hand.removeCard(card);
        card.setTurned(false);

        if (isValidPlay(card, discardPile)) {
            discardPile.add(card);
        } else {
            discardPile2.add(card);
        }

        winCondition();

        if (!gameOver) {
            switchTurns();
        }
        return true;
    }

    public boolean playerCanPlay(Hand hand) {
         for (int pile = 1; pile <= 5; pile++) {
            int i = (pile * (pile + 1)) / 2 - 1;
            if (i >= hand.getSize()) continue;
            Card card = hand.getCard(i);
            if (card != null) {
                if (isValidPlay(card, discardPile) || isValidPlay(card, discardPile2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void spiiiit() {
        if (!deck.isEmpty()) {
            discardPile.add(deck.remove(0));
            discardPile2.add(deck.remove(0));
        }
    }

    public void handleComputerTurn() {
        boolean computerCanPlay = playerCanPlay(playerTwoHand);
        boolean personCanPlay = playerCanPlay(playerOneHand);
        
        if (computerCanPlay) {
            if (gameOver) return;
            for (int i = 0; i < playerTwoHand.getSize(); i++) {
                Card card = playerTwoHand.getCard(i);
                if (card != null && (isValidPlay(card, discardPile) || isValidPlay(card, discardPile2))) {
                    playCard(card, playerTwoHand);
                    return; 
                }
            }
        }
    
        if (!computerCanPlay && personCanPlay) {
            switchTurns();
            return;
        }

        if (!computerCanPlay && !personCanPlay) {
            winCondition();
            if (!gameOver) {
                spiiiit();
            }
            return;
        }

    }

    public void handleMyTurn() {
        if (gameOver) return;
        boolean computerCanPlay = playerCanPlay(playerTwoHand);
        boolean personCanPlay = playerCanPlay(playerOneHand);

        if (!personCanPlay && computerCanPlay) {
            switchTurns();
            return;
        } 
            
        if (!personCanPlay && !computerCanPlay) {
            winCondition();

            if (!gameOver) {
                spiiiit();
            }
            return;
        }
    }

    boolean gameOver = false;
    boolean playerWon = false;
    boolean tie = false;
}