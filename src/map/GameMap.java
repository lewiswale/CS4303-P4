package map;

import player.Player;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class GameMap {
    private PApplet p;
    private Room root;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Room> toInspect = new ArrayList<>();
    private ArrayList<Integer> reachableRooms = new ArrayList<>();
    public ArrayList<Room> nextRooms = new ArrayList<>();
    private int roomSelected = -1;
    private Player player;
    private int floorNumber;

    public GameMap(PApplet p, Player player, int floorNumber) {
        this.p = p;
        this.player = player;
        this.floorNumber = floorNumber;
        root = new Room(p, "Boss", 700, 50, 1);
        buildMap(root);
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).isBottom()) {
                nextRooms.add(rooms.get(i));
            }
        }
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

            String roomName = "Fight";

            for (int i = 0; i < childCount; i++) {
                Room newChild;
                switch (room.getBranch()) {
                    case 0:
                        if (childCount == 2) {
                            newChild = new Room(p, roomName, (room.getX() + i * 200), room.getY() + 80, i);
                        } else {
                            int coinFlip = r.nextInt(2);
                            if (coinFlip == 0) {
                                newChild = new Room(p, roomName, room.getX(), room.getY() + 80, 0);
                            } else {
                                newChild = new Room(p, roomName, (room.getX() + 200), room.getY() + 80, 1);
                            }
                        }
                        break;
                    case 1:
                        if (childCount == 3) {
                            newChild = new Room(p,roomName, ((room.getX() - 200) + i*200), room.getY()+80, i);
                        } else if (childCount == 2){
                            int coinFlip = r.nextInt(2);

                            if (coinFlip == 0) {
                                newChild = new Room(p,roomName, ((room.getX() - 200) + i*200), room.getY()+80, i);
                            } else {
                                newChild = new Room(p,roomName, (room.getX() + i*200), room.getY()+80, i+1);
                            }
                        } else {
                            newChild = new Room(p, roomName, room.getX(), room.getY()+80, 1);
                        }
                        break;
                    case 2:
                        if (childCount == 2) {
                            newChild = new Room(p,roomName, ((room.getX() - 200) + i*200), room.getY()+80, i+1);
                        } else {
                            int coinFlip = r.nextInt(2);

                            if (coinFlip == 0) {
                                newChild = new Room(p, roomName, room.getX(), room.getY() + 80, 2);
                            } else {
                                newChild = new Room(p, roomName, room.getX() - 200, room.getY() + 80, 1);
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

    public String validRoomClicked() {
        for (int i = 0; i < nextRooms.size(); i++) {
            Room current = nextRooms.get(i);

            if (current.isSelected()) { //Will probably need to check reachability later
                reachableRooms = new ArrayList<>();
                nextRooms = new ArrayList<>();
                roomSelected = rooms.indexOf(current);
                reachableRooms.add(roomSelected);
                getReachableRooms(roomSelected);
                setReachability();
                return current.getName();
            }

        }

        return "";
    }

    public void setReachability() {
        for (int i = 0; i < rooms.size(); i++) {
            if (!reachableRooms.contains(i)) {
                rooms.get(i).setReachable(false);
            }
        }
    }

    public void getReachableRooms(int n) {
        Room selected = rooms.get(n);
        reachableRooms.add(n);

        ArrayList<Room> parents = selected.getParents();

        if (parents.size() != 0) {
            ArrayList<Integer> toVisit = new ArrayList<>();

            for (int i = 0; i < parents.size(); i++) {
                if (n == roomSelected) {
                    nextRooms.add(parents.get(i));
                }

                for (int j = 0; j < rooms.size(); j++) {
                    Room room = rooms.get(j);

                    if (room.getX() == parents.get(i).getX() && room.getY() == parents.get(i).getY()) {
                        toVisit.add(j);
                        break;
                    }
                }
            }

            for (int i = 0; i < toVisit.size(); i++) {
                int roomToVisit = toVisit.get(i);

                if (!reachableRooms.contains(roomToVisit)) {
                    getReachableRooms(roomToVisit);
                }
            }
        }
    }

    public void drawMap() {
        p.textSize(30);

        for (int i = 0; i < rooms.size(); i++) {
            Room currentRoom = rooms.get(i);

            if (p.mouseX > currentRoom.getX() && p.mouseX < (currentRoom.getX() + currentRoom.getWIDTH())
            && p.mouseY > (currentRoom.getY() - currentRoom.getHEIGHT()) && p.mouseY < currentRoom.getY()) {
                if (!currentRoom.isSelected()) {
                    currentRoom.setSelected(true);
                }
            } else {
                currentRoom.setSelected(false);
            }

            if (currentRoom.isSelected()) {
                p.fill(255, 255, 0);
            }

            p.text(currentRoom.getName(), currentRoom.getX(), currentRoom.getY());
            p.fill(0);

            if (roomSelected == -1) {
                if (currentRoom.isBottom()) {
                    p.noFill();
                    p.stroke(0, 255, 0);
                    p.strokeWeight(5);
                    p.rect(currentRoom.getX(), (currentRoom.getY() - currentRoom.getHEIGHT()) + 3, currentRoom.getWIDTH(),
                            currentRoom.getHEIGHT() + 5);
                    p.fill(0);
                    p.stroke(0);
                    p.strokeWeight(1);
                }
            } else {
                for (int j = 0; j < nextRooms.size(); j++) {
                    p.noFill();
                    p.stroke(0, 255, 0);
                    p.strokeWeight(5);
                    p.rect(nextRooms.get(j).getX(), (nextRooms.get(j).getY() - nextRooms.get(j).getHEIGHT()) + 3, nextRooms.get(j).getWIDTH(),
                            nextRooms.get(j).getHEIGHT() + 5);
                    p.fill(0);
                    p.stroke(0);
                    p.strokeWeight(1);
                }
            }

            if (currentRoom.getParents().size() > 0) {
                for (int j = 0; j < currentRoom.getParents().size(); j++) {
                    Room currentParent = currentRoom.getParents().get(j);

                    if (currentParent.isReachable() && currentRoom.isReachable()) {
                        p.line(currentParent.getX() + 30, currentParent.getY() + 5, currentRoom.getX() + 30,
                                currentRoom.getY() - 30);
                    }
                }
            }
        }
    }
}
