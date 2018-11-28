import combat.CombatEngine;
import map.GameMap;
import player.Player;
import processing.core.PApplet;

public class MainSketch extends PApplet {
    private GameMap map;
    private boolean[] keys = new boolean[128];
    private boolean showingMap, roomSelected, fightSelected, cardClicked;
    private Player player;
    private CombatEngine ce;

    public void settings() {
        size(1600, 900);
        showingMap = true;
        roomSelected = false;
        player = new Player(this);
        ce = new CombatEngine(this, player);
        map = new GameMap(this, player, 1);
    }

    public void draw() {
        background(128);
        if (!roomSelected) {
            map.drawMap();
        } else {
            if (fightSelected) {
                ce.displayCombat();
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
            }
        } else {
            if (fightSelected) {
                cardClicked = ce.cardSelected();
            }
        }
    }

    public void mouseReleased() {
        if (fightSelected) {
            if (cardClicked) {
                ce.released();
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
