package cn.qihoo.calender.sqlite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AudioHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "Memo.db";
	private static final String TBL_NAME = "AudioTbl";
	private static final String CREATE_TBL = " create table "
			+ " AudioTbl(_id integer primary key autoincrement,date date,url text, remind text) ";

	private SQLiteDatabase db;

	public AudioHelper(Context c) {
		super(c, DB_NAME, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		db.execSQL(CREATE_TBL);
	}

	public void insert(ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TBL_NAME, null, values);
		db.close();
	}

	public Cursor query() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.query(TBL_NAME, null, null, null, null, null, null);
		return c;
	}

	// 输入yyyy-MM
	// 找出同一个月有记录的
	public Cursor query4Month(String date) {

		String stardate = date + "-01";
		String enddate = date + "-31";

		SQLiteDatabase db = getWritableDatabase();
		String[] args = { stardate, enddate };
		Cursor c = db.query(TBL_NAME, null, "date>=? and date <=?", args, null,
				null, null);
		return c;

	}

	// 找出同一个天有记录的
	public Cursor query4Day(Date date) {
		SQLiteDatabase db = getWritableDatabase();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(date);

		String[] args = { datestr };
		Cursor c = db.query(TBL_NAME, null, "date=?", args, null, null, null);

		return c;
	}

	// 找出单个id的
	public Cursor query4Id(int id) {
		SQLiteDatabase db = getWritableDatabase();
		String[] args = { String.valueOf(id) };
		Cursor c = db.query(TBL_NAME, null, "_id=?", args, null, null, null);
		return c;
	}

	public void del(int id) {
		if (db == null)
			db = getWritableDatabase();
		String[] args = { String.valueOf(id) };
		Cursor cur = db.query(TBL_NAME, null, "_id=?", args, null, null, null);
		if (cur.moveToFirst() == false) {	
			// 为空的Cursor
			return;
		}
		int urlcolum = cur.getColumnIndex("url");
		String url = cur.getString(urlcolum);

		try {
			File file = new File(url);
			file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

		db.delete(TBL_NAME, "_id=?", new String[] { String.valueOf(id) });
	}

	public void close() {
		if (db != null)
			db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}