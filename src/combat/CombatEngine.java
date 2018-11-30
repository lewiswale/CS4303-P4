package combat;

import cards.Card;
import cards.CardSelectionScreen;
import enemies.*;
import player.Player;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class CombatEngine {
    private PApplet p;
    private Player player;
    private ArrayList<Enemy> enemies;
    private boolean cardClicked;
    private Card selectedCard;
    private EndTurnButton endTurnButton;
    private CardSelectionScreen rew;
    private int floorNumber;
    private boolean isBoss;

    public CombatEngine(PApplet p, Player player, CardSelectionScreen rew, boolean isBoss, int floorNumber) {
        this.p = p;
        this.player = player;
        this.floorNumber = floorNumber;
        this.isBoss = isBoss;
        player.drawHand();
        this.enemies = new ArrayList<>();
        if (!isBoss) {
            this.enemies = generateEnemies();
        } else {
            switch (floorNumber) {
                case 1:
                    this.enemies.add(new FirstFloorBoss(p, 1000, 400));
                    break;
                case 2:
                    this.enemies.add(new SecondFloorBoss(p, 1000, 200));
                    break;
                case 3:
                    this.enemies.add(new ThirdFloorBoss(p, 1000, 300));
                    break;
            }
        }

        for (Enemy enemy : enemies) {
            enemy.chooseNextTurn();
        }

        this.endTurnButton = new EndTurnButton(p);
        this.rew = rew;
    }

    public ArrayList<Enemy> generateEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        Random r = new Random();
        int n = r.nextInt(4);
//        int n = 3;
        switch (n) {
            case 0:
                enemies.add(new SmallSquare(p, 800, 250));
                enemies.add(new SmallSquare(p, 1100, 350));
                if (floorNumber > 1) {
                    enemies.add(new SmallSquare(p, 1300, 300));
                }
                if (floorNumber > 2) {
                    enemies.add(new TallBoy(p, 900, 250));
                }
                return enemies;
            case 1:
                enemies.add(new TallBoy(p, 1000, 260));
                if (floorNumber > 1) {
                    enemies.add(new SharpBoy(p, 1300, 250));
                }
                return enemies;
            case 2:
                enemies.add(new SmallSquare(p, 800, 400));
                enemies.add(new Roller(p, 1100, 300));
                if (floorNumber > 2) {
                    enemies.add(new Roller(p, 1300, 350));
                }
                return enemies;
            case 3:
                enemies.add(new SharpBoy(p, 900, 300));
                if (floorNumber > 1) {
                    enemies.add(new TallBoy(p, 1100, 350));
                }
                if (floorNumber > 2) {
                    enemies.add(new Roller(p, 1300, 320));
                }
                return enemies;
        }

        return null;
    }

    public boolean isBoss() {
        return isBoss;
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
                    enemy.setBlock(0);
                    enemy.doNextTurn(player);
                    enemy.setTempDebuff(0);
                    enemy.chooseNextTurn();
                }
            }
            player.setEnergy(3);
            player.setBlock(0);
            player.setTempStrength(0);
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
                    if (card.isPower()) {
                        player.addPower(card);
                    } else {
                        player.discard(card);
                    }
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
