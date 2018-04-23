package exercise1;

import turtle.Turtle;
import turtle.TurtleDisplay;

public class  TurtleDouble{
    public static void run (){
        TurtleDisplay d = new TurtleDisplay();
        MyTurtle t1 = new MyTurtle();
        Turtle t2 = new Turtle();
        d.addTurtle(t1);
        d.addTurtle(t2);
        
        t1.setPaintTurtle(true);
        t2.setPaintTurtle(true);
        
        TurtleDouble tmp = new TurtleDouble();     
        
        t1.sleep(100);
        t1.move(400);
        t1.sleep(100);
        t1.rotate(90); 
        t1.sleep(100);
        t1.move(300);
        t1.sleep(100);
        
        t2.sleep(100);
        t2.move(400-15);
        t2.sleep(100);
        t2.rotate(90); 
        t2.sleep(100);
        t2.move(300-15);
        t2.sleep(100);
        
        tmp.TuMove(t1);
        tmp.TuMove(t2);
        
        t1.sayTotalDistance();
        
        RandomWalkTurtle t3 = new RandomWalkTurtle();
        d.addTurtle(t3);
        t3.setPaintTurtle(true);
        
        t3.sleep(100);
        t3.move(400-30);
        t3.sleep(100);
        t3.rotate(90); 
        t3.sleep(100);
        t3.move(300-30);
        t3.sleep(100);
        
        t3.walk();
        
    }
    
   public static void TuMove(Turtle t){
             
       t.penDown();
       t.say("start!");
       for(int i=0; i<10; i++){
           for(int j=0; j<10; j++){
               t.move(i);
               t.sleep(10);
           }
           t.rotate(90);
       }
       t.say("end!");
   }
      
}
