package events;

import main.MainSketch;
import player.Player;

public class FightEvent extends EventOption {
    public FightEvent(MainSketch p, String option) {
        super(p, option);
    }

    @Override
    public void activateOption(Player player) {
        p.startFight();
    }
}
