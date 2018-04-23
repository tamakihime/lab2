package exercise2;

public class MyArrayListIterator implements MyIterator{
    int index = 0;
    MyArrayList tmp;
    public MyArrayListIterator (MyArrayList t) {
        tmp = t;
    }
    public boolean hasNext(){
        if( index < tmp.size()){
            return true;
        }else{
            index = 0;
            return false;
        }
    }
    public Object next() {
        Object nt = tmp.data[index];
        index++;
        return nt;
    }
}
