package exercise3;
import turtle.Turtle;
import turtle.TurtleDisplay;
import turtle.input.TurtleButton;
import turtle.input.TurtleFileOpen;

import java.io.File;
import java.util.List;

public class TurtleTree {
    public static void main(String[] args) {
        new TurtleTree().runFileTree();
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

    public static void moveChild(Turtle parent, Turtle child, int y, String str) {
        child.setXY(parent.getX(), parent.getY());
        child.move(60, 0);
        child.penDown();
        child.move(100, y);
        child.say(str);
    }

    public void runButton() {
        root().setInput(new ExpandButton());
    }
    public void runFileTree(){
        root().setInput(new DirectoryOpen());
    }
}
class ExpandButton extends TurtleButton{
    public ExpandButton(){
        super("Expand");

    }
    public void click() {
        FileTurtle f = (FileTurtle) getTurtle();
        f.closeSiblings();
        List<FileTurtle> cs = f.getChildren();
        int y =0;
        for(FileTurtle i :cs){
           i.show();
            y = y + 50;
            f.sleep(100);
        }
    }

}
class DirectoryOpen extends TurtleFileOpen{
    public void openFile(File selectedFile){
        Turtle root = this.getTurtle();
        FileTurtle f = new FileTurtle(null,selectedFile);

        root.getDisplay().addTurtle(f);
        TurtleTree.moveChild(root, f, 0,selectedFile.getPath());

        ExpandButton b = new ExpandButton();
        f.setInput(b);
    }
}
