package player;

import cards.Attack;
import cards.Card;
import processing.core.PApplet;

import java.util.ArrayList;

public class Player {
    private PApplet p;
    private int health, energy;
    private ArrayList<Card> deck;

    public Player(PApplet p) {
        this.p = p;
        this.health = 100;
        this.energy = 3;
        this.deck = new ArrayList<>();
        this.deck.add(new Attack(p));
        this.deck.add(new Attack(p));
        this.deck.add(new Attack(p));
        this.deck.add(new Attack(p));
        this.deck.add(new Attack(p));
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void drawPlayer() {
        p.fill(0, 255, 0);
        p.ellipse(200, 300, 100, 100);
        p.fill(0);
    }
}
