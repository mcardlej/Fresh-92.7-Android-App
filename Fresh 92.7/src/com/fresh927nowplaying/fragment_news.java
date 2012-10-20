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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;

public class fragment_news extends SherlockListFragment {

	private static String url = "http://fresh927.com.au/category/news/feed/json";
	public Context c;
	public ListView lv;
	public View mainview;
	public View main;
	public List<Post> posts;
 
	// JSON Node names
	private static final String TAG_title = "title";
	private static final String TAG_when = "date";
	private static final String TAG_excerpt = "excerpt";
	private static final String TAG_url = "permalink";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		c = getActivity().getApplicationContext();
		
		GetPosts task = new GetPosts();
		task.execute();

		mainview = inflater.inflate(R.layout.fragment_news, container, false);

		return mainview;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Post p = this.posts.get(position);

		//String artist = "";
		//if (s.artists.size() > 0) {
		//			artist = s.artists.get(0);
		//}
		
		//Handle Click
		String url = p.url;
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);

	}
	
	
	public class GetPosts extends AsyncTask<Void, Void, List<Post>> {

		@Override
		protected void onPreExecute() {
			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(
					true);
		}

		@Override
		protected void onPostExecute(List<Post> result) {

			posts = result;

			List<Map<String, String>> contactList = new ArrayList<Map<String, String>>();

			for (Post s : posts) {
				HashMap<String, String> map = new HashMap<String, String>();

				String sansHtml = Html.fromHtml(s.excerpt.replaceAll("\\&.*?\\;", "").trim()).toString();
				
				
				// adding each child node to HashMap key => value
				map.put(TAG_title, capitalizeString(s.title.replaceAll("\\&.*?\\;", "")));
				map.put(TAG_when, s.when);
				map.put(TAG_excerpt, sansHtml );
				map.put(TAG_url, s.url);
				
				contactList.add(map);
			}

			ListAdapter adapter = new SimpleAdapter(c, contactList,
					R.layout.row, new String[] { TAG_title, TAG_excerpt },
					new int[] { R.id.title, R.id.details });
	

			setListAdapter(adapter);
			

			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(
					false);

		}

		@Override
		protected List<Post> doInBackground(Void... params) {
			
			JSONParser jParser = new JSONParser();
			JSONArray json = jParser.getJSONFromUrl(url);

			List<Post> posts = new ArrayList<Post>();

			try {

				for (int i = 0; i < json.length(); i++) {
					JSONObject c = json.getJSONObject(i);

					String t = c.getString(TAG_title);
					String w = c.getString(TAG_when);
					String e = c.getString(TAG_excerpt);
					String u = c.getString(TAG_url);

					e.replaceAll("\\&.*?\\;", "");
					
					Post s = new Post(t, w, e, u);
 
					posts.add(s);
				}
			} catch (JSONException e) {
			}

			return posts;
		}
	}
	
	public static String capitalizeString(String string) {
		  char[] chars = string.toLowerCase().toCharArray();
		  boolean found = false;
		  for (int i = 0; i < chars.length; i++) {
		    if (!found && Character.isLetter(chars[i])) {
		      chars[i] = Character.toUpperCase(chars[i]);
		      found = true;
		    } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
		      found = false;
		    }
		  }
		  return String.valueOf(chars);
		}
	
}