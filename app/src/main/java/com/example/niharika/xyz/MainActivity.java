package com.example.niharika.xyz;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LandMarkListFragment.ListSelectionListener {
    public final String DANGEROUS="edu.uic.cs478.Application22Permission";
    public final int MY_PERMISSIONS_REQUEST=1;
    private FragmentManager mFragmentManager;
    private FrameLayout monumentListFrameLayout, monumentsWebFrameLayout;
    private LandMarkListFragment monumentListFragment;
    private MonumentWebFragment monumentWebFragment;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "MainActivity";
    private static final String TAG_RETAINED_LIST_FRAGMENT = "RetainedListFragment";
    private static final String TAG_RETAINED_WEB_FRAGMENT = "RetainedWebFragment";
    int config;

    public static String[] monuments_list, monument_urls_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        monuments_list = getResources().getStringArray(R.array.Titles);
        monument_urls_list = getResources().getStringArray(R.array.Urls);

        setContentView(R.layout.activity_main);

        // Setting the Action Bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        config = getResources().getConfiguration().orientation;
        monumentListFrameLayout = (FrameLayout) findViewById(R.id.landmark_fragment_container);
        monumentsWebFrameLayout = (FrameLayout) findViewById(R.id.web_fragment_container);
        mFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;

        if(mFragmentManager.findFragmentByTag(TAG_RETAINED_LIST_FRAGMENT)==null){
            monumentListFragment= new LandMarkListFragment();
            fragmentTransaction=mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.landmark_fragment_container, monumentListFragment,TAG_RETAINED_LIST_FRAGMENT);
            fragmentTransaction.commit();
        }


        if(mFragmentManager.findFragmentByTag(TAG_RETAINED_WEB_FRAGMENT)==null){
            monumentWebFragment= new MonumentWebFragment();}
        else{
            monumentWebFragment=(MonumentWebFragment)mFragmentManager.findFragmentByTag(TAG_RETAINED_WEB_FRAGMENT);
        }
        setLayout();

        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });


    }


    public void setLayout() {

        if(!monumentWebFragment.isAdded()) {
            monumentListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            monumentsWebFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
            Log.i(TAG, "Web Fragment Not Added");
        }
        else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                monumentListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
                monumentsWebFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            }
            else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                monumentListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                monumentsWebFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
                Log.i(TAG, "Web Fragment Added");
            }
        }
    }

    public void onListSelection(int position) {
        if(config == Configuration.ORIENTATION_PORTRAIT) {
            if(!monumentWebFragment.isAdded()) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.web_fragment_container, monumentWebFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                mFragmentManager.executePendingTransactions();
            }

        } else if(config == Configuration.ORIENTATION_LANDSCAPE) {
            if(!monumentWebFragment.isAdded()) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

                fragmentTransaction.add(R.id.web_fragment_container, monumentWebFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                mFragmentManager.executePendingTransactions();
            }
        }

        if(monumentWebFragment.getShownIndex() != position) {
            Log.i(TAG, "In Get Loaded Index  : ");
            monumentWebFragment.showQuoteAtIndex(position);
        }

    }




    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menu, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app1:
                //exit app1
                finishAndRemoveTask();
                return true;

            case R.id.app2:

                Toast.makeText(this, "Opening Gallery Application", Toast.LENGTH_LONG).show();
                askPermission(DANGEROUS, MY_PERMISSIONS_REQUEST);
                return true;

            default:
                Toast.makeText(this, "Default Option", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }


    public void askPermission(String permission, int requestCode){
        if (ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_GRANTED) {

             ActivityCompat.requestPermissions(this,new String[]{permission}, requestCode);

            } else {
                Toast.makeText(this, "permission is already granted", Toast.LENGTH_LONG).show();
            }
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "permission  granted", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.setAction("com.example.niharika.xyz.show1");// having a unique name for our apps broadcast.
            // The recievers that have this broadcast in their intent filter can only respond
            sendBroadcast(intent, null);


        }else {
            Log.i(TAG,"Permission  not granted");
            Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();


        }
    }

}