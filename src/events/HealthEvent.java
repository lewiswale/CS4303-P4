package events;

import main.MainSketch;
import player.Player;

public class HealthEvent extends EventOption {
    int healthMod;
    public HealthEvent(MainSketch p, String option, int healthMod) {
        super(p, option);
        this.healthMod = healthMod;
    }

    @Override
    public void activateOption(Player player) {
        player.setHealth(player.getHealth() + healthMod);
        if (player.isDead()) {
            p.gameOver(true);
        }
    }
}
