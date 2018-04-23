package turtle;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * a display showing multiple {@link Turtle} objects.
 */
public class TurtleDisplay {
    /**
     * the actual display (window) object
     */
    protected JFrame frame;
    /**
     * a panel on the window
     */
    protected TurtlePanel pane;
    /**
     * a scroll covering the pane
     **/
    protected JScrollPane scroll;

    protected static List<TurtleDisplay> displays;

    protected List<Turtle[]> overlappedTurtles = new ArrayList<>(); //{from, to}

    /**
     * initializing the display, and it shows a window
     */
    public TurtleDisplay() {
        addToDisplays();

        //executes init() within the event dispatching thread
        if (SwingUtilities.isEventDispatchThread()) {
            init(); //If it is under the event dispatching thread, it runs as is
        } else {
            try {
                //If not a event dispatching thread, it runs under the event dispatching thread,
                //via invokeAndWait
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        init();
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void addToDisplays() {
        synchronized (TurtleDisplay.class) {
            if (displays == null) {
                displays = Collections.synchronizedList(new ArrayList<>());
            }
            displays.add(this);
        }
    }

    protected void init() {
        try {
            //sets the appearance to the system standard
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println(ex);
        }

        frame = new JFrame("TurtleDisplay: " + LocalTime.now().format(DateTimeFormatter.ISO_TIME));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane = new TurtlePanel();
        scroll = new JScrollPane(pane);
        frame.getContentPane().add(scroll);

        //set the size of the window to the screen's size -200
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(Math.max(800, screen.width - 200), Math.max(600, screen.height - 200));

        frame.setVisible(true);
    }

    public TurtlePanel getPane() {
        return pane;
    }

    /**
     * adds a {@link Turtle} object to the display and immediately displays the traces
     */
    public void addTurtle(Turtle t) {
        pane.add(t);
        t.setDisplay(this);
        update();
    }

    /**
     * removes a {@link Turtle} object from the display
     */
    public void removeTurtle(Turtle t) {
        pane.remove(t);
        update();
    }



    /**
     * true if it shows the {@link Turtle} object
     */
    public boolean containsTurtle(Turtle t) {
        return pane.containsTurtle(t);
    }

    /**
     * obtains the width of the display. It will be changed by the user changing the size of the window.
     */
    public int getWidth() {
        return frame.getWidth();
    }

    /**
     * obtains the height of the display. It will be changed by the user changing the size of the window.
     */
    public int getHeight() {
        return frame.getHeight();
    }

    /**
     * updates the display: it re-displays the window. The method is automatically called by a state changed Turtle.
     * If it re-displayed once, it calls {@link TurtlePanel#paintComponent(Graphics)} to the added {@link TurtlePanel}.
     */
    public void update() {
        //re-displays on the event dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //udpates UI components
                pane.updateInputs();
                pane.revalidate();
                //re-display
                frame.repaint();
            }
        });
    }

    public void update(Turtle from) {
        for (Turtle t : getPane().getTurtles()) {
            if (t != from && t.getX() == from.getX() && t.getY() == from.getY()) {
                overlappedTurtles.add(new Turtle[]{from, t});
            }
        }
        update();
    }

    public List<Turtle[]> getOverlappedTurtles() {
        return overlappedTurtles;
    }

    public static List<TurtleDisplay> getDisplays() {
        List<TurtleDisplay> ds = displays;
        if (ds == null) {
            return Collections.emptyList();
        } else {
            return ds;
        }
    }

}
