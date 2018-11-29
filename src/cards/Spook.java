package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class Spook extends Card {
    int debuff;
    public Spook(PApplet p) {
        super(p);
        this.name = "Spook";
        this.flavourText = "Give an enemy -2\nstrength this turn.";
        this.cost = 1;
        this.canTarget = Target.ENEMIES;
        this.debuff = -2;
    }

    @Override
    public void activateCard(Enemy enemy, Player player) {
        enemy.setTempDebuff(debuff);
    }
}
