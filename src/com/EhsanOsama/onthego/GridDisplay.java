//OnTheGo prepared by Ehsanul Hakim Khan and Osama Tariq

package com.EhsanOsama.onthego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

//Activity is called to show notes history files in gridView display
//grid.xml layout is connected to this activity
public class GridDisplay extends Activity {

	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);

		// References gridView
		gridView = (GridView) findViewById(R.id.gridShow);
		// MediaContentAdapter object is created
		MediaContentAdapter mediaContentAdapter = new MediaContentAdapter(this);

		// Arraylist is populated with existing video,audio,text files in order
		// to display in gridView
		try {
			ImpVariables.populateMyList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Refreshes data set
		mediaContentAdapter.notifyDataSetChanged();
		// View is rebuilt and redrawn
		gridView.invalidateViews();
		// This method sets a custom made adapter (MediaContentAdapter) for all
		// items to be displayed in the grid
		gridView.setAdapter(mediaContentAdapter);

		// Event is triggered on item selection in gridView
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// If a text file is selected, context is switched to
				// NoteDisplay activity
				// Also, text data is attached as extension to intent
				if (ImpVariables.myList.get(position).contains(".txt")) {
					Intent i = new Intent(getApplicationContext(),
							NoteDisplay.class);
					i.putExtra("path", ImpVariables.myList.get(position));
					startActivity(i);

					// If either video or audio file is selected, context is
					// switched to DisplayVideo activity
					// Once again, text data is attached as extension to intent
				} else {

					// Send File Path to Image Display Activity
					Intent intent = new Intent(getApplicationContext(),
							MediaPlayback.class);
					intent.putExtra("path", ImpVariables.myList.get(position));
					startActivity(intent);

				}
			}
		});
	}

	// This method is invoked upon pressing "Return to Main Menu" button
	// Context is switched to MainActivity activity
	public void returnToMain(View view) {
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i);
	}

}
