package events;

import main.MainSketch;
import player.Player;

public class DexterityEvent extends EventOption {
    int dex;
    public DexterityEvent(MainSketch p, String option, int dex) {
        super(p, option);
        this.dex = dex;
    }

    @Override
    public void activateOption(Player player) {
        player.setPermDex(player.getPermDex() + dex);
    }
}
