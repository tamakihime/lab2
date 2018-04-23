package exercise1;
import turtle.Turtle;
public class MyTurtle extends Turtle{
    int totalDistance;
    public MyTurtle(){
        this.totalDistance = 0;
    }
    public void move(int distance){ 
        super.move(distance);
        this.totalDistance += distance;
    }
    public void sayTotalDistance(){
        say("Total: " + this.totalDistance);
    }
}
