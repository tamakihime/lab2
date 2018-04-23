package turtle.example;

import turtle.Turtle;
import turtle.TurtleDisplay;

/**
 * an executable example program
 */
public class TurtleExample {
    public static void main(String[] args) {
        TurtleDisplay d = new TurtleDisplay();

        Turtle t = new Turtle();
        d.addTurtle(t);

        t.setPaintTurtle(true);

        t.sleep(100);
        t.move(400);
        t.sleep(100);
        t.rotate(90);

        t.sleep(100);
        t.move(300);
        t.sleep(100);

        t.penDown();
        t.say("Start");

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 50; ++j) {
                t.move(i);
                t.sleep(3);
            }
            t.rotate(90);
        }
        t.penUp();
        t.say("End");
    }
}
