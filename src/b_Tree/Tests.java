package b_Tree;

import java.util.Hashtable;

import jarvis.tuple;

public class Tests {
	public static void main(String[] args) {
		
		//worked on m3 btree only because it used tuple as value
		B__Tree x = new B__Tree(4);

		Hashtable<String, Object> ctblColNameValue2 = new Hashtable<String, Object>();
		ctblColNameValue2.put("ID", Integer.valueOf("1"));
		ctblColNameValue2.put("Name", "Data Bases II");
		ctblColNameValue2.put("Code", "CSEN 604");
		ctblColNameValue2.put("Hours", Integer.valueOf("4"));
		ctblColNameValue2.put("Semester", Integer.valueOf("6"));
		ctblColNameValue2.put("Major_ID", Integer.valueOf("2"));

//		x.insert(3, new tuple(ctblColNameValue2));
//		x.insert(1, new tuple(ctblColNameValue2));
//		x.insert(2, new tuple(ctblColNameValue2));
//		x.insert(4, new tuple(ctblColNameValue2));
//		x.insert(24, new tuple(ctblColNameValue2));
//		x.insert(4123, new tuple(ctblColNameValue2));
//		x.insert(2124, new tuple(ctblColNameValue2));
//		x.insert(4123, new tuple(ctblColNameValue2));
//		x.insert(234, new tuple(ctblColNameValue2));
		System.out.println(x.search(3) + "found");
		System.out.println(x.search(2) + "found");
		System.out.println(x.search(1) + "found");
		System.out.println(x.search(4) + "found");
		System.out.println(x.search(2124) + "found");
		System.out.println(x.search(16));
		System.out.println(x.search(18));
		System.out.println(x.search(20));
		System.out.println(x.search(234) + "found");
	}
}
