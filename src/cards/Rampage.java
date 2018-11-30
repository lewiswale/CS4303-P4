package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class Rampage extends Card {
    int eDmg, pDmg;
    public Rampage(PApplet p) {
        super(p);
        this.name = "Rampage";
        this.flavourText = "Deal 11 damage\nto an enemy.\nDeal 5 damage\nto yourself.";
        this.cost = 1;
        this.canTarget = Target.ENEMIES;
        this.eDmg = 11;
        this.pDmg = 5;
    }

    @Override
    public void activateCard(Enemy enemy, Player player) {
        enemy.takeDamage(player.getStrength() + eDmg);
        player.takeDamage(player.getStrength() + pDmg);
    }
}
