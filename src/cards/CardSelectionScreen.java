package cards;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class CardSelectionScreen {
    PApplet p;
    ArrayList<Card> cardsToShow;

    public CardSelectionScreen(PApplet p) {
        this.p = p;
        this.cardsToShow = new ArrayList<>();
    }

    public void generateRewards() {
        cardsToShow = new ArrayList<>();
        ArrayList<Integer> chosen = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < 3; i++) {
            int n = -1;
            while (!chosen.contains(n)) {
                n = r.nextInt(5);
                if (!chosen.contains(n))
                    chosen.add(n);
            }
            switch (n) {
                case 0:
                    cardsToShow.add(new Strength(p));
                    break;
                case 1:
                    cardsToShow.add(new Dexterity(p));
                    break;
                case 2:
                    cardsToShow.add(new Foresight(p));
                    break;
                case 3:
                    cardsToShow.add(new Whirlwind(p));
                    break;
                case 4:
                    cardsToShow.add(new LeechLife(p));
                    break;
            }
        }
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
