package Bloaters;
// From https://rosettacode.org/wiki/Morpion_solitaire/Java
// Large class and also contains data/lazy classes (line) and 
// long methods (draw and PlayerMove)
// and checkline has a long parameter list

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;


class Grid {
    enum Result {
        GOOD, BAD, UGLY
    }

    final int EMPTY = 0, POINT = 1, HORI = 2, VERT = 4, DIUP = 8, DIDO = 16,
            HORI_END = 32, VERT_END = 64, DIUP_END = 128, DIDO_END = 256,
            CAND = 512, ORIG = 1024, HINT = 2048;

    final int[] basePoints = {120, 72, 72, 975, 513, 513, 975, 72, 72, 120};

    int cellSize, pointSize, halfCell, centerX, centerY, origX, origY;
    int minC, minR, maxC, maxR;

    int[][] points;
    List<Line> lines;
    Map<Point, Choice> choices;
    List<Choice> candidates;

    class Line {
        final Point p1, p2;

        Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    class Choice {
        int[] dir;
        List<Point> points;

        Choice(List<Point> p, int[] d) {
            points = p;
            dir = d;
        }
    }

    Grid(int cs, int ps) {
        cellSize = cs;
        pointSize = ps;
        halfCell = cs / 2;
        points = new int[50][50];
        minC = minR = 0;
        maxC = maxR = 50;
        newGame();
    }

    final void newGame() {
        for (int r = minR; r < maxR; r++)
            for (int c = minC; c < maxC; c++)
                points[r][c] = EMPTY;

        choices = new HashMap<>();
        candidates = new ArrayList();
        lines = new ArrayList<>();
        minC = minR = 18;
        maxC = maxR = 31;

        // cross
        for (int r = 0; r < 10; r++)
            for (int c = 0; c < 10; c++)
                if ((basePoints[r] & (1 << c)) != 0)
                    points[20 + r][20 + c] = POINT;
    }

    void draw(Graphics2D g, int w, int h) {
        centerX = w / 2;
        centerY = h / 2;
        origX = centerX - halfCell - 24 * cellSize;
        origY = centerY - halfCell - 24 * cellSize;

        // grid
        g.setColor(Color.lightGray);

        int x = (centerX - halfCell) % cellSize;
        int y = (centerY - halfCell) % cellSize;

        for (int i = 0; i <= w / cellSize; i++)
            g.drawLine(x + i * cellSize, 0, x + i * cellSize, h);

        for (int i = 0; i <= h / cellSize; i++)
            g.drawLine(0, y + i * cellSize, w, y + i * cellSize);

        // lines
        g.setStroke(new BasicStroke(2));
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (i == lines.size() - 1)
                g.setColor(new Color(0x3399FF));
            else
                g.setColor(Color.orange);
            int x1 = origX + line.p1.x * cellSize;
            int y1 = origY + line.p1.y * cellSize;
            int x2 = origX + line.p2.x * cellSize;
            int y2 = origY + line.p2.y * cellSize;
            g.drawLine(x1, y1, x2, y2);
        }

        // points
        for (int r = minR; r < maxR; r++)
            for (int c = minC; c < maxC; c++) {
                int p = points[r][c];

                if (p == EMPTY)
                    continue;

                if ((p & ORIG) != 0)
                    g.setColor(Color.red);

                else if ((p & CAND) != 0)
                    g.setColor(Color.green);

                else if ((p & HINT) != 0) {
                    g.setColor(Color.lightGray);
                    points[r][c] &= ~HINT;
                } else
                    g.setColor(Color.darkGray);

                drawPoint(g, c, r);
            }
    }

    private void drawPoint(Graphics2D g, int x, int y) {
        x = origX + x * cellSize - (pointSize / 2);
        y = origY + y * cellSize - (pointSize / 2);
        g.fillOval(x, y, pointSize, pointSize);
    }

    Result computerMove(int r, int c) {
        checkLines(r, c);
        if (candidates.size() > 0) {
            Choice choice = candidates.get(0);
            addLine(choice.points, choice.dir);
            return Result.GOOD;
        }
        return Result.BAD;
    }

    Result playerMove(float x, float y) {
        int r = Math.round((y - origY) / cellSize);
        int c = Math.round((x - origX) / cellSize);

        // see if inside active area
        if (c < minC || c > maxC || r < minR || r > maxR)
            return Result.BAD;

        // only process when mouse click is close enough to grid point
        int diffX = (int) Math.abs(x - (origX + c * cellSize));
        int diffY = (int) Math.abs(y - (origY + r * cellSize));
        if (diffX > cellSize / 5 || diffY > cellSize / 5)
            return Result.BAD;

        // did we have a choice in the previous turn
        if ((points[r][c] & CAND) != 0) {
            Choice choice = choices.get(new Point(c, r));
            addLine(choice.points, choice.dir);
            for (Choice ch : choices.values()) {
                for (Point p : ch.points)
                    points[p.y][p.x] &= ~(CAND | ORIG);
            }
            choices.clear();
            return Result.GOOD;
        }

        if (points[r][c] != EMPTY || choices.size() > 0)
            return Result.BAD;

        checkLines(r, c);

        if (candidates.size() == 1) {
            Choice choice = candidates.get(0);
            addLine(choice.points, choice.dir);
            return Result.GOOD;
        } else if (candidates.size() > 1) {
            // we can make more than one line
            points[r][c] |= ORIG;
            for (Choice ch : candidates) {
                List<Point> cand = ch.points;
                Point p = cand.get(cand.size() - 1);
                if (p.equals(new Point(c, r)))
                    p = cand.get(0);
                points[p.y][p.x] |= CAND;
                choices.put(p, ch);
            }
            return Result.UGLY;
        }

        return Result.BAD;
    }

    void checkLine(int dir, int end, int r, int c, int rIncr, int cIncr) {
        List<Point> result = new ArrayList<>(5);
        for (int i = -4; i < 1; i++) {
            result.clear();
            for (int j = 0; j < 5; j++) {
                int y = r + rIncr * (i + j);
                int x = c + cIncr * (i + j);
                int p = points[y][x];
                if (p != EMPTY && (p & dir) == 0 || (p & end) != 0 || i + j == 0)
                    result.add(new Point(x, y));
                else
                    break;
            }
            if (result.size() == 5) {
                candidates.add(new Choice(new ArrayList<>(result),
                        new int[]{dir, end}));
            }
        }
    }

    void checkLines(int r, int c) {
        candidates.clear();
        checkLine(HORI, HORI_END, r, c, 0, 1);
        checkLine(VERT, VERT_END, r, c, 1, 0);
        checkLine(DIUP, DIUP_END, r, c, -1, 1);
        checkLine(DIDO, DIDO_END, r, c, 1, 1);
    }

    void addLine(List<Point> line, int[] dir) {
        Point p1 = line.get(0);
        Point p2 = line.get(line.size() - 1);

        // mark end points for 5T
        points[p1.y][p1.x] |= dir[1];
        points[p2.y][p2.x] |= dir[1];

        lines.add(new Line(p1, p2));

        for (Point p : line)
            points[p.y][p.x] |= dir[0];

        // growable active area
        minC = Math.min(p1.x - 1, Math.min(p2.x - 1, minC));
        maxC = Math.max(p1.x + 1, Math.max(p2.x + 1, maxC));
        minR = Math.min(p1.y - 1, Math.min(p2.y - 1, minR));
        maxR = Math.max(p1.y + 1, Math.max(p2.y + 1, maxR));
    }

    List<Point> possibleMoves() {
        List<Point> moves = new ArrayList<>();
        for (int r = minR; r < maxR; r++)
            for (int c = minC; c < maxC; c++) {
                if (points[r][c] == EMPTY) {
                    checkLines(r, c);
                    if (candidates.size() > 0)
                        moves.add(new Point(c, r));
                }
            }
        return moves;
    }

    void showHints() {
        for (Point p : possibleMoves())
            points[p.y][p.x] |= HINT;
    }
}
