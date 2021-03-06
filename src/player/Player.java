package player;

import cards.*;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private PApplet p;
    private int health, energy, block, permStr, strength, permDex, dexterity, tempStrength;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private ArrayList<Card> discarded;
    private ArrayList<Card> powers;
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
        this.powers = new ArrayList<>();
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

    public void addPower(Card card) {
        powers.add(card);
        hand.remove(card);
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

    public void setTempStrength(int str) {
        this.tempStrength = str;
    }

    public void setPermDex(int permDex) {
        this.permDex = permDex;
    }

    public int getPermDex() {
        return permDex;
    }

    public int getPermStr() {
        return permStr;
    }

    public void setPermStr(int str) {this.permStr = str;}

    public boolean isDead() {
        return health <= 0;
    }

    public void takeDamage(int dmg) {
        if (block > 0) {
            block -= dmg;
            if (block < 0) {
                dmg = block * -1;
            } else {
                dmg = 0;
            }
        }

        if (dmg > 0) {
            health -= dmg;
        }
    }

    public void getDebuffed(int n) {
        strength -= n;
    }

    public int getStrength() {
        return strength + tempStrength + permStr;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getDexterity() {
        return dexterity + permDex;
    }

    public void reset() {
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

        size = powers.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                deck.add(powers.get(0));
                powers.remove(0);
            }
        }

        shuffleIntoDeck(deck);
        energy = 3;
        strength = 0;
        tempStrength = 0;
        dexterity = 0;
        block = 0;
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void drawCard() {
        if (deck.size() ==0) {
            shuffleIntoDeck(discarded);
        }
        hand.add(deck.get(0));
        deck.remove(0);
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
        if (strength + tempStrength + permStr > 0) {
            p.text("Strength: +" + (strength + tempStrength + permStr) + " dmg", x - rad, abilityOffset);
            abilityOffset += 25;
        } else if (strength + tempStrength + permStr < 0) {
            p.text("Strength: " + (strength + tempStrength + permStr) + " dmg", x - rad, abilityOffset);
            abilityOffset += 25;
        }
        if (dexterity + permDex > 0) {
            p.text("Dexterity: +" + (dexterity + permDex) + " blk", x - rad, abilityOffset);
            abilityOffset += 25;
        } else if (dexterity + permDex < 0) {
            p.text("Dexterity: " + (dexterity + permDex) + " blk", x - rad, abilityOffset);
            abilityOffset += 25;
        }
    }
}
