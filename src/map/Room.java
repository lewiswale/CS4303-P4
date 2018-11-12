package map;

import processing.core.PApplet;

import java.util.ArrayList;

public class Room {
    private PApplet p;
    private String name;
    private ArrayList<Room> children = new ArrayList<>();
    private double x, y;
    private int branch;
    private ArrayList<Room> parents = new ArrayList<>();
    private final int WIDTH = 75;
    private final int HEIGHT = 30;
    private boolean selected = false;
    private boolean isReachable = true;

    public Room(PApplet p, String name, double x, double y, int branch) {
        this.p = p;
        this.name = name;
        this.x = x;
        this.y = y;
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Room> getChildren() {
        return children;
    }

    public float getX() {
        return (float) x;
    }

    public float getY() {
        return (float) y;
    }

    public int getBranch() {
        return branch;
    }

    public void addParent(Room parent) {
        parents.add(parent);
    }

    public ArrayList<Room> getParents() {
        return parents;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isBottom() {
        return children.size() == 0;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean reachable) {
        isReachable = reachable;
    }
}
