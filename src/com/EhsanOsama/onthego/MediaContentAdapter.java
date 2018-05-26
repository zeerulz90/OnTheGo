//OnTheGo prepared by Ehsanul Hakim Khan and Osama Tariq

package com.EhsanOsama.onthego;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//This is a custom made adapter (MediaContentAdapter) for all
//items to be displayed in the grid
public class MediaContentAdapter extends BaseAdapter {
	private Context context;
	private Bitmap bitmap;

	public MediaContentAdapter(Context c) {
		context = c;
	}

	public int getCount() {
		return ImpVariables.myList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	// Forms an ImageView for all the items referenced by the Adapter
	// Returns a View corresponding to the data at the specified position.

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {

		// View object created
		View grid;

		// Retrieve a layoutInflater for inflating layout resources in this
		// context
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Note: View must not be left null.
		// If null, new view is created in the GridDisplay context
		// If not, view is converted to display correct data sets
		if (convertView == null) {
			grid = new View(context);
		} else {
			grid = (View) convertView;
		}

		// If a video file is created (.mp4)
		if (ImpVariables.myList.get(position).contains(".mp4")) {

			// Create a micro video thumbnail for a video (0==micro size)
			bitmap = ThumbnailUtils.createVideoThumbnail(
					ImpVariables.myList.get(position), 0);
		}

		// grid_single.xml layout includes an imageView and a textView
		// grid_single gives the layout for each item to be displayed in
		// gridView

		grid = layoutInflater.inflate(R.layout.singlegrid, null);
		// Reference to textView and imageView linked to singlegrid.xml layout
		TextView textView = (TextView) grid.findViewById(R.id.grid_text);
		ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);

		// Item name is set to filename for the specified media content
		// Filename is retrieved from myListText
		textView.setText(ImpVariables.myListText.get(position));

		if (ImpVariables.myList.get(position).contains(".mp4")) {
			// If item is a video file, then imageView shows the video thumbnail
			imageView.setImageBitmap(bitmap);
		} else if (ImpVariables.myList.get(position).contains(".3gpp")) {
			// if item is an audio file, then imageView shows an audio icon
			imageView.setImageResource(R.drawable.audio);
		} else if (ImpVariables.myList.get(position).contains(".txt")) {
			// if item is a text file, then imageView shows a text icon
			imageView.setImageResource(R.drawable.text);
		}

		return grid;
	}
}
