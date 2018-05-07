package exercise3;
import turtle.Turtle;
import turtle.TurtleDisplay;
import turtle.input.TurtleButton;
public class TurtleTree {
    public static void main(String[] args) {
        new TurtleTree().runButton();
    }

    public Turtle root() {
        TurtleDisplay d = new TurtleDisplay();

        Turtle root = new Turtle();
        d.addTurtle(root);


        root.penDown();
        root.move(100, 50);
        root.say("root");
        return root;
    }

    public void runAuto() {
        TurtleTree.expand(this.root());
    }

    public static void expand(Turtle parent) {
        int y = 0;
        for (int i = 0; i < 5; i++) {
            Turtle child = new Turtle();
            parent.getDisplay().addTurtle(child);

            moveChild(parent, child, y, "child" + i);
            child.setInput(new ExpandButton());
            y += 50;
            parent.sleep(300);
        }
    }

    private static void moveChild(Turtle parent, Turtle child, int y, String str) {
        child.setXY(parent.getX(), parent.getX());
        child.move(60, 0);
        child.penDown();
        child.move(100, y);
        child.say(str);
    }

    public void runButton() {
        root().setInput(new ExpandButton());
    }
}
class ExpandButton extends TurtleButton{
    public ExpandButton(){
        super("Expand");

    }
    public void click() {
        Turtle t = getTurtle();
        TurtleTree.expand(t);
        t.setInput(null);
    }

}
