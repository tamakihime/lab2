package exercise2;

public class MyLinkedListIterator  implements MyIterator{
    MyLinkedList.LinkedListCell tmp;
    public MyLinkedListIterator(MyLinkedList.LinkedListCell t) {
        tmp = t;
    }
    public boolean hasNext(){
        if(tmp != null){
            return true;
        }else{
            return  false;
        }
    }
    public Object next() {
        Object nt = tmp.value;
        tmp = tmp.next;
        return nt;
    }
}