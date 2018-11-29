package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class SlashAndDash extends Card {
    int dmg;
    int block;
    public SlashAndDash(PApplet p) {
        super(p);
        this.name = "Slash and\nDash";
        this.flavourText = "Deal 6 damage.\nGain 6 block.";
        this.cost = 1;
        this.canTarget = Target.ENEMIES;
        this.dmg = 6;
        this.block = 6;
    }

    @Override
    public void activateCard(Enemy enemy, Player player) {
        enemy.takeDamage(player.getStrength() + dmg);
        player.setBlock(player.getBlock() + block);
    }
}
