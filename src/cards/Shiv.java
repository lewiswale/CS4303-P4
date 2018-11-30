package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class Shiv extends Card {
    int dmg;

    public Shiv(PApplet p) {
        super(p);
        this.name = "Shiv";
        this.flavourText = "Deal 3 damage";
        this.cost = 0;
        this.canTarget = Target.ENEMIES;
        this.dmg = 3;
    }

    @Override
    public void activateCard(Enemy enemy, Player player) {
        enemy.takeDamage(player.getStrength() + dmg);
    }
}
