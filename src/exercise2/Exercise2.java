package exercise2;

import csl.infolab.TestCodeRunner;
import turtle.Turtle;
import turtle.TurtleDisplay;
import turtle.example.TurtleMovePattern;

import java.util.HashSet;
import java.util.Set;

public class Exercise2 {
    public static void main(String[] args) {
        TestCodeRunner.run(Exercise2.class);
    }



    public void test01ArrayListNew() {
        MyArrayList list = new MyArrayList();
        if (list.size() != 0) {
            TestCodeRunner.printError("size " + list.size() + " != 0");
            return;
        }
        TestCodeRunner.succeed();
    }

    public void test02ArrayListOne() {
        MyArrayList list = new MyArrayList();
        list.add("hello");
        TestCodeRunner.succeed();
    }


    public void test03ArrayListOneGet() {
        MyArrayList list = new MyArrayList();
        list.add("hello");
        if (list.size() != 1) {
            TestCodeRunner.printError("size " + list.size() + " != 1");
            return;
        }
        if (!list.get(0).equals("hello")) {
            TestCodeRunner.printError("unexpected get(0) " + list.get(0));
            return;
        }
        TestCodeRunner.succeed();
    }


    public void test04ArrayListFive() {
        MyArrayList list = new MyArrayList();

        //adding various type objects
        list.add("hello");
        list.add(Integer.valueOf(20));
        list.add(Double.valueOf(123.4));
        Turtle turtle = new Turtle();
        list.add(turtle);
        list.add(this);

        if (list.size() != 5) {
            TestCodeRunner.printError("size " + list.size() + " != 5");
            return;
        }
        if (!list.get(0).equals("hello")) {
            TestCodeRunner.printError("unexpected get(0) " + list.get(0));
            return;
        }
        if (!list.get(1).equals(Integer.valueOf(20))) {
            TestCodeRunner.printError("unexpected get(1) " + list.get(1));
            return;
        }
        if (!list.get(2).equals(Double.valueOf(123.4))) {
            TestCodeRunner.printError("unexpected get(2) " + list.get(2));
            return;
        }
        if (!list.get(3).equals(turtle)) {
            TestCodeRunner.printError("unexpected get(3) " + list.get(3));
            return;
        }
        if (!list.get(4).equals(this)) {
            TestCodeRunner.printError("unexpected get(3) " + list.get(3));
            return;
        }
        TestCodeRunner.succeed();
    }


    public void test05ArrayListTen() {
        MyArrayList list = new MyArrayList();
        for (int i = 0; i < 10; ++i) {
            list.add("item-" + i);
        }
        if (list.size() != 10) {
            TestCodeRunner.printError("size " + list.size() + " != 10");
            return;
        }

        for (int i = 0; i < 10; ++i) {
            Object o = list.get(i);
            if (!o.equals("item-" + i)) {
                TestCodeRunner.printError("unexpected get(" + i + ") " + list.get(i));
                return;
            }
        }

        TestCodeRunner.succeed();
    }

    public void test06ArrayListThirty() {
        MyArrayList list = new MyArrayList();
        for (int i = 0; i < 10; ++i) {
            list.add("item-" + i);
        }
        for (int i = 10; i < 30; ++i) {
            list.add(Integer.valueOf(i));
        }
        if (list.size() != 30) {
            TestCodeRunner.printError("size " + list.size() + " != 10");
            return;
        }

        for (int i = 0; i < 30; ++i) {
            Object o = list.get(i);
            if (i < 10) {
                if (!o.equals("item-" + i)) {
                    TestCodeRunner.printError("unexpected get(" + i + ") " + list.get(i));
                    return;
                }
            } else {
                if (!o.equals(Integer.valueOf(i))) {
                    TestCodeRunner.printError("unexpected get(" + i + ") " + list.get(i));
                    return;
                }
            }
        }

        TestCodeRunner.succeed();
    }

    public void test07ArrayListInterface() {
        MyList list = new MyArrayList();
        list.add("hello");
        if (list.size() != 1) {
            TestCodeRunner.printError("size " + list.size() + " != 0");
            return;
        }
        if (!list.get(0).equals("hello")) {
            TestCodeRunner.printError("unexpected get(0) " + list.get(0));
            return;
        }
        TestCodeRunner.succeed();
    }


    public void test08LinkedList() {
        MyLinkedList list = new MyLinkedList();
        if (list.size() != 0) {
            TestCodeRunner.printError("size " + list.size() + " != 0");
            return;
        }
        TestCodeRunner.succeed();
    }


    public void test09LinkedListOne() {
        MyLinkedList list = new MyLinkedList();
        list.add("hello");
        TestCodeRunner.succeed();
    }


    public void test10LinkedListOneGet() {
        MyList list = new MyLinkedList();
        list.add("hello");
        if (list.size() != 1) {
            TestCodeRunner.printError("size " + list.size() + " != 0");
            return;
        }
        if (!list.get(0).equals("hello")) {
            TestCodeRunner.printError("unexpected get(0) " + list.get(0));
            return;
        }
        TestCodeRunner.succeed();
    }


    public void test11LinkedListFive() {
        MyList list = new MyLinkedList();

        //adding various type objects
        list.add("hello");
        list.add(Integer.valueOf(20));
        list.add(Double.valueOf(123.4));
        Turtle turtle = new Turtle();
        list.add(turtle);
        list.add(this);

        if (list.size() != 5) {
            TestCodeRunner.printError("size " + list.size() + " != 5");
            return;
        }
        if (!list.get(0).equals("hello")) {
            TestCodeRunner.printError("unexpected get(0) " + list.get(0));
            return;
        }
        if (!list.get(1).equals(Integer.valueOf(20))) {
            TestCodeRunner.printError("unexpected get(1) " + list.get(1));
            return;
        }
        if (!list.get(2).equals(Double.valueOf(123.4))) {
            TestCodeRunner.printError("unexpected get(2) " + list.get(2));
            return;
        }
        if (!list.get(3).equals(turtle)) {
            TestCodeRunner.printError("unexpected get(3) " + list.get(3));
            return;
        }
        if (!list.get(4).equals(this)) {
            TestCodeRunner.printError("unexpected get(3) " + list.get(3));
            return;
        }
        TestCodeRunner.succeed();
    }

    public void test12LinkedListThirty() {
        MyList list = new MyLinkedList();
        for (int i = 0; i < 10; ++i) {
            list.add("item-" + i);
        }
        for (int i = 10; i < 30; ++i) {
            list.add(Integer.valueOf(i));
        }
        if (list.size() != 30) {
            TestCodeRunner.printError("size " + list.size() + " != 10");
            return;
        }

        for (int i = 0; i < 30; ++i) {
            Object o = list.get(i);
            if (i < 10) {
                if (!o.equals("item-" + i)) {
                    TestCodeRunner.printError("unexpected get(" + i + ") " + list.get(i));
                    return;
                }
            } else {
                if (!o.equals(Integer.valueOf(i))) {
                    TestCodeRunner.printError("unexpected get(" + i + ") " + list.get(i));
                    return;
                }
            }
        }

        TestCodeRunner.succeed();
    }


    public void test13ListPerformance() {
        MyArrayList al = new MyArrayList();
        MyLinkedList ll = new MyLinkedList();

        int addLoop = 20_000_000;

        //adding an object to an array list
        long arrayAddTime = System.nanoTime();
        for (int i = 0; i < addLoop; ++i) {
            al.add("item");
        }
        arrayAddTime = System.nanoTime() - arrayAddTime;

        //adding an object to a linked list
        long linkedAddTime = System.nanoTime();
        for (int i = 0; i < addLoop; ++i) {
            ll.add("item");
        }
        linkedAddTime = System.nanoTime() - linkedAddTime;

        double addTimeRatio = (double) linkedAddTime / (double) arrayAddTime;
        TestCodeRunner.println(String.format("adding: time ratio: %.3f = linked:%,d ns / array:%,d ns",
                addTimeRatio, linkedAddTime, arrayAddTime));
        if (addTimeRatio < 1.0) {
            TestCodeRunner.printError("irregular adding time ratio: " + addTimeRatio);
            return;
        }

        //size checking
        if (al.size() != addLoop) {
            TestCodeRunner.printError("array size " + al.size() + " != " + addLoop);
            return;
        }
        if (ll.size() != addLoop) {
            TestCodeRunner.printError("linked size " + ll.size() + " != " + addLoop);
            return;
        }

        int getLoop = 200;

        //getting the last object from an array list
        long arrayGetTime = System.nanoTime();
        for (int i = 0; i < getLoop; ++i) {
            if (!al.get(addLoop - 1).equals("item")) {
                TestCodeRunner.printError("array get(" + i + ") " + al.get(i));
                return;
            }
        }
        arrayGetTime = System.nanoTime() - arrayGetTime;

        //getting the last object from a linked list
        long linkedGetTime = System.nanoTime();
        for (int i = 0; i < getLoop; ++i) {
            if (!ll.get(addLoop - 1).equals("item")) {
                TestCodeRunner.printError("array get(" + i + ") " + ll.get(i));
                return;
            }
        }
        linkedGetTime = System.nanoTime() - linkedGetTime;

        double getTimeRatio = (double) linkedGetTime / (double) arrayGetTime;
        TestCodeRunner.println(String.format("getting: time ratio: %.3f = linked:%,d ns / array:%,d ns",
                getTimeRatio, linkedGetTime, arrayGetTime));

        if (getTimeRatio < 10.0) {
            TestCodeRunner.printError("irregular getting time ratio: " + getTimeRatio);
            return;
        }

        TestCodeRunner.succeed();
    }

    public void test14ArrayListIteratorEmpty() {
        MyList list = new MyArrayList();

        MyIterator i = list.iterator();
        if (i.hasNext()) {
            TestCodeRunner.printError("irregular empty iterator hasNext()");
            return;
        }

        TestCodeRunner.succeed();
    }


    public void test15ArrayListIterator() {
        MyList list = new MyArrayList();
        for (int i = 0; i < 20; ++i) {
            list.add("item-" + i);
        }

        MyIterator i = list.iterator();
        int count = 0;
        while (i.hasNext()) {
            Object o = i.next();
            if (!o.equals("item-" + count)) {
                TestCodeRunner.printError("unexpected next() [" + count + "] " + o);
                return;
            }
            ++count;
        }
        if (count != 20) {
            TestCodeRunner.printError("irregular iterator loop : " + count);
            return;
        }

        TestCodeRunner.succeed();
    }


    public void test16LinkedListIteratorEmpty() {
        MyList list = new MyLinkedList();

        MyIterator i = list.iterator();
        if (i.hasNext()) {
            TestCodeRunner.printError("irregular empty iterator hasNext()");
            return;
        }

        TestCodeRunner.succeed();
    }


 public void test17LinkedListIterator() {
        MyList list = new MyLinkedList();
        for (int i = 0; i < 20; ++i) {
            list.add("item-" + i);
        }

        MyIterator i = list.iterator();
        int count = 0;
        while (i.hasNext()) {
            Object o = i.next();
            if (!o.equals("item-" + count)) {
                TestCodeRunner.printError("unexpected next() [" + count + "] " + o);
                return;
            }
            ++count;
        }
        if (count != 20) {
            TestCodeRunner.printError("irregular iterator loop : " + count);
            return;
        }

        TestCodeRunner.succeed();
    }


    public void test18ListIteratorPerformance() {
        MyArrayList al = new MyArrayList();
        MyLinkedList ll = new MyLinkedList();

        int addLoop = 20_000_000;

        //adding an object to an array list
        for (int i = 0; i < addLoop; ++i) {
            al.add("item");
        }

        //adding an object to a linked list
        for (int i = 0; i < addLoop; ++i) {
            ll.add("item");
        }

        //size checking
        if (al.size() != addLoop) {
            TestCodeRunner.printError("array size " + al.size() + " != " + addLoop);
            return;
        }
        if (ll.size() != addLoop) {
            TestCodeRunner.printError("linked size " + ll.size() + " != " + addLoop);
            return;
        }

        int getLoop = 200_000;
        TestCodeRunner.println("start MyArrayList iterator()");

        //getting the last object from an array list
        long arrayGetTime = System.nanoTime();
        MyIterator ai = al.iterator();
        while (ai.hasNext()) {
            Object o = ai.next();
            if (!o.equals("item")) {
                TestCodeRunner.printError("array next() " + o);
                return;
            }
        }
        arrayGetTime = System.nanoTime() - arrayGetTime;

        TestCodeRunner.println("start MyLinkedList iterator()");

        //getting the last object from a linked list
        long linkedGetTime = System.nanoTime();
        MyIterator li = ll.iterator();
        while (li.hasNext()) {
            Object o = li.next();
            if (!o.equals("item")) {
                TestCodeRunner.printError("array next() " + o);
                return;
            }
        }
        linkedGetTime = System.nanoTime() - linkedGetTime;

        double getTimeRatio = (double) linkedGetTime / (double) arrayGetTime;
        TestCodeRunner.println(String.format("getting: time ratio: %.3f = linked:%,d ns / array:%,d ns",
                getTimeRatio, linkedGetTime, arrayGetTime));

        if (getTimeRatio > 10.0) {
            TestCodeRunner.printError("irregular getting time ratio: " + getTimeRatio);
            return;
        }

        TestCodeRunner.succeed();
    }
/*
    public void test19TurtleFireworks() {
        //setting up
        TurtleDisplay d = new TurtleDisplay();
        TurtleList tl = new TurtleList();
        d.addTurtle(tl);

        for (int i = 0; i < 35; ++i) {
            Turtle t = new Turtle();
            d.addTurtle(t);
            tl.addTurtle(t);
        }

        //animation
        TurtleMovePattern.moveCenter(tl);
        int centerX = tl.getX();
        int centerY = tl.getY();

        tl.rotateAll(0, 10); //direction: t1:0, t2:10, t3:20, t4:30, ...
        TurtleMovePattern.move(tl);

        checkTurtleFireworks(tl, centerX, centerY);
    }

    private void checkTurtleFireworks(TurtleList tl, int centerX, int centerY) {
        MyList list = tl.getTurtles();
        if (list.size() != 35) {
            TestCodeRunner.printError("invalid size: " + list.size() + " != 36");
            return;
        }

        System.err.println("center: x=" + centerX + " y=" + centerY);


        System.err.println(String.format("%2d: x=%3d y=%3d r=%3d len=%3d", 0, tl.getX(), tl.getY(), tl.getDirection(),
                getDistance(tl, centerX, centerY)));

        Set<Integer> rSet = new HashSet<>();
        Set<Integer> lenSet = new HashSet<>();
        for (int i = 0; i < list.size(); ++i) {
            Turtle t = (Turtle) list.get(i);
            rSet.add(t.getDirection());
            int len = getDistance(t, centerX, centerY);
            len -= len % 10; //margin of error
            lenSet.add(len);
            System.err.println(String.format("%2d: x=%3d y=%3d r=%3d len=%3d", i + 1, t.getX(), t.getY(), t.getDirection(), len));
        }
        if (rSet.size() != 35) {
            TestCodeRunner.printError("there are same directions: " + rSet.size() + " : " + rSet);
            return;
        }
        if (lenSet.size() != 1) {
            TestCodeRunner.printError("there are abnormal points from the center: " + lenSet);
            return;
        }
        TestCodeRunner.succeed();
    }

    private int getDistance(Turtle t, int centerX, int centerY) {
        return (int) (Math.sqrt(
                Math.pow(Math.abs(t.getX() - centerX), 2) +
                        Math.pow(Math.abs(t.getY() - centerY), 2)));
    }

    */
}
