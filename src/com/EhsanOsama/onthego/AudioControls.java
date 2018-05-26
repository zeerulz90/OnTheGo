//OnTheGo prepared by Ehsanul Hakim Khan and Osama Tariq

package com.EhsanOsama.onthego;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AudioControls extends Activity {

	private MediaPlayer mediaPlayer;
	private MediaRecorder mediaRecorder;
	private String currentAudioName;
	private String currentAudioPath;
	private Button startRecord;
	private Button stopRecord;
	private Button startPlayback;
	private Button stopPlayback;
	private Button returnButton;

	// Enters onCreate on activity initialization
	// Connects the audio.xml layout to this activity

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio);

		// Provides reference to all five buttons in user interface
		startRecord = (Button) findViewById(R.id.recordA);
		stopRecord = (Button) findViewById(R.id.stopA);
		startPlayback = (Button) findViewById(R.id.playA);
		stopPlayback = (Button) findViewById(R.id.stopPlayback);
		returnButton = (Button) findViewById(R.id.buttonGoBack);

		// Only "Start Record" and "Return To Main Menu" buttons are enabled at
		// start
		stopRecord.setEnabled(false);
		startPlayback.setEnabled(false);
		stopPlayback.setEnabled(false);

	}

	// This method is invoked upon pressing any of the buttons related to audio
	// record and playback
	public void performAction(View view) {
		switch (view.getId()) {
		case R.id.recordA:
			try {
				startRecord();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case R.id.stopA:
			try {
				stopRecord();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case R.id.playA:
			try {
				startPlayback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case R.id.stopPlayback:
			try {
				stopPlayback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	// Returns to MainActivity context
	public void returnToMenu(View view) {
		Intent i = new Intent(AudioControls.this, MainActivity.class);
		startActivity(i);
	}

	// This method is invoked upon pressing the "Record Audio" button
	private void startRecord() throws Exception {
		// Releases mediaRecorded if not null
		releaseMediaRecorder();

		// Creates file directory where all audio contents will be stored
		File fileRoot = new File("/storage/emulated/0/DCIM/OnTheGo");

		if (!fileRoot.exists()) {
			fileRoot.mkdirs();
		}

		// "file" contains root directory and filename for audio content
		File file;

		// Creates unique timestamp for audio filename
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());

		// filename for audio content
		currentAudioName = "audio_" + timeStamp + ".3gpp";

		// file is created in root directory "fileRoot" with filename
		file = new File(fileRoot, currentAudioName);
		currentAudioPath = file.getAbsolutePath();

		// MediaRecorder object is created,
		mediaRecorder = new MediaRecorder();
		// audio source is set to microphone
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// to be recorded in .3gpp format
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		// with default encoder
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		// setting the file path where the audio content is to be stored
		mediaRecorder.setOutputFile(currentAudioPath);
		// prepares the mediaRecorder before starting
		mediaRecorder.prepare();
		// starts recording
		mediaRecorder.start();

		// "Stop Record" button is enabled
		stopRecord.setEnabled(true);
		startRecord.setEnabled(false);
		startPlayback.setEnabled(false);
		stopPlayback.setEnabled(false);
		returnButton.setEnabled(false);
	}

	// Releases media recorder
	private void releaseMediaRecorder() {
		// TODO Auto-generated method stub
		if (mediaRecorder != null) {
			mediaRecorder.release();
		}

	}

	// Method invoked when "Stop Record" button is pressed
	private void stopRecord() {
		if (mediaRecorder != null) {
			mediaRecorder.stop();
			mediaRecorder.release();

			stopRecord.setEnabled(false);
			startRecord.setEnabled(true);
			startPlayback.setEnabled(true);
			stopPlayback.setEnabled(false);
			returnButton.setEnabled(true);

		}
	}

	// Method invoked when "Start Playback" button is pressed
	private void startPlayback() throws Exception {
		releaseMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(currentAudioPath);
		mediaPlayer.prepare();
		mediaPlayer.start();

		startRecord.setEnabled(true);
		stopRecord.setEnabled(false);
		startPlayback.setEnabled(true);
		stopPlayback.setEnabled(true);
		returnButton.setEnabled(false);

	}

	// Releases media player
	private void releaseMediaPlayer() {
		// TODO Auto-generated method stub
		if (mediaPlayer != null) {

			mediaPlayer.release();

		}
	}

	// //Method invoked when "Stop Playback" button is pressed
	private void stopPlayback() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();

			startRecord.setEnabled(true);
			stopRecord.setEnabled(false);
			startPlayback.setEnabled(true);
			stopPlayback.setEnabled(false);
			returnButton.setEnabled(true);
		}
	}

}
