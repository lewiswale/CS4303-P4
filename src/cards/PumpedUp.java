package cards;

import combat.Target;
import player.Player;
import processing.core.PApplet;

public class PumpedUp extends Card {
    int str;

    public PumpedUp(PApplet p) {
        super(p);
        this.name = "Pumped Up";
        this.flavourText = "Gain 4 strength\nthis turn";
        this.cost = 1;
        this.canTarget = Target.PLAYER;
        this.str = 4;
    }

    @Override
    public void activateCard(Player player) {
        player.setTempStrength(str);
    }
}
