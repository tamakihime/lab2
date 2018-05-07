package exercise3;

import csl.infolab.TestCodeRunner;
import turtle.Turtle;
import turtle.TurtleDisplay;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise3 {
    public static void main(String[] args) {
        TestCodeRunner.run(Exercise3.class);
    }


    public void test01Root() {
        new TurtleTree().root();
        Exercise3Checker.checkRoot();
    }

    public void test02Expand() {
        new TurtleTree().runAuto();
        Exercise3Checker.checkExpanded();
    }

    public void test03ExpandRecursively() {
        new TurtleTree().runAuto();
        Exercise3Checker.checkExpandedRecursively();
    }

    public void test04ExpandByButton() {
        new TurtleTree().runButton();
        Exercise3Checker.checkExpandedByButton();
    }

    public void test05ExpandByButtonRecursively() {
        new TurtleTree().runButton();
        Exercise3Checker.checkExpandedByButtonRecursively();
    }

    public void test06FileTurtleCreateChildren() {
        Exercise3Checker.setupSrcLibDirForTest();
        FileTurtle srcLibTurtle = new FileTurtle(new File("src-lib"));

        List<FileTurtle> children = srcLibTurtle.createChildren();

        if (children.size() < 2) {
            TestCodeRunner.printError("unexpected size of children: " + children.size());
            return;
        }

        boolean turtleOk = false;
        boolean cslOk = false;
        for (FileTurtle c : children) {
            String name = c.getFile().getName();
            if (name.equals("turtle")) {
                turtleOk = true;
            } else if (name.equals("csl")) {
                cslOk = true;
            } else {
                TestCodeRunner.printError("unexpected file: " + name);
            }
        }

        if (!turtleOk) {
            TestCodeRunner.printError("no turtle");
            return;
        }
        if (!cslOk) {
            TestCodeRunner.printError("no csl");
            return;
        }

        TestCodeRunner.succeed();
    }

    public void test07FileTreeDirectoryOpen() {
        Exercise3Checker.setupSrcLibDirForTest();
        new TurtleTree().runFileTree();
        Exercise3Checker.checkFileTreeDirectoryOpen();
    }

    public void test08FileTreeExpanding() {
        Exercise3Checker.setupSrcLibDirForTest();
        new TurtleTree().runFileTree();
        Exercise3Checker.checkFileTreeExpanding();
    }

    public void test09FileTurtleGetChildren() {
        Exercise3Checker.setupSrcLibDirForTest();
        FileTurtle srcLibTurtle = new FileTurtle(new File("src-lib"));

        List<FileTurtle> fs = srcLibTurtle.getChildren();
        List<FileTurtle> fs2 = srcLibTurtle.getChildren();
        if (fs != fs2) {
            TestCodeRunner.printError("it needs to return an unique ist: " + fs + " vs " + fs2);
        }

        List<File> srcLibChildFiles = new ArrayList<>();
        for (File file : srcLibTurtle.getFile().listFiles()) {
            srcLibChildFiles.add(file);
        }

        Field parentField = null;
        try {
            parentField = FileTurtle.class.getDeclaredField("parent");
        } catch (Exception ex) {
            TestCodeRunner.println("error for obtaining parent field: " + ex);
        }

        List<File> createdChildFiles = new ArrayList<>();
        for (FileTurtle child : fs) {
            createdChildFiles.add(child.getFile());

            if (parentField != null) {
                try {
                    Object p = parentField.get(child);
                    if (p != srcLibTurtle) {
                        TestCodeRunner.printError("invalid parent file: " + p + " != " + srcLibTurtle);
                    }
                } catch (Exception ex) {
                    TestCodeRunner.println("error for obtaining parent field: " + ex);
                }
            }
        }

        if (srcLibChildFiles.size() != createdChildFiles.size()) {
            TestCodeRunner.printError("unexpected number of child FileTurtles: " +
                    "expected: " + createdChildFiles.size() + " vs actual: " + srcLibChildFiles.size() +
                    "\n   expected-files: " + createdChildFiles + "\n  actual-files: " + srcLibChildFiles);
        }

        TestCodeRunner.succeed();
    }

    public void test10FileTurtleShow() {
        Exercise3Checker.setupSrcLibDirForTest();
        TurtleDisplay d = new TurtleDisplay();
        File srcLibDir = new File("src-lib");
        FileTurtle srcLibTurtle = new FileTurtle(srcLibDir);
        d.addTurtle(srcLibTurtle);

        srcLibTurtle.move(100, 100);

        List<File> srcLibChildFiles = new ArrayList<>();
        for (File f : srcLibTurtle.getFile().listFiles()) {
            srcLibChildFiles.add(f);
        }

        for (FileTurtle child : srcLibTurtle.getChildren()) {
            child.show();

            if (!d.containsTurtle(child)) {
                TestCodeRunner.printError("the child is not shown: " + child.getFile());
                return;
            }
        }

        List<File> displayedFiles = new ArrayList<>();
        for (Turtle displayedTurtle : d.getPane().getTurtles()) {
            if (displayedTurtle instanceof FileTurtle) {
                displayedFiles.add(((FileTurtle) displayedTurtle).getFile());
            }
        }

        File turtleDir = new File(srcLibDir, "turtle");
        if (!displayedFiles.contains(turtleDir)) {
            TestCodeRunner.printError("invalid displayed files; no src-lib/turtle: " + displayedFiles);
        }
        if (displayedFiles.contains(new File(turtleDir, "Turtle.java"))) {
            TestCodeRunner.printError("invalid displayed files; Turtle.java is displayed: " + displayedFiles);
        }

        TestCodeRunner.succeed();
    }

    public void test11FileTurtleCloseSiblings() {
        Exercise3Checker.setupSrcLibDirForTest();
        TurtleDisplay d = new TurtleDisplay();
        FileTurtle srcLibTurtle = new FileTurtle(new File("src-lib"));
        d.addTurtle(srcLibTurtle);

        srcLibTurtle.move(100, 100);

        FileTurtle turtle = null;
        for (FileTurtle child : srcLibTurtle.getChildren()) {
            child.show();

            for (FileTurtle childChild : child.getChildren()) {
                childChild.show();
            }
            if (child.getFile().getName().equals("turtle")) {
                turtle = child;
            }

            if (!d.containsTurtle(child)) {
                TestCodeRunner.printError("the child is not shown: " + child.getFile());
                return;
            }
        }

        if (turtle == null) {
            return;
        }

        turtle.closeSiblings();

        for (FileTurtle child : srcLibTurtle.getChildren()) {
            if (child.getFile().getName().equals("csl")) {
                for (FileTurtle childChild : child.getChildren()) {
                    if (d.containsTurtle(childChild)) { //displayed
                        TestCodeRunner.printError("unexpectedly displayed: " + childChild.getFile());
                        return;
                    }
                }
            }
            if (child.getFile().getName().equals("turtle")) {
                for (FileTurtle childChild : child.getChildren()) {
                    if (!d.containsTurtle(childChild)) { //hidden
                        TestCodeRunner.printError("unexpectedly closed: " + childChild.getFile());
                        return;
                    }
                }
            }
        }


        TestCodeRunner.succeed();
    }


    public void test12FileTreeExpandingWithClosing() {
        Exercise3Checker.setupSrcLibDirForTest();
        new TurtleTree().runFileTree();
        Exercise3Checker.checkFileTreeExpandingWithClosing();
    }
/*
    public void test13FileSearch() {
        Exercise3Checker.setupSrcLibDirForTest();
        new TurtleTree().runFileTree();
        Exercise3Checker.checkFileSearch();
    }


    */
}
