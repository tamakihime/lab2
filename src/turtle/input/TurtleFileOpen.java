package turtle.input;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 * A button displaying the file dialog:
 * It is used by inheritance.
 * <p>
 * A sub-class can override {@link TurtleFileOpen#openFile(File)} like the folloing code:
 * <pre>
 *  class MyOpen extends TurtleFileOpen {
 *     public void openDir(File selectedFile) {
 *        //after the button is clicked and the user selects the file
 *        Turtle t = getTurtle();
 *        //displays the path of the selected file
 *        t.say(selectedFile.getPath());
 *     }
 *  }
 * </pre>
 * <p>
 * <pre>
 *   Turtle t = ...;
 *   ...
 *   t.setInput(new MyOpen());
 * </pre>
 */
public class TurtleFileOpen extends TurtleButton {
    private static final long serialVersionUID = 1L;
    protected JFileChooser fileChooser;

    public TurtleFileOpen() {
        super("Open...");
    }

    @Override
    protected void init() {
        super.init();
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    @Override
    public void click() {
        //displays the file dialog and waits user's inputs
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int result = fileChooser.showOpenDialog(TurtleFileOpen.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    final File selFile = fileChooser.getSelectedFile();
                    //the user selects the file
                    execute(new Runnable() {
                        public void run() {
                            openFile(selFile);
                        }
                    });
                }
            }
        });
    }

    /**
     * processes the selected file (or directory) by the dialog]: a sub-class can override the method.
     */
    public void openFile(File selectedFile) {
        System.err.println("selected: " + selectedFile);
    }
}
