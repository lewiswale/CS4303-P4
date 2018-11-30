package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

import java.util.ArrayList;

public class DragonDance extends Card {
    int dmg;
    public DragonDance(PApplet p) {
        super(p);
        this.name = "Dragon\nDance";
        this.flavourText = "Deal 10 damage\nto all enemies";
        this.cost = 3;
        this.canTarget = Target.ALL_ENEMIES;
        this.dmg = 10;
    }

    @Override
    public void activateCard(ArrayList<Enemy> enemies, Player player) {
        for (Enemy enemy: enemies) {
            enemy.takeDamage(dmg + player.getStrength());
        }
    }
}
