package main;

import java.util.Comparator;

public class ArtistCompare implements Comparator<RecordInfo> {

	public int compare(RecordInfo a, RecordInfo b) {

		int cmp;
		cmp = a.getArtist().toLowerCase().compareTo(b.getArtist().toLowerCase());
		if(cmp != 0) {
			return cmp;
		}
		cmp = a.getAlbumTitle().toLowerCase().compareTo(b.getAlbumTitle().toLowerCase());
		if(cmp != 0) {
			return cmp;
		}
		cmp = a.getYear().compareTo(b.getYear());
		if(cmp != 0) {
			return cmp;
		}
		return 0;
	}
	
}
