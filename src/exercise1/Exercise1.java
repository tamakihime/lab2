package exercise1;

import csl.infolab.SetupChecking;
import csl.infolab.TestCodeRunner;

public class Exercise1 {
    public static void main(String[] args) {
        TestCodeRunner.run(Exercise1.class);
    }
    
    public void test00Start() throws Exception {
        new SetupChecking().check();
        TestCodeRunner.succeed();
    }



    public void test01Defined() throws Exception {
        new TurtleDouble().run();
        TestCodeRunner.succeed();
    }


    public void test02MovedSingleLines() {
        new TurtleDouble().run();
        Exercise1Checker.checkSingleLines();
    }

    public void test03MoveDoubleOverlappedLines() {
        new TurtleDouble().run();
        Exercise1Checker.checkDoubleOverlappedLines();
    }


    public void test04MoveDoubleTranslatedLines() {
        new TurtleDouble().run();
        Exercise1Checker.checkDoubleTranslatedLines();
    }


    public void test05MoveDoubleTranslatedLinesWithDifferentType() {
        new TurtleDouble().run();
        Exercise1Checker.checkDoubleTranslatedLinesWithDifferentType();
    }


    public void test06MoveDoubleTranslatedLinesWithDifferentTypeAndMethod() {
        new TurtleDouble().run();
        Exercise1Checker.checkDoubleTranslatedLinesWithDifferentTypeAndStaticMethod();
    }


    public void test07MoveDoubleLinesAndRandom() {
        new TurtleDouble().run();
        Exercise1Checker.checkDoubleLinesAndRandom();
    }

}
