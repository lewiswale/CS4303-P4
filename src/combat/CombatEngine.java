package combat;

import cards.Card;
import enemies.Enemy;
import enemies.SmallSquare;
import player.Player;
import processing.core.PApplet;

import java.util.ArrayList;

public class CombatEngine {
    private PApplet p;
    private Player player;
    private ArrayList<Enemy> enemies;
    private boolean cardClicked;
    private Card selectedCard;


    public CombatEngine(PApplet p, Player player) {
        this.p = p;
        this.player = player;
        this.enemies = new ArrayList<>();
        this.enemies.add(new SmallSquare(p, 800, 250));
    }

    public boolean cardSelected() {
        for (Card card :
                player.getDeck()) {
            if (card.isMouseIsOver()) {
                cardClicked = true;
                card.setDoNotMove(true);
                selectedCard = card;
                return true;
            }
        }

        return false;
    }

    public void released() {
        for (Card card :
                player.getDeck()) {
            if (card.getX() == selectedCard.getX()) {
                card.setDoNotMove(false);
                selectedCard = null;
                cardClicked = false;
                break;
            }
        }
    }

    public void displayCombat() {
        player.drawPlayer();
        for (Enemy enemy :
                enemies) {
            enemy.drawEnemy();
        }

        int x = 300;
        int y = 750;
        for (Card card :
                player.getDeck()) {
            if (p.mouseX > x && p.mouseX < x + card.getCARD_WIDTH() && p.mouseY > y && p.mouseY < y + card.getCARD_HEIGHT()) {
                card.setXY(x, y - 150);
                card.drawCard();
                card.setMouseIsOver(true);
            } else {
                if (!card.isDoNotMove())
                    card.setXY(x, y);
                card.drawCard();
                card.setMouseIsOver(false);
            }
            x += 200;
        }

        if (cardClicked) {
            float cardMidX = selectedCard.getX() + selectedCard.getCARD_WIDTH()/2;
            float cardMidY = selectedCard.getY() + selectedCard.getCARD_HEIGHT()/2;
            p.fill(0);
            p.line(cardMidX, cardMidY, p.mouseX, p.mouseY);
        }
    }
}