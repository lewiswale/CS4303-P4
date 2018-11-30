package cards;

import combat.Target;
import player.Player;
import processing.core.PApplet;

public class SteelPlating extends Card {
    int block;
    public SteelPlating(PApplet p) {
        super(p);
        this.name = "Steel\nPlating";
        this.flavourText = "Gain 10 block";
        this.cost = 1;
        this.canTarget = Target.PLAYER;
        this.block = 10;
    }

    @Override
    public void activateCard(Player player) {
        player.setBlock(player.getBlock() + player.getDexterity() + block);
    }
}
