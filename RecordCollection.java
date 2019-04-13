package main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.awt.event.ActionEvent;

public class RecordCollection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmMyRecordCollection;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField searchQuery;
	private DefaultListModel<String> test;
	private JList<String> recordList;
	private ArrayList<RecordInfo> recordArrayList = new ArrayList<RecordInfo>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordCollection window = new RecordCollection();
					window.frmMyRecordCollection.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecordCollection() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMyRecordCollection = new JFrame();
		frmMyRecordCollection.getContentPane().setBackground(new Color(240, 240, 240));
		frmMyRecordCollection.setTitle("My Record Collection");
		frmMyRecordCollection.setResizable(false);
		frmMyRecordCollection.setBounds(100, 100, 800, 440);
		frmMyRecordCollection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMyRecordCollection.getContentPane().setLayout(null);
		
		/*
		 * 
		 * Initialize the data from a serialized object file that contains the records
		 * 
		 */
		try{
			InputStream file = new FileInputStream("records.ser");
		    InputStream buffer = new BufferedInputStream(file);
		    ObjectInput input = new ObjectInputStream (buffer);
		    try{
		        @SuppressWarnings("unchecked")
				ArrayList<RecordInfo> readRecords = (ArrayList<RecordInfo>)input.readObject();
		        recordArrayList = readRecords;
		    } finally {
		        input.close();
		    }
		} catch(ClassNotFoundException ex) {
		    	System.out.println(ex);
		} catch(IOException ex) {
		      System.out.println(ex);
		}
		/*
		 * 
		 * the data that goes into the main JList.  It is initialized with an array list
		 * 
		 */
		test = new DefaultListModel<>();
		if(!recordArrayList.isEmpty()) {
			test = setArrayToListModel(recordArrayList);
		}
		
		
		JLabel lblSoryBy = new JLabel("Sort by:");
		lblSoryBy.setBounds(6, 6, 65, 28);
		lblSoryBy.setHorizontalAlignment(SwingConstants.CENTER);
		frmMyRecordCollection.getContentPane().add(lblSoryBy);
		
		/*
		 * 
		 * sorts the records by their title, artist, then year
		 * 
		 */
		JRadioButton sortTitle = new JRadioButton("Title");
		sortTitle.setBounds(83, 6, 65, 28);
		buttonGroup.add(sortTitle);
		sortTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sortTitle.isSelected()) {
					Collections.sort(recordArrayList, new TitleCompare());
					test = new DefaultListModel<>();
					test = setArrayToListModel(recordArrayList);
					recordList.setModel(test);
					recordList.revalidate();
					System.out.println("Sorting by Title.");
				}
			}
		});
		frmMyRecordCollection.getContentPane().add(sortTitle);
		
		/*
		 * 
		 * sorts the records by artist, title, then year
		 * 
		 */
		JRadioButton sortArtist = new JRadioButton("Artist");
		sortArtist.setBounds(160, 6, 72, 28);
		buttonGroup.add(sortArtist);
		sortArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sortArtist.isSelected()) {
					Collections.sort(recordArrayList, new ArtistCompare());
					test = new DefaultListModel<>();
					test = setArrayToListModel(recordArrayList);
					recordList.setModel(test);
					recordList.revalidate();
					System.out.println("Sorting by Artist.");
				}
			}
		});
		frmMyRecordCollection.getContentPane().add(sortArtist);
		
		/*
		 * 
		 * sorts the records in the array list by the year first, then title,
		 * then artist
		 * 
		 */
		JRadioButton sortYear = new JRadioButton("Year");
		sortYear.setBounds(244, 6, 65, 28);
		buttonGroup.add(sortYear);
		sortYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sortYear.isSelected()) {
					Collections.sort(recordArrayList, new YearCompare());
					test = new DefaultListModel<>();
					test = setArrayToListModel(recordArrayList);
					recordList.setModel(test);
					recordList.revalidate();
					System.out.println("Sorting by Artist.");
				}
			}
		});
		frmMyRecordCollection.getContentPane().add(sortYear);
		
		/*
		 * 
		 * search textbox
		 * 
		 */
		searchQuery = new JTextField();
		searchQuery.setBounds(410, 6, 292, 29);
		frmMyRecordCollection.getContentPane().add(searchQuery);
		searchQuery.setColumns(10);
		
		/*
		 * 
		 * Search button, will search for album and artist keywords
		 * 
		 */
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(searchQuery.getText() == null || searchQuery.getText() == "") {
					test = new DefaultListModel<>();
					test = setArrayToListModel(recordArrayList);
					recordList.setModel(test);
					recordList.revalidate();
				} else {
					String query = searchQuery.getText().toLowerCase();
					ArrayList<RecordInfo> searchList = new ArrayList<RecordInfo>();
					for(RecordInfo record : recordArrayList) {
						if (record.getAlbumTitle().toLowerCase().contains(query) || record.getArtist().toLowerCase().contains(query)) {
							searchList.add(record);
						}
					}
					System.out.println("Found " + searchList.size() + " records containing " + query + ".");
					test = new DefaultListModel<>();
					test = setArrayToListModel(searchList);
					recordList.setModel(test);
					recordList.revalidate();
				}
			}
		});
		searchButton.setBounds(714, 6, 80, 29);
		frmMyRecordCollection.getContentPane().add(searchButton);
		
		
		/*
		 * 
		 * This button opens a dialog that allows the user to import new record info
		 * the record info is returned, the dialog is disposed, and the record is added to
		 * the array list which gets imported into the main list.
		 * 
		 */
		JButton addNewButton = new JButton("Add New");
		addNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddRecord dialog = new AddRecord();
				dialog.setVisible(true);
				RecordInfo newRecord = dialog.getAddedRecord();
				dialog.dispose();
				recordArrayList.add(newRecord);
				System.out.println(newRecord.recordToString());
				test = new DefaultListModel<>();
				test = setArrayToListModel(recordArrayList);
				recordList.setModel(test);
				recordList.revalidate();
				try (
					OutputStream file = new FileOutputStream("records.ser");
					OutputStream buffer = new BufferedOutputStream(file);
					ObjectOutput output = new ObjectOutputStream(buffer);
				){
					{
						output.writeObject(recordArrayList);
					}
				}catch(IOException ex) {
					System.out.println(ex);
				}
			}
		});
		addNewButton.setBounds(694, 383, 100, 29);
		frmMyRecordCollection.getContentPane().add(addNewButton);
		
		/*
		 * 
		 * this button will remove whatever the user has selected in the list.
		 * it will store the multiple selections and for each selection, remove
		 * them from the array list and update the main list.
		 * 
		 */
		JButton removeSelectedButton = new JButton("Remove Selected");
		removeSelectedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selected = recordList.getSelectedIndices();
				for(int i = selected.length-1; i >= 0; i--) {
					recordArrayList.remove(selected[i]);
				}
				try (
					OutputStream file = new FileOutputStream("records.ser");
					OutputStream buffer = new BufferedOutputStream(file);
					ObjectOutput output = new ObjectOutputStream(buffer);
				){
					{
						output.writeObject(recordArrayList);
					}
				}catch(IOException ex) {
					System.out.println(ex);
				}
				test = new DefaultListModel<>();
				test = setArrayToListModel(recordArrayList);
				recordList.setModel(test);
				recordList.revalidate();
				System.out.println("Removed " + selected.length + " records.");
			}
		});
		removeSelectedButton.setBounds(6, 383, 142, 29);
		frmMyRecordCollection.getContentPane().add(removeSelectedButton);
		
		/*
		 * 
		 * the pane that allows the user to scroll through the record list
		 * 
		 */
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(6, 78, 788, 293);
		frmMyRecordCollection.getContentPane().add(scrollPane);
		
		/*
		 * 
		 * The actual list where the records are contained.  The viewport of the scrollpane is set to this
		 * and the font is monospaced for readability and layout
		 * 
		 */
		recordList = new JList<String>(test);
		recordList.setFont(new Font("Monospaced", recordList.getFont().getStyle(), recordList.getFont().getSize()));
		scrollPane.setViewportView(recordList);
		recordList.setBorder(null);
		
		/*
		 * 
		 * this is just the main label with the column information, nothing interesting here
		 * 
		 */
		JLabel lblRecord = new JLabel(" Cat #                  Album Title                                                                         Artist                                                       Year");
		lblRecord.setHorizontalAlignment(SwingConstants.LEFT);
		lblRecord.setBounds(6, 50, 788, 28);
		frmMyRecordCollection.getContentPane().add(lblRecord);
	}
	
	/*
	 * 
	 * this method updates the list model that goes into the JList by importing data from the array list.
	 * 
	 */
	public DefaultListModel<String> setArrayToListModel(ArrayList<RecordInfo> arraylist){
		DefaultListModel<String> newListModel = new DefaultListModel<String>();
		for(int i = 0; i < arraylist.size(); i++) {
			newListModel.addElement(arraylist.get(i).recordToString());
		}
		return newListModel;
	}

}
