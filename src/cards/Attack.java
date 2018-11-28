package cards;

import processing.core.PApplet;

public class Attack extends Card {
    public Attack(PApplet p) {
        super(p);
        this.name = "Attack";
        this.flavourText = "Deal 6 damage";
        this.cost = 1;
    }

//    @Override
//    public void drawCard(int x, int y) {
//
//    }
}
