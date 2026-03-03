import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;



public class Spit extends CardGame {

    private int validPlays(Card card){
        String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(card.value)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isValidPlay(Card card) {
        if (lastPlayedCard == null) return true;

        int playing = validPlays(card);
        int last = validPlays(lastPlayedCard);

        if (playing == 0 && last == 12) return true;
        if (playing == 12 && last == 0) return true;

        return Math.abs(playing - last) == 1;

    }
}