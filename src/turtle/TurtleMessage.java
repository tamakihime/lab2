package turtle;

import java.awt.Graphics;

/**
 * a class for representing message display of a {@link Turtle},
 * created in {@link Turtle#say(String)}
 */
public class TurtleMessage implements TurtleTrace {
    protected int x;
    protected int y;
    protected String message;

    /**
     * a message at the position (x,y)
     */
    public TurtleMessage(int x, int y, String message) {
        this.x = x;
        this.y = y;
        this.message = message;
    }

    public void paint(Graphics g) {
        g.drawString(message, x, y + 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMessage() {
        return message;
    }
}
