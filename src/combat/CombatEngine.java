package combat;

import enemies.Enemy;
import enemies.SmallSquare;
import player.Player;
import processing.core.PApplet;

import java.util.ArrayList;

public class CombatEngine {
    private PApplet p;
    private Player player;
    private ArrayList<Enemy> enemies;


    public CombatEngine(PApplet p, Player player) {
        this.p = p;
        this.player = player;
        this.enemies = new ArrayList<>();
        this.enemies.add(new SmallSquare(p, 800, 250));
    }

    public void displayCombat() {
        player.drawPlayer();
        for (Enemy enemy :
                enemies) {
            enemy.drawEnemy();
        }
    }
}
