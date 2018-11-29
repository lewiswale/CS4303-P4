package events;

import main.MainSketch;
import player.Player;

public class PuzzleEvent extends EventOption {
    public PuzzleEvent(MainSketch p, String option) {
        super(p, option);
    }

    @Override
    public void activateOption(Player player) {
        p.startPuzzle();
    }
}
