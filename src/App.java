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

    @Override
    public void draw() {
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
        cardGame.handleCardClick(mouseX, mouseY);
    }

}
