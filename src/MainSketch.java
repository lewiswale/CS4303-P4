import map.GameMap;
import processing.core.PApplet;

public class MainSketch extends PApplet {
    private GameMap map;
    private boolean[] keys = new boolean[128];
    private boolean showingMap, roomSelected;

    public void settings() {
        size(1600, 900);
        showingMap = true;
        roomSelected = false;
        map = new GameMap(this);
    }

    public void draw() {
        background(128);
        if (!roomSelected) {
            map.drawMap();
        }
    }

    public void keyPressed() {
        keys[key] = true;
        if (keys['r']) {
            showingMap = true;
            roomSelected = false;
            map = new GameMap(this);
        }
    }

    public void keyReleased() {
        keys[key] = false;
    }

    public void mouseClicked() {
        if (showingMap) {
            map.validRoomClicked();
        }
    }

    public static void main(String[] args) {
        String[] pArgs = new String[] {"MainSketch"};
        MainSketch mainSketch = new MainSketch();
        PApplet.runSketch(pArgs, mainSketch);
    }
}
