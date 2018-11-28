package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class Attack extends Card {
    int damage;

    public Attack(PApplet p) {
        super(p);
        this.name = "Attack";
        this.flavourText = "Deal 6 damage";
        this.cost = 1;
        this.canTarget = Target.ENEMIES;
        this.damage = 6;
    }

    public void activateCard(Enemy enemy, Player player) {
        int damageToDeal = damage + player.getStrength();

        if (enemy.getBlock() > 0) {
            if (damageToDeal > enemy.getBlock()) {
                damageToDeal -= enemy.getBlock();
                enemy.setBlock(0);
                enemy.setHealth(enemy.getHealth() - damageToDeal);
            } else {
                enemy.setBlock(enemy.getBlock() - damageToDeal);
            }
        } else {
            enemy.setHealth(enemy.getHealth() - damageToDeal);
        }
    }
}
