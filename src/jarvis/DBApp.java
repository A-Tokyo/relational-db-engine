package jarvis;

//touch Date column exists and is always updated, I just don't show it in select as it wasn't in the requirements 
/**
 * Please Note that I've exported the file with no metaData so that you can test the file only using run directly on dbApp
 * this will create the new schema as written in the folder and will do the tests on it
 * the right displayed text after the tests is also in the main folder;
 * thank you and have a great day
 * @author Tokyo
 */

/*
 * to Do: Delete Index
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import b_Tree.B__Tree;

class DBAppException extends Exception {
	public DBAppException(String exceptionType) {
		super(exceptionType);
	}

	public DBAppException() {
		super("DB App Exception");
	}
}

class DBEngineException extends Exception {
	public DBEngineException(String exceptionType) {
		super(exceptionType);
	}

	public DBEngineException() {
		super("DB Engine Exception");
	}
}

public class DBApp {
	public static int configSize; // to be read from config file
	public static int BTreeDegree = 4; // to be read from config file
	public static Hashtable<String, Table> tableMeta = new Hashtable<String, Table>();

	// Karam's Parts
	public void init() throws Exception {
		config();
		File f = new File("data/metadata.csv");
		if (f.exists()) {
			loadMeta(f);
		} else {
			createMetaCSV();
		}
	}

	public void createTable(String strTableName, Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) throws DBAppException, IOException {
		if (strTableName.contains("#")) { // validating name, as # is used as
											// the unique data separator
			throw new DBAppException("You can not use the character # in a table's Name");
		}
		if (tableMeta.containsKey(strTableName)) {
			System.err.println("Table Already Exists");
			return;
		}
		Table newTable = new Table(strTableName, htblColNameType, htblColNameRefs, strKeyColName);
		tableMeta.put(strTableName, newTable);
	} // M1 DONE

	public void createIndex(String strTableName, String strColName)
			throws DBAppException, FileNotFoundException, ClassNotFoundException, DBEngineException, IOException {
		B__Tree index = new B__Tree(BTreeDegree);
		Table toIndexTable = tableMeta.get(strTableName);
		Iterator<tuple> All = selectFromTable(strTableName, new Hashtable<String, Object>(), "or");
		int i = 0;
		while (All.hasNext()) {
			tuple currTuple = All.next();
			index.insert((Comparable) currTuple.data.get(strColName),
					strTableName + "#" + currTuple.getPageNo() + "#" + i % configSize); // locaation
			i++;
		}
		toIndexTable.colNameBTree.put(strColName, index);
		toIndexTable.save();
	}

	/**
	 * Inserts into the table strTableName and saves it to disk immediately,
	 * your data is always safe
	 * 
	 * @param strTableName
	 * @param htblColNameValue
	 * @throws DBAppException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void insertIntoTable(String strTableName, Hashtable<String, Object> htblColNameValue)
			throws DBAppException, FileNotFoundException, ClassNotFoundException, IOException {
		// Using Btree Done
		if (!tableMeta.containsKey(strTableName))
			throw new DBAppException("Table Does not exist");

		Table toInsert = tableMeta.get(strTableName);
		int pageNo = toInsert.pageCount;
		Page lastPage;

		lastPage = Page.load(strTableName + "#" + pageNo);
		if (!lastPage.isFull()) {
			lastPage.addtoPage(htblColNameValue);
		} else {
			lastPage = new Page(configSize, strTableName + "#" + (pageNo + 1));
			lastPage.addtoPage(htblColNameValue);
			toInsert.pageCount++; // updateTable;
			toInsert.save();
		}
		tableMeta.remove(strTableName); // reload metaHash
		tableMeta.put(strTableName, toInsert);
	} // M1 DONE

	/**
	 * gets the page No from the primary key index and searches the tuples in
	 * O(n) time to update the tuple and it's time
	 * 
	 * @param strTableName
	 * @param keyToUpdateData
	 * @param htblColNameValue
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws DBEngineException
	 * @throws Exception
	 */
	// DOOOOO THIIIISSSS NEW BTREE
	public void updateTable(String strTableName, Object keyToUpdateData, Hashtable<String, Object> htblColNameValue)
			throws DBAppException, FileNotFoundException, ClassNotFoundException, IOException, DBEngineException {
		// Using Btree Done
		Table myTable = tableMeta.get(strTableName);
		B__Tree thisTree = myTable.getPrimaryKeys();

		// wet
		Hashtable<String, Object> htbl = new Hashtable<String, Object>();
		htbl.put(myTable.primaryKey, keyToUpdateData);
		Iterator<tuple> found = selectFromTable(strTableName, htbl, "OR");

		if (!found.hasNext()) {
			throw new DBAppException("Item not found to update");
		}

		tuple currTuple = found.next();
		if (!htblColNameValue.containsKey(myTable.primaryKey)) {
			Enumeration<String> keysToCompare = htblColNameValue.keys();
			while (keysToCompare.hasMoreElements()) {
				String currKey = keysToCompare.nextElement();
				Page x = Page.load(strTableName + "#" + currTuple.getPageNo());
				x.getTuples().get(currTuple.location).data.replace(currKey, htblColNameValue.get(currKey));
				x.save();
			}
		} else {
			System.out.println("Can not update primary key according to description");
		}
	}// M2 Done

	/**
	 * to implement try keeping the code dry by using select
	 * 
	 * @param strTableName
	 * @param htblColNameValue
	 * @param strOperator
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue, String strOperator)
			throws DBEngineException, FileNotFoundException, ClassNotFoundException, IOException {
		Hashtable indexes = tableMeta.get(strTableName).getBTreeIndexes();
		Enumeration<String> indexColumns = indexes.keys();
		Iterator<tuple> toDelete = selectFromTable(strTableName, htblColNameValue, strOperator);
		boolean done = false;
		while (toDelete.hasNext()) {
			tuple currTuple = toDelete.next();
			Page x = Page.load(strTableName + "#" + currTuple.getPageNo());
			currTuple.setDeleted(true);
			x.getTuples().get(currTuple.location).setDeleted(true);
			done = true;
			x.save();
			while (indexColumns.hasMoreElements()) {
				String colName = indexColumns.nextElement();
				if (currTuple.data.containsKey(colName)) {
					((B__Tree) indexes.get(colName)).delete((Comparable) currTuple.data.get(colName));
				}
			}
			indexColumns = indexes.keys();
		}
		// delete from table
		if (!done) {
			selectOrDel(strTableName, htblColNameValue, strOperator, true);
		}
	}// M2 Done

	public Iterator<tuple> selectFromTable(String strTable, Hashtable<String, Object> htblColNameValue,
			String strOperator) throws DBEngineException, FileNotFoundException, ClassNotFoundException, IOException {
		strOperator = strOperator.toLowerCase();
		Table thisTable = tableMeta.get(strTable);
		if (strOperator == "and") {
			boolean byIndex = false;
			Enumeration<String> keySearch = htblColNameValue.keys();
			while (keySearch.hasMoreElements()) {
				String currKeySearch = keySearch.nextElement();
				if (thisTable.colNameBTree.containsKey(currKeySearch)) {
					byIndex = true;
				}

			}
			if (byIndex) {
				// System.out.println("I was retrieved through B_TREE");
				return selectByIndex(thisTable, htblColNameValue, strOperator);
			}
		}
		return selectOrDel(strTable, htblColNameValue, strOperator, false);
	} // M1 Done Tokyo

	private Iterator<tuple> selectByIndex(Table thisTable, Hashtable<String, Object> htblColNameValue,
			String strOperator) throws FileNotFoundException, ClassNotFoundException, DBEngineException, IOException {
		Enumeration<String> keys = htblColNameValue.keys();
		if (!keys.hasMoreElements()) {
			return displayTable(thisTable.tableName);
		}

		Hashtable<String, B__Tree> indexes = thisTable.getBTreeIndexes();
		ArrayList<tuple> list = new ArrayList<tuple>();
		String currKey = keys.nextElement();
		if (!thisTable.colNameBTree.contains(currKey)) {
			while (keys.hasMoreElements()) {
				currKey = keys.nextElement();
				if (thisTable.colNameBTree.contains(currKey))
					break;
			}
		}
		ArrayList<String> foundPages = indexes.get(currKey).search((Comparable) htblColNameValue.get(currKey));
		ArrayList<tuple> found = new ArrayList<tuple>();

		for (int i = 0; i < foundPages.size(); i++) {
			String[] split = foundPages.get(i).split("#");
			Page currPage = Page.load(split[0] + "#" + split[1]);
			int location = Integer.parseInt(split[2]);
			tuple currTuple = currPage.getTuples().get(location);
			if (!currTuple.isDeleted()) {
				found.add(currTuple);
			}
		}

		for (int i = 0; i < found.size(); i++) {
			boolean toSelect = true;
			while (keys.hasMoreElements()) {
				currKey = keys.nextElement();
				if (!found.get(i).data.get(currKey).equals(htblColNameValue.get(currKey))) {
					toSelect = false;
				}
			}
			if (toSelect)
				list.add(found.get(i));
		}
		return list.iterator();
	}

	private Iterator<tuple> displayTable(String tableName)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		ArrayList<tuple> list = new ArrayList<tuple>();
		Page myPage = Page.load(tableName + "#1");
		while (myPage != null) {
			ArrayList<tuple> tuples = myPage.getTuples();
			for (int i = 0; i < tuples.size(); i++) {
				if (!tuples.get(i).isDeleted())
					list.add(tuples.get(i));
			}
			myPage = myPage.next();
		}
		return list.iterator();
	}

	private Iterator<tuple> selectOrDel(String strTable, Hashtable<String, Object> htblColNameValue, String strOperator,
			boolean delete) throws DBEngineException, FileNotFoundException, ClassNotFoundException, IOException {
		if (htblColNameValue.isEmpty())
			return displayTable(strTable);
		ArrayList<tuple> list = new ArrayList<tuple>();
		Page myPage = Page.load(strTable + "#1");
		while (myPage != null) {
			for (int j = 0; j < myPage.getTuples().size(); j++) {// loop_on_tuples
				tuple currTuple = myPage.getTuples().get(j);
				Enumeration<String> keysToCompare = htblColNameValue.keys();
				switch (strOperator) {
				case "and":
					boolean toSelect = true;
					while (keysToCompare.hasMoreElements()) {
						String currKey = keysToCompare.nextElement();
						if (!currTuple.data.get(currKey).equals(htblColNameValue.get(currKey))) {
							toSelect = false;
							break;
						}
					}
					if (toSelect) {
						if (delete)
							myPage.removeTuple(j--);
						else {
							if (!currTuple.isDeleted())
								list.add(currTuple);
						}
					}
					break;
				case "or":
					while (keysToCompare.hasMoreElements()) {
						String currKey = (String) keysToCompare.nextElement();
						if (currTuple.data.get(currKey).equals(htblColNameValue.get(currKey))) {
							if (delete)
								myPage.removeTuple(j--);
							else {
								if (!currTuple.isDeleted())
									list.add(currTuple);
							}
							break;
						}
					}
					break;
				}
			}
			myPage = myPage.next();
		}
		return (delete) ? null : list.iterator();
	}

	/**
	 * reads Data from Config File
	 */
	private static void config() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config/DBApp.properties");
			// load a properties file
			prop.load(input);
			configSize = Integer.parseInt(prop.getProperty("MaximumRowsCountInPage"));
			// BTreeDegree = Integer.parseInt(prop.getProperty("BPlusTreeN"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Loads metadata from disk, which is the state of the app before
	 * terminating
	 * 
	 * @param f
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private void loadMeta(File f) throws IOException, ClassNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		line = br.readLine(); // skip first line the header
		String tableName = ""; // init here 3shan kol maa ba compare 3aleh
		while ((line = br.readLine()) != null) {
			String[] parsedLine = line.split(","); // got my info
			if (!tableName.equals(parsedLine[0])) {
				tableName = parsedLine[0];
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream("data/tableMeta/" + tableName + "_meta.class"));
				tableMeta.put(tableName, (Table) ois.readObject());
			}
			tableName = parsedLine[0];
		}
	}

	/**
	 * Creates the CSV file for storing table Headers
	 * 
	 * @throws IOException
	 */
	private void createMetaCSV() throws IOException {
		FileWriter fileWriter = new FileWriter("data/metadata.csv");
		// Write the CSV file header
		fileWriter.append("Table Name, Column Name, Column Type, Key, Indexed, References");
		// Add a new line separator after the header
		fileWriter.append("\n");
		fileWriter.flush();
		fileWriter.close();
	}

	public static void main(String[] args) throws Exception {
		DBApp myDB = new DBApp();
		try {
			myDB.init();
		} catch (Exception e) {
			System.err.println("Could not Start Application, Some Data files are missing");
			e.printStackTrace();
		}
		Tests.evaluate(myDB);
	}
}