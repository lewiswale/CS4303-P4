package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class ShieldSlam extends Card {
    public ShieldSlam(PApplet p) {
        super(p);
        this.name = "Shield Slam";
        this.flavourText = "Deal damage equal\nto your block";
        this.cost = 1;
        this.canTarget = Target.ENEMIES;
    }

    @Override
    public void activateCard(Enemy enemy, Player player) {
        enemy.takeDamage(player.getBlock() + player.getStrength());
    }
}
