package cards;

import combat.Target;
import player.Player;
import processing.core.PApplet;

public class Strength extends Card {
    int strength;

    public Strength(PApplet p) {
        super(p);
        this.name = "Strength";
        this.flavourText = "Gain 2 strength";
        this.canTarget = Target.PLAYER;
        this.cost = 1;
        this.strength = 2;
    }

    @Override
    public void activateCard(Player player) {
        player.setStrength(player.getStrength() + strength);
    }
}
