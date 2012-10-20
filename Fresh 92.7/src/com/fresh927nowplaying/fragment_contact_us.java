package com.fresh927nowplaying;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class fragment_contact_us extends SherlockFragment {

	public View mainview;
	public String versionInfo;
	public Context c;
	public Button fbbutton;
	public Button twbutton;
	public Button ytbutton;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	// Inflate the layout for this fragment
        mainview = inflater.inflate(R.layout.fragment_contact_us, container, false);
        c = getActivity().getApplicationContext();
        

        // GET TRACKING INFORMATION
        PackageInfo pInfo;
		try {
			pInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e1) {
			
			pInfo = null;
		}
        
		// APP VERSION INFO
        String versionInfo = pInfo.versionName;
                
        TextView text = (TextView) mainview.findViewById(R.id.version);
        text.setText("App Version: "+versionInfo);
    		
        fbbutton = (Button) mainview.findViewById(R.id.facebook);
        fbbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent browserIntent = 
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/fresh927"));
       		 			startActivity(browserIntent);
                    }
        });
        
        twbutton = (Button) mainview.findViewById(R.id.twitter);
        twbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent browserIntent = 
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://mobile.twitter.com/fresh927"));
       		 			startActivity(browserIntent);
                    }
        });
        
        ytbutton = (Button) mainview.findViewById(R.id.youtube);
        ytbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent browserIntent = 
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.youtube.com/user/fresh927tv"));
       		 			startActivity(browserIntent);
                    }
        });

        
        return mainview;
        
        }
    
   
}