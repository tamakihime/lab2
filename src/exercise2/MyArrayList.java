package exercise2;

public class MyArrayList implements MyList{
	Object[] data;
	int count;
	
	public MyArrayList(){
		count = 0;
		data = new Object[5];
	}
	public void add(Object o) {
		if(count < data.length){
			data[count] = o;
		}else{
			Object[] tmpdata;
			tmpdata = new Object[data.length*2];
			for(int i=0; i<data.length; i++){
				tmpdata[i] = data[i];
			}
			tmpdata[data.length] = o;
			data = tmpdata;
		}
		count++;
	}
	public int size () {
		return count;
	}
	public Object get(int i) {
		return data[i];
	}
	
	public MyIterator iterator(){
	    return  new MyArrayListIterator(this);
	}

}
