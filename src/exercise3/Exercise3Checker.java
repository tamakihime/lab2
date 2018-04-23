package exercise3;

import csl.infolab.TestCodeRunner;
import turtle.Turtle;
import turtle.TurtleDisplay;
import turtle.TurtleMessage;
import turtle.input.TurtleButton;
import turtle.input.TurtleFileOpen;
import turtle.input.TurtleTextField;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Exercise3Checker {

    public static void checkRoot() {
        new Exercise3Checker().assertRoot();
    }

    public void assertRoot() {
        if (!askRootAdded()) {
            return;
        }
        TestCodeRunner.succeed();
    }

    public boolean askRootAdded() {
        List<Turtle> ts = askTurtlesInLastSingleDisplay(1);
        if (ts.size() < 1) {
            return false;
        }

        Turtle root = ts.get(0);

        if (root.getX() > 10 && root.getY() > 10) {
            return true;
        } else {
            printError("invalid root: " + root.getX() + ", " + root.getY());
            return false;
        }
    }

    ////////////////////////////////////////


    public static void checkExpanded() {
        new Exercise3Checker().assertExpanded();
    }

    public void assertExpanded() {
        List<Turtle> ts = askTurtlesInLastSingleDisplay(1 + 5); //root + 5 children

        if (ts.size() < 6) {
            return;
        }

        if (!askExpandedTree(ts)) {
            return;
        }

        TestCodeRunner.succeed();
    }

    private boolean askExpandedTree(List<Turtle> ts) {
        TurtlePosTree root = getPosTree(ts);
        return askExpandedTree(root);
    }

    private boolean askExpandedTree(TurtlePosTree parent) {
        if (parent.isLoop()) {
            printError("invalid tree structure: " + parent + " -> " + parent.parent + " -> ...");
            return false;
        }
        TurtlePosTree prev = null;
        for (TurtlePosTree c : parent.children) {
            if (parent.turtle.getX() >= c.turtle.getX()) {
                printError("invalid child position : parent " + parent + " vs child " + c);
                return false;
            }
            if (prev != null) {
                //next item in same level
                if (prev.turtle.getY() >= c.turtle.getY()) {
                    printError("invalid sibling position : upper " + prev + " vs lower " + c);
                    return false;
                }
            }
            prev = c;
        }

        for (TurtlePosTree c : parent.children) {
            if (!askExpandedTree(c)) {
                return false;
            }
        }
        return true;
    }

    ////////////////////////////////////////


    public static void checkExpandedRecursively() {
        new Exercise3Checker().assertExpandedRecursively();
    }

    public void assertExpandedRecursively() {
        int n = 1 + 5 * 3;
        List<Turtle> ts = askTurtlesInLastSingleDisplay(n); //root + 5 children * 3 level

        if (ts.size() < n) {
            return;
        }

        if (!askExpandedTree(ts)) {
            return;
        }

        TestCodeRunner.succeed();
    }


    ////////////////////////////////////////

    public static void checkExpandedByButton() {
        new Exercise3Checker().assertExpandedByButton();
    }

    public void assertExpandedByButton() {
        if (!askExpandedByButton()) {
            return;
        }

        TestCodeRunner.succeed();
    }

    private boolean askExpandedByButton() {
        List<Turtle> ts = askTurtlesInLastSingleDisplay(1); //only root
        if (ts.size() != 1) {
            return false;
        }

        Turtle root = ts.get(0);
        TurtleButton b = askButtonInput(root);
        if (b == null) {
            return false;
        }

        b.click();

        int n = 1 + 5;
        ts = askTurtlesInLastSingleDisplay(n);
        if (ts.size() != n) {
            return false;
        }

        if (!askExpandedTree(ts)) {
            return false;
        }
        return true;
    }

    private TurtleButton askButtonInput(Turtle t) {
        if (t.getInput() == null) {
            printError("required a button but no input : " + t);
            return null;
        }
        if (!(t.getInput() instanceof TurtleButton)) {
            printError("required a button but : " + t.getInput());
        }
        return (TurtleButton) t.getInput();
    }

    private TurtleButton getButtonInput(Turtle t) {
        if (t.getInput() != null && (t.getInput()) instanceof TurtleButton) {
            return (TurtleButton) t.getInput();
        } else {
            return null;
        }
    }

    ////////////////////////////////////////


    public static void checkExpandedByButtonRecursively() {
        new Exercise3Checker().assertExpandedByButtonRecursively();
    }

    public void assertExpandedByButtonRecursively() {
        if (!askExpandedByButton()) {
            return;
        }
        //expanded level 1
        int n = 6;
        List<Turtle> ts = askTurtlesInLastSingleDisplay(n);
        if (ts.size() < n) {
            return;
        }

        int lastIndex = 1;
        for (int i = 0; i < 3; ++i) {
            Turtle t = ts.get(lastIndex + i);
            TurtleButton b = askButtonInput(t);
            if (b == null) {
                return;
            }

            b.click();

            n += 5;
            ts = askTurtlesInLastSingleDisplay(n);
            if (ts.size() < n) {
                return;
            }

            if (!askExpandedTree(ts)) {
                return;
            }

            lastIndex = n - 5;
        }

        TestCodeRunner.succeed();
    }

    ////////////////////////////////////////

    public static void setupSrcLibDirForTest() {
        new Exercise3Checker().setupSrcLibDirForTestImpl();
    }

    public void setupSrcLibDirForTestImpl() {
        /*
         *  src-lib/
         *    turtle/
         *       Turtle.java
         *       TurtleDisplay.java
         *       input/
         *          TurtleInput.java
         *          TurtleButton.java
         *    csl/
         *       infolab/
         */
        final List<File> createdFiles = new ArrayList<>();
        try {
            createFileForTest(srcLibDir, true, createdFiles);

            File turtleDir = new File(srcLibDir, srcLibDirTurtleName);
            createFileForTest(turtleDir, true, createdFiles);
            createFileForTest(new File(turtleDir, "Turtle.java"), false, createdFiles);
            createFileForTest(new File(turtleDir, "TurtleDisplay.java"), false, createdFiles);

            File turtleInputDir = new File(turtleDir, srcLibDirTurtleInputName);
            createFileForTest(turtleInputDir, true, createdFiles);
            createFileForTest(new File(turtleInputDir, "TurtleInput.java"), false, createdFiles);
            createFileForTest(new File(turtleInputDir, "TurtleButton.java"), false, createdFiles);

            File cslDir = new File(srcLibDir, srcLibDirCslName);
            createFileForTest(cslDir, true, createdFiles);
            createFileForTest(new File(cslDir, "infolab"), true, createdFiles);

        } catch (Exception ex) {
            TestCodeRunner.println("setupSrcLibDirForTest error: " + ex);
        } finally {
            Collections.reverse(createdFiles);
            //at the end of the JVM process
            if (!createdFiles.isEmpty()) {
                Runtime.getRuntime().addShutdownHook(new Thread(
                        () -> createdFiles.forEach(Exercise3Checker::deleteFile)));
                TestCodeRunner.println("those files will be removed automatically: " + createdFiles);
            }
        }
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    deleteFile(child);
                }
            }
            file.delete();
        }
    }

    private void createFileForTest(File file, boolean dir, List<File> createdFiles) {
        try {
            if (file.exists()) {
                if (file.isDirectory() != dir) {
                    TestCodeRunner.println("unexpected file-type for test: " + file +
                            " expected-isDir=" + dir + " actual-isDir=" + file.isDirectory());
                }
            } else {
                if (dir) {
                    if (file.mkdir()) {
                        createdFiles.add(file);
                    } else {
                        TestCodeRunner.println("cannot create dir: " + file);
                    }
                } else {
                    Files.write(file.toPath(), "test".getBytes(StandardCharsets.UTF_8));
                    createdFiles.add(file);
                }
            }
        } catch (Exception ex) {
            TestCodeRunner.println("unexpected error while check test file: " + file + " : " + ex);
        }
    }

    public static void checkFileTreeDirectoryOpen() {
        new Exercise3Checker().assertFileTreeDirectoryOpen();
    }

    public void assertFileTreeDirectoryOpen() {
        if (!askRootDirectoryOpen()) {
            return;
        }

        TestCodeRunner.succeed();
    }

    private File srcLibDir = new File("src-lib");
    private String srcLibDirTurtleName = "turtle"; //src-lib/turtle
    private String srcLibDirCslName = "csl"; //src-lib/csl
    private String srcLibDirTurtleInputName = "input"; //src-lib/turtle/input

    private boolean askRootDirectoryOpen() {
        List<Turtle> ts = askTurtlesInLastSingleDisplay(1);
        if (ts.size() < 1) {
            return false;
        }

        TurtleFileOpen open = askFileOpenInput(ts.get(0));
        if (open == null) {
            return false;
        }

        open.openFile(srcLibDir);

        ts = askTurtlesInLastSingleDisplay(2);
        if (ts.size() < 2) {
            return false;
        }

        try {
            Class<?> ft = Class.forName("exercise3.FileTurtle");

            Turtle rootFile = ts.get(1);
            if (!(ft.isInstance(rootFile))) {
                printError("must be a file turtle: " + rootFile);
                return false;
            }

            String rootLabel = getString(rootFile);
            if (!rootLabel.contains(srcLibDir.getName())) {
                printError("no dir name in the root turtle: " + rootLabel);
                return false;
            }

            File displayedFile = File.class.cast(ft.getMethod("getFile").invoke(rootFile));

            if (!displayedFile.equals(srcLibDir)) {
                printError("unexpected displayed file: " + displayedFile);
                return false;
            }
        } catch (Exception ex) {
            printError("unexpected file turtle error" + ex);
            throw new RuntimeException(ex);
        }

        if (!askExpandedTree(ts)) {
            return false;
        }

        return true;
    }

    private TurtleFileOpen askFileOpenInput(Turtle t) {
        TurtleButton b = askButtonInput(t);
        if (b != null && b instanceof TurtleFileOpen) {
            return (TurtleFileOpen) b;
        } else {
            printError("required a open button: " + b);
            return null;
        }
    }

    ////////////////////////////////////////

    public static void checkFileTreeExpanding() {
        new Exercise3Checker().assertFileTreeExpanding();
    }

    public void assertFileTreeExpanding() {
        if (!askFileTreeExpandTurtle()) {
            return;
        }

        TestCodeRunner.succeed();
    }

    private boolean askFileTreeExpandTurtle() {
        if (!askRootDirectoryOpen()) {
            return false;
        }

        List<Turtle> ts = askTurtlesInLastSingleDisplay(2);
        TurtleButton b = askButtonInput(ts.get(1));
        if (b == null) {
            return false;
        }

        b.click();

        int srcLibSize = srcLibDir.listFiles().length;
        int n = 2 + srcLibSize;
        ts = askTurtlesInLastSingleDisplay(n);
        if (ts.size() < n) {
            return false;
        }

        Optional<TurtlePosTree> turtleOpt = askTurtleDir(ts, srcLibSize);
        if (!turtleOpt.isPresent()) {
            return false;
        }

        Optional<TurtleButton> turtleExpand = turtleOpt.map(m -> askButtonInput(m.turtle));
        if (!turtleExpand.isPresent() || turtleExpand.get() == null) {
            return false;
        }

        turtleExpand.get().click();

        File turtleDir = new File(srcLibDir, srcLibDirTurtleName);
        File[] turtleSubs = turtleDir.listFiles();
        int turtleSize = turtleSubs.length;
        int turtleExpandedSize = n + turtleSize;
        ts = askTurtlesInLastSingleDisplay(turtleExpandedSize);
        if (ts.size() < turtleExpandedSize) {
            return false;
        }

        //root -> src-lib -> { csl, turtle -> { ... } }
        turtleOpt = askTurtleDir(ts, srcLibSize);
        if (!turtleOpt.isPresent()) {
            return false;
        }

        TurtlePosTree turtle = turtleOpt.get();
        for (File turtleSub : turtleSubs) {
            Optional<TurtlePosTree> sub = turtle.getChildByLabelContains(turtleSub.getName());
            if (!sub.isPresent()) {
                printError("the child not found: " + turtleSub + " : " + turtle.getSubLabels() + " : " + turtle.toTreeString());
                return false;
            }

            TurtleButton btn = getButtonInput(sub.get().turtle);
            if (turtleSub.isFile()) {
                if (btn != null) {
                    printError("unexpected button attached to the file " + turtleSub + " : " + btn + ": " + turtle.toTreeString());
                    return false;
                }
            } else {
                if (btn == null) {
                    printError("no button attached to the dir " + turtleSub + " : " + turtle.toTreeString());
                    return false;
                }
            }
        }
        return true;
    }

    private Optional<TurtlePosTree> askTurtleDir(List<Turtle> ts, int srcLibSize) {
        TurtlePosTree root = getPosTree(ts);
        if (!askExpandedTree(root)) {
            return Optional.empty();
        }

        //root -> src-lib -> { csl, turtle }
        if (root.children.size() < 1) {
            printError("root: unexpected tree structure: " + root + " -> " + root.getSubLabels() + " : " + root.toTreeString());
            return Optional.empty();
        }

        TurtlePosTree srcLib = root.children.get(0);
        List<String> subs = srcLib.getSubLabels();
        if (srcLib.children.size() < srcLibSize) {
            printError("src-lib: unexpected tree structure: " + srcLib + " -> " + subs + ": " + root.toTreeString());
            return Optional.empty();
        }

        Optional<TurtlePosTree> cslOpt = srcLib.getChildByLabelContains(srcLibDirCslName);
        Optional<TurtlePosTree> turtleOpt = srcLib.getChildByLabelContains(srcLibDirTurtleName);
        if (!cslOpt.isPresent() ||
                !turtleOpt.isPresent()) {
            printError("csl and turtle: unexpected tree structure: " + subs + " : " + root.toTreeString());
            return Optional.empty();
        }

        return turtleOpt;
    }

    ////////////////////////////////////////

    public static void checkFileTreeExpandingWithClosing() {
        new Exercise3Checker().assertFileTreeExpandingWithClosing();
    }

    public void assertFileTreeExpandingWithClosing() {
        if (!askFileTreeExpandTurtle()) {
            return;
        }


        int srcLibSize = srcLibDir.listFiles().length;
        int n = 2 + srcLibSize;

        File turtleDir = new File(srcLibDir, srcLibDirTurtleName);
        File[] turtleSubs = turtleDir.listFiles();
        int turtleSize = turtleSubs.length;
        int turtleExpandedSize = n + turtleSize;
        List<Turtle> ts = askTurtlesInLastSingleDisplay(turtleExpandedSize);
        if (ts.size() < turtleExpandedSize) {
            return;
        }

        TurtlePosTree root = getPosTree(ts);
        Optional<TurtlePosTree> input = root.getChildByLabelContains(srcLibDir.getName())
                .flatMap(m -> m.getChildByLabelContains(srcLibDirTurtleName))
                .flatMap(m -> m.getChildByLabelContains(srcLibDirTurtleInputName));

        if (!input.isPresent()) {
            printError("invalid tree: no root -> src-lib -> turtle -> input: " + root.toTreeString());
            return;
        }

        input.ifPresent(t -> {
            TurtleButton btn = askButtonInput(t.turtle);
            if (btn != null) {
                btn.click();
            }
        });

        // root -> src-lib -> { csl, turtle -> { input -> {...} , ... } , ... }
        File inputDir = new File(turtleDir, srcLibDirTurtleInputName);
        File[] inputSubs = inputDir.listFiles();
        int inputSize = inputSubs.length;
        int inputExpandedSize = turtleExpandedSize + inputSize;
        ts = askTurtlesInLastSingleDisplay(inputExpandedSize);
        if (ts.size() < inputExpandedSize) {
            return;
        }

        root = getPosTree(ts);

        Optional<TurtlePosTree> csl = root.getChildByLabelContains(srcLibDir.getName())
                .flatMap(m -> m.getChildByLabelContains(srcLibDirCslName));
        if (!csl.isPresent()) {
            printError("invalid tree: no root -> src-lib -> csl: " + root.toTreeString());
            return;
        }

        csl.ifPresent(t -> {
            TurtleButton btn = askButtonInput(t.turtle);
            if (btn != null) {
                btn.click();
            }
        });

        //root -> src-lib -> { csl -> {... } , turtle, ... }

        File cslDir = new File(srcLibDir, srcLibDirCslName);
        File[] cslSubs = cslDir.listFiles();
        int cslSize = cslSubs.length;
        int cslExpandedSize = srcLibSize + cslSize;
        ts = askTurtlesInLastSingleDisplay(cslExpandedSize);
        if (ts.size() < cslExpandedSize) {
            return;
        }

        root = getPosTree(ts);
        csl = root.getChildByLabelContains(srcLibDir.getName())
                .flatMap(m -> m.getChildByLabelContains(srcLibDirCslName));
        if (!csl.isPresent()) {
            printError("invalid tree: no root -> src-lib -> csl: " + root.toTreeString());
            return;
        }
        if (csl.get().children.isEmpty()) {
            printError("invalid tree: no children of csl: " + root.toTreeString());
            return;
        }

        Optional<TurtlePosTree> turtleOpt = root.getChildByLabelContains(srcLibDir.getName())
                .flatMap(m -> m.getChildByLabelContains(srcLibDirTurtleName));
        if (!turtleOpt.isPresent()) {
            printError("invalid tree: no root -> src-lib -> turtle: " + root.toTreeString());
            return;
        }
        if (!turtleOpt.get().children.isEmpty()) {
            printError("invalid tree: turtle, a sibling of csl, is not closed: " + root.toTreeString());
            return;
        }

        TestCodeRunner.succeed();
    }

    ////////////////////////////////////////

    public static void checkFileSearch() {
        new Exercise3Checker().assertFileSearch();
    }

    public void assertFileSearch() {
        if (!askFileTreeExpandTurtle()) {
            return;
        }

        List<Turtle> ts = askTurtlesInLastSingleDisplay(2);
        TurtlePosTree root = getPosTree(ts);

        if (root.children.size() < 2) {
            printError("no search field: " + root.getSubLabels() + " : " + root.toTreeString());
            return;
        }

        TurtlePosTree search = root.children.get(1);

        if (search.turtle.getInput() == null ||
                !(search.turtle.getInput() instanceof TurtleTextField)) {
            printError("not a text field: " + search.turtle.getInput());
            return;
        }

        TurtleTextField fld = (TurtleTextField) search.turtle.getInput();
        fld.input("TurtleDisplay");

        //   root -> { src-lib -> { csl, turtle -> { ... }, ... } , search }
        //=> root -> { src-lib -> { turtle -> { TurtleDisplay.java }  } , search }

        ts = askTurtlesInLastSingleDisplay(2);
        root = getPosTree(ts);

        Optional<TurtlePosTree> srcLib = root.getChildByLabelContains(srcLibDir.getName());
        Optional<TurtlePosTree> turtle = srcLib.flatMap(m -> m.getChildByLabelContains(srcLibDirTurtleName));
        Optional<TurtlePosTree> td = turtle.flatMap(m -> m.getChildByLabelContains("TurtleDisplay.java"));
        Optional<TurtlePosTree> csl = srcLib.flatMap(m -> m.getChildByLabelContains(srcLibDirCslName));

        if (!srcLib.isPresent()) {
            printError("no src-lib: " + root.toTreeString());
            return;
        }

        if (!turtle.isPresent()) {
            printError("no parent of matched item: " + root.toTreeString());
            return;
        }

        if (!td.isPresent()) {
            printError("no matched item: " + root.toTreeString());
            return;
        }

        if (csl.isPresent()) {
            printError("unmatched item: " + root.toTreeString());
            return;
        }


        TestCodeRunner.succeed();
    }

    ////////////////////////////////////////

    private static String getString(Turtle turtle) {
        return turtle.getTrace().stream()
                .filter(TurtleMessage.class::isInstance)
                .map(TurtleMessage.class::cast)
                .map(TurtleMessage::getMessage)
                .reduce("", (p, s) -> s);
    }

    static class TurtlePosTree {
        public TurtlePosTree parent;
        public List<TurtlePosTree> children = new ArrayList<>();
        public Turtle turtle;

        public TurtlePosTree(Turtle turtle) {
            this.turtle = turtle;
        }

        public void add(TurtlePosTree c) {
            c.parent = this;
            children.add(c);
        }

        public boolean isLoop() {
            TurtlePosTree p = parent;
            while (p != null) {
                if (p == this) {
                    return true;
                }
                p = p.parent;
            }
            return false;
        }

        public String getLabel() {
            return Exercise3Checker.getString(turtle);
        }

        @Override
        public String toString() {
            return getLabel() + ": (" + turtle.getX() + "," + turtle.getY() + ")";
        }

        public List<String> getSubLabels() {
            return children.stream()
                    .map(TurtlePosTree::getLabel)
                    .collect(Collectors.toList());
        }

        public Optional<TurtlePosTree> getChildByLabelContains(String label) {
            return children.stream()
                    .filter(c -> c.getLabel().contains(label))
                    .findFirst();
        }

        public String toTreeString() {
            return toTreeString(0);
        }

        protected String toTreeString(int lv) {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < lv; ++i) {
                buf.append("    ");
            }
            buf.append(getLabel());
            if (isLoop()) {
                buf.append(": [LOOP]");
            } else if (!children.isEmpty()) {
                buf.append("{\n");
                for (TurtlePosTree c : children) {
                    buf.append(c.toTreeString(lv + 1)).append("\n");
                }
                for (int i = 0; i < lv; ++i) {
                    buf.append("    ");
                }
                buf.append("}");
            }
            return buf.toString();
        }

    }

    private TurtlePosTree getPosTree(List<Turtle> ts) {
        TurtleDisplay d = askLastSingleDisplay();
        Map<Turtle, TurtlePosTree> nodes = new HashMap<>();

        //constructs a map, Turtle -> TurtlePosTree node
        for (Turtle t : ts) {
            TurtlePosTree node = new TurtlePosTree(t);
            nodes.put(t, node);
        }

        //constructs edges: nodes of an edge have been set to a same point,
        //  saved in overlappedTurtles list of the display.
        for (Turtle[] fromTo : d.getOverlappedTurtles()) {
            TurtlePosTree c = nodes.get(fromTo[0]);
            TurtlePosTree p = nodes.get(fromTo[1]);

            if (c != null && p != null) {
                p.add(c);
            }
        }

        TurtlePosTree root = null;
        for (TurtlePosTree n : nodes.values()) {
            if (n.parent == null) {
                root = n;
            }
        }
        if (root == null) {
            root = nodes.get(ts.get(0));
        }
        return root;
    }

    private TurtleDisplay display;

    private TurtleDisplay askLastSingleDisplay() {
        if (display != null) {
            return display;
        }
        List<TurtleDisplay> ds = TurtleDisplay.getDisplays();
        if (ds.isEmpty()) {
            printError("no display");
        }
        display = ds.get(ds.size() - 1);
        return display;
    }


    //no caching
    private List<Turtle> askTurtlesInLastSingleDisplay(int n) {
        TurtleDisplay d = askLastSingleDisplay();
        List<Turtle> ts = d.getPane().getTurtles();
        if (ts.size() < n) {
            printError("there are " + ts.size() + " turtles < " + n);
        }
        return ts;
    }

    private void printError(String message) {
        TestCodeRunner.printError(message);
    }
}
