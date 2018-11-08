import map.GameMap;
import map.Room;
import processing.core.PApplet;

public class MainSketch extends PApplet {
    private GameMap map;
    private Room root;
    private boolean[] keys = new boolean[128];

    public void settings() {
        size(1600, 900);
        map = new GameMap(this);
        root = new Room(this, "Boss", 700, 50, 1);
        map.buildMap(root);
    }

    public void draw() {
        background(128);
        map.drawMap();
    }

    public void keyPressed() {
        keys[key] = true;
        if (keys['r']) {
            map = new GameMap(this);
            map.buildMap(root);
        }
    }

    public void keyReleased() {
        keys[key] = false;
    }

    public static void main(String[] args) {
        String[] pArgs = new String[] {"MainSketch"};
        MainSketch mainSketch = new MainSketch();
        PApplet.runSketch(pArgs, mainSketch);
    }
}
