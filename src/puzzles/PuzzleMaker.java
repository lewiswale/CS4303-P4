package puzzles;

import processing.core.PApplet;

import java.util.Random;

public class PuzzleMaker {
    private PApplet p;
    private int width, height;
    private int startX, startY;
    private PuzzleBlock[][] puzzle;
    private int minesToFind;
    private boolean won = false;
    private boolean lost = false;

    public PuzzleMaker(PApplet p) {
        this.p = p;
        this.width = 5;
        this.height = 5;
        this.startX = 300;
        this.startY = 300;
        this.minesToFind = 3;
        makePuzzle();
    }

    public void makePuzzle() {
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

        Random r = new Random();
        int xR, yR;
        int minesToPlace = minesToFind;

        while (minesToPlace > 0) {
            xR = r.nextInt(width);
            yR = r.nextInt(height);

            if (!(xR == 0 && yR == 0)) {
                puzzle[yR][xR].setMine(true);
                minesToPlace--;
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
                            uncovered = true;
                        }
                    }
                } else {
                    if (current.isCovered()) {
                        coveredCount++;
                    }
                }
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
    }
}
