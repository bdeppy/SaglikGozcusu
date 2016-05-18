package com.gencgirisimciler.saglikgozcusu.saglikgozcusu.NavigationDrawerClasses;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.gencgirisimciler.saglikgozcusu.saglikgozcusu.R;

import java.util.ArrayList;

public class NavDrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	public static boolean [] tikliMiArray ;


	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
			this.context = context;
			this.navDrawerItems = navDrawerItems;

		tikliMiArray = new boolean[getCount()];
		for (int i = 0; i < tikliMiArray.length; i++) {
			tikliMiArray[i] = true;
		}

		}

	public boolean [] getTikliMiArray () {
		return tikliMiArray;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
		final CheckBox toggle = (CheckBox) convertView.findViewById(R.id.check);
         
        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(navDrawerItems.get(position).getTitle());

		toggle.setChecked(true);

		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked) {
					tikliMiArray[position] = true;
					toggle.setButtonDrawable(android.R.drawable.checkbox_on_background);
				}
				else {
					tikliMiArray[position] = false;
					toggle.setButtonDrawable(android.R.drawable.checkbox_off_background);
				}

			}
		});


        return convertView;
	}

}
