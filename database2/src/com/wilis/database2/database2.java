package com.wilis.database2;

import android.app.TabActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class database2 extends TabActivity {
	Cursor model=null;
	AlmagAdapter adapter=null;
	EditText nama=null;
	EditText alamat=null;
	EditText hp=null;
	RadioGroup jekel=null;
	AlmagHelper helper=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		helper=new AlmagHelper(this);
		
		nama=(EditText)findViewById(R.id.nama);
		alamat=(EditText)findViewById(R.id.alamat);
		hp=(EditText)findViewById(R.id.hp);
		jekel=(RadioGroup)findViewById(R.id.jekel);
		
		Button save=(Button)findViewById(R.id.save);
		
		save.setOnClickListener(onSave);
		
		ListView list=(ListView)findViewById(R.id.almag);
		
		model=helper.getAll();
		startManagingCursor(model);
		adapter=new AlmagAdapter(model);
		list.setAdapter(adapter);
		
		TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
		
		spec.setContent(R.id.almag);
		spec.setIndicator("List", getResources().getDrawable(R.drawable.list));
		getTabHost().addTab(spec);
		
		spec=getTabHost().newTabSpec("tag2");
		spec.setContent(R.id.details);
		spec.setIndicator("Details", getResources().getDrawable(R.drawable.alamat));
		
		getTabHost().addTab(spec);
		
		getTabHost().setCurrentTab(0);
		
		list.setOnItemClickListener(onListClick);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		helper.close();
	}
	
	private View.OnClickListener onSave=new View.OnClickListener() {
		public void onClick(View v) {
			String type=null;
			
			switch (jekel.getCheckedRadioButtonId()) {
				case R.id.pria:
					type="Pria";
					break;
				case R.id.perempuan:
					type="Perempuan";
					break;
			}
			
			helper.insert(nama.getText().toString(),alamat.getText().toString(),type,hp.getText().toString());
			model.requery();
		}
	};
	
	private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			model.moveToPosition(position);
			nama.setText(helper.getNama(model));
			alamat.setText(helper.getAlamat(model));
			hp.setText(helper.getHp(model));
			
			if (helper.getJekel(model).equals("Pria")) {
				jekel.check(R.id.pria);
			}
			else if (helper.getJekel(model).equals("Perempuan")) {
				jekel.check(R.id.perempuan);
			}
			
			
			getTabHost().setCurrentTab(1);
		}
	};
	
	class AlmagAdapter extends CursorAdapter {
		AlmagAdapter(Cursor c) {
			super(database2.this, c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			AlmagHolder holder=(AlmagHolder)row.getTag();
			
			holder.populateFrom(c, helper);
		}
		
		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row, parent, false);
			AlmagHolder holder=new AlmagHolder(row);
			
			row.setTag(holder);
			
			return(row);
		}
	}
	
	static class AlmagHolder {
		private TextView nama=null;
		private TextView alamat=null;
		private ImageView icon=null;
		private View row=null;
		
		AlmagHolder(View row) {
			this.row=row;
			
			nama=(TextView)row.findViewById(R.id.title);
			alamat=(TextView)row.findViewById(R.id.alamat);
			icon=(ImageView)row.findViewById(R.id.icon);
		}
		
		void populateFrom(Cursor c, AlmagHelper helper) {
			nama.setText(helper.getNama(c));
			alamat.setText(helper.getAlamat(c));
	
			if (helper.getJekel(c).equals("Pria")) {
				icon.setImageResource(R.drawable.pria);
			}
			else if (helper.getJekel(c).equals("Perempuan")) {
				icon.setImageResource(R.drawable.perempuan);
			}
			
		}
	}
}
