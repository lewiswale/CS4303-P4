package main;

import cards.Card;
import cards.CardSelectionScreen;
import combat.CombatEngine;
import events.EventManager;
import map.GameMap;
import player.Player;
import processing.core.PApplet;
import puzzles.PuzzleMaker;

public class MainSketch extends PApplet {
    private GameMap map;
    private boolean[] keys = new boolean[128];
    private boolean showingMap, roomSelected, fightSelected, cardClicked, showRewardScreen, showingPuzzle, showingEvent;
    private boolean gameOver = false;
    private Player player;
    private CombatEngine ce;
    private CardSelectionScreen rew;
    private PuzzleMaker pm;
    private EventManager em;
    private int floorNumber;
    private boolean gameWon = false;

    public void settings() {
        size(1600, 900);
        showingMap = true;
        roomSelected = false;
        floorNumber = 1;
        player = new Player(this);
        rew = new CardSelectionScreen(this);
        ce = new CombatEngine(this, player, rew, false, floorNumber);
        pm = new PuzzleMaker(this, floorNumber);
        em = new EventManager(this, player);
        map = new GameMap(this, player, floorNumber);
    }

    public void draw() {
        background(128);
        if (!gameOver && !gameWon) {
            if (!roomSelected) {
                map.drawMap();
            } else {
                if (fightSelected) {
                    if (!ce.isFinished())
                        ce.displayCombat();
                    else {
                        if (player.isDead()) {
                            gameOver = true;
                        } else {
                            if (ce.isBoss()) {
                                floorNumber++;
                                if (floorNumber == 4) {
                                    gameWon = true;
                                } else {
                                    map = new GameMap(this, player, floorNumber);
                                    pm = new PuzzleMaker(this, floorNumber);
                                    if (player.getHealth() < 100) {
                                        player.setHealth(100);
                                    }
                                }
                            }
                            showRewardScreen = true;
                            fightSelected = false;
                        }
                    }
                } else if (showRewardScreen) {
                    rew.displayRewardScreen();
                } else if (showingPuzzle) {
                    pm.drawPuzzle();
                } else if (showingEvent) {
                    em.displayEvent();
                }
            }
        } else if (gameOver) {
            fill(255, 0, 0);
            textSize(50);
            text("Game over", 700, 400);
        } else {
            fill(0, 255, 0);
            textSize(50);
            text("You won!", 700, 400);
        }
    }

    public void keyPressed() {
        keys[key] = true;
        if (keys['r']) {
            showingMap = true;
            roomSelected = false;
            fightSelected = false;
            map = new GameMap(this, player, floorNumber);
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
                ce = new CombatEngine(this, player, rew, false, floorNumber);
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
            } else if (room.equals("Boss")) {
                showingMap = false;
                roomSelected = true;
                fightSelected = true;
                player.reset();
                ce = new CombatEngine(this, player, rew, true, floorNumber);
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
                } else if (rew.pressedSkip()) {
                    showRewardScreen = false;
                    fightSelected = false;
                    roomSelected = false;
                    showingMap = true;
                }
            } else if (showingPuzzle) {
                if (pm.checkMove()) {
                    if (pm.isLost()) {
                        player.setHealth(player.getHealth() - pm.getDifficulty()*10);
                        if (player.isDead()) {
                            gameOver = true;
                        } else {
                            showingPuzzle = false;
                            roomSelected = false;
                            showingMap = true;
                        }
                    } else if (pm.isWon()) {
                        rew.generateRewards();
                        showingPuzzle = false;
                        showRewardScreen = true;
                    }
                }
            } else if (showingEvent) {
                if (em.checkSelection()) {
                    showingEvent = false;
                    if (!fightSelected && !showingPuzzle) {
                        roomSelected = false;
                        showingMap = true;
                    }
                }
            }
        }
    }

    public void startFight() {
        roomSelected = true;
        fightSelected = true;
        showingMap = false;
        showingEvent = false;
        player.reset();
        ce = new CombatEngine(this, player, rew, false, floorNumber);
    }

    public void startPuzzle() {
        roomSelected = true;
        showingPuzzle = true;
        showingEvent = false;
        showingMap = false;
        pm.makePuzzle();
    }

    public void gameOver(boolean gameOver) {
        this.gameOver = gameOver;
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
        String[] pArgs = new String[] {"main.MainSketch"};
        MainSketch mainSketch = new MainSketch();
        PApplet.runSketch(pArgs, mainSketch);
    }
}
