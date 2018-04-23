package turtle.example;

import turtle.Turtle;
import turtle.TurtleDisplay;

public class TurtleMovePattern {
    public static void main(String[] args) {
        TurtleDisplay d = new TurtleDisplay();

        Turtle t = new Turtle();
        d.addTurtle(t);

        moveCenter(t);
        t.rotate(30);
        move(t);
    }

    public static void moveCenter(Turtle t) {
        t.move(500);
        t.rotate(90);
        t.move(300);
    }

    public static void move(Turtle t) {
        t.penDown();
        for (int i = 0; i < 90; ++i) {
            t.move(5);
            t.rotate(2);
            t.sleep(10);
        }
        t.penUp();
    }
}
