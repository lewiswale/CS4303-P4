package cards;

import combat.Target;
import player.Player;
import processing.core.PApplet;

public class Foresight extends Card {
    public Foresight(PApplet p) {
        super(p);
        this.name = "Foresight";
        this.flavourText = "Draw 2 cards";
        this.cost = 1;
        this.canTarget = Target.PLAYER;
    }

    @Override
    public void activateCard(Player player) {
        player.drawCard();
        player.drawCard();
    }
}
