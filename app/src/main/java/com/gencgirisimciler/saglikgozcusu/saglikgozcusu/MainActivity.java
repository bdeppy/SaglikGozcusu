package com.gencgirisimciler.saglikgozcusu.saglikgozcusu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.gencgirisimciler.saglikgozcusu.saglikgozcusu.NavigationDrawerClasses.NavDrawerItem;
import com.gencgirisimciler.saglikgozcusu.saglikgozcusu.NavigationDrawerClasses.NavDrawerListAdapter;
import com.gencgirisimciler.saglikgozcusu.saglikgozcusu.android.ResultsActivity;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int TAKE_PICTURE = 0;
    private final int SELECT_FILE = 1;
    private String resultUrl = "result.txt";

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }
    public void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        View header = getLayoutInflater().inflate(R.layout.header, null);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.dot);

        mTitle = mDrawerTitle = getTitle();

        // navMenuTitles=  EnumLanguage.names();
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items_small);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));

        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.addHeaderView(header);
        mDrawerList.setAdapter(adapter);

        ////////////////      NavDrawerListAdapter.tikliMiArray[]
        // enabling action bar app icon and behaving it as toggle button

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(R.string.app_name);
                // değiştirmesin diye yapılmıstır
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.app_name);
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    public void captureImageFromSdCard( View view ) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, SELECT_FILE);
    }

    public static final int MEDIA_TYPE_IMAGE = 1;

    private static Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ABBYY Cloud OCR SDK Demo App");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        // Create a media file name
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "image.jpg" );

        return mediaFile;
    }

    public void captureImageFromCamera( View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri fileUri = getOutputMediaFileUri(); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        String imageFilePath = null;

        switch (requestCode) {
            case TAKE_PICTURE:
                imageFilePath = getOutputMediaFileUri().getPath();
                break;
            case SELECT_FILE: {
                Uri imageUri = data.getData();

                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cur = managedQuery(imageUri, projection, null, null, null);
                cur.moveToFirst();
                imageFilePath = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            break;
        }

        //Remove output file
        deleteFile(resultUrl);

        Intent results = new Intent( this, ResultsActivity.class);
        results.putExtra("IMAGE_PATH", imageFilePath);
        results.putExtra("RESULT_PATH", resultUrl);
        startActivity(results);
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position,view);
        }
    }

    private void displayView(int position,View view) {
        // update the main content by replacing fragments
        switch (position) {
            case 0:
//                fragment = new HomeFragment();
                break;


            default:
                CheckBox cb = (CheckBox)view.findViewById(R.id.check);
                if(NavDrawerListAdapter.tikliMiArray[position-1]) {
                    cb.setChecked(false);
                    NavDrawerListAdapter.tikliMiArray[position-1]=false;
                }
                else
                {
                    cb.setChecked(true);
                    NavDrawerListAdapter.tikliMiArray[position-1]=true;
                }
                break;
        }

//        mDrawerList.setItemChecked(position, true);
//        mDrawerList.setSelection(position);
//        setTitle(navMenuTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
//        if (fragment != null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_container, fragment).commit();
//
//            // update selected item and title, then close the drawer
//
//        } else {
//            // error in creating fragment
//            Log.e("MainActivity", "Error in creating fragment");
//        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
///////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }
}
