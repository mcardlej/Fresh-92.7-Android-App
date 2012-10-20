package com.fresh927nowplaying;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener  {

	GoogleAnalyticsTracker tracker;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
    	
        Log.i("MyActivity", "ONCREATE");
 
        // GET TRACKING INFORMATION
        PackageInfo pInfo;
		try {
			pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e1) {
			
			pInfo = null;
		}
        
		// APP VERSION INFO
        String versionInfo = pInfo.versionName;
        
        //GoogleAnalyticsTracker tracker;
        tracker = GoogleAnalyticsTracker.getInstance(); 
        tracker.startNewSession("UA-17067215-4", this);
		tracker.trackPageView("/Main");
		tracker.trackEvent("Models",Build.MODEL,"recorded", 1);
		tracker.trackEvent("Versions",versionInfo,"recorded", 1);
		tracker.trackEvent("Android Version",Build.VERSION.RELEASE,"recorded", 1);
		tracker.dispatch();
		tracker.stopSession();
        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
                
        setContentView(R.layout.tab_navigation);
        Log.i("MyActivity", "TAB_NAVIGATION");

        
    	getSupportActionBar();
    	getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Log.i("MyActivity", "NAVIGATION_MODE_TABS");
        
        ActionBar.Tab ListenLiveTab  = getSupportActionBar().newTab().setText(this.getString(R.string.listen_live));
        ActionBar.Tab NowPlayingTab	 = getSupportActionBar().newTab().setText(this.getString(R.string.now_playing));
        ActionBar.Tab NewsTab		 = getSupportActionBar().newTab().setText(this.getString(R.string.news));
        ActionBar.Tab ContactUsTab 	 = getSupportActionBar().newTab().setText(this.getString(R.string.contact_us));
        
        ListenLiveTab.setTabListener(this);
        NowPlayingTab.setTabListener(this);
        NewsTab.setTabListener(this);
        ContactUsTab.setTabListener(this);
        
        getSupportActionBar().addTab(ListenLiveTab);
        getSupportActionBar().addTab(NowPlayingTab);
        getSupportActionBar().addTab(NewsTab);
        getSupportActionBar().addTab(ContactUsTab);
        
       
        if (savedInstanceState != null) {
        	getSupportActionBar().setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        
    }
        
        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }

        /**
         * Open Fragment based on selected Tab
         */
        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // choose current fragment based on tab position
            Fragment fragment = null;
            switch (tab.getPosition()) {
            case 0:
                fragment = new fragment_now_playing();
                break;
            case 1:
                fragment = new fragment_listen_live();
                break;
            case 2:
                fragment = new fragment_news();
                break;
            default:
                fragment = new fragment_contact_us();
                break;
            }
            
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, fragment);
            fragmentTransaction.commit();
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        }


}
    
    

