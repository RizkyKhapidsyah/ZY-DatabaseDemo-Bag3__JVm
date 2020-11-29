package com.wilis.database2;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


class AlmagHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="addressmanager.db";
	private static final int SCHEMA_VERSION=1;
	
	public AlmagHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE almag (_id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, alamat TEXT, jekel TEXT, hp TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// no-op, since will not be called until 2nd schema
		// version exists
	}

	public Cursor getAll() {
		return(getReadableDatabase().rawQuery("SELECT _id, nama, alamat, jekel, hp FROM almag ORDER BY nama", null));
	}
	
	public void insert(String nama, String alamat, String jekel, String hp) {
		ContentValues cv=new ContentValues();
					
		cv.put("nama", nama);
		cv.put("alamat", alamat);
		cv.put("jekel", jekel);
		cv.put("hp", hp);
		
		getWritableDatabase().insert("almag", "name", cv);
	}
	
	public String getNama(Cursor c) {
		return(c.getString(1));
	}
	
	public String getAlamat(Cursor c) {
		return(c.getString(2));
	}
	
	public String getJekel(Cursor c) {
		return(c.getString(3));
	}
	
	public String getHp(Cursor c) {
		return(c.getString(4));
	}
}
