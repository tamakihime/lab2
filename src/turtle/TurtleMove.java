package turtle;

import java.awt.Graphics;

/**
 * a class for representing moving of a {@link Turtle},
 * created in {@link Turtle#move(double, double)}
 */
public class TurtleMove implements TurtleTrace {
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;
    protected boolean pen;

    /**
     * a move from (x1,y1) to (x2,y2). If the pen is down , pen=true.
     */
    public TurtleMove(int x1, int y1, int x2, int y2, boolean pen) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.pen = pen;
    }

    public void paint(Graphics g) {
        if (pen) {
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public boolean isPen() {
        return pen;
    }
}
