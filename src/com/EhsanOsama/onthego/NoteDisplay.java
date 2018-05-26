//OnTheGo prepared by Ehsanul Hakim Khan and Osama Tariq

package com.EhsanOsama.onthego;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NoteDisplay extends Activity {

	private String path;
	private TextView text;
	private Button returnButton;
	private StringBuffer message;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);

		text = (TextView) findViewById(R.id.textDisplay);
		returnButton = (Button) findViewById(R.id.btnReturn);

		// Contains filepath for text file
		path = getIntent().getExtras().getString("path");

		// Initially sets text field to empty
		text.setText("");

		// Saves text content that is to be displayed
		message = readFromFile(path);
		// Displayed in text field
		text.setText(message);
		// Textview listener is set to null to make text field uneditable
		text.setKeyListener(null);

	}

	// Method that returns a StringBuffer object that contains the text of a
	// file
	// in a filepath "path"
	private StringBuffer readFromFile(String path) {
		// Initialised to null
		StringBuffer fileContent = null;

		try {
			// file is created using the filepath
			File file = new File(path);
			// fileInputStream is used to read data from file
			FileInputStream fIn = new FileInputStream(file);
			fileContent = new StringBuffer("");

			// indicator lets us know when buffer is done reading data from fIn
			int indicator;
			// buffer of 1024 bytes is created to read in from fIn and append to
			// fileContent
			byte[] buffer = new byte[1024];

			// As long as indicator>0, data remains to be read from fIn to
			// buffer
			// Once indicator==-1 or >0, all data from fIn has been read by
			// buffer
			// Each time buffer reads from fIn, it transfers that data to
			// fileContent
			// byte array is converted to string before appending to fileContent
			while ((indicator = fIn.read(buffer)) > 0) {
				fileContent.append(new String(buffer, 0, indicator));
			}
		} catch (IOException e) {
			// Error log message upon exception
			Log.e("Exception", "File write failed: " + e.toString());
		}
		return fileContent;
	}

	// Mehtod invoked upon pressing the return button
	// Creates intent to switch context to GridDisplay activity
	public void goBack(View view) {
		Intent i = new Intent(NoteDisplay.this, GridDisplay.class);
		startActivity(i);
	}

}
