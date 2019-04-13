package main;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddRecord extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField catNumInput;
	private JTextField albumTitleInput;
	private JTextField artistInput;
	private JTextField yearInput;
	private RecordInfo newRecord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddRecord dialog = new AddRecord();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddRecord() {
		setTitle("Add a New Record");
		setBackground(new Color(240, 240, 240));
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 300, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cat #:");
		lblNewLabel.setBounds(6, 6, 44, 26);
		contentPanel.add(lblNewLabel);
		
		JLabel errorMessage = new JLabel("Please enter a valid year");
		errorMessage.setForeground(Color.RED);
		errorMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		errorMessage.setBounds(6, 156, 288, 16);
		errorMessage.setVisible(false);
		contentPanel.add(errorMessage);
		
		catNumInput = new JTextField();
		catNumInput.setToolTipText("Maximum 12 characters");
		catNumInput.setBounds(108, 6, 186, 26);
		contentPanel.add(catNumInput);
		catNumInput.setColumns(10);
		
		JLabel lblAlbum = new JLabel("Album title:");
		lblAlbum.setBounds(6, 44, 79, 26);
		contentPanel.add(lblAlbum);
		
		albumTitleInput = new JTextField();
		albumTitleInput.setToolTipText("Maximum 32 characters");
		albumTitleInput.setBounds(108, 44, 186, 26);
		contentPanel.add(albumTitleInput);
		albumTitleInput.setColumns(10);
		
		JLabel lblArtist = new JLabel("Artist:");
		lblArtist.setBounds(6, 82, 79, 26);
		contentPanel.add(lblArtist);
		
		artistInput = new JTextField();
		artistInput.setToolTipText("Maximum 23 characters");
		artistInput.setBounds(108, 82, 186, 26);
		contentPanel.add(artistInput);
		artistInput.setColumns(10);
		
		JLabel lblYear = new JLabel("Year:");
		lblYear.setBounds(6, 120, 79, 26);
		contentPanel.add(lblYear);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) throws NumberFormatException {
				if(Integer.parseInt(yearInput.getText()) > 1890 && Integer.parseInt(yearInput.getText()) < 2100) {
					if(!catNumInput.getText().isEmpty() && !albumTitleInput.getText().isEmpty() && !artistInput.getText().isEmpty()) {
						String newCatNum = catNumInput.getText();
						String newAlbumTitle = albumTitleInput.getText();
						String newArtist = artistInput.getText();
						if(newCatNum.length() > 12) {
							newCatNum = newCatNum.substring(0, 11);
						}
						if(newAlbumTitle.length() > 50) {
							newAlbumTitle = newAlbumTitle.substring(0, 49);
						}
						if(newArtist.length() > 33) {
							newArtist = newArtist.substring(0, 34);
						}
						newRecord = new RecordInfo(newCatNum, newAlbumTitle, newArtist, Integer.parseInt(yearInput.getText()));
						setVisible(false);
						System.out.println("Record successfully added!");
					}
				} else {
					errorMessage.setVisible(true);
				}
			}
		});
		btnAdd.setBounds(206, 120, 88, 29);
		contentPanel.add(btnAdd);
		
		yearInput = new JTextField();
		yearInput.setBounds(108, 120, 86, 29);
		contentPanel.add(yearInput);
		yearInput.setColumns(10);
		
	}
	public RecordInfo getAddedRecord() {
		return this.newRecord;
	}
}
