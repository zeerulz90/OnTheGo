//OnTheGo prepared by Ehsanul Hakim Khan and Osama Tariq

package com.EhsanOsama.onthego;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//MainActivity is the main activity which opens when application is played
public class MainActivity extends ActionBarActivity {

	// This is the request code for startActivityForResult and onActivityResult
	private static final int REQUEST_VIDEO_CAPTURE = 1;

	@Override
	// Called when entering MainActivity activity
	// activity_main is the xml file which provides the User Interface for this
	// activity
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// This method is invoked on returning to this activity from the Video
	// Camera application
	// Here if video is saved, resultCode==RESULT_OK.
	// requestCode==REQUEST_VIDEO_CAPTURE is a way of recognizing intent
	// connections
	// This method creates a folder in specified directory and stores the
	// recorded video there
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// The two passing stages (consider it like a key-lock) to allow method
		// to save recorded video
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_VIDEO_CAPTURE) {

				try {
					// Sends an error log message to know the source of the
					// problem
					Log.e("videopath", "videopath");

					// fileDescriptor gets video data content from Intent
					// fileInputStream is created using fileDescriptor
					// fileInputStream would be used to transfer data into bytes
					// array

					AssetFileDescriptor fileDescriptor = getContentResolver()
							.openAssetFileDescriptor(data.getData(), "r");
					FileInputStream fileInputStream = fileDescriptor
							.createInputStream();
					// Creates the File directory to save specific video content
					File fileDir = new File("/storage/emulated/0/DCIM/OnTheGo");

					// Creates file in specified directory if not already
					// created
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}

					// This file contains the video content
					File file;

					// Creates a timestamp at that specific moment when file is
					// to be created
					// Unique timestamps are retrieved at every second, which
					// removes any possibility
					// for one file to overwrite another
					String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
							.format(new Date());

					// Name of the video file (.mp4 file)
					String mCurrentVideoName = "video_" + timeStamp + ".mp4";
					// file with full directory name
					file = new File(fileDir, mCurrentVideoName);
					// FileOutputStream is used to write video content data into
					// file
					FileOutputStream fileOutputStream = new FileOutputStream(
							file);

					// buf is an array which can hold 1024 characters
					byte[] buffer = new byte[1024];
					int indicator;
					// buffer (array of bytes) reads from fileInputStream
					// indicator>0 is true as long as fileInputStream contains
					// data to
					// be read
					// Returns -1 when fileInputStream is done transferring all
					// data to buffer
					// As long as indicator>0, buffer will read data from
					// fileInputStream
					// and write to fileOutputStream
					while ((indicator = fileInputStream.read(buffer)) > 0) {
						fileOutputStream.write(buffer, 0, indicator);
					}
					// Closes both input and output streams when content
					// transfer is done
					fileInputStream.close();
					fileOutputStream.close();
					// Enters if any exception occurs and prints stack trace
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	}

	// This method is invoked when "Write Note" button is pressed
	// Creates an intent to move from MainActivity to NoteActivity context
	public void goToNoteActivity(View view) {
		Intent i = new Intent(MainActivity.this, NoteActivity.class);
		startActivity(i);
	}

	// This method is invoked when "Record Audio" button is pressed
	// Creates an intent to move from MainActivity to AudioControls context
	public void goToAudioControls(View view) {
		Intent i = new Intent(MainActivity.this, AudioControls.class);
		startActivity(i);
	}

	// This method is invoked when "Record Video" button is pressed
	// "MediaStore.ACTION_VIDEO_CAPTURE" calls the built-in video camera
	// application
	// resolveActivity method is called to make sure there exists at least one
	// Activity
	// component that can handle this intent. If there is, external activity
	// opens
	// startActivityForResult starts the external video record activity
	public void goToVideoActivity(View view) {
		Intent recordVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		if (recordVideoIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(recordVideoIntent, REQUEST_VIDEO_CAPTURE);
		}
	}

	// Exits the application upon pressing the "Exit OnTheGo" button
	// Kills all android processes
	public void exitApp(View view) {
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	// This method is invoked upon pressing the "Show History" button
	// Creates an intent to transfer from MainActivity to GridDisplay context
	public void showGrid(View view) throws Exception {
		ImpVariables.populateMyList();
		Intent i = new Intent(MainActivity.this, GridDisplay.class);
		startActivity(i);
	}

	// Created by default upon building the project
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Created by default upon building the project
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
