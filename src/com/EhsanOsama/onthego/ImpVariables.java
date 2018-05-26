//OnTheGo prepared by Ehsanul Hakim Khan and Osama Tariq

package com.EhsanOsama.onthego;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImpVariables {

	// myList is an arrayList that contains all the filepaths for the media
	// contents
	// myListText contains all the filenames for the media contents
	public static List<String> myList = new ArrayList<String>();
	public static List<String> myListText = new ArrayList<String>();

	// This is a static method used to fill both myList and myListText with
	// all the filepaths and filenames for all the saved media contents
	public static void populateMyList() throws Exception {

		// This is the root path
		String path = "/storage/emulated/0/DCIM/OnTheGo";

		// Both the arrayLists are made empty before filling them up
		myList.clear();
		myListText.clear();

		// directoryListing consists of array of files in the root directory
		// specified above
		// for each file in directoryListing, the filepath is retrieved and
		// added to myList
		// for each file in directoryListing, the filenames are added to
		// myListText
		File dir = new File(path);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				myList.add(child.getAbsolutePath());
				myListText.add(child.getAbsolutePath().substring(29));
			}
		} else {
		}

		// Both arrayList contents are reversed in order so that when viewed in
		// grid
		// the most recent saved items are displayed first
		Collections.reverse(myList);
		Collections.reverse(myListText);
	}
}
