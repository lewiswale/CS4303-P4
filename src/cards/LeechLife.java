package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class LeechLife extends Card {
    int dmg;

    public LeechLife(PApplet p) {
        super(p);
        this.name = "Leech Life";
        this.flavourText = "Deal 6 damage.\nHeal for any\nunblocked damage\ndealt.";
        this.cost = 1;
        this.canTarget = Target.ENEMIES;
        this.dmg = 6;
    }

    @Override
    public void activateCard(Enemy enemy, Player player) {
        int leeched = enemy.takeDamage(dmg + player.getStrength());
        player.setHealth(player.getHealth() + leeched);
    }
}
