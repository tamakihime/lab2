package turtle.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * a button:
 *  it is used by inheritance.
 * <p>
 *  A sub-class can override {@link TurtleButton#click()} like the following code:
 * <pre>
 *  class MyButton extends TurtleButton {
 *     public MyButton() {
 *        super("Go");
 *     }
 *     public void click() {
 *        Turtle t = getTurtle();
 *        //it moves 100 by clicking the button
 *        t.move(100);
 *        t.setInput(null); //removes the button
 *     }
 *  }
 * </pre>
 * <p>
 * <pre>
 *   Turtle t = ...;
 *   ...
 *   t.setInput(new MyButton());
 * </pre>
 */
public class TurtleButton extends TurtleInput implements ActionListener {
    private static final long serialVersionUID = 1L;
    protected String label;

    /**
     * A UI control displaying a button. The label is the displaying name of the button.
     */
    public TurtleButton(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    protected void init() {
        JButton btn = new JButton(label);
        btn.addActionListener(this);
        add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //it runs when the button is clicked
        execute(new Runnable() {
            public void run() {
                click();
            }
        });
    }

    /**
     * defines an action when the button is clicked.
     *  A subclass can override and define it's own action.
     */
    public void click() {
    }
}
