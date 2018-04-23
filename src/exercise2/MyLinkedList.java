package exercise2;

public class MyLinkedList implements MyList  {
    LinkedListCell head;
    LinkedListCell tail;

    public MyLinkedList(){
        head = null;
        tail = null;
    }
    public int size(){
        int count =0;
        LinkedListCell cell=head;
        while(cell != null){
            cell =cell.next;
            count++;
        }
        return count;
    }
    public void add(Object o){
        LinkedListCell tmp =new LinkedListCell();
        tmp.next = null;
        tmp.value = o;
        if(head == null){
            head=tmp;
            tail=tmp;
        }
        else {
            tail.next = tmp;
            tail = tmp;
        }

    }

    public Object get(int i){
        int count = 0;
        LinkedListCell cell = head;
        while(cell != null){
            if(count == i){
                return cell.value;
            }
            cell = cell.next;
            count++;
        }
        return null;
    }
    public  MyIterator iterator(){
        return (MyIterator) new MyLinkedListIterator(this.head);
    }
    class LinkedListCell{
        Object value;
        LinkedListCell next;
    }
}
