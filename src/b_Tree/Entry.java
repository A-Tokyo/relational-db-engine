package b_Tree;

import java.io.Serializable;

public class Entry implements Comparable, Serializable{
	protected Comparable key;
	protected Node leftChild;
//	Node next;
	public Entry(Comparable key){
		this.key = key;
	}
@Override
public int compareTo(Object o) {
	// TODO Auto-generated method stub
	return this.key.compareTo(((Entry)o).key);
}
}
