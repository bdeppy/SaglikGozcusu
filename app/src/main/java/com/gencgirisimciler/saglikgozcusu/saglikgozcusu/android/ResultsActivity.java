package com.gencgirisimciler.saglikgozcusu.saglikgozcusu.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarSearchHandler;
import com.blunderer.materialdesignlibrary.listeners.OnSearchListener;
import com.gencgirisimciler.saglikgozcusu.saglikgozcusu.R;
import com.gencgirisimciler.saglikgozcusu.saglikgozcusu.utils.GeneralClasses;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ResultsActivity extends com.blunderer.materialdesignlibrary.activities.Activity {

	String outputPath;
	TextView tv;


	@Override
	protected boolean enableActionBarShadow() {
		return false;
	}

	@Override
	protected ActionBarHandler getActionBarHandler() {
		return new ActionBarSearchHandler(this, new OnSearchListener() {

			@Override
			public void onSearched(String text) {
				Toast.makeText(getApplicationContext(),
						"Searching \"" + text + "\"", Toast.LENGTH_SHORT).show();
			}

		});
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_results);

//		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//		setSupportActionBar(toolbar);
//
		GeneralClasses.StatusBar statusBar = new GeneralClasses.StatusBar(this);
		statusBar.setStatusBarColor(findViewById(R.id.statusBarBackground), getResources().getColor(R.color.colorPrimaryDark));

		tv = (TextView)findViewById(R.id.sonucTextView);
//		setContentView(tv);
		
		String imageUrl = "unknown";
		
		Bundle extras = getIntent().getExtras();
		if( extras != null) {
			imageUrl = extras.getString("IMAGE_PATH" );
			outputPath = extras.getString( "RESULT_PATH" );
		}
		
		// Starting recognition process
		new AsyncProcessTask(this).execute(imageUrl, outputPath);
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_results;
	}

	public void updateResults(Boolean success) {
		if (!success)
			return;
		try {
			StringBuffer contents = new StringBuffer();

			FileInputStream fis = openFileInput(outputPath);
			try {
				Reader reader = new InputStreamReader(fis, "UTF-8");
				BufferedReader bufReader = new BufferedReader(reader);
				String text = null;
				while ((text = bufReader.readLine()) != null) {
					contents.append(text).append(System.getProperty("line.separator"));
				}
			} finally {
				fis.close();
			}

			displayMessage(contents.toString());
		} catch (Exception e) {
			displayMessage("Error: " + e.getMessage());
		}
	}
	
	public void displayMessage( String text )
	{
		tv.post( new MessagePoster( text ) );
	}
	



	class MessagePoster implements Runnable {
		public MessagePoster( String message )
		{
			_message = message;
		}

		public void run() {
			tv.append( _message + "\n" );
//			setContentView( tv );
		}

		private final String _message;
	}
}
