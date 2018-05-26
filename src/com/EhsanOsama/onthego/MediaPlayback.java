//OnTheGo prepared by Ehsanul Hakim Khan and Osama Tariq

package com.EhsanOsama.onthego;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.VideoView;

//This activity is called upon playing back video from history
//Sets the video.xml layout for this activity
public class MediaPlayback extends Activity {
	private String path;
	private MediaController mediaController;
	private VideoView videoView;
	private DisplayMetrics displayMetrics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);

		// displayMetrics is used to get dimensions of display device being used
		// to view app
		displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		// Gets the path for the video to be played from intent data
		path = getIntent().getExtras().getString("path");

		// References videoView and sets the video path for video to be played
		// in videoView widget
		videoView = (VideoView) findViewById(R.id.videoView1);
		videoView.setVideoPath(path);

		// overrides hide method from mediacontroller class for mediaController
		// to appear at all times
		// prevent it from hiding
		// also overrides dispatchKeyEvent method to allow activity to end
		// properly upon pressing back key
		mediaController = new MediaController(this) {
			@Override
			public void hide() {
			}

			// for 'back' key action
			@Override
			public boolean dispatchKeyEvent(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					Activity a = (Activity) getContext();
					a.finish();
				}
				return true;
			}
		};

		// mediaController deals with stopping, rewinding and forwarding of
		// video
		// mediaController is included with videoView
		// focus set on videoView
		mediaController.setAnchorView(videoView);
		mediaController.setMediaPlayer(videoView);
		videoView.setMediaController(mediaController);
		videoView.requestFocus();

		// This event is triggered when the media file is loaded and ready to go
		// Observations: height and width of display device is taken
		// to adjust the videoView layout and set it to fullscreen
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				int height = displayMetrics.heightPixels;
				int width = displayMetrics.widthPixels;
				int left = videoView.getLeft();
				int top = videoView.getTop();
				int right = left + (width);
				int bottom = top + (height);
				videoView.layout(left, top, right, bottom);
				videoView.requestFocus();
				videoView.start();
				mediaController.show(900000000);
			}
		});

		// method is invoked when the videoView is done playing the video
		// event is triggered when video is done playing in videoView
		// closes the activity upon video completion
		videoView
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mediaPlayer) {
						finish();
					}
				});

	}
}