package turtle.input;

import java.awt.BorderLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComponent;

import turtle.Turtle;

/**
 * A base class for a UI component attaching to a Turtle:
 * There are following sub-classes:
 * <ul>
 * <li> {@link TurtleButton} : displaying a button
 * <li> {@link TurtleFileOpen} : displaying a button showing a file dialog
 * <li> {@link TurtleTextField} : displaying a text field
 * </ul>
 * <p>
 * <p>
 * <pre>
 *  Turtle t = new Turtle();
 *  ...
 *  t.setInput(new TurtleButton("Hello"));
 * </pre>
 */
public abstract class TurtleInput extends JComponent {
    private static final long serialVersionUID = 1L;
    protected Turtle turtle;
    protected boolean initialized = false;

    protected static ExecutorService runner;

    public TurtleInput() {
        setLayout(new BorderLayout());
    }

    /**
     * sets the associated Turtle. it will be called automatically
     */
    public void setTurtle(Turtle turtle) {
        this.turtle = turtle;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    /**
     * updates a UI component. It is called automatically
     * (running under the event dispatching thread)
     */
    public void update() {
        if (!initialized) {
            init();
            initialized = true;
        }
        setSize(getPreferredSize());
        setLocation(turtle.getX() - getWidth() - 3, turtle.getY());
    }

    /**
     * initialize the UI component. It is called only once from update()
     * (running under the event dispatching thread)
     */
    protected abstract void init();

    /**
     * removed the UI component
     */
    public void remove() {
        turtle.setInput(null);
    }

    /**
     * executes the task under the thread that is not the event dispatching thread.
     * It is required for running with updating UI components.
     * A event handler in a sub-class can use the method for changing states of Turtles (etc.)
     */
    public void execute(Runnable task) {
        synchronized (TurtleInput.class) {
            if (runner == null) {
                runner = Executors.newSingleThreadExecutor();
            }
        }
        runner.execute(task);
    }
}
