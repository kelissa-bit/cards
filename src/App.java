import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {

    Spit cardGame = new Spit();
    private int timer;
    PImage bg;
    PImage award;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    @Override
    public void settings() {
        size(600, 600);   
        cardGame.discardPiles();
        bg = loadImage("table.png");
        award = loadImage("award!!!.png");
        cardGame = new Spit();
    }

    public void gameOverScreen() {
        background(255);
        fill (0);
        textSize(30);
        textAlign(CENTER, CENTER);

        if (cardGame.tie) {
            text ("Draw!", width/2, height/2);
        }
        else if (cardGame.playerWon) {
            image(award, width/2 - 75, 100, 150, 150);
            text("Wow... You won the Spit Showdown!!! \n" + 
            "You are truly the Spit Master.\n" + 
            "\n" +
            "I present to you...\n" +
            "this beautiful trophy!", width/2, height/2 + 100);
        } else {
            text("You lost...\n" + 
            "You can always try again!", width/2, height/2);
        }

        textSize(20);
        text("Click to rematch!", width/2, 550);
    }

    @Override
    public void draw() {
        if (cardGame.gameOver) {
            gameOverScreen();
            return;
        }
        background(bg);
        // Draw player hands
        for (int i = 0; i < cardGame.playerOneHand.getSize(); i++) {
            Card card = cardGame.playerOneHand.getCard(i);
            if (card != null) {
                card.draw(this);
            }
        }
        

        // Draw computer hand
        for (int i = 0; i < cardGame.playerTwoHand.getSize(); i++) {
            Card card = cardGame.playerTwoHand.getCard(i);
            if (card != null) {
                card.draw(this);
            }
        }

        // draw discardpile!
        if (!cardGame.discardPile.isEmpty()) {
            Card card = cardGame.discardPile.get(cardGame.discardPile.size()-1);
            card.setPosition(350, 240, 80, 120);
            card.draw(this);
        }

        // draw discardpile2!
        if (!cardGame.discardPile2.isEmpty()) {
            Card card = cardGame.discardPile2.get(cardGame.discardPile2.size()-1);
            card.setPosition(150, 240, 80, 120);
            card.draw(this);
        }

        // Display current player
        fill(0);
        textSize(16);
        textAlign(CENTER);
        text("Current Player: " + cardGame.getCurrentPlayer(), width / 2, 30);

        // Display deck size
        text("Deck Size: " + cardGame.getDeckSize(), width / 2,
                height - 10);
        // Display last played card
        // if (cardGame.getLastPlayedCard() != null) {
        //     cardGame.getLastPlayedCard().setPosition(width / 2 - 40, height / 2 - 60, 80, 120);
        //     cardGame.getLastPlayedCard().draw(this);
        // }
        if (cardGame.getCurrentPlayer() == "Player Two") {
            fill(0);
            textSize(16);
            text("Computer is thinking...", width / 2, height / 2 + 100);
            timer++;
            if (timer == 50) {
                cardGame.handleComputerTurn();;
                timer = 0;
            }
        }

        if(cardGame.getCurrentPlayer() == "Player One") {
            cardGame.handleMyTurn();
        }

        cardGame.drawChoices(this);
    }
    
    @Override
    public void mousePressed() {
        if (cardGame.gameOver) {
            cardGame = new Spit();
            return;
        }
        cardGame.handleCardClick(mouseX, mouseY);
    }

}
