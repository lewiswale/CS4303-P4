import cards.Card;
import cards.CardSelectionScreen;
import combat.CombatEngine;
import events.Event;
import events.EventManager;
import map.GameMap;
import player.Player;
import processing.core.PApplet;
import puzzles.PuzzleMaker;

public class MainSketch extends PApplet {
    private GameMap map;
    private boolean[] keys = new boolean[128];
    private boolean showingMap, roomSelected, fightSelected, cardClicked, showRewardScreen, showingPuzzle, showingEvent;
    private Player player;
    private CombatEngine ce;
    private CardSelectionScreen rew;
    private PuzzleMaker pm;
    private EventManager em;

    public void settings() {
        size(1600, 900);
        showingMap = true;
        roomSelected = false;
        player = new Player(this);
        rew = new CardSelectionScreen(this);
        ce = new CombatEngine(this, player, rew);
        pm = new PuzzleMaker(this);
        em = new EventManager(this);
        map = new GameMap(this, player, 1);
    }

    public void draw() {
        background(128);
        if (!roomSelected) {
            map.drawMap();
        } else {
            if (fightSelected) {
                if (!ce.isFinished())
                    ce.displayCombat();
                else {
                    showRewardScreen = true;
                    fightSelected = false;
                }
            } else if (showRewardScreen) {
                rew.displayRewardScreen();
            } else if (showingPuzzle) {
                pm.drawPuzzle();
            } else if (showingEvent) {
                em.displayEvent();
            }
        }
    }

    public void keyPressed() {
        keys[key] = true;
        if (keys['r']) {
            showingMap = true;
            roomSelected = false;
            fightSelected = false;
            map = new GameMap(this, player, 1);
        }
    }

    public void keyReleased() {
        keys[key] = false;
    }

    public void mousePressed() {
        if (showingMap) {
            String room = map.validRoomClicked();
            if (room.equals("Fight")) {
                showingMap = false;
                roomSelected = true;
                fightSelected = true;
                player.reset();
                ce = new CombatEngine(this, player, rew);
            } else if (room.equals("Puzzle")) {
                showingMap = false;
                roomSelected = true;
                showingPuzzle = true;
                pm.makePuzzle();
            } else if (room.equals("Event")) {
                showingMap = false;
                roomSelected = true;
                showingEvent = true;
                em.makeEvent();
            }
        } else {
            if (fightSelected) {
                cardClicked = ce.cardSelected();
                if (!cardClicked) {
                    ce.endPressed();
                }

            } else if (showRewardScreen) {
                Card cardToAdd = rew.getSelection();
                if (cardToAdd != null) {
                    player.addCardToDeck(cardToAdd);
                    showRewardScreen = false;
                    fightSelected = false;
                    roomSelected = false;
                    showingMap = true;
                }
            } else if (showingPuzzle) {
                if (pm.checkMove()) {
                    if (pm.isWon() || pm.isLost()) {
                        showingPuzzle = false;
                        roomSelected = false;
                        showingMap = true;
                    }
                }
            }
        }
    }

    public void mouseReleased() {
        if (fightSelected) {
            if (cardClicked) {
                ce.getTarget();
                cardClicked = false;
            }
        }
    }

    public static void main(String[] args) {
        String[] pArgs = new String[] {"MainSketch"};
        MainSketch mainSketch = new MainSketch();
        PApplet.runSketch(pArgs, mainSketch);
    }
}
