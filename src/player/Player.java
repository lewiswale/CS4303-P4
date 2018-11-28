package player;

import cards.Attack;
import cards.Block;
import cards.Card;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private PApplet p;
    private int health, energy, block;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private ArrayList<Card> discarded;
    private int cardsDrawn = 0;
    private int amountOfCards;
    private float x, y;
    private int width, height;

    public Player(PApplet p) {
        this.p = p;
        this.health = 100;
        this.energy = 3;
        this.block = 0;
        this.x = 200;
        this.y = 300;
        this.width = 100;
        this.height = 100;
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

    public boolean isMouseOverPlayer() {
        float x1 = x - p.mouseX;
        x1 *= x1;
        float y1 = y - p.mouseY;
        y1 *= y1;

        return (width/2) * (width/2) >= x1 + y1;
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

    public void setBlock(int block) {
        this.block = block;
    }

    public void drawPlayer() {
        p.fill(0, 255, 0);
        if (isMouseOverPlayer()) {
            p.stroke(255, 255, 0);
        }
        p.ellipse(x, y, width, height);
        p.fill(0);
        p.stroke(0);
    }
}
