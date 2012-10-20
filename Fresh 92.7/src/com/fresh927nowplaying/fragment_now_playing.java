package com.fresh927nowplaying;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;


public class fragment_now_playing extends SherlockFragment implements OnClickListener,  MediaPlayerControl {

	public Button buttonPlayPause;
	public EditText string;
	public ImageView splash;
	public Animation anim;
	public Context c;
	
	public TextView title;
	public TextView artist;
	
	// Track Info
	private static String url = "http://fresh927.com.au/apps/fresh.php";
	public ListView lv;
	public View mainview;
	public View main;
	public List<Song> songs;

	// JSON Node names
	private static final String TAG_artist = "a";
	private static final String TAG_when = "w";
	private static final String TAG_title = "t";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   		
    	View mainview = inflater.inflate(R.layout.fragment_now_playing, container, false);

		buttonPlayPause = (Button) mainview.findViewById(R.id.ButtonTestPlayPause);
		title = (TextView) mainview.findViewById(R.id.title_nowplaying);
		artist = (TextView) mainview.findViewById(R.id.artist_nowplaying);
	
			
		buttonPlayPause.setOnClickListener(this);
		
		String mUrl = this.getString(R.string.testsong_20_sec);

		MusicService.setSong(mUrl, "Fresh 92.7 - Webstream...", null);

		c = getActivity().getApplicationContext();
		GetAndListTracks task = new GetAndListTracks();
		task.execute();
		
		if (isPlaying()) {	
			//splash.startAnimation(anim);
			buttonPlayPause.setText("Stop");
		}
		
        return mainview;
    }


	public void onClick(View v) {
		
    	RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	anim.setInterpolator(new LinearInterpolator());
    	anim.setRepeatCount(Animation.INFINITE);
    	anim.setDuration(3300);
		splash = (ImageView) getView().findViewById(R.id.imageView1);
		
		if(v.getId() == R.id.ButtonTestPlayPause){

			if (!isPlaying()) {			
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						getActivity().getApplicationContext().startService(new Intent("PLAY"));
					}
					
				}).start();
				
				buttonPlayPause.setText("Stop");
				splash.startAnimation(anim);

			} else {			
				pause();	
				buttonPlayPause.setText("Listen Live");
				splash.clearAnimation();
			}
			   
		}
	}
	
@Override
	public void start() {
		if (MusicService.getInstance() != null) {
			MusicService.getInstance().startMusic();
		}
	}
	
@Override
	public void pause() {
		if (MusicService.getInstance() != null) {
			MusicService.getInstance().pauseMusic();
			getActivity().getApplicationContext().stopService(new Intent("PLAY"));
		}
	}
	
@Override
	public boolean isPlaying() {
		if (MusicService.getInstance() != null) {
			return MusicService.getInstance().isPlaying();
		}
		return false;
	}


@Override
public boolean canPause() {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean canSeekBackward() {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean canSeekForward() {
	// TODO Auto-generated method stub
	return false;
}


@Override
public int getBufferPercentage() {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public int getCurrentPosition() {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public int getDuration() {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public void seekTo(int arg0) {
	// TODO Auto-generated method stub
	
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
		
		Song s = songs.get(0);
		
		String artist1 = "";
		
		
		if (s.artists.size() == 0) {
			artist1 = "";
		}
		else {		
			artist1 = StringUtils.join(s.artists, " / ");			
		}
		

		title.setText(s.title);
		artist.setText(artist1);
		
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
			for (int i = 0; i < 1 ; i++) {
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


}