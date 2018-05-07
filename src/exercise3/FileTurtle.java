package exercise3;
import turtle.Turtle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class FileTurtle extends Turtle {
    FileTurtle parent;
    File file;
    List<FileTurtle> children;
    public FileTurtle(FileTurtle p, File f){
        parent = p;
        file = f;
    }
    public FileTurtle(File f){
        this(null, f);
    }

    public File getFile(){
        return file;
    }
    public List<FileTurtle> createChildren(){
        List<FileTurtle> cs = new ArrayList<FileTurtle>();
        if(file.isDirectory()){
            for(File child : file.listFiles()){
                FileTurtle tmp = new FileTurtle(this, child);
                cs.add(tmp);
                if(child.isDirectory()){
                    ExpandButton b = new ExpandButton();
                    tmp.setInput(b);
                }
            }
        }
        return cs;
    }
    public List<FileTurtle> getChildren() {
        if(children == null) children = createChildren();
        return children;
    }

    public  void show(){
        parent.getDisplay().addTurtle(this);
        int j = this.parent.getChildren().indexOf(this);
        TurtleTree.moveChild(parent,this,j,file.getName());

    }
    public void closeSiblings() {
        if(this.parent != null){
            for(FileTurtle i : this.parent.getChildren()){
                if(i != this) i.close();
            }
        }
    }
    public void close() {
        if(this.children == null) return;
        for(FileTurtle i : this.children){
            i.close();
            if(i.getDisplay() != null){
                i.getDisplay().removeTurtle(i);
            }
        }
    }

}
