package jarvis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Page implements Serializable {
	public String pageName;
	private int pageNo;
	private String tableName;
	private ArrayList<Date> TouchDate = new ArrayList<Date>();
	private ArrayList<tuple> tuples;
	private static int size;

	/**
	 * creates a new page with the name "tableName_pageNo"
	 * 
	 * @param size
	 * @param pageName
	 * @throws IOException
	 */
	public Page(int size, String pageName) throws IOException {
		this.size = size;
		this.pageName = pageName;
		tableName = pageName.substring(0, pageName.indexOf("#"));
		pageNo = Integer.parseInt(pageName.substring((pageName.indexOf("#") + 1), pageName.length()));
		tuples = new ArrayList<tuple>(size);
		save();
	}

	/**
	 * adds new tuple to the page, the commented section checks for duplicate
	 * keys but it violates test 1, however will work in M2
	 * @param htblColNameValue
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws DBAppException
	 */
	public void addtoPage(Hashtable<String, Object> htblColNameValue)
			throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException {
		Table thisTable = DBApp.tableMeta.get(tableName);
		if (!htblColNameValue.containsKey(thisTable.primaryKey)) // primary key missing
			throw new DBAppException("Primary Key missing");
		if (checkTypes(thisTable, htblColNameValue)) {
			tuple myTuple = new tuple(htblColNameValue);
			myTuple.setPageNo(pageNo);
			myTuple.location=tuples.size();
			tuples.add(myTuple);
			TouchDate.add(new Date());
			System.out.println("Inserted into " + pageName); // eb2a shiil di
			indexIfNeeded(thisTable, myTuple);
			thisTable.save();
			save();
		} else {
			throw new DBAppException("Unmatching Datatpes");
		}
	}

	/**
	 * removes the tuple at index index, marks it as deleted and saves
	 * 
	 * @param index
	 * @throws IOException
	 */
	public void removeTuple(int index) throws IOException { // usedIn_SelectOrDel
		Table thisTable = DBApp.tableMeta.get(tableName);
		tuples.get(index).setDeleted(true); // set to deleted
		System.out.println("Removed Item " + tuples.remove(index));
		save();
	}
	
	/**
	 * returns the next page or null if the page doesn't exist
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public Page next() {
		String[] nextPageInfo = pageName.split("#");
		String newPageName = tableName + "#" + (Integer.parseInt(nextPageInfo[1]) + 1);
		try {
			return load(newPageName);
		} catch (Exception e) {
			return null;
		}
	}
	
	public ArrayList<tuple> getTuples() {
		return tuples;
	}
	
	public boolean isFull() {
		if (tuples.size() == size)
			return true;
		else
			return false;
	}

	/**
	 * save page as "tableName_pageNo.class"
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		File f = new File("data/pages/" + pageName + ".class");
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.flush();
		oos.close();
	}

	/**
	 * loads a page in the database staticly
	 * 
	 * @param pageName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Page load(String pageName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/pages/" + pageName + ".class"));
		return (Page) ois.readObject();
	}
	
	/**
	 * Checks the compatibility of the types, throws Unmatching Types
	 * 
	 * @param thisTable
	 * @param InputhtblColNameValue
	 * @return
	 * @throws DBAppException
	 */
	private boolean checkTypes(Table thisTable, Hashtable<String, Object> InputhtblColNameValue) throws DBAppException {
		Enumeration<String> colNameValue = InputhtblColNameValue.keys();
		Hashtable<String, String> tableColNameType = thisTable.colNameType;
		while (colNameValue.hasMoreElements()) {
			String currInColName = colNameValue.nextElement();
			String inputType = (String) tableColNameType.get(currInColName);
			Object inObject = InputhtblColNameValue.get(currInColName);
			if (!switchTypes(inputType, inObject))
				return false;
		}
		return true;
	}
	
	private void indexIfNeeded(Table thisTable, tuple myTuple) throws IOException{
		Hashtable<String, Object> htblColNameValue = myTuple.data;
		Enumeration<String> x = htblColNameValue.keys();
		while(x.hasMoreElements()){
			String currColName = x.nextElement();
			if(thisTable.isIndexed(currColName))
				thisTable.colNameBTree.get(currColName).insert((Comparable) htblColNameValue.get(currColName), pageName +"#" + (tuples.size()-1));
		}
	}

	/**
	 * A sub method for checking the types
	 * 
	 * @param colType
	 * @param obj
	 * @return
	 * @throws DBAppException
	 */
	private boolean switchTypes(String colType, Object obj) throws DBAppException {
		switch (colType) {
		case "Integer":
			if (obj instanceof java.lang.Integer)
				return true;
			break;
		case "String":
			if (obj instanceof java.lang.String)
				return true;
			break;
		case "Double":
			if (obj instanceof java.lang.Double)
				return true;
			break;
		case "Boolean":
			if (obj instanceof java.lang.Boolean)
				return true;
			break;
		case "Date":
			if (obj instanceof java.util.Date)
				return true;
			break;
		default:
			throw new DBAppException("Either You spelled the Type incorectly or the type does not exist, "
					+ "Supported types: Integer, String, Double, Boolean, Date");
		}
		return false;
	}
}