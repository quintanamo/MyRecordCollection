package main;

import java.io.Serializable;

public class RecordInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String catNum;
	private String albumTitle;
	private String artist;
	private Integer year;
	
	public RecordInfo(String catNum, String albumTitle, String artist, Integer year) {
		this.catNum = catNum;
		this.albumTitle = albumTitle;
		this.artist = artist;
		this.year = year;
	}
	
	public String recordToString() {
		String newString = "";
		newString += catNum + calculateSpace(catNum, 15);
		newString += albumTitle + calculateSpace(albumTitle, 52);
		newString += artist + calculateSpace(artist, 37);
		newString += year;
		return newString;
	}
	
	public String calculateSpace(String element, int max) {
		int spaceLeft = max - element.length();
		String whiteSpace = "";
		for(int i = 0; i < spaceLeft; i++) {
			whiteSpace += " ";
		}
		return whiteSpace;
	}
	
	public String getAlbumTitle() {
		return this.albumTitle;
	}
	public String getArtist() {
		return this.artist;
	}
	public Integer getYear() {
		return this.year;
	}
	
}
