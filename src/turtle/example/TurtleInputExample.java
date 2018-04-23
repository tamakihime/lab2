package turtle.example;

import turtle.Turtle;
import turtle.TurtleDisplay;
import turtle.input.TurtleButton;

/**
 * an example program using buttons
 */
public class TurtleInputExample extends TurtleButton {
    public static void main(String[] args) {

        TurtleDisplay d = new TurtleDisplay();

        Turtle t = new Turtle();
        d.addTurtle(t);

        t.setXY(100, 100);
        t.setInput(new TurtleInputExample(30));
        t.rotate(90);
    }

    private static final long serialVersionUID = 1L;

    int amount;

    public TurtleInputExample(int amount) {
        super("Go");
        this.amount = amount;
    }


    @Override
    public void click() {
        Turtle y = getTurtle();

        Turtle x = new Turtle();
        y.getDisplay().addTurtle(x);
        x.setXY(y.getX(), y.getY());
        x.penDown();
        x.move(amount);
        x.rotate(90);
        x.setInput(new TurtleInputExample(amount + amount / 2));

        y.penDown();
        y.move(amount);
        amount += amount / 2;
    }
}
