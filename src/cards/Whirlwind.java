package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

import java.util.ArrayList;

public class Whirlwind extends Card {
    int dmg;

    public Whirlwind(PApplet p) {
        super(p);
        this.name = "Whirlwind";
        this.flavourText = "Deal 4 damage to\nALL enemies.";
        this.cost = 1;
        this.canTarget = Target.ALL_ENEMIES;
        this.dmg = 4;
    }

    @Override
    public void activateCard(ArrayList<Enemy> enemies, Player player) {
        int damageToDeal = dmg + player.getStrength();
        for (Enemy enemy: enemies) {
            enemy.takeDamage(damageToDeal);
        }
    }
}
