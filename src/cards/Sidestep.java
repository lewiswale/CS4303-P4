package cards;

import combat.Target;
import player.Player;
import processing.core.PApplet;

public class Sidestep extends Card {
    int block;
    public Sidestep(PApplet p) {
        super(p);
        this.name = "Sidestep";
        this.flavourText = "Gain 3 block";
        this.cost = 0;
        this.canTarget = Target.PLAYER;
        this.block = 3;
    }

    @Override
    public void activateCard(Player player) {
        player.setBlock(player.getBlock() + player.getDexterity() + block);
    }
}
