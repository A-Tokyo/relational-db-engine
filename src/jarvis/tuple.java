package jarvis;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

public class tuple implements Serializable {
	Hashtable<String, Object> data; // over written often that's why it has default visibility
	private boolean isDeleted;
	private int pageNo;
	int location;
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Hashtable<String, Object> getData() {
		return data;
	}

	public void setData(Hashtable<String, Object> data) {
		this.data = data;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public tuple(Hashtable<String, Object> htblColNameValue) {
		this.data = htblColNameValue;
	}

	@Override
	public String toString() {
		String sb = "";
		Enumeration<String> keys = data.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			sb = sb + key + ": " + data.get(key) + ", ";
		}
		if(sb.length()>2)
		return sb.substring(0, sb.length() - 2);
		return sb;
	}
}