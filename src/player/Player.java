package player;

import cards.Attack;
import cards.Block;
import cards.Card;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private PApplet p;
    private int health, energy, block, strength, dex;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private ArrayList<Card> discarded;
    private int cardsDrawn = 0;
    private int amountOfCards;
    private float x, y;
    private int width, height, rad;

    public Player(PApplet p) {
        this.p = p;
        this.health = 100;
        this.energy = 3;
        this.block = 0;
        this.x = 200;
        this.y = 300;
        this.width = 100;
        this.height = 100;
        this.rad = width/2;
        this.deck = new ArrayList<>();
        this.deck.add(new Attack(p));
        this.deck.add(new Attack(p));
        this.deck.add(new Attack(p));
        this.deck.add(new Attack(p));
        this.deck.add(new Attack(p));
        this.deck.add(new Block(p));
        this.deck.add(new Block(p));
        this.deck.add(new Block(p));
        this.deck.add(new Block(p));
        this.deck.add(new Block(p));
        shuffleIntoDeck(deck);
        amountOfCards = deck.size();
        this.hand = new ArrayList<>();
        this.discarded = new ArrayList<>();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void shuffleIntoDeck(ArrayList<Card> cards) {
        ArrayList<Card> shuffled = new ArrayList<>();
        Random r = new Random();
        int size = cards.size();

        for (int i = 0; i < size; i++) {
            int card = r.nextInt(cards.size());
            shuffled.add(cards.get(card));
            cards.remove(card);
        }

        this.deck = shuffled;
    }

    public void drawHand() {
        if (hand.size() > 0) {
            int size = hand.size();
            for (int i = 0; i < size; i++) {
                discard(hand.get(0));
            }
        }

        for (int i = 0; i < 5; i++) {
            if (deck.size() > 0) {
                hand.add(deck.get(0));
                deck.remove(0);
            } else {
                shuffleIntoDeck(discarded);
                hand.add(deck.get(0));
                deck.remove(0);
            }
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getDiscardedSize() {
        return discarded.size();
    }

    public int getDeckSize() {
        return deck.size();
    }

    public boolean isMouseOverPlayer() {
        float x1 = x - p.mouseX;
        x1 *= x1;
        float y1 = y - p.mouseY;
        y1 *= y1;

        return rad * rad >= x1 + y1;
    }

    public void discard(Card card) {
        discarded.add(card);
        hand.remove(card);
    }

    public int getBlock() {
        return block;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void resetDeck() {
        int size = hand.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                discarded.add(hand.get(0));
                hand.remove(0);
            }
        }

        size = discarded.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                deck.add(discarded.get(0));
                discarded.remove(0);
            }
        }

        shuffleIntoDeck(deck);
        energy = 3;
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void drawPlayer() {
        p.fill(0, 255, 0);
        if (isMouseOverPlayer()) {
            p.stroke(255, 255, 0);
        }
        p.ellipse(x, y, width, height);
        p.fill(0);
        p.stroke(0);
        p.textSize(20);
        int yOffset = (int) (y + rad + 25);
        p.text("Health: " + health, x - rad, yOffset);
        p.text("Energy: " + energy, x - rad, yOffset + 25);
        int abilityOffset = yOffset + 50;
        if (block > 0) {
            p.text("Block: " + block, x - rad, abilityOffset);
            abilityOffset += 25;
        }
        if (strength > 0) {
            p.text("Strength: +" + strength + " dmg", x - rad, abilityOffset);
            abilityOffset += 25;
        }
    }
}
