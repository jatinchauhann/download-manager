package downloadmanager;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

//the download manager
public class DownloadManager extends JFrame implements Observer {
	
	//add download text field
	private JTextField addTextField;
	
	//download table's data model
	private DownloadsTableModel tableModel;
	
	//table listing downloads
	private JTable table;
	
	//buttons for managing the selected download
	private JButton pauseButton, resumeButton;
	private JButton cancelButton, clearButton;
	
	//currently selected download
	private Download selectedDownload;
	
	//flag for whether or not table selection is being cleared
	private boolean clearing;
	
	//constructor for download manager
	public DownloadManager(){
		//set application title
		setTitle("Download Manager");
		
		//set window size
		setSize(640,480);
		
		
	}
}
