package cards;

import combat.Target;
import player.Player;
import processing.core.PApplet;

public class Strength extends Card {
    int strength;

    public Strength(PApplet p) {
        super(p);
        this.name = "Strength";
        this.flavourText = "POWER\nGain 2 strength\nfor the rest of this\ncombat";
        this.canTarget = Target.PLAYER;
        this.cost = 2;
        this.isPower = true;
        this.strength = 2;
    }

    @Override
    public void activateCard(Player player) {
        player.setStrength(player.getStrength() + strength);
    }
}
