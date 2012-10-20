package com.fresh927nowplaying;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class fragment_listen_live extends SherlockListFragment {

	GoogleAnalyticsTracker tracker;
	
	private static String url = "http://fresh927.com.au/apps/fresh.php";
	public Context c;
	public ListView lv;
	public View mainview;
	public View main;
	public List<Song> songs;

	// JSON Node names
	private static final String TAG_artist = "a";
	private static final String TAG_when = "w";
	private static final String TAG_title = "t";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Clear existing data

		c = getActivity().getApplicationContext();
		GetAndListTracks task = new GetAndListTracks();
		task.execute();

		mainview = inflater.inflate(R.layout.fragment_listen_live, container, false);

		return mainview;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Song s = this.songs.get(position);

		String artist = "";
		
		if (s.artists.size() == 0) {
			artist = "";
		}
		else {		
			artist = StringUtils.join(s.artists, " / ");			
		}
		 
        tracker = GoogleAnalyticsTracker.getInstance(); 
        tracker.startNewSession("UA-17067215-4", c);
		tracker.trackPageView("/Music/"+s.title+" - "+artist);
		tracker.dispatch();
		tracker.stopSession();
		
		share("Fresh 92.7 Android App","Title: "+s.title+"\nArtist: "+artist+"\nTo listen live head to http://www.fresh927.com.au via @fresh927");
	}

	public class GetAndListTracks extends AsyncTask<Void, Void, List<Song>> {

		@Override
		protected void onPreExecute() {
			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(
					true);
		}

		@Override
		protected void onPostExecute(List<Song> result) {

			songs = result;

			Log.i("MyActivity", "Context");

			Log.i("MyActivity", "Find listView1");

			List<Map<String, String>> contactList = new ArrayList<Map<String, String>>();

			for (Song s : songs) {
				HashMap<String, String> map = new HashMap<String, String>();

				// adding each child node to HashMap key => value
				map.put(TAG_title, s.title);
				map.put(TAG_when, s.when);
				
				if (s.artists.size() == 0) {
					map.put(TAG_artist, String.format("%s", s.getFormattedDuration()));
				}
				else {				
					map.put(TAG_artist, String.format("%s - %s", StringUtils.join(s.artists, " / "), s.getFormattedDuration()));
				}
				// map.put(TAG_EMAIL, email);
				// map.put(TAG_PHONE_MOBILE, mobile);

				contactList.add(map);
			}

			ListAdapter adapter = new SimpleAdapter(c, contactList,
					R.layout.row, new String[] { TAG_title, TAG_artist },
					new int[] { R.id.title, R.id.details });
			Log.i("MyActivity", "Create List Adapter");

			setListAdapter(adapter);
			Log.i("MyActivity", "Set List Adapter");

			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(
					false);

		}

		@Override
		protected List<Song> doInBackground(Void... params) {
			// Creating JSON Parser instance
			JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			JSONArray json = jParser.getJSONFromUrl(url);

			List<Song> songs = new ArrayList<Song>();

			try {
				// Getting Array of Contacts

				// looping through All Contacts
				for (int i = 0; i < json.length(); i++) {
					JSONObject c = json.getJSONObject(i);

					String t = c.getString(TAG_title);
					String w = c.getString(TAG_when);
					//String a = c.getString(TAG_artist);
					
					JSONArray artists = c.getJSONArray(TAG_artist);

					Song s = new Song(t, w);
					
					int numArtists = artists.length();
					for (int j = 0; j < numArtists; j++) {
						s.addArtist(artists.getString(j));
					}

					songs.add(s);
				}
			} catch (JSONException e) {
			}

			return songs;
		}
	}

	public void share(String subject, String text) {
		final Intent intent = new Intent(Intent.ACTION_SEND);

		intent.setType("text/plain");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		intent.putExtra("sms_body", text);

		startActivity(Intent.createChooser(intent, "Share Track Details"));
	}
}