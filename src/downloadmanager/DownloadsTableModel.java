package downloadmanager;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

//this class manages the download table data
public class DownloadsTableModel extends AbstractTableModel implements Observer {
	// these are the names for the table columns
	private static final String[] columnNames = { "URL", "Size", "Progress", "Status" };

	// these are the classes for each column values
	private static final Class[] columnClasses = { String.class, String.class, JProgressBar.class, String.class };

	// the table's list of downloads
	private ArrayList<Download> downloadList = new ArrayList<Download>();

	// add a new download to the table
	public void addDownload(Download download) {
		// register to be notified when the download changes.
		download.addObserver(this);
		downloadList.add(download);

		// fire table roe insertion notification to table
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}

	// get a download for a specific row
	public Download getDownload(int row) {
		return downloadList.get(row);
	}

	// remove a download from the list
	public void clearDownload(int row) {
		downloadList.remove(row);

		// fire table row deletion notification to table
		fireTableRowsDeleted(row, row);
	}

	// get table's column count
	public int getColumnCount() {
		return columnNames.length;
	}

	// get a column's name
	public String getColumnName(int col) {
		return columnNames[col];
	}

	// get a column's class
	public Class<?> getColumnClass(int col) {
		return columnClasses[col];
	}

	// get table's row count
	public int getRowCount() {
		return downloadList.size();
	}

	// get value for a specific row and column combination
	public Object getValueAt(int row, int col) {
		Download download = downloadList.get(row);
		switch (col) {
		case 0: // URL
			return download.getUrl();
		case 1: // size
			int size = download.getSize();
			return (size == -1) ? "" : Integer.toString(size);
		case 2: // progress
			return new Float(download.getProgress());
		case 3: // status
			return Download.STATUSES[download.getStatus()];
		}
		return "";
	}

	// update is called when a download notifies its observers of any changes
	public void update(Observable o, Object arg) {
		int index = downloadList.indexOf(o);

		// fire table row update notification to table
		fireTableRowsUpdated(index, index);
	}
}
