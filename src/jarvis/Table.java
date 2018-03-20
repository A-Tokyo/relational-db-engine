package jarvis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import b_Tree.B__Tree;

public class Table implements Serializable {
	public String tableName;
	public int pageCount = 1;
	public String primaryKey;
	Hashtable<String, String> colNameType;
	Hashtable<String, String> colNameRefs;
	Hashtable<String, B__Tree> colNameBTree = new Hashtable<String,B__Tree>();
	
	public Hashtable<String, B__Tree> getBTreeIndexes() {
		return colNameBTree;
	}

	public Table(String tableName, Hashtable<String, String> htblColNameType, Hashtable<String, String> htblColNameRefs,
			String key) throws IOException, DBAppException {
		this.tableName = tableName;
		this.colNameType = htblColNameType;
		this.primaryKey = key;
		this.colNameRefs = htblColNameRefs;
		// checkRefs();
		colNameBTree.put(key, new B__Tree(DBApp.BTreeDegree));
		new Page(DBApp.configSize, tableName + "#" + pageCount);
		saveCSV();
		save(); // save Table Meta
	}
	
	
	
	public boolean isIndexed(String strColName){
		return colNameBTree.containsKey(strColName)?true:false;
	}

	
	public void incrementPageCount() throws IOException {
		pageCount++;
		save();
	}
	/**
	 * returns the 1st Level Dens Index of Primary Keys
	 * @return
	 */
	public B__Tree getPrimaryKeys() {
		return colNameBTree.get(primaryKey);
	}
	
	/**
	 * checks references but had to be abandoned as it violates the test data so
	 * it was commented in the cons
	 * 
	 * @throws DBAppException
	 */
	private void checkRefs() throws DBAppException {
		Enumeration<String> refKeys = colNameRefs.keys();
		while (refKeys.hasMoreElements()) {
			String ref = colNameRefs.get(refKeys.nextElement());
			ref.replaceAll(".", "##");
			String[] refInfo = ref.split("##");
			if ((DBApp.tableMeta.contains(refInfo[0]))
					&& (DBApp.tableMeta.get(refInfo[0]).colNameType.containsKey(refInfo[1]))) {
			} else
				throw new DBAppException("Reference Error While Creating Table");
		}
	}
	
	/**
	 * saves the current table to disk as "tableName_meta.class"
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		File f = new File("data/tableMeta/" + tableName + "_meta" + ".class");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(this);
		oos.flush();
		oos.close();
	}

	/**
	 * Saves the Table Headers to the CSV file as metaData
	 * 
	 * @throws IOException
	 */
	public void saveCSV() throws IOException {
		StringBuilder sb = new StringBuilder();
		Enumeration<String> colNames = colNameType.keys();
		while (colNames.hasMoreElements()) {
			String currColKey = colNames.nextElement();
			// Table Name
			sb.append(tableName + ",");
			// Column Name
			sb.append(currColKey + ",");
			sb.append(colNameType.get(currColKey) + ","); // col type
			// primary key
			if (this.primaryKey == currColKey)
				sb.append("True");
			else
				sb.append("False");
			sb.append(",");
			sb.append(isIndexed(currColKey));
			sb.append(",");
			// References
			if (colNameRefs.containsKey(currColKey))
				sb.append("True");
			else
				sb.append("False");

			sb.append("\n"); // new Line
		}
		FileWriter fileWriter = new FileWriter("data/metadata.csv", true);
		fileWriter.append(sb);
		fileWriter.flush();
		fileWriter.close();
	}

	/**
	 * loads table from disk. "tableName"
	 * 
	 * @param tableName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Table load(String tableName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("data/tableMeta/" + tableName + "_meta" + ".class"));
		return (Table) ois.readObject();
	}
}