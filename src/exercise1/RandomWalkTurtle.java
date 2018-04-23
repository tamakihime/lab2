package exercise1;
import turtle.Turtle;
public class RandomWalkTurtle extends Turtle{
    int tx,ty;
    public void walk(){
        super.penDown();
        int fl = 0;
        for(int i=0; i<50; i++){
         
            int mx = this.getDisplay().getWidth();
            int my = this.getDisplay().getHeight();
            
            int nx = super.getX();
            int ny = super.getY();
            
            //test
            System.out.println("mx" + mx + "my:" + my);
            System.out.println("x" + super.getX());
            System.out.println("y" + super.getY());
                     
            if(nx <= mx && 0 <= nx && ny <= my && 0 <= ny){
                fl = (int)(Math.random()*200);
                super.move(fl); 
                super.rotate((int)(Math.random()*360));
                super.sleep(1);
            }else{               
                super.rotate(180);
                super.move(fl);
                super.sleep(1);
            }
        }
    }
}
