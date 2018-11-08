package map;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class GameMap {
    private PApplet p;
    private Room root;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Room> toInspect = new ArrayList<>();

    public GameMap(PApplet p) {
        this.p = p;
    }

    /**
     * Map tree built recursively in breadth-first order until certain depth has been reached.
     * @param room Room to have children added.
     */
    public void buildMap(Room room) {
        if (room.getY() < 750) {
            int childCount;
            Random r = new Random();

            if (rooms.size() == 0) {
                root = room;
                rooms.add(room);
                toInspect.add(room);
                childCount = 3;
            } else if (room.getY() == 690) {
                if (room.getBranch() != 1) {
                    childCount = 2;
                } else {
                    childCount = 3;
                }
            } else {
                if (room.getBranch() != 1) {
                    childCount = r.nextInt(2) + 1;
                } else {
                    childCount = r.nextInt(3) + 1;
                }
            }

            toInspect.remove(0);

            for (int i = 0; i < childCount; i++) {
                Room newChild;
                switch (room.getBranch()) {
                    case 0:
                        if (childCount == 2) {
                            newChild = new Room(p, "Rest", (room.getX() + i * 200), room.getY() + 80, i);
                        } else {
                            int coinFlip = r.nextInt(2);
                            if (coinFlip == 0) {
                                newChild = new Room(p, "Rest", room.getX(), room.getY() + 80, 0);
                            } else {
                                newChild = new Room(p, "Rest", (room.getX() + 200), room.getY() + 80, 1);
                            }
                        }
                        break;
                    case 1:
                        if (childCount == 3) {
                            newChild = new Room(p,"Rest", ((room.getX() - 200) + i*200), room.getY()+80, i);
                        } else if (childCount == 2){
                            int coinFlip = r.nextInt(2);

                            if (coinFlip == 0) {
                                newChild = new Room(p,"Rest", ((room.getX() - 200) + i*200), room.getY()+80, i);
                            } else {
                                newChild = new Room(p,"Rest", (room.getX() + i*200), room.getY()+80, i+1);
                            }
                        } else {
                            newChild = new Room(p, "Rest", room.getX(), room.getY()+80, 1);
                        }
                        break;
                    case 2:
                        if (childCount == 2) {
                            newChild = new Room(p,"Rest", ((room.getX() - 200) + i*200), room.getY()+80, i+1);
                        } else {
                            int coinFlip = r.nextInt(2);

                            if (coinFlip == 0) {
                                newChild = new Room(p, "Rest", room.getX(), room.getY() + 80, 2);
                            } else {
                                newChild = new Room(p, "Rest", room.getX() - 200, room.getY() + 80, 1);
                            }
                        }
                        break;
                    default:
                        newChild = null;
                }

                int match = roomExists(newChild);
                if (match < 0) {
                    newChild.addParent(room);
                    room.getChildren().add(newChild);
                    rooms.add(newChild);
                    toInspect.add(newChild);
                } else {
                    rooms.get(match).addParent(room);
                    room.getChildren().add(rooms.get(match));
                }
            }

            buildMap(toInspect.get(0));
        }
    }

    public int roomExists(Room room) {
        for (int i = 0; i < rooms.size(); i++) {
            Room current = rooms.get(i);
            if (current.getX() == room.getX() && current.getY() == room.getY()) {
                return i;
            }
        }

        return -1;
    }

    public void drawMap() {
        p.textSize(30);

        for (int i = 0; i < rooms.size(); i++) {
            Room currentRoom = rooms.get(i);

            p.text(currentRoom.getName(), currentRoom.getX(), currentRoom.getY());
            if (currentRoom.getParents().size() > 0) {
                for (int j = 0; j < currentRoom.getParents().size(); j++) {
                    Room currentParent = currentRoom.getParents().get(j);

                    p.line(currentParent.getX() + 30, currentParent.getY() + 5, currentRoom.getX() + 30,
                            currentRoom.getY() - 30);
                }
            }
        }
    }
}
