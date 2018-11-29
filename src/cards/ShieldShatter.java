package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class ShieldShatter extends Card {
    int dmg;
    public ShieldShatter(PApplet p) {
        super(p);
        this.name = "Shield\nShatter";
        this.flavourText = "Remove all of an\nenemies block.\nDeal 6 damage.";
        this.cost = 1;
        this.canTarget = Target.ENEMIES;
        this.dmg = 6;
    }

    @Override
    public void activateCard(Enemy enemy, Player player) {
        enemy.setBlock(0);
        enemy.takeDamage(dmg + player.getStrength());
    }
}
