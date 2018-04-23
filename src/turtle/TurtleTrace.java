package turtle;

import java.awt.Graphics;

/**
 * the interface representing an unit of changing state of a {@link Turtle} on the display
 */
public interface TurtleTrace {
    /**
     * it draws the changing state on the graphic context g.
     * If it is a move, draws a line for the move.
     * If it is a message, displays a text line.
     */
    public void paint(Graphics g);
}
