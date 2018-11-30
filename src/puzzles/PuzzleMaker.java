package puzzles;

import cards.CardSelectionScreen;
import processing.core.PApplet;

import java.util.Random;

public class PuzzleMaker {
    private PApplet p;
    private int width, height;
    private int startX, startY;
    private int difficulty;
    private PuzzleBlock[][] puzzle;
    private int minesToFind;
    private boolean won = false;
    private boolean lost = false;

    public PuzzleMaker(PApplet p, int difficulty) {
        this.p = p;
        this.difficulty = difficulty;
        makePuzzle();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void makePuzzle() {
        won = false;
        lost = false;
        Random r = new Random();
        switch (difficulty) {
            case 1:
                this.width = 3;
                this.height = 3;
                this.startX = 725;
                this.startY = 375;
                this.minesToFind = 1;
                break;
            case 2:
                this.width = 5;
                this.height = 5;
                this.startX = 675;
                this.startY = 325;
                this.minesToFind = 3;
                break;
            case 3:
                this.width = 7;
                this.height = 7;
                this.startX = 625;
                this.startY = 275;
                this.minesToFind = 5;
                break;
        }

        puzzle = new PuzzleBlock[height][width];
        int x = startX;
        int y = startY;

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                puzzle[i][j] = new PuzzleBlock(p);
                puzzle[i][j].setXY(x, y);
                puzzle[i][j].setCovered(true);
                x += 50;
            }
            y += 50;
            x = startX;
        }

        int xR, yR;
        int minesToPlace = minesToFind;

        while (minesToPlace > 0) {
            xR = r.nextInt(width);
            yR = r.nextInt(height);

            if (!(xR == 0 && yR == 0)) {
                if (!puzzle[yR][xR].isMine()) {
                    puzzle[yR][xR].setMine(true);
                    minesToPlace--;
                }
            }
        }

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (!puzzle[i][j].isMine()) {
                    puzzle[i][j].setClue(countNeighbouringMines(j, i));
                }
            }
        }
    }

    private int countNeighbouringMines(int x, int y) {
        int minX = Math.max(x - 1, 0);
        int maxX = Math.min(x + 1, width -1);
        int minY = Math.max(y - 1, 0);
        int maxY = Math.min(y + 1, height - 1);
        int count = 0;

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                if (puzzle[i][j].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isLost() {
        return lost;
    }

    public boolean isWon() {
        return won;
    }

    public void revealNeighbours(int x, int y) {
        int minX = Math.max(x - 1, 0);
        int maxX = Math.min(x + 1, width -1);
        int minY = Math.max(y - 1, 0);
        int maxY = Math.min(y + 1, height - 1);

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                if (!((x == j) && (y == i))) {
                    if (puzzle[i][j].getClue() == 0 && puzzle[i][j].isCovered())
                        revealNeighbours(j, i);
                }
                puzzle[i][j].setCovered(false);
            }
        }
    }

    public boolean checkMove() {
        int coveredCount = 0;
        boolean uncovered = false;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                PuzzleBlock current = puzzle[i][j];
                if (current.isMouseOver()) {
                    if (current.isCovered()) {
                        if (current.isMine()) {
                            lost = true;
                            return true;
                        } else {
                            current.setCovered(false);
                            if (current.getClue() == 0)
                                revealNeighbours(j, i);
                            uncovered = true;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (puzzle[i][j].isCovered())
                    coveredCount++;
            }
        }

        if (coveredCount == minesToFind) {
            won = true;
        }

        return uncovered;
    }

    public void drawPuzzle() {
        PuzzleBlock pb = null;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (!puzzle[i][j].isMouseOver()) {
                    puzzle[i][j].drawBlock();
                } else {
                    pb = puzzle[i][j];
                }
            }
        }
        if (pb != null)
            pb.drawBlock();

        p.textSize(30);
        p.text("Mines hidden: " + minesToFind, 100, 100);
        p.text("Lose: -" + (difficulty*10) + " health", 100, 140);
        p.text("Win: Get a card!", 100, 180);
    }
}
