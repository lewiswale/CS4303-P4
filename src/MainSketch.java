import map.GameMap;
import map.Room;
import processing.core.PApplet;

public class MainSketch extends PApplet {
    private GameMap map;

    public void settings() {
        size(1600, 900);
        map = new GameMap(this);
        Room boss = new Room(this, "Boss", 700, 50, 1);
        map.buildMap(boss);
    }

    public void draw() {
        background(128);
        map.drawMap();
    }

    public static void main(String[] args) {
        String[] pArgs = new String[] {"MainSketch"};
        MainSketch mainSketch = new MainSketch();
        PApplet.runSketch(pArgs, mainSketch);
    }
}
