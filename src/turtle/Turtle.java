package turtle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import turtle.input.TurtleInput;

/**
 * A class representing a turtle object which moves on the display.
 * <p>
 * <pre>
 * TurtleDisplay d = new TurtleDisplay();
 *
 * Turtle t = new Turtle();
 * d.addTurtle(t);
 * </pre>
 */
public class Turtle implements Cloneable {
    protected double x = 0;
    protected double y = 0;
    protected double direction = 0;
    protected boolean pen = false;
    protected boolean paintTurtle = false;

    protected List<TurtleTrace> trace = new ArrayList<TurtleTrace>();

    protected TurtleInput input;

    protected TurtleDisplay display;

    protected Color color;

    public Turtle() {
        this(getNextColor());
    }

    public Turtle(Color color) {
        this.color = color;
    }


    /**
     * obtains the current x position of the turtle.
     *  The initial value is 0 and it means left side of the display.
     */
    public int getX() {
        return (int) x;
    }

    /**
     * obtains the current y position of the turtle.
     *  The initial value is 0 and it means top side of the display.
     */
    public int getY() {
        return (int) y;
    }

    /**
     * obtains the current directory degree of the turtle,
     *  in the range of 0 to 359.
     */
    public int getDirection() {
        return (int) direction;
    }

    /**
     * obtains whether the turtle is putting down the pen.
     *  If down, true. The initial value is false.
     */
    public boolean isPenDown() {
        return pen;
    }

    /**
     * displays the message on the current turtle's position
     */
    public synchronized void say(String message) {
        trace.add(new TurtleMessage((int) x, (int) y, message));
        update();
    }

    /**
     * puts down the pen. Moves the turtle after this, it draws a track.
     */
    public void penDown() {
        pen = true;
    }

    /**
     * puts up the pen
     */
    public void penUp() {
        pen = false;
    }

    /**
     * rotates the turtle r degree. r takes an arbitrary integer number.
     */
    public void rotate(int r) {
        direction = (direction + r) % 360;
    }

    /**
     * moves the turtle by the distance with the current direction.
     * If {@link #penDown()} is called, it draws a track.
     * The implementation of the method internally calls {@link #move(double, double)}.
     */
    public void move(int distance) {
        double d = (double) distance;
        double r = direction * Math.PI * 2.0 / 360.0;
        double dx = d * Math.cos(r);
        double dy = d * Math.sin(r);

        move(dx, dy);
    }

    /**
     * move the turtle by (dx,dy) from the current position regardless of the current direction
     */
    public synchronized void move(double dx, double dy) {
        double px = x + dx;
        double py = y + dy;

        trace.add(new TurtleMove((int) x, (int) y, (int) px, (int) py, pen));
        setXY(px, py);
    }

    /**
     * sets the position of the turtle to (x,y) regardless of the current direction
     */
    public synchronized void setXY(double x, double y) {
        this.x = x;
        this.y = y;
        updateXY();
    }

    /**
     * pauses the given milli-seconds.
     *  It can realize animation by calling it between {@link #move(int)}s
     */
    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * returns a list of {@link TurtleTrace} objects created by {@link #move(int)} and {@link #say(String)}.
     */
    public List<TurtleTrace> getTrace() {
        return trace;
    }

    /**
     * sets the display as the target of the turtle updates.
     * It is automatically called from {@link TurtleDisplay#addTurtle(Turtle)},
     *   thus you need not to directly call the method.
     */
    public void setDisplay(TurtleDisplay display) {
        this.display = display;
    }

    /**
     * returns the display displaying the turtle
     */
    public TurtleDisplay getDisplay() {
        return display;
    }

    private void update() {
        if (display != null) {
            display.update();
        }
    }

    private void updateXY() {
        if (display != null) {
            display.update(this);
        }
    }

    /**
     * it takes true and then it displays a marker for indicating the current position of the turtle.
     * The main purpose is debugging.
     */
    public void setPaintTurtle(boolean paintTurtle) {
        this.paintTurtle = paintTurtle;
    }

    public boolean isPaintTurtle() {
        return paintTurtle;
    }

    /**
     * sets an UI component.
     * For each Turtle, one TurtleInput can be set.
     * <p>
     * setInput(null) will be delete the TurtleInput.
     */
    public void setInput(TurtleInput input) {
        this.input = input;
        if (input != null) {
            input.setTurtle(this);
        }
        update();
    }

    /**
     * obtains the current UI component. The default value is null
     */
    public TurtleInput getInput() {
        return input;
    }

    /**
     * Color information for drawing
     */
    public Color getColor() {
        return color;
    }

    private static int colorNum = 0;

    public static Color getNextColor() {
        int n = colorNum;
        synchronized (Turtle.class) {
            ++colorNum;
        }
        int div = 10;
        float block = 1.0f / div;
        float h = (n % div) * block + ((n % 100) / 100.0f) * block;
        return Color.getHSBColor(h, 0.8f, 0.8f);
    }

    /**
     * creates a copy of this turtle: the returned turtle will not be added in the display
     */
    public Turtle copy() {
        try {
            /* Object#clone() is another way to create a new instance.
               To successfully call the clone() method, it needs to implement Cloneable interface.
               Otherwise, it causes a CloneNotSupportedException. */
            Turtle turtleCopy = (Turtle) super.clone();
            turtleCopy.trace = new ArrayList<>(trace);
            turtleCopy.display = null;
            return turtleCopy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); //never
        }
    }
}
