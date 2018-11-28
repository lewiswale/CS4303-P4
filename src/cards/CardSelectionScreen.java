package cards;

import processing.core.PApplet;

import java.util.ArrayList;

public class CardSelectionScreen {
    PApplet p;
    ArrayList<Card> cardsToShow;

    public CardSelectionScreen(PApplet p) {
        this.p = p;
        this.cardsToShow = new ArrayList<>();
    }

    public void generateRewards() {
        cardsToShow = new ArrayList<>();
        cardsToShow.add(new Strength(p));
        cardsToShow.add(new Attack(p));
        cardsToShow.add(new Block(p));
    }

    public Card getSelection() {
        for (Card card: cardsToShow) {
            if (card.mouseIsOver) {
                return card;
            }
        }
        return null;
    }

    public void displayRewardScreen() {
        int x = 300;
        int y = 300;
        for (Card card: cardsToShow) {
            card.setXY(x, y);
            if (card.mouseIsOver()) {
                card.setXY(x, y - 50);
                card.drawCard();
                card.setMouseIsOver(true);
            } else {
                card.drawCard();
                card.setMouseIsOver(false);
            }
            x += 400;
        }
    }
}
