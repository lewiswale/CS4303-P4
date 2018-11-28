package combat;

import cards.Card;
import cards.CardSelectionScreen;
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
    private EndTurnButton endTurnButton;
    private CardSelectionScreen rew;

    public CombatEngine(PApplet p, Player player, CardSelectionScreen rew) {
        this.p = p;
        this.player = player;
        player.drawHand();
        this.enemies = new ArrayList<>();
        this.enemies.add(new SmallSquare(p, 800, 250));
        this.enemies.add(new SmallSquare(p, 1300, 400));
        this.endTurnButton = new EndTurnButton(p);
        this.rew = rew;
    }

    public boolean cardSelected() {
        for (Card card : player.getHand()) {
            if (card.isMouseIsOver()) {
                cardClicked = true;
                card.setDoNotMove(true);
                selectedCard = card;
                return true;
            }
        }

        return false;
    }

    public void endPressed() {
        if (endTurnButton.isMouseOver()) {
            for (Enemy enemy: enemies) {
                if (!enemy.isDead()) {
                    enemy.doNextTurn(player);
                    enemy.chooseNextTurn();
                }
            }
            player.setEnergy(3);
            player.drawHand();
        }
    }

    public void getTarget() {
        switch (selectedCard.getCanTarget()) {
            case ENEMIES:
                if (checkEnemies(false)) {
                    released(true);
                } else {
                    released(false);
                }
                break;
            case PLAYER:
                if (checkPlayer()) {
                    released(true);
                } else {
                    released(false);
                }
                break;
            case ALL_ENEMIES:
                if (checkEnemies(true)) {
                    released(true);
                } else {
                    released(false);
                }
                break;
        }
    }

    public boolean checkEnemies(boolean all) {
        for (Enemy enemy : enemies) {
            if (enemy.isMouseOver()) {
                if (playerHasEnergy()) {
                    if (!all) {
                        selectedCard.activateCard(enemy, player);
                        return true;
                    } else {
                        selectedCard.activateCard(enemies, player);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkPlayer() {
        if (player.isMouseOverPlayer()) {
            if (playerHasEnergy()) {
                selectedCard.activateCard(player);
                return true;
            }
        }
        return false;
    }

    public boolean playerHasEnergy() {
        if (player.getEnergy() >= selectedCard.getCost()) {
            player.setEnergy(player.getEnergy() - selectedCard.getCost());
            return true;
        }
        return false;
    }

    public void released(boolean validTarget) {
        for (Card card : player.getHand()) {
            if (card.getX() == selectedCard.getX()) {
                card.setDoNotMove(false);
                if (validTarget) {
                    player.discard(card);
                }
                selectedCard = null;
                cardClicked = false;
                break;
            }
        }
    }

    public boolean isFinished() {
        if (player.isDead()) {
            return true;
        } else {
            for (Enemy enemy : enemies) {
                if (!enemy.isDead())
                        return false;
            }
            rew.generateRewards();
            return true;
        }
    }

    public void displayCombat() {
        player.drawPlayer();
        for (Enemy enemy : enemies) {
            if (!enemy.isDead())
                enemy.drawEnemy();
        }

        int x = 300;
        int y = 750;
        boolean onCard = false;
        Card cardOnTop = null;
        for (Card card : player.getHand()) {
            if (card.mouseIsOver() && !onCard) {
                card.setXY(x, y - 150);
                card.setMouseIsOver(true);
                cardOnTop = card;
                onCard = true;
            } else {
                if (!card.isDoNotMove())
                    card.setXY(x, y);
                card.drawCard();
                card.setMouseIsOver(false);
            }
            x += 1000/player.getHand().size();
        }

        if (cardOnTop != null)
            cardOnTop.drawCard();

        if (cardClicked) {
            float cardMidX = selectedCard.getX() + selectedCard.getCARD_WIDTH()/2;
            float cardMidY = selectedCard.getY() + selectedCard.getCARD_HEIGHT()/2;
            p.fill(0);
            p.line(cardMidX, cardMidY, p.mouseX, p.mouseY);
        }

        p.textSize(20);
        p.text("Cards left:\n" + player.getDeckSize(), 10, 700);
        p.text("Discarded cards:\n" + player.getDiscardedSize(), 1400, 700);
        endTurnButton.drawEndTurn();
    }
}
