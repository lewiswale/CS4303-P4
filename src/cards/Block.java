package cards;

import combat.Target;
import player.Player;
import processing.core.PApplet;

public class Block extends Card {
    int block;

    public Block(PApplet p) {
        super(p);
        this.name = "Block";
        this.flavourText = "Gain 6 block";
        this.cost = 1;
        this.canTarget = Target.PLAYER;
        this.block = 6;
    }

    public void activateCard(Player player) {
        player.setBlock(player.getBlock() + block);
    }
}
