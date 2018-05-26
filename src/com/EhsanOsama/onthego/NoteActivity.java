//OnTheGo prepared by Ehsanul Hakim Khan and Osama Tariq

package com.EhsanOsama.onthego;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NoteActivity extends Activity {

	private TextView text;
	private Button saveButton;
	private String currentNoteName;
	private String currentNotePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes);

		text = (TextView) findViewById(R.id.text1);
		saveButton = (Button) findViewById(R.id.btnSave);
		saveButton.setEnabled(false);

		// Adds a listener to text field to enable/disable save button
		// If text field is empty, save button is disabled
		// Otherwise, save button is enabled
		text.addTextChangedListener(textWatcher);
	}

	// Method invoked upon pressing the save button
	public void saveNote(View view) {
		try {

			// Create the root of the file directory where text files are to be
			// saved
			File fileRoot = new File("/storage/emulated/0/DCIM/OnTheGo");

			// Creates a file directory if not already created
			if (!fileRoot.exists()) {
				fileRoot.mkdirs();
			}

			// This file will contain the filepath of the text file
			File file;

			// Creates unique timestamp for each file
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(new Date());

			// filename with timestamp
			currentNoteName = "text_" + timeStamp + ".txt";
			file = new File(fileRoot, currentNoteName);
			// filepath string variable
			currentNotePath = file.getAbsolutePath();

			// Retrieves text from text field and converts to string
			String noteContent = (String) text.getText().toString();

			// Text content is written to file
			writeToFile(file, noteContent);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Once saved, context is switched to MainActivity
		Intent i = new Intent(NoteActivity.this, MainActivity.class);
		startActivity(i);

	}

	// Method that writes "data" to a "file"
	private void writeToFile(File file, String data) {
		try {
			// FileOutputStream object is created to write "data" to "file"
			FileOutputStream fOut = new FileOutputStream(file);

			// A class for turning a character stream into a byte stream.
			// Data written to the target input stream is converted into bytes
			// OutputStreamWriter contains a buffer of bytes to be written to
			// fOut
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

			// Appends the sequence of characters in "data" and writes to file
			// using fOut
			myOutWriter.append(data);
			// Closes the writer and the output stream fOut
			myOutWriter.close();
			fOut.close();
		} catch (IOException e) {
			// Error log message if exception occurs
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	// Events for detecting text change in text field
	// For our project, all we do is disable save button if text field is empty
	// And enable save button if text field is not empty
	private TextWatcher textWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
			if (text.getText().toString().trim().length() > 0) {
				saveButton.setEnabled(true);
			} else {
				saveButton.setEnabled(false);
			}
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			if (text.getText().toString().trim().length() > 0) {
				saveButton.setEnabled(true);
			} else {
				saveButton.setEnabled(false);
			}
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (text.getText().toString().trim().length() > 0) {
				saveButton.setEnabled(true);
			} else {
				saveButton.setEnabled(false);
			}

		}
	};
}
