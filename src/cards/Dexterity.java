package cards;

import combat.Target;
import player.Player;
import processing.core.PApplet;

public class Dexterity extends Card {
    int dex;
    public Dexterity(PApplet p) {
        super(p);
        this.name = "Dexterity";
        this.flavourText = "Gain 2 dexterity";
        this.cost = 2;
        this.canTarget = Target.PLAYER;
        this.dex = 2;
    }

    @Override
    public void activateCard(Player player) {
        player.setDexterity(player.getDexterity() + dex);
    }
}

