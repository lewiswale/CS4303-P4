package events;

import cards.Card;
import main.MainSketch;
import player.Player;

public class RemoveCardEvent extends EventOption {
    Card card;
    public RemoveCardEvent(MainSketch p, String option, Card card) {
        super(p, option);
        this.card = card;
    }

    @Override
    public void activateOption(Player player) {
        player.reset();
        player.getDeck().remove(card);
    }
}
