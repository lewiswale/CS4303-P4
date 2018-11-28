package cards;

import combat.Target;
import enemies.Enemy;
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

    public void activateCard(Enemy enemy) {
        enemy.setHealth(enemy.getHealth() - damage);
    }
}
