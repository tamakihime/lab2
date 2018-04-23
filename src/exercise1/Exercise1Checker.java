package exercise1;

import csl.infolab.TestCodeRunner;
import turtle.Turtle;
import turtle.TurtleDisplay;
import turtle.TurtleMessage;
import turtle.TurtleMove;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//static check...()
//void assert...() // calls a succeed() directly
//void callAssert...() // calls an assert...() method
//boolean ask...(...) // calls printError(...)
//... get...(...),  boolean is....(...), boolean has...(...)

public class Exercise1Checker {
    public static void checkSingleLines() {
        new Exercise1Checker().assertSingleLines();
    }

    public void assertSingleLines() {
        List<int[]> points = getTurtlesMovedPointsInLastSingleDisplay(1).get(0);
        if (askNonPerpendicularLines(points)) {
            TestCodeRunner.succeed();
        }
    }

    private boolean askNonPerpendicularLines(List<int[]> points) {
        int[] prev = {0, 0};
        int i = 0;
        boolean result = true;
        for (int[] p : points) {
            if (i != 0 && !(prev[0] == p[0] || prev[1] == p[1])) {
                printError("there is a non perpendicular point : [" + i + "] " + Arrays.toString(prev) + " -> " + Arrays.toString(p));
                result = false;
                break;
            }
            ++i;
            prev = p;
        }
        if (points.isEmpty()) {
            printError("no drawn lines");
            result = false;
        }
        return result;
    }

    public static void checkDoubleOverlappedLines() {
        new Exercise1Checker().assertDoubleOverlappedLines();
    }

    public void assertDoubleOverlappedLines() {
        List<List<int[]>> points = getTurtlesMovedPointsInLastSingleDisplay(2);
        if (points.size() < 2) {
            return;
        }

        List<int[]> ps1 = points.get(0);
        List<int[]> ps2 = points.get(1);
        if (askNonPerpendicularOverlappedLines(ps1, ps2)) {
            TestCodeRunner.succeed();
        }
    }

    private boolean askNonPerpendicularOverlappedLines(List<int[]> ps1, List<int[]> ps2) {
        boolean result = true;
        if (ps1.size() != ps2.size()) {
            printError("different size of drawing points of 2 turtles: " + ps1.size() + " != " + ps2.size());
            result = false;
        }

        result &= askNonPerpendicularLines(ps1);
        result &= askNonPerpendicularLines(ps2);

        double preX = 0;
        double preY = 0;
        for (int i = 0, l = Math.min(ps1.size(), ps2.size()); i < l; ++i) {
            int[] p1 = ps1.get(i);
            int[] p2 = ps2.get(i);
            double px = ((double) p1[0]) / ((double) p2[0]);
            double py = ((double) p1[1]) / ((double) p2[1]);
            if (i != 0 && (!isEqualToDouble(preX, px) || !isEqualToDouble(preY, py))) {
                printError("not a same point of a line: " +
                        String.format("[%d] (%d,%d) != (%d,%d)", i, p1[0], p1[1], p2[0], p2[1]));
                result = false;
                break;
            }
            preX = px;
            preY = py;
        }
        return result;
    }

    public static void checkDoubleTranslatedLines() {
        new Exercise1Checker().assertDoubleTranslatedLines();
    }

    public void assertDoubleTranslatedLines() {
        List<List<int[]>> points = getTurtlesMovedPointsInLastSingleDisplay(2);
        if (points.size() < 2) {
            return;
        }

        List<int[]> ps1 = points.get(0);
        List<int[]> ps2 = points.get(1);
        if (askNonPerpendicularTranslatedLines(ps1, ps2)) {
            TestCodeRunner.succeed();
        }
    }

    private boolean askNonPerpendicularTranslatedLines(List<int[]> ps1, List<int[]> ps2) {
        boolean result = true;
        if (ps1.size() != ps2.size()) {
            printError("different size of drawing points of 2 turtles: " + ps1.size() + " != " + ps2.size());
            result = false;
        }

        result &= askNonPerpendicularLines(ps1);
        result &= askNonPerpendicularLines(ps2);

        double preX = 0;
        double preY = 0;
        for (int i = 0, l = Math.min(ps1.size(), ps2.size()); i < l; ++i) {
            int[] p1 = ps1.get(i);
            int[] p2 = ps2.get(i);
            double px = ((double) p1[0]) / ((double) p2[0]);
            double py = ((double) p1[1]) / ((double) p2[1]);
            if (i != 0 && (!isEqualToDouble(preX, px) || !isEqualToDouble(preY, py) || p1[0] == p2[0] || p1[1] == p2[1])) {
                printError("not a slanting translated line : " +
                        String.format("[%d] (%.3f,%.3f) -> (%d/%d=%.3f, %d/%d=%.3f) ",
                                i,
                                preX, preY,
                                p1[0], p2[0], px,
                                p1[1], p2[1], py));
                result = false;
                break;
            }
            preX = px;
            preY = py;
        }
        return result;
    }

    private boolean isEqualToDouble(double a, double b) {
        return Math.abs(b - a) <= 0.1;
    }

    public static void checkDoubleTranslatedLinesWithDifferentType() {
        new Exercise1Checker().callAssertDoubleTranslatedLinesWithDifferentType();
    }

    public void callAssertDoubleTranslatedLinesWithDifferentType() {
        List<Turtle> ts = askTurtlesInLastSingleDisplay(2);
        if (ts.size() < 2) {
            return;
        }

        if (!askDifferentTypeTurtleOverridingMove(ts)) {
            return;
        }

        if (!askDifferentTypeTurtleHasTotalMessage(ts)) {
            return;
        }

        assertDoubleTranslatedLines();
    }


    private List<List<int[]>> movePoints;

    private List<List<int[]>> getTurtlesMovedPointsInLastSingleDisplay(int n) {
        if (movePoints != null && movePoints.size() == n) {
            return movePoints;
        }
        movePoints = askTurtlesInLastSingleDisplay(n).stream()
                .map(this::getTurtleMovedPoints)
                .collect(Collectors.toList());
        return movePoints;
    }

    private List<int[]> getTurtleMovedPoints(Turtle turtle) {
        return turtle.getTrace().stream()
                .filter(t -> t instanceof TurtleMove)
                .map(TurtleMove.class::cast)
                .filter(TurtleMove::isPen)
                .map(tm -> new int[]{tm.getX2(), tm.getY2()})
                .collect(Collectors.toList());
    }

    private List<Turtle> turtles;

    private List<Turtle> askTurtlesInLastSingleDisplay(int n) {
        if (turtles != null) {
            return turtles;
        }
        TurtleDisplay d = askLastSingleDisplay();
        List<Turtle> ts = d.getPane().getTurtles();
        if (ts.size() < n) {
            printError("there are " + ts.size() + " turtles < " + n);
        }
        turtles = ts;
        return ts;
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

    private boolean askDifferentTypeTurtleOverridingMove(List<Turtle> ts) {
        List<Turtle> diffTypedTurtles = ts.stream()
                .filter(this::isOverridingMove)
                .collect(Collectors.toList());
        if (diffTypedTurtles.size() != 1) {
            printError("1 non-original turtle with overriding 'move(int)' is required: " + ts);
            return false;
        } else {
            return true;
        }
    }

    private boolean isOverridingMove(Turtle t) {
        return !t.getClass().equals(Turtle.class) &&
                Arrays.stream(t.getClass().getDeclaredMethods())
                        .filter(m -> m.getName().equals("move") &&
                                m.getParameterCount() == 1 &&
                                m.getParameterTypes()[0].equals(int.class)) //overrides move(int)
                        .count() == 1;
    }

    private boolean askDifferentTypeTurtleHasTotalMessage(List<Turtle> ts) {
        List<Turtle> diffTypedTurtles = ts.stream()
                .filter(this::isOverridingMove)
                .collect(Collectors.toList());
        if (diffTypedTurtles.size() == 0) {
            printError("1 non-original turtle with overriding 'move(int)' is required: " + ts);
            return false;
        } else {
            if (diffTypedTurtles.stream()
                    .filter(this::hasTotalMessage)
                    .count() > 0) {
                return true;
            } else {
                printError("a non-original turtle must display the total distance message");
                return false;
            }
        }
    }

    private boolean hasTotalMessage(Turtle t) {
        Pattern pat = Pattern.compile("total\\s*?:\\s*(\\d+)");
        return t.getTrace().stream()
                .filter(TurtleMessage.class::isInstance)
                .map(TurtleMessage.class::cast)
                .map(TurtleMessage::getMessage)
                .map(msg -> pat.matcher(msg.toLowerCase()))
                .filter(m -> m.matches() && Integer.parseInt(m.group(1)) > 100)
                .count() > 0;
    }

    /////////////////////////////////////

    public static void checkDoubleTranslatedLinesWithDifferentTypeAndStaticMethod() {
        new Exercise1Checker().callAssertDoubleTranslatedLinesWithDifferentTypeAndStaticMethod();
    }

    public void callAssertDoubleTranslatedLinesWithDifferentTypeAndStaticMethod() {
        if (!askRefactoredInStaticMethod()) {
            return;
        }
        callAssertDoubleTranslatedLinesWithDifferentType();
    }

    private boolean askRefactoredInStaticMethod() {
        String name = "exercise1.TurtleDouble";
        try {
            Class<?> cls = Class.forName(name);

            List<Method> ms = Arrays.stream(cls.getDeclaredMethods())
                    //.filter(f -> Modifier.isStatic(f.getModifiers()))
                    .filter(f -> f.getParameterCount() >= 1 &&
                            Arrays.stream(f.getParameterTypes())
                                    .filter(Turtle.class::equals)
                                    .collect(Collectors.toList()).size() == 1)
                    .collect(Collectors.toList());

            if (ms.isEmpty()) {
                printError("no method taking 1 Turtle");
                return false;
            } else {
                return true;
            }
        } catch (ClassNotFoundException e) {
            printError("no class named " + name +
                    " : you should check spelling of your class if defined");
            return false;
        }
    }

    private void printError(String message) {
        TestCodeRunner.printError(message);
    }

    private void println(String message) {
        TestCodeRunner.println(message);
    }


    /////////////////////////////////////

    public static void checkDoubleLinesAndRandom() {
        new Exercise1Checker().assertDoubleLinesAndRandom();
    }

    public void assertDoubleLinesAndRandom() {
        List<Turtle> ts = askTurtlesInLastSingleDisplay(3);
        if (ts.size() < 3) {
            return;
        }

        if (!askDifferentTypeTurtleOverridingMove(ts)) {
            return;
        }

        if (!askDifferentTypeTurtleHasTotalMessage(ts)) {
            return;
        }

        //checking walk()
        List<Turtle> randomTurtle = ts.stream()
                .filter(t -> !t.getClass().equals(Turtle.class)
                        && Arrays.stream(t.getClass().getDeclaredMethods())
                        .filter(m -> m.getName().equals("walk") &&
                                m.getParameterCount() == 0 &&
                                !Modifier.isStatic(m.getModifiers()) &&
                                Modifier.isPublic(m.getModifiers())).count() == 1)
                .collect(Collectors.toList());

        if (randomTurtle.size() != 1) {
            printError("1 non-original turtle having 'walk()' is required: " + ts);
            return;
        }

        List<int[]> randPoints = getTurtleMovedPoints(randomTurtle.get(0));
        if (randPoints.size() < 50) {
            printError("less points than 50 : " + randPoints.size());
            return;
        }

        TurtleDisplay d = randomTurtle.get(0).getDisplay();
        int[] window = {0, 0, d.getWidth(), d.getHeight()};

        int outPoints = (int) IntStream.range(1, randPoints.size())
                .filter(i -> !isWithinWindow(randPoints.get(i - 1), window) &&
                        !isWithinWindow(randPoints.get(i), window))
                .count();
        if (outPoints > 0) {
            printError("successively out of the window : " + outPoints + " points");
            return;
        }

        Set<String> uniquePoints = randPoints.stream()
                .map(p -> p[0] + "," + p[1])
                .collect(Collectors.toSet());
        if (uniquePoints.size() < (randPoints.size() * 0.40)) {
            Map<String,Integer> counts = new HashMap<>();
            randPoints.stream()
                    .map(p -> p[0] + "," + p[1])
                    .forEach(s -> counts.compute(s, (k,v) -> v == null ? 0 : v + 1));
            printError("non random points: " + (randPoints.size() - uniquePoints.size()) + " : " + counts);
            return;
        }

        List<Turtle> squareTurtles = ts.stream()
                .filter(t -> !randomTurtle.contains(t))
                .collect(Collectors.toList());

        if (!askNonPerpendicularTranslatedLines(
                getTurtleMovedPoints(squareTurtles.get(0)),
                getTurtleMovedPoints(squareTurtles.get(1)))) {
            printError(""); //TODO ????
            return;
        }

        TestCodeRunner.succeed();
    }

    private boolean isWithinWindow(int[] point, int[] window) {
        return window[0] <= point[0] && point[0] <= window[2] &&
                window[1] <= point[1] && point[1] <= window[3];
    }
}
