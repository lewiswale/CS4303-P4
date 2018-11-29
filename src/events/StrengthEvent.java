package events;

import main.MainSketch;
import player.Player;

public class StrengthEvent extends EventOption {
    int str;
    public StrengthEvent(MainSketch p, String option, int str) {
        super(p, option);
        this.str = str;
    }

    @Override
    public void activateOption(Player player) {
        player.setPermStr(player.getPermStr() + str);
    }
}
