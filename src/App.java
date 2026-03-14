import processing.core.PApplet;

public class App extends PApplet {

    Spit cardGame = new Spit();
    private int timer;

    public static void main(String[] args) {
        PApplet.main("App");
    }
    @Override
    public void settings() {
        size(600, 600);   
        cardGame.discardPiles();
    }

    public void gameOverScreen() {
        background(255);
        fill (0);
        textSize(30);
        textAlign(CENTER);

        if (cardGame.playerWon) {
            text("wow... you won the spit showdown!!! \n" + 
            "you are truly the spit master.\n" + 
            "\n" +
            "i present to you...\n" +
            "this beautiful trophy!", width/2, height/2);
        } else {
            text("oh... you lost...\n" + 
            "you lost to my poorly coded robot...\n" +
            "\n" +
            "well.\n" +
            "you can always try again!", width/2, height/2 - 40);
        }

        textSize(20);
        text("click to rematch!", width/2, 550);
    }

    @Override
    public void draw() {
        if (cardGame.gameOver) {
            gameOverScreen();
            return;
        }
        background(255);
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
            card.setPosition(350, 240);
            card.draw(this);
        }

        // draw discardpile2!
        if (!cardGame.discardPile2.isEmpty()) {
            Card card = cardGame.discardPile2.get(cardGame.discardPile2.size()-1);
            card.setPosition(200, 240);
            card.draw(this);
        }

        // Display current player
        fill(0);
        textSize(16);
        text("Current Player: " + cardGame.getCurrentPlayer(), width / 2, 20);

        // Display deck size
        text("Deck Size: " + cardGame.getDeckSize(), width / 2,
                height - 20);
        // Display last played card
        // if (cardGame.getLastPlayedCard() != null) {
        //     cardGame.getLastPlayedCard().setPosition(width / 2 - 40, height / 2 - 60, 80, 120);
        //     cardGame.getLastPlayedCard().draw(this);
        // }
        if (cardGame.getCurrentPlayer() == "Player Two") {
            fill(0);
            textSize(16);
            text("Computer is thinking...", width / 2, height / 2 + 80);
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
