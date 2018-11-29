package cards;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class CardSelectionScreen {
    PApplet p;
    ArrayList<Card> cardsToShow;
    final int amountOfUniqueCards = 9;

    public CardSelectionScreen(PApplet p) {
        this.p = p;
        this.cardsToShow = new ArrayList<>();
    }

    public void generateRewards() {
        cardsToShow = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        Random r = new Random();

        while (cardsToShow.size() < 3) {
            int n = r.nextInt(amountOfUniqueCards);
            Card toAdd = null;

            switch (n) {
                case 0:
                    toAdd = new Strength(p);
                    break;
                case 1:
                    toAdd = new Dexterity(p);
                    break;
                case 2:
                    toAdd = new Whirlwind(p);
                    break;
                case 3:
                    toAdd = new LeechLife(p);
                    break;
                case 4:
                    toAdd = new Foresight(p);
                    break;
                case 5:
                    toAdd = new PumpedUp(p);
                    break;
                case 6:
                    toAdd = new ShieldShatter(p);
                    break;
                case 7:
                    toAdd = new SlashAndDash(p);
                    break;
                case 8:
                    toAdd = new Spook(p);
                    break;
            }

            if (!names.contains(toAdd.getName())) {
                names.add(toAdd.getName());
                cardsToShow.add(toAdd);
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
