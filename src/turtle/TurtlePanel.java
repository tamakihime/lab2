package turtle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import turtle.input.TurtleInput;

/**
 * a panel displaying {@link Turtle}s
 */
public class TurtlePanel extends JComponent {
    private static final long serialVersionUID = 1L;
    protected List<Turtle> turtles = new ArrayList<Turtle>();

    public TurtlePanel() {
        setPreferredSize(new Dimension(700, 500));
        setLayout(null);
    }

    /**
     * called when the display is updated
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //saves the size before re-displaying
        Dimension prefSize = new Dimension(getPreferredSize());

        for (Turtle t : getTurtles()) {
            paint(t, g);
        }

        //compares the preferred size after re-displaying,
        // and if it changed, re-layouts
        if (!getPreferredSize().equals(prefSize)) {
            revalidate();
        }
    }

    //draw a turtle t on the graphic context g
    private void paint(Turtle t, Graphics g) {
        //a Turtle is controlled under both the event dispatching thread and the main thread.
        //The paint method is called under the event dispatching thread.
        //synchronize t in order to protect t from changes in the main thread while executing the paint method
        synchronized (t) {
            //setting of anti-aliasing
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            //if it beyonds the display, it changes the size
            Dimension prefSize = getPreferredSize();
            if (prefSize.width < t.getX()) {
                prefSize.width = t.getX() + 100;
            }
            if (prefSize.height < t.getY()) {
                prefSize.height = t.getY() + 100;
            }
            setPreferredSize(prefSize);

            if (t.isPaintTurtle()) {
                //draws a red circle on the current position of the turtle
                g.setColor(Color.red);
                g.drawOval(t.getX() - 5, t.getY() - 5, 10, 10);
            }

            //draws current changing states of the turtle
            g.setColor(t.getColor());
            for (TurtleTrace trace : t.getTrace()) {
                trace.paint(g);
            }
        }
    }

    /**
     * returns a list of added {@link Turtle} turtles
     */
    public synchronized List<Turtle> getTurtles() {
        return new ArrayList<Turtle>(turtles);
    }

    /**
     * adds a {@link Turtle}. called from {@link TurtleDisplay#addTurtle(Turtle)}
     */
    public synchronized void add(Turtle t) {
        if (!turtles.contains(t)) {
            turtles.add(t);
        }
    }

    /**
     * removes a {@link Turtle}
     */
    public synchronized void remove(Turtle r) {
        turtles.remove(r);
    }

    /**
     * whether it displays the {@link Turtle} or not
     */
    public synchronized boolean containsTurtle(Turtle t) {
        return turtles.contains(t);
    }

    /**
     * the method runs under the event dispatching thread
     */
    public void updateInputs() {
        //At first, collects Turtle's current TurtleInput: those are displayed
        List<TurtleInput> mustBeAdded;
        synchronized (this) {
            mustBeAdded = new ArrayList<TurtleInput>(turtles.size());
            for (Turtle t : turtles) {
                TurtleInput i = t.getInput();
                if (i != null) {
                    mustBeAdded.add(i);
                }
            }
        }

        //checks current displaying TurtleInputs, and puts them into the removing list if they must be hidden.
        List<TurtleInput> willBeRemoved = new ArrayList<TurtleInput>();
        for (Component child : getComponents()) {
            if (child instanceof TurtleInput) {
                TurtleInput i = (TurtleInput) child;
                if (mustBeAdded.remove(i)) { //removes a current added TurtleInput from the adding list.
                    i.update(); //updates the UI component.
                } else {
                    willBeRemoved.add(i); //it should not be added, so puts it into the removing list
                }
            }
        }
        //actually removes elements in the removing list
        for (TurtleInput c : willBeRemoved) {
            remove(c);
        }
        //actually adds elements in the adding list. updates UI components
        for (TurtleInput c : mustBeAdded) {
            c.update();
            add(c);
        }
    }
}
