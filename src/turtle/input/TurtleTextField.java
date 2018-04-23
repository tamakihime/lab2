package turtle.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * A text field:
 *  It is used by inheritance.
 * <p>
 * A sub-class can override {@link TurtleTextField#input(String)} like the folloing code:
 * <pre>
 *  class MyField extends TurtleTextFields {
 *     public void input(String text) {
 *        Turtle t = getTurtle();
 *        //moves by the input number
 *        t.move(Integer.parseInt(text));
 *     }
 *  }
 * </pre>
 * <p>
 * <pre>
 *   Turtle t = ...;
 *   ...
 *   t.setInput(new MyField());
 * </pre>
 */
public class TurtleTextField extends TurtleInput implements ActionListener {
    private static final long serialVersionUID = 1L;
    protected JTextField field;
    protected String text;

    public TurtleTextField(String text) {
        this.text = text;
    }

    public TurtleTextField() {
        this("");
    }

    @Override
    protected void init() {
        field = new JTextField(15);
        field.setText(text);
        field.addActionListener(this);
        add(field);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //the enter key is down on the text field
        final String fldTxt = field.getText();
        execute(new Runnable() {
            public void run() {
                input(fldTxt);
            }
        });
    }

    /**
     * a text is input and down the enter key: a sub-class can override the method
     */
    public void input(String text) {
        System.err.println("input: " + text);
    }

    /**
     * sets the text of the text field
     */
    public void setText(String text) {
        this.text = text;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (field != null) {
                    field.setText(TurtleTextField.this.text);
                }
            }
        });
    }
}

