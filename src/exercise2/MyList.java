package exercise2;

public interface MyList  {
    public void add(Object o);
    public int size();
    public Object get(int i);
    public MyIterator iterator();
}

